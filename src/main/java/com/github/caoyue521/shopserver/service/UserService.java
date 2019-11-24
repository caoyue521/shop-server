package com.github.caoyue521.shopserver.service;

import com.github.caoyue521.shopserver.dao.UserDao;
import com.github.caoyue521.shopserver.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    UserDao userDao;
    public void save(User user){
        userDao.save(user);
    }
}
