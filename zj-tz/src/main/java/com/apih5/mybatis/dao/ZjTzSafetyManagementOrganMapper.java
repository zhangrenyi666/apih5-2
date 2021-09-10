package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSafetyManagementOrgan;

public interface ZjTzSafetyManagementOrganMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSafetyManagementOrgan record);

    int insertSelective(ZjTzSafetyManagementOrgan record);

    ZjTzSafetyManagementOrgan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSafetyManagementOrgan record);

    int updateByPrimaryKey(ZjTzSafetyManagementOrgan record);

    List<ZjTzSafetyManagementOrgan> selectByZjTzSafetyManagementOrganList(ZjTzSafetyManagementOrgan record);

    int batchDeleteUpdateZjTzSafetyManagementOrgan(List<ZjTzSafetyManagementOrgan> recordList, ZjTzSafetyManagementOrgan record);

}

