package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzEmployeeQualification;

public interface ZjTzEmployeeQualificationMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzEmployeeQualification record);

    int insertSelective(ZjTzEmployeeQualification record);

    ZjTzEmployeeQualification selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzEmployeeQualification record);

    int updateByPrimaryKey(ZjTzEmployeeQualification record);

    List<ZjTzEmployeeQualification> selectByZjTzEmployeeQualificationList(ZjTzEmployeeQualification record);

    int batchDeleteUpdateZjTzEmployeeQualification(List<ZjTzEmployeeQualification> recordList, ZjTzEmployeeQualification record);

}

