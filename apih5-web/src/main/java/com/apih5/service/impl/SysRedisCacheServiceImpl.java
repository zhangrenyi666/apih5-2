package com.apih5.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.cache.RedisCacheHandler;
import com.apih5.framework.cache.RedisCacheService;
import com.apih5.framework.entity.TokenEntity;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("redisCacheService")
public class SysRedisCacheServiceImpl implements RedisCacheService {
    @Autowired
    private RedisCacheHandler redisCacheHandler;
    
    /**
     * 向redis中插入token数据
     * @param tokenId
     * @param token
     */
    @Override
    public void putUserTokenRedis(String userCacheKey, TokenEntity token) {
        JSONObject jsonObject = new JSONObject(token);
        // 方式过期再正常白天被情况，采用时间为3天后的凌晨2点过期
        redisCacheHandler.setCacheWithTimeout("userToken:" + userCacheKey, jsonObject.toString(), 3L, TimeUnit.DAYS);
    }

    /**
     * 从redis中获取token
     * @param tokenId
     */
    @Override
    public TokenEntity getUserTokenRedis(String userCacheKey) {
        Object object = redisCacheHandler.getCache("userToken:" + userCacheKey);
        if(object == null) {
            return null;
        }
        String token = (String)object;
        if(StrUtil.isEmpty(token)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(token);
        TokenEntity tokenEntity = jsonObject.toBean(TokenEntity.class);
        return tokenEntity;
    }

    /**
     * 从redis中删除token
     * @param tokenId
     */
    @Override
    public void removeUserTokenRedis(String userCacheKey) {
        redisCacheHandler.clearCacheByKey("userToken:" + userCacheKey);
    }
 
    /**
     * 向redis的set中写入验证码
     * @param key
     * @param value
     */
    @Override
    public void putCaptchaCodeRedis(String key, String value) {
        redisCacheHandler.setCacheWithTimeout("captcha:" + key, value, 60L, TimeUnit.SECONDS);
    }
    
    /**
     * 判断验证码是否存在
     * @param key
     */
    @Override
    public String getCaptchaCodeRedis(String key) {
        return (String)redisCacheHandler.getCache("captcha:" + key);
    }

    /**
     * 从redis中删除验证码
     * @param key
     */
    @Override
    public void removeCaptchaCodeRedis(String key) {
        redisCacheHandler.clearCacheByKey("captcha:" + key);
    }
    
    /**
     * 向redis的set中写入登陆错误数量
     * @param key
     * @param value
     */
    @Override
    public void putLoginLockRedis(String key, int value) {
        redisCacheHandler.setCacheWithTimeout("loginLock:" + key, String.valueOf(value), 3L, TimeUnit.DAYS);
    }
    
    /**
     * 获取登陆错误数量
     * @param key
     */
    @Override
    public int getLoginLockRedis(String key) {
        String value = (String)redisCacheHandler.getCache("loginLock:" + key);
        if(StrUtil.isEmpty(value)) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    /**
     * 从redis中删除登陆错误数量
     * @param key
     */
    @Override
    public void removeLoginLockRedis(String key) {
        redisCacheHandler.clearCacheByKey("loginLock:" + key);
    }
    
//
//    /**
//     * 将用户的菜单字符串写到redis中
//     */
//    @Override
//    public void writeUserMenuInfoToRedis(Map<String, String> param) {
//        String userId = param.get("userId");
//        String menuInfo = param.get("userInfo");
//        redisCacheHandler.setCacheWithTimeout("menu:" + userId, menuInfo, 3L, TimeUnit.HOURS);
//    }
//
//    /**
//     * 从redis中获取用户的菜单字符串
//     * @param userId
//     */
//    @Override
//    public String getUserMenuInfo(String userId) {
//        return (String)redisCacheHandler.getCache("menu:" + userId);
//    }
//
//    /**
//     * 从redis中删除用户菜单字符串
//     * @param userId
//     */
//    @Override
//    public void deleteUserMenuInfo(String userId) {
//        redisCacheHandler.clearCacheByKey("menu:" + userId);
//    }
//
//    /**
//     * 根据用户id上锁
//     */
//    @Override
//    public void lockByAdmin() {
//        redisCacheHandler.setCacheWithTimeout("lock", "lock", 5L, TimeUnit.MINUTES);
//    }
//
//    /**
//     * 判断用户id是否上锁
//     */
//    @Override
//    public String getLockByAdmin() {
//        return (String)redisCacheHandler.getCache("lock");
//    }
//
//    /**
//     * 删除用户id的锁
//     */
//    @Override
//    public void deleteLockByAdmin() {
//        redisCacheHandler.clearCacheByKey("lock");
//    }

}
