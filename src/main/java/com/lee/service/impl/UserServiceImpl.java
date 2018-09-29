package com.lee.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.lee.entity.User;
import com.lee.exception.BusinessException;
import com.lee.mapper.MenuMapper;
import com.lee.mapper.UserMapper;
import com.lee.req.login.LoginReq;
import com.lee.req.login.LogoutReq;
import com.lee.service.UserService;
import com.lee.utils.JsonUtil;
import com.lee.utils.JwtHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public String login(LoginReq loginReq) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginReq.getUserName(), loginReq.getPassword());
        SecurityUtils.getSubject().login(usernamePasswordToken);

        //生成token，token中包含用户名和权限
//		String token = JwtHelper
//				.genToken(ImmutableMap.of("userName", user.getUserName(), "perms", JsonUtil.objectToJson(urls)));
        //缓存token
//        redisTemplate.opsForValue().set(loginReq.getUserName(), token);
//        redisTemplate.expire(loginReq.getUserName(), 1, TimeUnit.MINUTES);
        return "token todo";
    }

    @Override
    public void logout(LogoutReq logoutReq) {
        User user = userMapper.selectOne(new User(logoutReq.getUserName()));
        if(user==null){
            throw new BusinessException("用户名不存在");
        }
        SecurityUtils.getSubject().logout();
        //删除redis数据
//        redisTemplate.delete(logoutReq.getUserName());
    }
}