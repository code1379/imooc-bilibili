package com.imooc.bilibili.service;

import com.imooc.bilibili.dao.UserDao;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.domain.constant.UserConstant;
import com.imooc.bilibili.domain.exception.ConditionException;
import com.imooc.bilibili.service.util.MD5Util;
import com.imooc.bilibili.service.util.RSAUtil;
import com.imooc.bilibili.service.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void addUser(User user) {
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }
        User dbUser = this.getUserByPhone(phone);
        if(dbUser != null) {
            throw new ConditionException("该手机号已经注册");
        }

        Date now = new Date();
        // 根据当前时间生成盐值
        String salt = String.valueOf(now.getTime());
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch(Exception e) {
            throw new ConditionException("密码解密失败！");
        }

        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);

        // 生成之后，会默认调 user.setId(id) 所以后面可以获取到
        userDao.addUser(user);

        // 创建完用户之后得到用户的 id，然后创建用户的基本信息表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICKNAME);
        userInfo.setGender(UserConstant.GENDER_UNKNOWN);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setCreateTime(now);

        userDao.addUserInfo(userInfo);
    }

    // 判断是否已经存在此手机号
    public User getUserByPhone(String phone) {
       return userDao.getUserByPhone(phone);
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    public String login(User user) throws Exception{
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)) {
            throw new ConditionException("手机号不能为空");
        }
        User dbUser = this.getUserByPhone(phone);
        if(dbUser == null) {
            throw new ConditionException("当前用户不存在");
        }

        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch(Exception e) {
            throw new ConditionException("密码解密失败！");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if(!md5Password.equals(dbUser.getPassword())) {
            throw new ConditionException("密码错误！");
        }

        String token = TokenUtil.generateToken(dbUser.getId());
        return token;
    }

    public User getUserInfo(Long userId) {
        User user =  userDao.getUserById(userId);
        UserInfo userInfo = userDao.getUserInfoByUserId(userId);
        // 如果我们想返回 User 实体类，想把 user 和 userInfo 进行一个整合
        // 两种方法 1. 放到将两个放到 Map 里面 2. 在 User 类里面添加冗余字段 userInfo
        user.setUserInfo(userInfo);
        return user;
    }

    public void updateUsers(User user) throws  Exception {
        Long id = user.getId();
        User dbUser = userDao.getUserById(id);
        if(dbUser == null){
            throw new ConditionException("用户不存在");
        }
        // 更改密码
        if(!StringUtils.isNullOrEmpty(user.getPassword())){
            String rawPassword = RSAUtil.decrypt(user.getPassword());
            String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Password);
        }
        user.setUpdateTime(new Date());
        userDao.updateUsers(user);
    }

    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userDao.updateUserInfos(userInfo);
    }

    public List<UserInfo> getUserInfoByUserIds(Set<Long> followingIdSet) {
        return userDao.getUserInfoByUserIds(followingIdSet);
    }
}
