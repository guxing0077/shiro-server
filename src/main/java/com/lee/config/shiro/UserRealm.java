package com.lee.config.shiro;


import com.lee.entity.User;
import com.lee.mapper.MenuMapper;
import com.lee.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		User user = (User) SecurityUtils.getSubject();
		Set<String> perms = menuMapper.findPermsByRoleId(user.getRoleId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		//查询数据库用户信息
		User param = new User(username);
		User user = userMapper.selectOne(param);
		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		// 密码错误
		if (!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		return new SimpleAuthenticationInfo(user, password, getName());
	}
}
