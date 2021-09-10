package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxSystemAutoScoreDetailed;

public interface ZjXmJxSystemAutoScoreDetailedMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxSystemAutoScoreDetailed record);

	int insertSelective(ZjXmJxSystemAutoScoreDetailed record);

	ZjXmJxSystemAutoScoreDetailed selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxSystemAutoScoreDetailed record);

	int updateByPrimaryKey(ZjXmJxSystemAutoScoreDetailed record);

	List<ZjXmJxSystemAutoScoreDetailed> selectByZjXmJxSystemAutoScoreDetailedList(ZjXmJxSystemAutoScoreDetailed record);

	int batchDeleteUpdateZjXmJxSystemAutoScoreDetailed(List<ZjXmJxSystemAutoScoreDetailed> recordList,
			ZjXmJxSystemAutoScoreDetailed record);

	int batchInsertZjXmJxSystemAutoScoreDetailed(List<ZjXmJxSystemAutoScoreDetailed> recordList);

}
