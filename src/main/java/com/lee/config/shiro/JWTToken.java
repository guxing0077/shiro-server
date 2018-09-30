package com.lee.config.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class JWTToken implements AuthenticationToken {

    //token
    private String token;

    //用户uid
    private Integer id;

    //用户名
    private String userName;

    //角色id
    private Integer roleId;

    public JWTToken() {
    }

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
