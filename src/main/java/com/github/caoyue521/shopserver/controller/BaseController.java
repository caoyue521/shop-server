package com.github.caoyue521.shopserver.controller;

import com.github.caoyue521.shopserver.entity.User;
import com.github.caoyue521.shopserver.util.UserUtil;

public interface BaseController {
    default User getUser(){
       return UserUtil.get();
    }
}
