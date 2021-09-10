package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.SysSelectDepartmentUser;

public interface SysSelectDepartmentUserMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysSelectDepartmentUser record);

    int insertSelective(SysSelectDepartmentUser record);

    SysSelectDepartmentUser selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysSelectDepartmentUser record);

    int updateByPrimaryKey(SysSelectDepartmentUser record);

    List<SysSelectDepartmentUser> selectBySysSelectDepartmentUserList(SysSelectDepartmentUser record);

    int batchDeleteUpdateSysSelectDepartmentUser(List<SysSelectDepartmentUser> recordList);

}

