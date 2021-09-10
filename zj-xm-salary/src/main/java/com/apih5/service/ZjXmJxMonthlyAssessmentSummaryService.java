package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessmentSummary;

public interface ZjXmJxMonthlyAssessmentSummaryService {

	public ResponseEntity getZjXmJxMonthlyAssessmentSummaryListByCondition(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary);

	public ResponseEntity getZjXmJxMonthlyAssessmentSummaryDetails(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary);

	public ResponseEntity saveZjXmJxMonthlyAssessmentSummary(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary);

	public ResponseEntity updateZjXmJxMonthlyAssessmentSummary(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary);

	public ResponseEntity batchDeleteUpdateZjXmJxMonthlyAssessmentSummary(
			List<ZjXmJxMonthlyAssessmentSummary> zjXmJxMonthlyAssessmentSummaryList);

	public ResponseEntity timingToCountZjXmJxMonthlyAssessmentSummary(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) throws Exception;

	public ResponseEntity exportRankZjXmJxMonthlyAssessmentSummary2(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary);

	public ResponseEntity exportLastZjXmJxMonthlyAssessmentSummary2(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary);

	public void exportRankZjXmJxMonthlyAssessmentSummary(ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary,
			HttpServletResponse response);

	public void exportLastZjXmJxMonthlyAssessmentSummary(ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary,
			HttpServletResponse response);

}
