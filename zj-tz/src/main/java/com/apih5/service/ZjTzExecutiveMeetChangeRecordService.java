package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzExecutiveMeetChangeRecord;

public interface ZjTzExecutiveMeetChangeRecordService {

    public ResponseEntity getZjTzExecutiveMeetChangeRecordListByCondition(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord);

    public ResponseEntity getZjTzExecutiveMeetChangeRecordDetails(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord);

    public ResponseEntity saveZjTzExecutiveMeetChangeRecord(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord);

    public ResponseEntity updateZjTzExecutiveMeetChangeRecord(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord);

    public ResponseEntity batchDeleteUpdateZjTzExecutiveMeetChangeRecord(List<ZjTzExecutiveMeetChangeRecord> zjTzExecutiveMeetChangeRecordList);

}

