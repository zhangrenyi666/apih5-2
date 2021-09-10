package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqGlobalCode;

public interface ZxEqGlobalCodeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqGlobalCode record);

    int insertSelective(ZxEqGlobalCode record);

    ZxEqGlobalCode selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqGlobalCode record);

    int updateByPrimaryKey(ZxEqGlobalCode record);

    List<ZxEqGlobalCode> selectByZxEqGlobalCodeList(ZxEqGlobalCode record);

    int batchDeleteUpdateZxEqGlobalCode(List<ZxEqGlobalCode> recordList, ZxEqGlobalCode record);

}

