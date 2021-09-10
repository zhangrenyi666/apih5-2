package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysDepartmentPermission;

public interface SysDepartmentPermissionMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysDepartmentPermission record);

    int insertSelective(SysDepartmentPermission record);

    SysDepartmentPermission selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysDepartmentPermission record);

    int updateByPrimaryKey(SysDepartmentPermission record);

    List<SysDepartmentPermission> selectBySysDepartmentPermissionList(SysDepartmentPermission record);

    int batchDeleteUpdateSysDepartmentPermission(List<SysDepartmentPermission> recordList);

}

