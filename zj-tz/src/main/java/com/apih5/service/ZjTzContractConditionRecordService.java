package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzContractConditionRecord;

public interface ZjTzContractConditionRecordService {

    public ResponseEntity getZjTzContractConditionRecordListByCondition(ZjTzContractConditionRecord zjTzContractConditionRecord);

    public ResponseEntity getZjTzContractConditionRecordDetails(ZjTzContractConditionRecord zjTzContractConditionRecord);

    public ResponseEntity saveZjTzContractConditionRecord(ZjTzContractConditionRecord zjTzContractConditionRecord);

    public ResponseEntity updateZjTzContractConditionRecord(ZjTzContractConditionRecord zjTzContractConditionRecord);

    public ResponseEntity batchDeleteUpdateZjTzContractConditionRecord(List<ZjTzContractConditionRecord> zjTzContractConditionRecordList);

}

