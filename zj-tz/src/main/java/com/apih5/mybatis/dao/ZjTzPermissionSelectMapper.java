package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPermissionSelect;

public interface ZjTzPermissionSelectMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPermissionSelect record);

    int insertSelective(ZjTzPermissionSelect record);

    ZjTzPermissionSelect selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPermissionSelect record);

    int updateByPrimaryKey(ZjTzPermissionSelect record);

    List<ZjTzPermissionSelect> selectByZjTzPermissionSelectList(ZjTzPermissionSelect record);

    int batchDeleteUpdateZjTzPermissionSelect(List<ZjTzPermissionSelect> recordList, ZjTzPermissionSelect record);

}

