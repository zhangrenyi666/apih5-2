package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSizeControlRecord;

public interface ZjTzSizeControlRecordService {

    public ResponseEntity getZjTzSizeControlRecordListByCondition(ZjTzSizeControlRecord zjTzSizeControlRecord);

    public ResponseEntity getZjTzSizeControlRecordDetails(ZjTzSizeControlRecord zjTzSizeControlRecord);

    public ResponseEntity saveZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord);

    public ResponseEntity updateZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord);

    public ResponseEntity batchDeleteUpdateZjTzSizeControlRecord(List<ZjTzSizeControlRecord> zjTzSizeControlRecordList);

	public ResponseEntity singleReleaseZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord);

	public ResponseEntity singleRecallZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord);
	
	public ResponseEntity checkAndFinishZjTzSizeControlRecord(ZjTzSizeControlRecord zjTzSizeControlRecord);

}

