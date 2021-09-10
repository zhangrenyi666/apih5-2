package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxMonthlySummaryFlow;

public interface ZjXmJxMonthlySummaryFlowMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmJxMonthlySummaryFlow record);

	int insertSelective(ZjXmJxMonthlySummaryFlow record);

	ZjXmJxMonthlySummaryFlow selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmJxMonthlySummaryFlow record);

	int updateByPrimaryKey(ZjXmJxMonthlySummaryFlow record);

	List<ZjXmJxMonthlySummaryFlow> selectByZjXmJxMonthlySummaryFlowList(ZjXmJxMonthlySummaryFlow record);

	int batchDeleteUpdateZjXmJxMonthlySummaryFlow(List<ZjXmJxMonthlySummaryFlow> recordList,
			ZjXmJxMonthlySummaryFlow record);

	int batchInsertZjXmJxMonthlySummaryFlow(List<ZjXmJxMonthlySummaryFlow> recordList);

	int deleteZjXmJxMonthlySummaryFlowByCondition(ZjXmJxMonthlySummaryFlow record);

}
