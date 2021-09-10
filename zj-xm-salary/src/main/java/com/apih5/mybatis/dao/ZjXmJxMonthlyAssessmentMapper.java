package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessment;

public interface ZjXmJxMonthlyAssessmentMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxMonthlyAssessment record);

	int insertSelective(ZjXmJxMonthlyAssessment record);

	ZjXmJxMonthlyAssessment selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxMonthlyAssessment record);

	int updateByPrimaryKey(ZjXmJxMonthlyAssessment record);

	List<ZjXmJxMonthlyAssessment> selectByZjXmJxMonthlyAssessmentList(ZjXmJxMonthlyAssessment record);

	int batchDeleteUpdateZjXmJxMonthlyAssessment(List<ZjXmJxMonthlyAssessment> recordList,
			ZjXmJxMonthlyAssessment record);

	int checkZjXmJxMonthlyAssessmentByCondition(ZjXmJxMonthlyAssessment record);

	ZjXmJxMonthlyAssessment getZjXmJxMonthlyAssessmentNewest(ZjXmJxMonthlyAssessment record);
}
