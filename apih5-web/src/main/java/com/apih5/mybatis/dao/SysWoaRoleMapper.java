package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysWoaRole;

public interface SysWoaRoleMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysWoaRole record);

    int insertSelective(SysWoaRole record);

    SysWoaRole selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysWoaRole record);

    int updateByPrimaryKey(SysWoaRole record);

    List<SysWoaRole> selectBySysWoaRoleList(SysWoaRole record);

    int batchDeleteUpdateSysWoaRole(List<SysWoaRole> recordList);

    List<SysWoaRole> selectBySysWoaRoleListInUserKeys(SysWoaRole record);
}

