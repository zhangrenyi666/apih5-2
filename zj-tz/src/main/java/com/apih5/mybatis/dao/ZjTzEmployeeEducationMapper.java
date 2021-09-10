package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzEmployeeEducation;

public interface ZjTzEmployeeEducationMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzEmployeeEducation record);

    int insertSelective(ZjTzEmployeeEducation record);

    ZjTzEmployeeEducation selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzEmployeeEducation record);

    int updateByPrimaryKey(ZjTzEmployeeEducation record);

    List<ZjTzEmployeeEducation> selectByZjTzEmployeeEducationList(ZjTzEmployeeEducation record);

    int batchDeleteUpdateZjTzEmployeeEducation(List<ZjTzEmployeeEducation> recordList, ZjTzEmployeeEducation record);

}

