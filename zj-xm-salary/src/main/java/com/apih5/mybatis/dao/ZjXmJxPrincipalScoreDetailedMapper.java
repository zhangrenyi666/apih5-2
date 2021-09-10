package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxPrincipalScoreDetailed;

public interface ZjXmJxPrincipalScoreDetailedMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxPrincipalScoreDetailed record);

	int insertSelective(ZjXmJxPrincipalScoreDetailed record);

	ZjXmJxPrincipalScoreDetailed selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxPrincipalScoreDetailed record);

	int updateByPrimaryKey(ZjXmJxPrincipalScoreDetailed record);

	List<ZjXmJxPrincipalScoreDetailed> selectByZjXmJxPrincipalScoreDetailedList(ZjXmJxPrincipalScoreDetailed record);

	int batchDeleteUpdateZjXmJxPrincipalScoreDetailed(List<ZjXmJxPrincipalScoreDetailed> recordList,
			ZjXmJxPrincipalScoreDetailed record);

	int batchInsertZjXmJxPrincipalScoreDetailed(List<ZjXmJxPrincipalScoreDetailed> recordList);

	List<ZjXmJxPrincipalScoreDetailed> getZjXmJxPrincipalScoreDetailedList(ZjXmJxPrincipalScoreDetailed record);

	int countZjXmJxPrincipalScoreDetailedList(ZjXmJxPrincipalScoreDetailed record);

	int batchUpdateZjXmJxPrincipalScoreDetailedByReviewer(List<ZjXmJxPrincipalScoreDetailed> recordList);

	List<ZjXmJxPrincipalScoreDetailed> getZjXmJxPrincipalScoreDetailedReviewerByTime(
			ZjXmJxPrincipalScoreDetailed record);

	List<ZjXmJxPrincipalScoreDetailed> getZjXmJxPrincipalScoreDetailedListBySystem(ZjXmJxPrincipalScoreDetailed record);

	List<ZjXmJxPrincipalScoreDetailed> getZjXmJxPrincipalScoreDetailedPrincipalExcel(ZjXmJxPrincipalScoreDetailed record);
}
