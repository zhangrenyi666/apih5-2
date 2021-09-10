package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessmentSummary;

public interface ZjXmJxMonthlyAssessmentSummaryMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxMonthlyAssessmentSummary record);

	int insertSelective(ZjXmJxMonthlyAssessmentSummary record);

	ZjXmJxMonthlyAssessmentSummary selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxMonthlyAssessmentSummary record);

	int updateByPrimaryKey(ZjXmJxMonthlyAssessmentSummary record);

	List<ZjXmJxMonthlyAssessmentSummary> selectByZjXmJxMonthlyAssessmentSummaryList(
			ZjXmJxMonthlyAssessmentSummary record);

	int batchDeleteUpdateZjXmJxMonthlyAssessmentSummary(List<ZjXmJxMonthlyAssessmentSummary> recordList,
			ZjXmJxMonthlyAssessmentSummary record);

	int batchInsertZjXmJxMonthlyAssessmentSummary(List<ZjXmJxMonthlyAssessmentSummary> recordList);

	int deleteZjXmJxMonthlyAssessmentSummaryByCondition(ZjXmJxMonthlyAssessmentSummary record);

	List<ZjXmJxMonthlyAssessmentSummary> getZjXmJxMonthlyAssessmentSummaryListByRank(
			ZjXmJxMonthlyAssessmentSummary record);

	List<ZjXmJxMonthlyAssessmentSummary> getZjXmJxMonthlyAssessmentSummaryListByLast(
			ZjXmJxMonthlyAssessmentSummary record);

	List<ZjXmJxMonthlyAssessmentSummary> getZjXmJxMonthlyAssessmentSummaryProjectByMonth(
			ZjXmJxMonthlyAssessmentSummary record);

	List<ZjXmJxMonthlyAssessmentSummary> getZjXmJxMonthlyAssessmentSummaryProjectByYear(
			ZjXmJxMonthlyAssessmentSummary record);
}
