package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtGlobalCode;

public interface ZxCtGlobalCodeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtGlobalCode record);

    int insertSelective(ZxCtGlobalCode record);

    ZxCtGlobalCode selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtGlobalCode record);

    int updateByPrimaryKey(ZxCtGlobalCode record);

    List<ZxCtGlobalCode> selectByZxCtGlobalCodeList(ZxCtGlobalCode record);

    int batchDeleteUpdateZxCtGlobalCode(List<ZxCtGlobalCode> recordList, ZxCtGlobalCode record);

}

