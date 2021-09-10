package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProfitExcel;

public interface ZjTzProfitExcelMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProfitExcel record);

    int insertSelective(ZjTzProfitExcel record);

    ZjTzProfitExcel selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProfitExcel record);

    int updateByPrimaryKey(ZjTzProfitExcel record);

    List<ZjTzProfitExcel> selectByZjTzProfitExcelList(ZjTzProfitExcel record);

    int batchDeleteUpdateZjTzProfitExcel(List<ZjTzProfitExcel> recordList, ZjTzProfitExcel record);

}

