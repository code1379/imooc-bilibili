package com.imooc.bilibili.service;

import com.imooc.bilibili.dao.UserFollowingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowingService {

    @Autowired
    private UserFollowingDao userFollowingDao;
}
