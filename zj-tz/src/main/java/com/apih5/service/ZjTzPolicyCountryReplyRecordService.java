package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReplyRecord;

public interface ZjTzPolicyCountryReplyRecordService {

    public ResponseEntity getZjTzPolicyCountryReplyRecordListByCondition(ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord);

    public ResponseEntity getZjTzPolicyCountryReplyRecordDetails(ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord);

    public ResponseEntity saveZjTzPolicyCountryReplyRecord(ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord);

    public ResponseEntity updateZjTzPolicyCountryReplyRecord(ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord);

    public ResponseEntity batchDeleteUpdateZjTzPolicyCountryReplyRecord(List<ZjTzPolicyCountryReplyRecord> zjTzPolicyCountryReplyRecordList);

}

