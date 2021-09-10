package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessmentDept;

public interface ZjXmJxQuarterlyAssessmentDeptMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxQuarterlyAssessmentDept record);

	int insertSelective(ZjXmJxQuarterlyAssessmentDept record);

	ZjXmJxQuarterlyAssessmentDept selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxQuarterlyAssessmentDept record);

	int updateByPrimaryKey(ZjXmJxQuarterlyAssessmentDept record);

	List<ZjXmJxQuarterlyAssessmentDept> selectByZjXmJxQuarterlyAssessmentDeptList(ZjXmJxQuarterlyAssessmentDept record);

	int batchDeleteUpdateZjXmJxQuarterlyAssessmentDept(List<ZjXmJxQuarterlyAssessmentDept> recordList,
			ZjXmJxQuarterlyAssessmentDept record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZjXmJxQuarterlyAssessmentDept(List<ZjXmJxQuarterlyAssessmentDept> recordList);

	int countZjXmJxQuarterlyAssessmentDeptByCondition(ZjXmJxQuarterlyAssessmentDept record);
}
