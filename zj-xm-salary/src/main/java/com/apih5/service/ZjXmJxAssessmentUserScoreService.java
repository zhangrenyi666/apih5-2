package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;

public interface ZjXmJxAssessmentUserScoreService {

	public ResponseEntity getZjXmJxAssessmentUserScoreListByCondition(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity getZjXmJxAssessmentUserScoreDetails(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity saveZjXmJxAssessmentUserScore(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity updateZjXmJxAssessmentUserScore(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity batchDeleteUpdateZjXmJxAssessmentUserScore(
			List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList);

	public ResponseEntity getZjXmJxAssessmentUserScoreListByTaskAuditee(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity getZjXmJxAssessmentUserScoreListByReviewer(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity getZjXmJxAssessmentUserScoreListByTaskReviewer(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity getZjXmJxAssessmentUserScoreListByPrincipalReviewer(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity getZjXmJxAssessmentUserScoreListByCompositeReviewer(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity batchSubmitZjXmJxAssessmentUserScoreBySecretary(
			List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList);

	public ResponseEntity getZjXmJxAssessmentUserScoreListByAuditee(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity timingToConfirmZjXmJxAssessmentUserScore(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore)
			throws Exception;

	public ResponseEntity timingToSendMessageZjXmJxAssessmentUserScore(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) throws Exception;

	public ResponseEntity timingToDealWithTaskZjXmJxAssessmentUserScore(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) throws Exception;

	public ResponseEntity timingToDealWithPeripheryZjXmJxAssessmentUserScore(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) throws Exception;

	public ResponseEntity timingToDealWithPrincipalZjXmJxAssessmentUserScore(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) throws Exception;

	public ResponseEntity getZjXmJxAssessmentUserScoreCompositeExcel(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore);

	public ResponseEntity cancelZjXmJxTaskScoreDetailedByLeader(
			List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList);

	public void exportZjXmJxAssessmentUserScoreCompositeExcel(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore,
			HttpServletResponse response);
}
