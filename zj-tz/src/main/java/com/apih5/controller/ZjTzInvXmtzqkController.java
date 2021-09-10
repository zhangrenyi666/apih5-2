package com.apih5.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzInvXmtzqk;
import com.apih5.service.ZjTzInvXmtzqkService;

@RestController
public class ZjTzInvXmtzqkController {

    @Autowired(required = true)
    private ZjTzInvXmtzqkService zjTzInvXmtzqkService;

    @ApiOperation(value="查询投资项目_项目投资情况", notes="查询投资项目_项目投资情况")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmtzqkList")
    public ResponseEntity getZjTzInvXmtzqkList(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
        return zjTzInvXmtzqkService.getZjTzInvXmtzqkListByCondition(zjTzInvXmtzqk);
    }

    @ApiOperation(value="查询详情投资项目_项目投资情况", notes="查询详情投资项目_项目投资情况")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmtzqkDetails")
    public ResponseEntity getZjTzInvXmtzqkDetails(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
        return zjTzInvXmtzqkService.getZjTzInvXmtzqkDetails(zjTzInvXmtzqk);
    }

    @ApiOperation(value="新增投资项目_项目投资情况", notes="新增投资项目_项目投资情况")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/addZjTzInvXmtzqk")
    public ResponseEntity addZjTzInvXmtzqk(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
        return zjTzInvXmtzqkService.saveZjTzInvXmtzqk(zjTzInvXmtzqk);
    }

    @ApiOperation(value="更新投资项目_项目投资情况", notes="更新投资项目_项目投资情况")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/updateZjTzInvXmtzqk")
    public ResponseEntity updateZjTzInvXmtzqk(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
        return zjTzInvXmtzqkService.updateZjTzInvXmtzqk(zjTzInvXmtzqk);
    }

    @ApiOperation(value="删除投资项目_项目投资情况", notes="删除投资项目_项目投资情况")
    @ApiImplicitParam(name = "zjTzInvXmtzqkList", value = "投资项目_项目投资情况List", dataType = "List<ZjTzInvXmtzqk>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzInvXmtzqk")
    public ResponseEntity batchDeleteUpdateZjTzInvXmtzqk(@RequestBody(required = false) List<ZjTzInvXmtzqk> zjTzInvXmtzqkList) {
        return zjTzInvXmtzqkService.batchDeleteUpdateZjTzInvXmtzqk(zjTzInvXmtzqkList);
    }
    
 // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="生成投资完成情况月报", notes="生成投资完成情况月报")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmtzqkMonthlyReportList")
    public ResponseEntity getZjTzInvXmtzqkMonthlyReportList(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getZjTzInvXmtzqkMonthlyReportList(zjTzInvXmtzqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="展示投资完成情况月报", notes="展示投资完成情况月报")
    //@ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmtzqkMonthlyReportListBasicData")
    public ResponseEntity getZjTzInvXmtzqkMonthlyReportListBasicData(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
        return zjTzInvXmtzqkService.getZjTzInvXmtzqkMonthlyReportListBasicData(zjTzInvXmtzqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="投资完成情况月报详情", notes="投资完成情况月报详情")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmtzqkMonthlyReportListBasicDataDetails")
    public ResponseEntity getZjTzInvXmtzqkMonthlyReportListBasicDataDetails(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getZjTzInvXmtzqkMonthlyReportList(zjTzInvXmtzqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="投资完成月报导出", notes="投资完成月报导出")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/exportZjTzInvXmtzqkMonthlyReportList")
    public List<ZjTzInvXmtzqk> exportZjTzInvXmtzqkMonthlyReportList(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.exportZjTzInvXmtzqkMonthlyReportList(zjTzInvXmtzqk);
    }
    
 // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="首页进度预警计划进度", notes="首页进度预警计划进度")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getHomeProgressWarningPlanningProgress")
    public ResponseEntity getHomeProgressWarningPlanningProgress(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getHomeProgressWarningPlanningProgress(zjTzInvXmtzqk);
    }
    
    @ApiOperation(value="导出首页进度预警计划进度", notes="导出首页进度预警计划进度")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
//    @RequireToken
    @PostMapping("/exportHomeProgressWarningPlanningProgress")
    public ResponseEntity exportHomeProgressWarningPlanningProgress(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk, HttpServletResponse response) {
    	return zjTzInvXmtzqkService.exportHomeProgressWarningPlanningProgress(zjTzInvXmtzqk, response);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="首页进度预警考核排名", notes="首页进度预警考核排名")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getHomeProgressWarningChecking")
    public ResponseEntity getHomeProgressWarningChecking(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getHomeProgressWarningChecking(zjTzInvXmtzqk);
    }
    
    @ApiOperation(value="首页进度预警预警信息", notes="首页进度预警预警信息")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getHomeProgressWarningInfo")
    public ResponseEntity getHomeProgressWarningInfo(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getHomeProgressWarningInfo(zjTzInvXmtzqk);
    }
    
    @ApiOperation(value="首页进度预警年度目标完成情况", notes="首页进度预警年度目标完成情况")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getHomeProgressWarningCompleteStatus")
    public ResponseEntity getHomeProgressWarningCompleteStatus(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getHomeProgressWarningCompleteStatus(zjTzInvXmtzqk);
    }
    @ApiOperation(value="首页图表数据项目管理单位统计", notes="首页图表数据项目管理单位统计")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getHomeChartComnameStatis")
    public ResponseEntity getHomeChartComnameStatis(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getHomeChartComnameStatis(zjTzInvXmtzqk);
    }
    @ApiOperation(value="首页图表数据趋势数据", notes="首页图表数据趋势数据")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getHomeChartTrendData")
    public ResponseEntity getHomeChartTrendData(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getHomeChartTrendData(zjTzInvXmtzqk);
    }
    @ApiOperation(value="首页地域总览", notes="首页地域总览")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getHomeRegionalOverviewZhtAndZja")
    public ResponseEntity getHomeRegionalOverviewZhtAndZja(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getHomeRegionalOverviewZhtAndZja(zjTzInvXmtzqk);
    }
    @ApiOperation(value="首页地域总览全国地图", notes="首页地域总览全国地图")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getHomeRegionalOverviewMap")
    public ResponseEntity getHomeRegionalOverviewMap(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getHomeRegionalOverviewMap(zjTzInvXmtzqk);
    }
    
    @ApiOperation(value="建设页面产值情况", notes="建设页面产值情况")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getConstructPageProduction")
    public ResponseEntity getConstructPageProduction(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getConstructPageProduction(zjTzInvXmtzqk);
    }
    
    @ApiOperation(value="建设页面趋势数据", notes="建设页面趋势数据")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getConstructPageTrendData")
    public ResponseEntity getConstructPageTrendData(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getConstructPageTrendData(zjTzInvXmtzqk);
    }
    
    @ApiOperation(value="建设页面产值排名", notes="建设页面产值排名")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getConstructPageProductionRanking")
    public ResponseEntity getConstructPageProductionRanking(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getConstructPageProductionRanking(zjTzInvXmtzqk);
    }
    
    @ApiOperation(value="项目页面预警", notes="项目页面预警")
    @ApiImplicitParam(name = "zjTzInvXmtzqk", value = "投资项目_项目投资情况entity", dataType = "ZjTzInvXmtzqk")
    @RequireToken
    @PostMapping("/getProjectPageWarning")
    public ResponseEntity getProjectPageWarning(@RequestBody(required = false) ZjTzInvXmtzqk zjTzInvXmtzqk) {
    	return zjTzInvXmtzqkService.getProjectPageWarning(zjTzInvXmtzqk);
    }
}
