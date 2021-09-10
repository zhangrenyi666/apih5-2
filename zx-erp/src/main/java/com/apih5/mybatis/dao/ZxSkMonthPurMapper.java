package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkMonthPur;

public interface ZxSkMonthPurMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkMonthPur record);

    int insertSelective(ZxSkMonthPur record);

    ZxSkMonthPur selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkMonthPur record);

    int updateByPrimaryKey(ZxSkMonthPur record);

    List<ZxSkMonthPur> selectByZxSkMonthPurList(ZxSkMonthPur record);

    int batchDeleteUpdateZxSkMonthPur(List<ZxSkMonthPur> recordList, ZxSkMonthPur record);

}

