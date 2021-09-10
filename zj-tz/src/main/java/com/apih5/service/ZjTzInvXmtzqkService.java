package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzInvXmtzqk;

public interface ZjTzInvXmtzqkService {

    public ResponseEntity getZjTzInvXmtzqkListByCondition(ZjTzInvXmtzqk zjTzInvXmtzqk);

    public ResponseEntity getZjTzInvXmtzqkDetails(ZjTzInvXmtzqk zjTzInvXmtzqk);

    public ResponseEntity saveZjTzInvXmtzqk(ZjTzInvXmtzqk zjTzInvXmtzqk);

    public ResponseEntity updateZjTzInvXmtzqk(ZjTzInvXmtzqk zjTzInvXmtzqk);

    public ResponseEntity batchDeleteUpdateZjTzInvXmtzqk(List<ZjTzInvXmtzqk> zjTzInvXmtzqkList);
    
    public ResponseEntity getZjTzInvXmtzqkMonthlyReportList(ZjTzInvXmtzqk zjTzInvXmtzqk);
    
    public ResponseEntity getZjTzInvXmtzqkMonthlyReportListBasicData(ZjTzInvXmtzqk zjTzInvXmtzqk);
    
    public List<ZjTzInvXmtzqk> exportZjTzInvXmtzqkMonthlyReportList(ZjTzInvXmtzqk zjTzInvXmtzqk);
    
    public ResponseEntity getHomeProgressWarningPlanningProgress(ZjTzInvXmtzqk zjTzInvXmtzqk);
    
    public ResponseEntity getHomeProgressWarningChecking(ZjTzInvXmtzqk zjTzInvXmtzqk);
    //预警信息
	public ResponseEntity getHomeProgressWarningInfo(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity getHomeProgressWarningCompleteStatus(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity getHomeChartComnameStatis(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity getHomeRegionalOverviewZhtAndZja(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity getHomeRegionalOverviewMap(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity getConstructPageProduction(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity getConstructPageTrendData(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity getHomeChartTrendData(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity getConstructPageProductionRanking(ZjTzInvXmtzqk zjTzInvXmtzqk);

	public ResponseEntity exportHomeProgressWarningPlanningProgress(ZjTzInvXmtzqk zjTzInvXmtzqk,
			HttpServletResponse response);

	public ResponseEntity getProjectPageWarning(ZjTzInvXmtzqk zjTzInvXmtzqk);
}

