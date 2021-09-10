package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzContractConditionRecord;

public interface ZjTzContractConditionRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzContractConditionRecord record);

    int insertSelective(ZjTzContractConditionRecord record);

    ZjTzContractConditionRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzContractConditionRecord record);

    int updateByPrimaryKey(ZjTzContractConditionRecord record);

    List<ZjTzContractConditionRecord> selectByZjTzContractConditionRecordList(ZjTzContractConditionRecord record);

    int batchDeleteUpdateZjTzContractConditionRecord(List<ZjTzContractConditionRecord> recordList, ZjTzContractConditionRecord record);

}

