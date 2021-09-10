package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzInvXmtzqk;

public interface ZjTzInvXmtzqkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzInvXmtzqk record);

    int insertSelective(ZjTzInvXmtzqk record);

    ZjTzInvXmtzqk selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzInvXmtzqk record);

    int updateByPrimaryKey(ZjTzInvXmtzqk record);

    List<ZjTzInvXmtzqk> selectByZjTzInvXmtzqkList(ZjTzInvXmtzqk record);

    int batchDeleteUpdateZjTzInvXmtzqk(List<ZjTzInvXmtzqk> recordList, ZjTzInvXmtzqk record);
    
    //月报
    List<ZjTzInvXmtzqk> selectZjTzInvXmtzqkMonthlyReportListByPeriodValue(ZjTzInvXmtzqk record, List<String> periodList);
    // 基础数据
    List<ZjTzInvXmtzqk> selectZjTzInvXmtzqkMonthlyReportListBasicData(ZjTzInvXmtzqk record);
    //计划进度
    List<ZjTzInvXmtzqk> selectHomeProgressWarningPlanningProgressWc(ZjTzInvXmtzqk record);
    //预警信息
    List<ZjTzInvXmtzqk> selectHomeProgressWarningInfo(ZjTzInvXmtzqk zjTzInvXmtzqk);
    //局用户考核排名
	List<ZjTzInvXmtzqk> selectHomeProgressWarningCheckingAdmin(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//非局用户考核排名
	List<ZjTzInvXmtzqk> selectHomeProgressWarningCheckingNotAdmin(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//上季考核排名
	ZjTzInvXmtzqk selectHomeProgressWarningCheckingSj(ZjTzInvXmtzqk zjTzInvXmtzqk);
	
	//年度目标完成情况（局用户）
	ZjTzInvXmtzqk selectHomeProgressWarningCompleteStatusAdmin(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//年度目标完成情况（托管公司用户）
	ZjTzInvXmtzqk selectHomeProgressWarningCompleteStatusNotAdmin(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//项目管理单位统计
	List<ZjTzInvXmtzqk> selectHomeChartComnameStatis(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//趋势数据
	ZjTzInvXmtzqk selectHomeChartTrendData(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//总合同（总建安）
	ZjTzInvXmtzqk selectHomeRegionalOverviewZhtAndZja(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//本年完成投资（本年完成建安）
	ZjTzInvXmtzqk selectHomeRegionalOverviewTzwcbnAndWcjafbn(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//全国地图
	List<ZjTzInvXmtzqk> selectHomeRegionalOverviewCountryMap(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//省份地图
	ZjTzInvXmtzqk selectHomeRegionalOverviewProvinceMap(ZjTzInvXmtzqk zjTzInvXmtzqk);
	// 建设页产值情况全时期
	ZjTzInvXmtzqk selectAllPeriod(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//建设页产值情况
	ZjTzInvXmtzqk selectConstructPageProduction(List<String> periodValueList, ZjTzInvXmtzqk zjTzInvXmtzqk);
	
	List<ZjTzInvXmtzqk> selectConstructPageTrendDataByQuarter(ZjTzInvXmtzqk zjTzInvXmtzqk);
	
	ZjTzInvXmtzqk selectConstructPageTrendDataByYear(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//建设页产值排名-权益投资
	ZjTzInvXmtzqk selectConstructPageProductionRankingQytz(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//建设页产值排名-局建安费
	ZjTzInvXmtzqk selectConstructPageProductionRankingJjaf(ZjTzInvXmtzqk zjTzInvXmtzqk);
	//项目页面计划预警
	ZjTzInvXmtzqk selectProjectPagePlanningWarning(ZjTzInvXmtzqk zjTzInvXmtzqk);
}

