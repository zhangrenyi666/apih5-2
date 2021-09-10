package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailedRecord;

public interface ZjXmJxTaskScoreDetailedRecordMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxTaskScoreDetailedRecord record);

	int insertSelective(ZjXmJxTaskScoreDetailedRecord record);

	ZjXmJxTaskScoreDetailedRecord selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxTaskScoreDetailedRecord record);

	int updateByPrimaryKey(ZjXmJxTaskScoreDetailedRecord record);

	List<ZjXmJxTaskScoreDetailedRecord> selectByZjXmJxTaskScoreDetailedRecordList(ZjXmJxTaskScoreDetailedRecord record);

	int batchDeleteUpdateZjXmJxTaskScoreDetailedRecord(List<ZjXmJxTaskScoreDetailedRecord> recordList,
			ZjXmJxTaskScoreDetailedRecord record);

	int batchInsertZjXmJxTaskScoreDetailedRecord(List<ZjXmJxTaskScoreDetailedRecord> recordList);

}
