package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.BaseSelectDepartmentUser;

public interface BaseSelectDepartmentUserMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseSelectDepartmentUser record);

    int insertSelective(BaseSelectDepartmentUser record);

    BaseSelectDepartmentUser selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseSelectDepartmentUser record);

    int updateByPrimaryKey(BaseSelectDepartmentUser record);

    List<BaseSelectDepartmentUser> selectByBaseSelectDepartmentUserList(BaseSelectDepartmentUser record);

    int batchDeleteUpdateBaseSelectDepartmentUser(List<BaseSelectDepartmentUser> recordList);

}

