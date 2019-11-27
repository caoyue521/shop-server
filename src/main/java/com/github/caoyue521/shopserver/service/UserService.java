package com.github.caoyue521.shopserver.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.caoyue521.shopserver.dao.UserDao;
import com.github.caoyue521.shopserver.entity.User;
import com.github.caoyue521.shopserver.model.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Resource
    UserDao userDao;
    public void save(User user){
        userDao.save(user);
    }
    public User getUser(String uid){
        return userDao.getByUid(uid);
    }
    public Boolean isExistUserName(String uid){
        return userDao.getByUid(uid)!=null;
    }
    public ApiResult login(String uid,String pwd){
        User user = userDao.getByUid(uid);
        if(user==null){
           ApiResult apiResult =  ApiResult.badRequest("用户名或者密码错误");
           return apiResult;
         }
        String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if(user.getPassword().equals(md5Pwd)){
            ApiResult apiResult =  ApiResult.ok("登录成功");
            String token = UUID.randomUUID().toString().replace("-","");
            redisTemplate.opsForValue().set(token,user.getUid(),1, TimeUnit.DAYS);
            apiResult.setData(token);
            return apiResult;
        }else {
            ApiResult apiResult =  ApiResult.badRequest("用户名或者密码错误");
            return apiResult;
        }
    }
    public ApiResult register(User user){
        User userOld = userDao.getByUid(user.getUid());
        if(userOld!=null){
            ApiResult apiResult =  ApiResult.badRequest("该用户名已存在");
            return apiResult;
        }
        String pwd = user.getPassword();
        String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        user.setPassword(md5Pwd);
        userDao.save(user);
        ApiResult apiResult = ApiResult.ok("注册成功");
        apiResult.setData(user);
        return apiResult;
    }
}
