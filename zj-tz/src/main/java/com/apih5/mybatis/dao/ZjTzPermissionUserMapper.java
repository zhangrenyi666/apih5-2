package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPermissionUser;

public interface ZjTzPermissionUserMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPermissionUser record);

    int insertSelective(ZjTzPermissionUser record);

    ZjTzPermissionUser selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPermissionUser record);

    int updateByPrimaryKey(ZjTzPermissionUser record);

    List<ZjTzPermissionUser> selectByZjTzPermissionUserList(ZjTzPermissionUser record);

    int batchDeleteUpdateZjTzPermissionUser(List<ZjTzPermissionUser> recordList, ZjTzPermissionUser record);

}

