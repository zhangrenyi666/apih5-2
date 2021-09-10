package com.apih5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.mybatis.dao.UserTokenMapper;
import com.apih5.mybatis.pojo.SysUserToken;
import com.apih5.service.UserTokenService;

@Service
@Transactional
public class UserTokenServiceImpl implements UserTokenService {

    @Autowired
   private UserTokenMapper userTokenMapper;

    @Override
    public void deleteByToken(String token) {
        userTokenMapper.deleteByToken(token);
    }

    @Override
    public SysUserToken getByToken(String token) {
        return userTokenMapper.getByToken(token);
    }
}
