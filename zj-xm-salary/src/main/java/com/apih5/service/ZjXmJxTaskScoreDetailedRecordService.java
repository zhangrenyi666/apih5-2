package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailedRecord;

public interface ZjXmJxTaskScoreDetailedRecordService {

    public ResponseEntity getZjXmJxTaskScoreDetailedRecordListByCondition(ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord);

    public ResponseEntity getZjXmJxTaskScoreDetailedRecordDetails(ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord);

    public ResponseEntity saveZjXmJxTaskScoreDetailedRecord(ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord);

    public ResponseEntity updateZjXmJxTaskScoreDetailedRecord(ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord);

    public ResponseEntity batchDeleteUpdateZjXmJxTaskScoreDetailedRecord(List<ZjXmJxTaskScoreDetailedRecord> zjXmJxTaskScoreDetailedRecordList);

}

