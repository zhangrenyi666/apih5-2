package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEmployeeNum;

public interface ZxEqEmployeeNumMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEmployeeNum record);

    int insertSelective(ZxEqEmployeeNum record);

    ZxEqEmployeeNum selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEmployeeNum record);

    int updateByPrimaryKey(ZxEqEmployeeNum record);

    List<ZxEqEmployeeNum> selectByZxEqEmployeeNumList(ZxEqEmployeeNum record);

    int batchDeleteUpdateZxEqEmployeeNum(List<ZxEqEmployeeNum> recordList, ZxEqEmployeeNum record);

}

