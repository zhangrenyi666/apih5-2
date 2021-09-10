package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreDetailed;

public interface ZjXmJxPeripheryScoreDetailedMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxPeripheryScoreDetailed record);

	int insertSelective(ZjXmJxPeripheryScoreDetailed record);

	ZjXmJxPeripheryScoreDetailed selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxPeripheryScoreDetailed record);

	int updateByPrimaryKey(ZjXmJxPeripheryScoreDetailed record);

	List<ZjXmJxPeripheryScoreDetailed> selectByZjXmJxPeripheryScoreDetailedList(ZjXmJxPeripheryScoreDetailed record);

	List<ZjXmJxPeripheryScoreDetailed> getZjXmJxPeripheryScoreDetailedList(ZjXmJxPeripheryScoreDetailed record);

	int batchDeleteUpdateZjXmJxPeripheryScoreDetailed(List<ZjXmJxPeripheryScoreDetailed> recordList,
			ZjXmJxPeripheryScoreDetailed record);

	int batchInsertZjXmJxPeripheryScoreDetailed(List<ZjXmJxPeripheryScoreDetailed> recordList);

	int batchUpdateZjXmJxPeripheryScoreDetailedByReviewer(List<ZjXmJxPeripheryScoreDetailed> recordList);

	int countZjXmJxPeripheryScoreDetailedList(ZjXmJxPeripheryScoreDetailed record);

	List<ZjXmJxPeripheryScoreDetailed> getZjXmJxPeripheryScoreDetailedReviewerByTime(
			ZjXmJxPeripheryScoreDetailed record);

	List<ZjXmJxPeripheryScoreDetailed> getZjXmJxPeripheryScoreDetailedListBySystem(ZjXmJxPeripheryScoreDetailed record);

	List<ZjXmJxPeripheryScoreDetailed> getZjXmJxPeripheryScoreDetailedListByReviewer(
			ZjXmJxPeripheryScoreDetailed record);

	List<ZjXmJxPeripheryScoreDetailed> getZjXmJxPeripheryScoreDetailedAllAuditee(ZjXmJxPeripheryScoreDetailed record);

	List<Map<String, Object>> getZjXmJxPeripheryScoreDetailedPeripheryExcel(ZjXmJxPeripheryScoreDetailed record);
}
