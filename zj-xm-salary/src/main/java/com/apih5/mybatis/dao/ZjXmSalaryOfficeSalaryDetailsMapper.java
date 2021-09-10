package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryOfficeSalaryDetails;

public interface ZjXmSalaryOfficeSalaryDetailsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmSalaryOfficeSalaryDetails record);

    int insertSelective(ZjXmSalaryOfficeSalaryDetails record);

    ZjXmSalaryOfficeSalaryDetails selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmSalaryOfficeSalaryDetails record);

    int updateByPrimaryKey(ZjXmSalaryOfficeSalaryDetails record);

    List<ZjXmSalaryOfficeSalaryDetails> selectByZjXmSalaryOfficeSalaryDetailsList(ZjXmSalaryOfficeSalaryDetails record);

    int batchDeleteUpdateZjXmSalaryOfficeSalaryDetails(List<ZjXmSalaryOfficeSalaryDetails> recordList, ZjXmSalaryOfficeSalaryDetails record);

}

