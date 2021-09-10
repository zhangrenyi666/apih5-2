package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSupReport;

public interface ZjTzSupReportService {

    public ResponseEntity getZjTzSupReportListByCondition(ZjTzSupReport zjTzSupReport);

    public ResponseEntity getZjTzSupReportDetails(ZjTzSupReport zjTzSupReport);

    public ResponseEntity saveZjTzSupReport(ZjTzSupReport zjTzSupReport);

    public ResponseEntity updateZjTzSupReport(ZjTzSupReport zjTzSupReport);

    public ResponseEntity batchDeleteUpdateZjTzSupReport(List<ZjTzSupReport> zjTzSupReportList);

}

