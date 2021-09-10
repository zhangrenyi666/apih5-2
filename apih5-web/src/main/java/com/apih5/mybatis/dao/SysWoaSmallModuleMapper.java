package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysWoaSmallModule;

public interface SysWoaSmallModuleMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysWoaSmallModule record);

    int insertSelective(SysWoaSmallModule record);

    SysWoaSmallModule selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysWoaSmallModule record);

    int updateByPrimaryKey(SysWoaSmallModule record);

    List<SysWoaSmallModule> selectBySysWoaSmallModuleList(SysWoaSmallModule record);

    int batchDeleteUpdateSysWoaSmallModule(List<SysWoaSmallModule> recordList);

}

