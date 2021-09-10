package com.apih5.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxAnnualAssessmentSummary;

public interface ZjXmJxAnnualAssessmentSummaryService {

	public ResponseEntity getZjXmJxAnnualAssessmentSummaryListByCondition(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary);

	public ResponseEntity getZjXmJxAnnualAssessmentSummaryDetails(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary);

	public ResponseEntity saveZjXmJxAnnualAssessmentSummary(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary);

	public ResponseEntity updateZjXmJxAnnualAssessmentSummary(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary);

	public ResponseEntity batchDeleteUpdateZjXmJxAnnualAssessmentSummary(
			List<ZjXmJxAnnualAssessmentSummary> zjXmJxAnnualAssessmentSummaryList);

	public ResponseEntity timingToCountZjXmJxAnnualAssessmentSummary(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) throws Exception;

	public void exportRankZjXmJxAnnualAssessmentSummary(ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary,
			HttpServletResponse response);

}
