package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;

public interface ZjXmJxAssessmentUserScoreMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxAssessmentUserScore record);

	int insertSelective(ZjXmJxAssessmentUserScore record);

	ZjXmJxAssessmentUserScore selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxAssessmentUserScore record);

	int updateByPrimaryKey(ZjXmJxAssessmentUserScore record);

	List<ZjXmJxAssessmentUserScore> selectByZjXmJxAssessmentUserScoreList(ZjXmJxAssessmentUserScore record);

	List<ZjXmJxAssessmentUserScore> getZjXmJxAssessmentUserScoreListByUser(ZjXmJxAssessmentUserScore record);

	List<ZjXmJxAssessmentUserScore> getZjXmJxAssessmentUserScoreListByReviewer(ZjXmJxAssessmentUserScore record);

	int batchDeleteUpdateZjXmJxAssessmentUserScore(List<ZjXmJxAssessmentUserScore> recordList,
			ZjXmJxAssessmentUserScore record);

	int batchInsertZjXmJxAssessmentUserScore(List<ZjXmJxAssessmentUserScore> recordList);

	int batchClearZjXmJxAssessmentUserScoreByLeader(List<ZjXmJxAssessmentUserScore> recordList);

	int deleteZjXmJxAssessmentUserScoreByCondition(ZjXmJxAssessmentUserScore record);

	int updateZjXmJxAssessmentUserScoreByCondition(ZjXmJxAssessmentUserScore record);

	int updateSystemScoreZjXmJxAssessmentUserScore(ZjXmJxAssessmentUserScore record);

	int updateZjXmJxAssessmentUserScoreForConfirmStatus(ZjXmJxAssessmentUserScore record);

	List<ZjXmJxAssessmentUserScore> getZjXmJxAssessmentUserScoreListByAuditee(ZjXmJxAssessmentUserScore record);

	List<ZjXmJxAssessmentUserScore> getZjXmJxAssessmentUserScoreReviewerByTime(ZjXmJxAssessmentUserScore record);

	List<ZjXmJxAssessmentUserScore> getZjXmJxAssessmentUserScoreReviewerByTime2(ZjXmJxAssessmentUserScore record);

	int countZjXmJxAssessmentUserScoreList(ZjXmJxAssessmentUserScore record);

	int batchUpdateZjXmJxAssessmentUserScoreByList(List<ZjXmJxAssessmentUserScore> recordList);

	List<Map<String, Object>> countZjXmJxMonthlyAssessmentSummaryByCondition(ZjXmJxAssessmentUserScore record);

}
