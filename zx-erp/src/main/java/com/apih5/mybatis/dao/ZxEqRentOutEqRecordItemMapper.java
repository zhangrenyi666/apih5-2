package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecordItem;

public interface ZxEqRentOutEqRecordItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqRentOutEqRecordItem record);

    int insertSelective(ZxEqRentOutEqRecordItem record);

    ZxEqRentOutEqRecordItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqRentOutEqRecordItem record);

    int updateByPrimaryKey(ZxEqRentOutEqRecordItem record);

    List<ZxEqRentOutEqRecordItem> selectByZxEqRentOutEqRecordItemList(ZxEqRentOutEqRecordItem record);

    int batchDeleteUpdateZxEqRentOutEqRecordItem(List<ZxEqRentOutEqRecordItem> recordList, ZxEqRentOutEqRecordItem record);

}

