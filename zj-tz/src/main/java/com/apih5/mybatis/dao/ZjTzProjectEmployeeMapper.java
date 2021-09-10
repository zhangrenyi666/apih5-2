package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProjectEmployee;

public interface ZjTzProjectEmployeeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProjectEmployee record);

    int insertSelective(ZjTzProjectEmployee record);

    ZjTzProjectEmployee selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProjectEmployee record);

    int updateByPrimaryKey(ZjTzProjectEmployee record);

    List<ZjTzProjectEmployee> selectByZjTzProjectEmployeeList(ZjTzProjectEmployee record);

    int batchDeleteUpdateZjTzProjectEmployee(List<ZjTzProjectEmployee> recordList, ZjTzProjectEmployee record);

    int checkZjTzProjectEmployee(List<ZjTzProjectEmployee> zjTzProjectEmployeeList, ZjTzProjectEmployee zjTzProjectEmployee);

    int recallZjTzProjectEmployee(List<ZjTzProjectEmployee> zjTzProjectEmployeeList, ZjTzProjectEmployee zjTzProjectEmployee);

    List<ZjTzProjectEmployee> selectByZjTzProjectEmployeeListMultilist(ZjTzProjectEmployee record);

    int updataPersonNumber(ZjTzProjectEmployee zjTzProjectEmployee);
}

