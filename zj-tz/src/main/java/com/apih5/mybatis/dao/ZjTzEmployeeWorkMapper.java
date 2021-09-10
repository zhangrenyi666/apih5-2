package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzEmployeeWork;

public interface ZjTzEmployeeWorkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzEmployeeWork record);

    int insertSelective(ZjTzEmployeeWork record);

    ZjTzEmployeeWork selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzEmployeeWork record);

    int updateByPrimaryKey(ZjTzEmployeeWork record);

    List<ZjTzEmployeeWork> selectByZjTzEmployeeWorkList(ZjTzEmployeeWork record);

    int batchDeleteUpdateZjTzEmployeeWork(List<ZjTzEmployeeWork> recordList, ZjTzEmployeeWork record);

}

