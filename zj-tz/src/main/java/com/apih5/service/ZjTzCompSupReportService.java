package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzCompSupReport;

public interface ZjTzCompSupReportService {

    public ResponseEntity getZjTzCompSupReportListByCondition(ZjTzCompSupReport zjTzCompSupReport);

    public ResponseEntity getZjTzCompSupReportDetails(ZjTzCompSupReport zjTzCompSupReport);

    public ResponseEntity saveZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport);

    public ResponseEntity updateZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport);

    public ResponseEntity batchDeleteUpdateZjTzCompSupReport(List<ZjTzCompSupReport> zjTzCompSupReportList);

	public ResponseEntity batchRecallZjTzCompSupReport(List<ZjTzCompSupReport> zjTzCompSupReportList);

	public ResponseEntity correctiveZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport);

	public ResponseEntity getZjTzCompSupReportReplyList(ZjTzCompSupReport zjTzCompSupReport);

	public ResponseEntity batchExportZjTzCompSupReportFile(List<ZjTzCompSupReport> zjTzCompSupReportList);

	public ResponseEntity replyZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport);

	public ResponseEntity reportZjTzCompSupReport(ZjTzCompSupReport zjTzCompSupReport);

}

