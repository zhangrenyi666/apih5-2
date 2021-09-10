package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzCashExcel;

public interface ZjTzCashExcelMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzCashExcel record);

    int insertSelective(ZjTzCashExcel record);

    ZjTzCashExcel selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzCashExcel record);

    int updateByPrimaryKey(ZjTzCashExcel record);

    List<ZjTzCashExcel> selectByZjTzCashExcelList(ZjTzCashExcel record);

    int batchDeleteUpdateZjTzCashExcel(List<ZjTzCashExcel> recordList, ZjTzCashExcel record);

}

