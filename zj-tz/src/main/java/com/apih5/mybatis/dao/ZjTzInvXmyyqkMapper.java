package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzInvXmyyqk;

public interface ZjTzInvXmyyqkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzInvXmyyqk record);

    int insertSelective(ZjTzInvXmyyqk record);

    ZjTzInvXmyyqk selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzInvXmyyqk record);

    int updateByPrimaryKey(ZjTzInvXmyyqk record);

    List<ZjTzInvXmyyqk> selectByZjTzInvXmyyqkList(ZjTzInvXmyyqk record);

    int batchDeleteUpdateZjTzInvXmyyqk(List<ZjTzInvXmyyqk> recordList, ZjTzInvXmyyqk record);
    
    List<ZjTzInvXmyyqk> selectZjTzInvXmyyqkMonthlyReportListBasicData(ZjTzInvXmyyqk record);
    
    ZjTzInvXmyyqk selectZjTzInvXmyyqkMonthlyReportListBasicDataDetail(ZjTzInvXmyyqk record);
    
    List<ZjTzInvXmyyqk> selectHomeChartOperateData(ZjTzInvXmyyqk record);
    
    ZjTzInvXmyyqk selectOperatePageCommentAndIncome(ZjTzInvXmyyqk record);
    
    ZjTzInvXmyyqk selectOperatePageTrendDataByYearBn(ZjTzInvXmyyqk record);
    
    ZjTzInvXmyyqk selectOperatePageTrendDataByYearKl(ZjTzInvXmyyqk record);

	ZjTzInvXmyyqk selectOperatePageCurrentPeriod(ZjTzInvXmyyqk zjTzInvXmyyqk);
	//项目页面投评营收预警
	ZjTzInvXmyyqk selectProjectPageYyWarning(ZjTzInvXmyyqk zjTzInvXmyyqk);
}

