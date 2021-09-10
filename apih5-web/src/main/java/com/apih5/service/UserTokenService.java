package com.apih5.service;

import com.apih5.mybatis.pojo.SysUserToken;

/**
 * 用户Token Service
 */
public interface UserTokenService {

    /**
     * 根据token删除数据
     * @param token
     */
    void deleteByToken(String token);

    /**
     * 根据token获得数据
     * @param token
     * @return
     */
    SysUserToken getByToken(String token);
}
