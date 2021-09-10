package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyProjectDetails;

public interface ZjXmJxQuarterlyProjectDetailsMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxQuarterlyProjectDetails record);

	int insertSelective(ZjXmJxQuarterlyProjectDetails record);

	ZjXmJxQuarterlyProjectDetails selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxQuarterlyProjectDetails record);

	int updateByPrimaryKey(ZjXmJxQuarterlyProjectDetails record);

	List<ZjXmJxQuarterlyProjectDetails> selectByZjXmJxQuarterlyProjectDetailsList(ZjXmJxQuarterlyProjectDetails record);

	int batchDeleteUpdateZjXmJxQuarterlyProjectDetails(List<ZjXmJxQuarterlyProjectDetails> recordList,
			ZjXmJxQuarterlyProjectDetails record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZjXmJxQuarterlyProjectDetails(List<ZjXmJxQuarterlyProjectDetails> recordList);

	int batchConfirmZjXmJxQuarterlyProjectDetails(List<ZjXmJxQuarterlyProjectDetails> recordList);

	List<Map<String, Object>> getZjXmJxQuarterlyProjectDetailsByPersonLiable(ZjXmJxQuarterlyProjectDetails record);

	int batchUpdateZjXmJxQuarterlyProjectDetailsScore(List<ZjXmJxQuarterlyProjectDetails> recordList);

	int checkZjXmJxQuarterlyProjectDetailsConfirmStatus(ZjXmJxQuarterlyProjectDetails record);

	int checkZjXmJxQuarterlyProjectDetailsActualScore(ZjXmJxQuarterlyProjectDetails record);

	List<Map<String, Object>> countZjXmJxQuarterlyProjectDetailsByDept(ZjXmJxQuarterlyProjectDetails record);

	List<ZjXmJxQuarterlyProjectDetails> getZjXmJxQuarterlyProjectDetailsDynamicColumn1(
			ZjXmJxQuarterlyProjectDetails record);

	List<Map<String, Object>> countZjXmJxQuarterlyProjectDetailsByWeight(ZjXmJxQuarterlyProjectDetails record);

	List<ZjXmJxQuarterlyProjectDetails> getZjXmJxQuarterlyProjectDetailsDynamicColumn2(
			ZjXmJxQuarterlyProjectDetails record);
}
