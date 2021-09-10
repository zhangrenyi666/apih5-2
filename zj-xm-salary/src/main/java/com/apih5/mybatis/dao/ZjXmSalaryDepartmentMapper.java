package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartment;

public interface ZjXmSalaryDepartmentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmSalaryDepartment record);

    int insertSelective(ZjXmSalaryDepartment record);

    ZjXmSalaryDepartment selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmSalaryDepartment record);

    int updateByPrimaryKey(ZjXmSalaryDepartment record);

    List<ZjXmSalaryDepartment> selectByZjXmSalaryDepartmentList(ZjXmSalaryDepartment record);

    int batchDeleteUpdateZjXmSalaryDepartment(List<ZjXmSalaryDepartment> recordList, ZjXmSalaryDepartment record);

}

