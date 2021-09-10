package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailed;

public interface ZjXmJxTaskScoreDetailedMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxTaskScoreDetailed record);

	int insertSelective(ZjXmJxTaskScoreDetailed record);

	ZjXmJxTaskScoreDetailed selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxTaskScoreDetailed record);

	int updateByPrimaryKey(ZjXmJxTaskScoreDetailed record);

	List<ZjXmJxTaskScoreDetailed> selectByZjXmJxTaskScoreDetailedList(ZjXmJxTaskScoreDetailed record);

	List<ZjXmJxTaskScoreDetailed> getZjXmJxTaskScoreDetailedListByHR(ZjXmJxTaskScoreDetailed record);

	int batchDeleteUpdateZjXmJxTaskScoreDetailed(List<ZjXmJxTaskScoreDetailed> recordList,
			ZjXmJxTaskScoreDetailed record);

	int batchUpdateZjXmJxTaskScoreDetailedOpinionByAuditee(List<ZjXmJxTaskScoreDetailed> recordList);

	int batchUpdateZjXmJxTaskScoreDetailedCompletion(List<ZjXmJxTaskScoreDetailed> recordList);

	int batchUpdateZjXmJxTaskScoreDetailedByLeader(List<ZjXmJxTaskScoreDetailed> recordList);

	int batchClearZjXmJxTaskScoreDetailedByLeader(List recordList);

	int batchUpdateZjXmJxTaskScoreDetailedZero(List recordList);

	int batchInsertZjXmJxTaskScoreDetailed(List<ZjXmJxTaskScoreDetailed> recordList);

	List<ZjXmJxTaskScoreDetailed> getZjXmJxTaskScoreDetailedTaskExcel(ZjXmJxTaskScoreDetailed record);
}
