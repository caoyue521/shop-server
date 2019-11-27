package com.github.caoyue521.shopserver.controller;

import com.github.caoyue521.shopserver.entity.User;
import com.github.caoyue521.shopserver.model.ApiResult;
import com.github.caoyue521.shopserver.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户相关api")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController implements BaseController {
    @Autowired
    UserService userService;
    @ApiOperation("判断当前输入的用户名是否存在")
    @GetMapping("/checkUid")
    public ApiResult checkUid(@RequestParam("uid") String uid){
        boolean b = userService.isExistUserName(uid);
        ApiResult apiResult = ApiResult.ok("请求成功");
        apiResult.setData(b);
        return apiResult;
    }
    @ApiOperation("注册方法")
    @PostMapping("/register")
    public ApiResult register(@RequestBody User user){
        return userService.register(user) ;
    }
    @ApiOperation("登录方法")
    @PostMapping("/login")
    public ApiResult login(@RequestParam("uid")String uid,@RequestParam("password") String password){
        return userService.login(uid,password);
    }
}
