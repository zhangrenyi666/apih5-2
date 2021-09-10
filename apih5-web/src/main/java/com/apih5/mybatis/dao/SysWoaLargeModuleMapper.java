package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysWoaLargeModule;

public interface SysWoaLargeModuleMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysWoaLargeModule record);

    int insertSelective(SysWoaLargeModule record);

    SysWoaLargeModule selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysWoaLargeModule record);

    int updateByPrimaryKey(SysWoaLargeModule record);

    List<SysWoaLargeModule> selectBySysWoaLargeModuleList(SysWoaLargeModule record);

    int batchDeleteUpdateSysWoaLargeModule(List<SysWoaLargeModule> recordList);

}

