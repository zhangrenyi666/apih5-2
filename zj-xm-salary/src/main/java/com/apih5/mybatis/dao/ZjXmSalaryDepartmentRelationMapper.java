package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartmentRelation;

public interface ZjXmSalaryDepartmentRelationMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmSalaryDepartmentRelation record);

    int insertSelective(ZjXmSalaryDepartmentRelation record);

    ZjXmSalaryDepartmentRelation selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmSalaryDepartmentRelation record);

    int updateByPrimaryKey(ZjXmSalaryDepartmentRelation record);

    List<ZjXmSalaryDepartmentRelation> selectByZjXmSalaryDepartmentRelationList(ZjXmSalaryDepartmentRelation record);

    int batchDeleteUpdateZjXmSalaryDepartmentRelation(List<ZjXmSalaryDepartmentRelation> recordList, ZjXmSalaryDepartmentRelation record);

}

