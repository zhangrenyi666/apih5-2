package com.apih5.mybatis.dao;

import org.springframework.stereotype.Repository;

import com.apih5.mybatis.pojo.SysLock;

/**
 * 系统锁mapper
 */
@Repository
public interface SysLockMapper {

    /**
     * 得到系统锁数据
     * @return
     */
    SysLock getSysLock();

    /**
     * 更新系统锁表数据
     * @param sysLock
     */
    void updateSysLock(SysLock sysLock);
}