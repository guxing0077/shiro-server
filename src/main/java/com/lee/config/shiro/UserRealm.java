package com.lee.config.shiro;


import com.lee.entity.User;
import com.lee.mapper.MenuMapper;
import com.lee.mapper.UserMapper;
import com.lee.utils.JwtHelper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Integer roleId = (Integer) principals.getPrimaryPrincipal();
		Set<String> perms = menuMapper.findPermsByRoleId(roleId);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		String token = (String) authcToken.getPrincipal();
		//校验token是否存在
		Map<String, String> resultMap = JwtHelper.verifyToken(token);
		if(resultMap.isEmpty()){
			throw new AuthenticationException("missing or invalid token");
		}
		//获取名称
		String userName = resultMap.get("userName");
		if(StringUtils.isBlank(userName)){
			throw new AuthenticationException("missing or invalid token");
		}
		//校验redis的token
		String redisToken = redisTemplate.opsForValue().get(userName);
		if(StringUtils.isBlank(token)||!redisToken.equals(token)){
			throw new AuthenticationException("missing or invalid token");
		}
		String roleId = resultMap.get("roleId");
		return new SimpleAuthenticationInfo(Integer.valueOf(roleId), token, userName);
	}
}
