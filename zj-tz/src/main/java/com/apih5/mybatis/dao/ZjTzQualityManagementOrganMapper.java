package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzQualityManagementOrgan;

public interface ZjTzQualityManagementOrganMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzQualityManagementOrgan record);

    int insertSelective(ZjTzQualityManagementOrgan record);

    ZjTzQualityManagementOrgan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzQualityManagementOrgan record);

    int updateByPrimaryKey(ZjTzQualityManagementOrgan record);

    List<ZjTzQualityManagementOrgan> selectByZjTzQualityManagementOrganList(ZjTzQualityManagementOrgan record);

    int batchDeleteUpdateZjTzQualityManagementOrgan(List<ZjTzQualityManagementOrgan> recordList, ZjTzQualityManagementOrgan record);

}

