package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryOfficeSalary;

public interface ZjXmSalaryOfficeSalaryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmSalaryOfficeSalary record);

    int insertSelective(ZjXmSalaryOfficeSalary record);

    ZjXmSalaryOfficeSalary selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmSalaryOfficeSalary record);

    int updateByPrimaryKey(ZjXmSalaryOfficeSalary record);

    List<ZjXmSalaryOfficeSalary> selectByZjXmSalaryOfficeSalaryList(ZjXmSalaryOfficeSalary record);

    int batchDeleteUpdateZjXmSalaryOfficeSalary(List<ZjXmSalaryOfficeSalary> recordList, ZjXmSalaryOfficeSalary record);

}

