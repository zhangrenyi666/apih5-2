package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzEmployeeProfessional;

public interface ZjTzEmployeeProfessionalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzEmployeeProfessional record);

    int insertSelective(ZjTzEmployeeProfessional record);

    ZjTzEmployeeProfessional selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzEmployeeProfessional record);

    int updateByPrimaryKey(ZjTzEmployeeProfessional record);

    List<ZjTzEmployeeProfessional> selectByZjTzEmployeeProfessionalList(ZjTzEmployeeProfessional record);

    int batchDeleteUpdateZjTzEmployeeProfessional(List<ZjTzEmployeeProfessional> recordList, ZjTzEmployeeProfessional record);

}

