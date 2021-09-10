package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreDetailed;

public interface ZjXmJxPeripheryScoreDetailedService {

	public ResponseEntity getZjXmJxPeripheryScoreDetailedListByCondition(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed);

	public ResponseEntity getZjXmJxPeripheryScoreDetailedDetails(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed);

	public ResponseEntity saveZjXmJxPeripheryScoreDetailed(ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed);

	public ResponseEntity updateZjXmJxPeripheryScoreDetailed(ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed);

	public ResponseEntity batchDeleteUpdateZjXmJxPeripheryScoreDetailed(
			List<ZjXmJxPeripheryScoreDetailed> zjXmJxPeripheryScoreDetailedList);

	public ResponseEntity getZjXmJxPeripheryScoreDetailedListByReviewer(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed);

	public ResponseEntity tempOrSubmitZjXmJxPeripheryScoreDetailedList(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) throws Exception;

	public ResponseEntity recalculateZjXmJxPeripheryScoreDetailed(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed);

	public ResponseEntity getZjXmJxPeripheryScoreDetailedPeripheryExcelColumn(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed);

	public ResponseEntity getZjXmJxPeripheryScoreDetailedPeripheryExcel(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed);

	public void exportZjXmJxPeripheryScoreDetailedPeripheryExcel(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed, HttpServletResponse response);
}
