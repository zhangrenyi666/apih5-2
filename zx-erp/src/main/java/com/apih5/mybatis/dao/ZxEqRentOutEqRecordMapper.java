package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecord;

public interface ZxEqRentOutEqRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqRentOutEqRecord record);

    int insertSelective(ZxEqRentOutEqRecord record);

    ZxEqRentOutEqRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqRentOutEqRecord record);

    int updateByPrimaryKey(ZxEqRentOutEqRecord record);

    List<ZxEqRentOutEqRecord> selectByZxEqRentOutEqRecordList(ZxEqRentOutEqRecord record);

    int batchDeleteUpdateZxEqRentOutEqRecord(List<ZxEqRentOutEqRecord> recordList, ZxEqRentOutEqRecord record);

}

