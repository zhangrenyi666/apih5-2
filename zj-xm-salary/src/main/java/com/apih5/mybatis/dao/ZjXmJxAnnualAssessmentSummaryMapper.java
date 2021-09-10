package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxAnnualAssessmentSummary;

public interface ZjXmJxAnnualAssessmentSummaryMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxAnnualAssessmentSummary record);

	int insertSelective(ZjXmJxAnnualAssessmentSummary record);

	ZjXmJxAnnualAssessmentSummary selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxAnnualAssessmentSummary record);

	int updateByPrimaryKey(ZjXmJxAnnualAssessmentSummary record);

	List<ZjXmJxAnnualAssessmentSummary> selectByZjXmJxAnnualAssessmentSummaryList(ZjXmJxAnnualAssessmentSummary record);

	int batchDeleteUpdateZjXmJxAnnualAssessmentSummary(List<ZjXmJxAnnualAssessmentSummary> recordList,
			ZjXmJxAnnualAssessmentSummary record);

	int batchInsertZjXmJxAnnualAssessmentSummary(List<ZjXmJxAnnualAssessmentSummary> recordList);

	int deleteZjXmJxAnnualAssessmentSummaryByCondition(ZjXmJxAnnualAssessmentSummary record);

	List<ZjXmJxAnnualAssessmentSummary> getZjXmJxAnnualAssessmentSummaryListByMonthLast(
			ZjXmJxAnnualAssessmentSummary record);
}
