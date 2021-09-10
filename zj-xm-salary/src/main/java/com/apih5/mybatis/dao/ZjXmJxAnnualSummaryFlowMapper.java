package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxAnnualSummaryFlow;

public interface ZjXmJxAnnualSummaryFlowMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxAnnualSummaryFlow record);

	int insertSelective(ZjXmJxAnnualSummaryFlow record);

	ZjXmJxAnnualSummaryFlow selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxAnnualSummaryFlow record);

	int updateByPrimaryKey(ZjXmJxAnnualSummaryFlow record);

	List<ZjXmJxAnnualSummaryFlow> selectByZjXmJxAnnualSummaryFlowList(ZjXmJxAnnualSummaryFlow record);

	int batchDeleteUpdateZjXmJxAnnualSummaryFlow(List<ZjXmJxAnnualSummaryFlow> recordList,
			ZjXmJxAnnualSummaryFlow record);

	int batchInsertZjXmJxAnnualSummaryFlow(List<ZjXmJxAnnualSummaryFlow> recordList);

	int deleteZjXmJxAnnualSummaryFlowByCondition(ZjXmJxAnnualSummaryFlow record);

}
