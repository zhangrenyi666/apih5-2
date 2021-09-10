package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzEmployeePolitics;

public interface ZjTzEmployeePoliticsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzEmployeePolitics record);

    int insertSelective(ZjTzEmployeePolitics record);

    ZjTzEmployeePolitics selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzEmployeePolitics record);

    int updateByPrimaryKey(ZjTzEmployeePolitics record);

    List<ZjTzEmployeePolitics> selectByZjTzEmployeePoliticsList(ZjTzEmployeePolitics record);

    int batchDeleteUpdateZjTzEmployeePolitics(List<ZjTzEmployeePolitics> recordList, ZjTzEmployeePolitics record);

}

