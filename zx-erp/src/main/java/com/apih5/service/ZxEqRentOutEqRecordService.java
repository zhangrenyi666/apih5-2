package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecord;

public interface ZxEqRentOutEqRecordService {

    public ResponseEntity getZxEqRentOutEqRecordListByCondition(ZxEqRentOutEqRecord zxEqRentOutEqRecord);

    public ResponseEntity getZxEqRentOutEqRecordDetails(ZxEqRentOutEqRecord zxEqRentOutEqRecord);

    public ResponseEntity saveZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord);

    public ResponseEntity updateZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord);

    public ResponseEntity batchDeleteUpdateZxEqRentOutEqRecord(List<ZxEqRentOutEqRecord> zxEqRentOutEqRecordList);

	public ResponseEntity auditZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord);

	public ResponseEntity importZxEqRentOutEqRecordList(ZxEqRentOutEqRecord zxEqRentOutEqRecord);

}

