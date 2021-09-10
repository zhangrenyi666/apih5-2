package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPermission;

public interface ZjTzPermissionMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPermission record);

    int insertSelective(ZjTzPermission record);

    ZjTzPermission selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPermission record);

    int updateByPrimaryKey(ZjTzPermission record);

    List<ZjTzPermission> selectByZjTzPermissionList(ZjTzPermission record);

    int batchDeleteUpdateZjTzPermission(List<ZjTzPermission> recordList, ZjTzPermission record);

    List<ZjTzPermission> selectZjTzPermissionListByProject(ZjTzPermission record);
}

