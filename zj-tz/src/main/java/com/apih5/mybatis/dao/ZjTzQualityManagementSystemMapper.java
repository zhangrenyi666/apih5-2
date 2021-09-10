package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzQualityManagementSystem;

public interface ZjTzQualityManagementSystemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzQualityManagementSystem record);

    int insertSelective(ZjTzQualityManagementSystem record);

    ZjTzQualityManagementSystem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzQualityManagementSystem record);

    int updateByPrimaryKey(ZjTzQualityManagementSystem record);

    List<ZjTzQualityManagementSystem> selectByZjTzQualityManagementSystemList(ZjTzQualityManagementSystem record);

    int batchDeleteUpdateZjTzQualityManagementSystem(List<ZjTzQualityManagementSystem> recordList, ZjTzQualityManagementSystem record);

}

