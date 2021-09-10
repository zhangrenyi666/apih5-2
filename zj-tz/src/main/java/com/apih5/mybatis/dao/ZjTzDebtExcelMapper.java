package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDebtExcel;

public interface ZjTzDebtExcelMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDebtExcel record);

    int insertSelective(ZjTzDebtExcel record);

    ZjTzDebtExcel selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDebtExcel record);

    int updateByPrimaryKey(ZjTzDebtExcel record);

    List<ZjTzDebtExcel> selectByZjTzDebtExcelList(ZjTzDebtExcel record);

    int batchDeleteUpdateZjTzDebtExcel(List<ZjTzDebtExcel> recordList, ZjTzDebtExcel record);

}

