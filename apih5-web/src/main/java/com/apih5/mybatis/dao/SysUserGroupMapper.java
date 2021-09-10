package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.SysUserGroup;

public interface SysUserGroupMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysUserGroup record);

    int insertSelective(SysUserGroup record);

    SysUserGroup selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysUserGroup record);

    int updateByPrimaryKey(SysUserGroup record);

    List<SysUserGroup> selectBySysUserGroupList(SysUserGroup record);

    int batchDeleteUpdateSysUserGroup(List<SysUserGroup> recordList, SysUserGroup record);

}

