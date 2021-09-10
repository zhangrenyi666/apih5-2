package com.apih5.service;

import java.util.List;

import com.apih5.mybatis.pojo.SysLog;
/**
 * Log Service
 */
public interface LogService {

    /**
     * 新增系统log
     * @param sysLog
     */
    void logAdd(SysLog sysLog);

    /**
     * 根据条件查找系统log
     * @param sysLog
     * @return
     */
    List<SysLog> listAll(SysLog sysLog);

    /**
     * 根据条件查找系统log数量
     * @param sysLog
     * @return
     */
    int getLogCount(SysLog sysLog);

    /**
     * 删除所有系统log
     */
    void delete();
}
