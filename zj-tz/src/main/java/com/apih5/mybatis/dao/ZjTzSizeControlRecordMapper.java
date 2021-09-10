package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSizeControlRecord;

public interface ZjTzSizeControlRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSizeControlRecord record);

    int insertSelective(ZjTzSizeControlRecord record);

    ZjTzSizeControlRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSizeControlRecord record);

    int updateByPrimaryKey(ZjTzSizeControlRecord record);

    List<ZjTzSizeControlRecord> selectByZjTzSizeControlRecordList(ZjTzSizeControlRecord record);

    int batchDeleteUpdateZjTzSizeControlRecord(List<ZjTzSizeControlRecord> recordList, ZjTzSizeControlRecord record);

}

