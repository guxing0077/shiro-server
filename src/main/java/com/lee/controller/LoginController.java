package com.lee.controller;

import com.lee.common.BaseController;
import com.lee.common.JsonRes;
import com.lee.req.login.LoginReq;
import com.lee.req.login.LogoutReq;
import com.lee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * @param loginReq 登录名和密码（密码为加密后的）
     * @return 如果登录成功，返回token
     */
    @PostMapping("login")
    public JsonRes<String> login(@RequestBody LoginReq loginReq){
        return success(userService.login(loginReq));
    }

    @PostMapping("logout")
    public JsonRes logout(@RequestBody LogoutReq logoutReq){
        userService.logout(logoutReq);
        return success();
    }
}