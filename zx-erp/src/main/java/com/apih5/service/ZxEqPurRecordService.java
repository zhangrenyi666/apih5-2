package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqPurRecord;

public interface ZxEqPurRecordService {

    public ResponseEntity getZxEqPurRecordListByCondition(ZxEqPurRecord zxEqPurRecord);

    public ResponseEntity getZxEqPurRecordDetails(ZxEqPurRecord zxEqPurRecord);

    public ResponseEntity saveZxEqPurRecord(ZxEqPurRecord zxEqPurRecord);

    public ResponseEntity updateZxEqPurRecord(ZxEqPurRecord zxEqPurRecord);

    public ResponseEntity batchDeleteUpdateZxEqPurRecord(List<ZxEqPurRecord> zxEqPurRecordList);

	public ResponseEntity returnZxEqPurRecord(ZxEqPurRecord zxEqPurRecord);

	public ResponseEntity requestNumberZxEqPurRecord(List<ZxEqPurRecord> zxEqPurRecordList);

	public ResponseEntity writeNumberZxEqPurRecord(ZxEqPurRecord zxEqPurRecord);

	public ResponseEntity reverseAuditZxEqPurRecord(ZxEqPurRecord zxEqPurRecord);

	public ZxEqPurRecord ureportZxEqPurRecordDetails(ZxEqPurRecord zxEqPurRecord);

}

