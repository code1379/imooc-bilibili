package com.imooc.bilibili.api;

import com.imooc.bilibili.dao.UserFollowingDao;
import com.imooc.bilibili.domain.FollowingGroup;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserFollowing;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.domain.constant.UserConstant;
import com.imooc.bilibili.domain.exception.ConditionException;
import com.imooc.bilibili.service.FollowingGroupService;
import com.imooc.bilibili.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFollowingApi {

    @Autowired
    private UserFollowingDao userFollowingDao;
    @Autowired
    private FollowingGroupService followingGroupService;
    @Autowired
    private UserService userService;


    @Transactional
    // 用户添加 关注（我关注别人）
    public void addUserFollowings(UserFollowing userFollowing) {
        Long groupId = userFollowing.getGroupId();
        if(groupId == null) {
            // 默认分组
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        }else {
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if(followingGroup == null) {
                throw new ConditionException("关注分组不存在！");
            }
        }

        // 判断关注的人存不存在
        Long followingId = userFollowing.getFollowingId();
        User user = userService.getUserById(followingId);
        if(user == null) {
            throw new ConditionException("关注的用户不存在！");
        }
        // delete
        // 用户的id， 和被关注人的 id
        // 如果之前关注过，换分组。没关注过就不需要删除。这样就减少了更新的逻辑
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), followingId);

        userFollowing.setCreateTime(new Date());
        userFollowingDao.addUserFollowing(userFollowing);
    }

    // 获取用户关注列表
    // 1. 根据 用户id（userId）， 获取到所有关注的人。
    // 2. 获取所有关注用户的基本信息
    // 3. 将所有人根据 关注分组（groupId） 进行分类
    public List<FollowingGroup> getUserFollowing(Long userId) {

        // 获取到了 [ { followingId, groupId}]
        List<UserFollowing> list = userFollowingDao.getUserFollowing(userId);
        // 抽取里面所有的 followingId 获取用户的基本信息
        Set<Long> followingIdSet = list.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if(followingIdSet.size() > 0) {
            userInfoList = userService.getUserInfoByUserIds(followingIdSet);
        }

        // 添加用户信息
        for(UserFollowing userFollowing: list) {
            for(UserInfo userInfo: userInfoList) {
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())) {
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }

        // 系统分组，和用户创建的自定义分组
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        // 分组1: 全部关注
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfoList);
        // 替他分组的
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        // groupList [ { id, userId, name, type }]
        // list = [ {id, groupId, userId, followingId , userInfo }]
        // 用户关注列表里面的 groupId 是和 分组表里面的 id
        // 所以这里要那 用户关注表之前设置的
        for(FollowingGroup followingGroup: groupList){
            List<UserInfo> infoList = new ArrayList<>();
            for(UserFollowing userFollowing: list) {
                if(userFollowing.getGroupId().equals(followingGroup.getId())) {
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            followingGroup.setFollowingUserInfoList(infoList);
            result.add(followingGroup);
        }

        return result;
    }
}
