package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReplyRecord;

public interface ZjTzPolicyLocalReplyRecordService {

    public ResponseEntity getZjTzPolicyLocalReplyRecordListByCondition(ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord);

    public ResponseEntity getZjTzPolicyLocalReplyRecordDetails(ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord);

    public ResponseEntity saveZjTzPolicyLocalReplyRecord(ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord);

    public ResponseEntity updateZjTzPolicyLocalReplyRecord(ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord);

    public ResponseEntity batchDeleteUpdateZjTzPolicyLocalReplyRecord(List<ZjTzPolicyLocalReplyRecord> zjTzPolicyLocalReplyRecordList);

}

