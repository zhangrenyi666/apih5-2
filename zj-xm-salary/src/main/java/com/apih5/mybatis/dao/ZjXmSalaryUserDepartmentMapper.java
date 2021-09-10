package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryUserDepartment;

public interface ZjXmSalaryUserDepartmentMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmSalaryUserDepartment record);

    int insertSelective(ZjXmSalaryUserDepartment record);

    ZjXmSalaryUserDepartment selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmSalaryUserDepartment record);

    int updateByPrimaryKey(ZjXmSalaryUserDepartment record);

    List<ZjXmSalaryUserDepartment> selectByZjXmSalaryUserDepartmentList(ZjXmSalaryUserDepartment record);

    int batchDeleteUpdateZjXmSalaryUserDepartment(List<ZjXmSalaryUserDepartment> recordList, ZjXmSalaryUserDepartment record);

}

