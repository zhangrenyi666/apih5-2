package com.apih5.mybatis.pojo;

/**
 * 用户token entity
 */
public class SysUserToken {

    private String userKey; // 用户id

    private String token; // 用户token

    private String ip; // 登录ip地址

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
