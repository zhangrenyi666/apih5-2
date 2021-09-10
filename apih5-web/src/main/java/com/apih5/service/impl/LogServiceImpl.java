package com.apih5.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.mybatis.dao.LogMapper;
import com.apih5.mybatis.pojo.SysLog;
import com.apih5.service.LogService;;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void logAdd(SysLog sysLog) {
        logMapper.logAdd(sysLog);
    }

    @Override
    public List<SysLog> listAll(SysLog sysLog) {
        return logMapper.listAll(sysLog);
    }

    @Override
    public int getLogCount(SysLog sysLog) {
        return logMapper.getLogCount(sysLog);
    }

    @Override
    public void delete() {
        logMapper.delete();
    }
}
