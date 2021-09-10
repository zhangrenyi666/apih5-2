package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqPurRecord;

public interface ZxEqPurRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqPurRecord record);

    int insertSelective(ZxEqPurRecord record);

    ZxEqPurRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqPurRecord record);

    int updateByPrimaryKey(ZxEqPurRecord record);

    List<ZxEqPurRecord> selectByZxEqPurRecordList(ZxEqPurRecord record);

    int batchDeleteUpdateZxEqPurRecord(List<ZxEqPurRecord> recordList, ZxEqPurRecord record);

}

