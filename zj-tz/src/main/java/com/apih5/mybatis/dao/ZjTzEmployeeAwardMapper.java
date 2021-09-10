package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzEmployeeAward;

public interface ZjTzEmployeeAwardMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzEmployeeAward record);

    int insertSelective(ZjTzEmployeeAward record);

    ZjTzEmployeeAward selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzEmployeeAward record);

    int updateByPrimaryKey(ZjTzEmployeeAward record);

    List<ZjTzEmployeeAward> selectByZjTzEmployeeAwardList(ZjTzEmployeeAward record);

    int batchDeleteUpdateZjTzEmployeeAward(List<ZjTzEmployeeAward> recordList, ZjTzEmployeeAward record);

}

