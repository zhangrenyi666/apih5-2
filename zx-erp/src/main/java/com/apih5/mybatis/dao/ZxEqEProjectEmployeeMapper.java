package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEProjectEmployee;

public interface ZxEqEProjectEmployeeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEProjectEmployee record);

    int insertSelective(ZxEqEProjectEmployee record);

    ZxEqEProjectEmployee selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEProjectEmployee record);

    int updateByPrimaryKey(ZxEqEProjectEmployee record);

    List<ZxEqEProjectEmployee> selectByZxEqEProjectEmployeeList(ZxEqEProjectEmployee record);

    int batchDeleteUpdateZxEqEProjectEmployee(List<ZxEqEProjectEmployee> recordList, ZxEqEProjectEmployee record);

}

