package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzInvXmhgqk;

public interface ZjTzInvXmhgqkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzInvXmhgqk record);

    int insertSelective(ZjTzInvXmhgqk record);

    ZjTzInvXmhgqk selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzInvXmhgqk record);

    int updateByPrimaryKey(ZjTzInvXmhgqk record);

    List<ZjTzInvXmhgqk> selectByZjTzInvXmhgqkList(ZjTzInvXmhgqk record);

    int batchDeleteUpdateZjTzInvXmhgqk(List<ZjTzInvXmhgqk> recordList, ZjTzInvXmhgqk record);

    List<ZjTzInvXmhgqk> selectZjTzInvXmhgqkMonthlyReportListByPeriodValue(ZjTzInvXmhgqk record);
    
    List<ZjTzInvXmhgqk> selectZjTzInvXmhgqkMonthlyReportListBasicData(ZjTzInvXmhgqk record);
    
    List<ZjTzInvXmhgqk> selectHomeChartHgData(ZjTzInvXmhgqk record);

	ZjTzInvXmhgqk selectHgPageHgStatus(ZjTzInvXmhgqk zjTzInvXmhgqk);
	
	ZjTzInvXmhgqk selectHgPageTrendDataByYearBn(ZjTzInvXmhgqk record);
	
	ZjTzInvXmhgqk selectHgPageTrendDataByYearKl(ZjTzInvXmhgqk record);

	ZjTzInvXmhgqk selectHgPageCurrentPeriod(ZjTzInvXmhgqk zjTzInvXmhgqk);
	
}

