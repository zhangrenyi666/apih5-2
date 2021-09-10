package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectBySysRoleList(SysRole record);

    int batchDeleteUpdateSysRole(List<SysRole> recordList);

}

