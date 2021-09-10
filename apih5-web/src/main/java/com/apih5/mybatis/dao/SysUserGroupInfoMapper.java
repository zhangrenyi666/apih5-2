package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.SysUserGroupInfo;

public interface SysUserGroupInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysUserGroupInfo record);

    int insertSelective(SysUserGroupInfo record);

    SysUserGroupInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysUserGroupInfo record);

    int updateByPrimaryKey(SysUserGroupInfo record);

    List<SysUserGroupInfo> selectBySysUserGroupInfoList(SysUserGroupInfo record);

    int batchDeleteUpdateSysUserGroupInfo(List<SysUserGroupInfo> recordList, SysUserGroupInfo record);

}

