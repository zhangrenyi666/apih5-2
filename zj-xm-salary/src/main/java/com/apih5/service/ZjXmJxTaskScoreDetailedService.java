package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailed;

public interface ZjXmJxTaskScoreDetailedService {

	public ResponseEntity getZjXmJxTaskScoreDetailedListByCondition(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity getZjXmJxTaskScoreDetailedDetails(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity saveZjXmJxTaskScoreDetailed(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity updateZjXmJxTaskScoreDetailed(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity batchDeleteUpdateZjXmJxTaskScoreDetailed(
			List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList);

	public ResponseEntity batchAppealZjXmJxTaskScoreDetailed(List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList);

	public ResponseEntity appealZjXmJxTaskScoreDetailedByAuditee(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity getZjXmJxTaskScoreDetailedListByAuditee(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity tempOrConfirmZjXmJxTaskScoreDetailedByAuditee(
			ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity getZjXmJxTaskScoreDetailedListByHR(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity rejectOrConfirmZjXmJxTaskScoreDetailedByHR(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public ResponseEntity rejectOrSubmitZjXmJxTaskScoreDetailedByLeader(
			ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);
	
	public ResponseEntity getZjXmJxTaskScoreDetailedTaskExcel(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed);

	public void exportZjXmJxTaskScoreDetailedTaskExcel(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed,
			HttpServletResponse response);
}
