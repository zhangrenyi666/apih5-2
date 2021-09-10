package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzExecutiveMeetChangeRecord;

public interface ZjTzExecutiveMeetChangeRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzExecutiveMeetChangeRecord record);

    int insertSelective(ZjTzExecutiveMeetChangeRecord record);

    ZjTzExecutiveMeetChangeRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzExecutiveMeetChangeRecord record);

    int updateByPrimaryKey(ZjTzExecutiveMeetChangeRecord record);

    List<ZjTzExecutiveMeetChangeRecord> selectByZjTzExecutiveMeetChangeRecordList(ZjTzExecutiveMeetChangeRecord record);

    int batchDeleteUpdateZjTzExecutiveMeetChangeRecord(List<ZjTzExecutiveMeetChangeRecord> recordList, ZjTzExecutiveMeetChangeRecord record);

}

