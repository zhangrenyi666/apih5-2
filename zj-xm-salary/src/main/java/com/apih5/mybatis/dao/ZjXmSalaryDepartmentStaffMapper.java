package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartmentStaff;

public interface ZjXmSalaryDepartmentStaffMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmSalaryDepartmentStaff record);

    int insertSelective(ZjXmSalaryDepartmentStaff record);

    ZjXmSalaryDepartmentStaff selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmSalaryDepartmentStaff record);

    int updateByPrimaryKey(ZjXmSalaryDepartmentStaff record);

    List<ZjXmSalaryDepartmentStaff> selectByZjXmSalaryDepartmentStaffList(ZjXmSalaryDepartmentStaff record);

    int batchDeleteUpdateZjXmSalaryDepartmentStaff(List<ZjXmSalaryDepartmentStaff> recordList, ZjXmSalaryDepartmentStaff record);

}

