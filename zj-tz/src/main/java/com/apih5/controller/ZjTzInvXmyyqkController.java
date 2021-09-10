package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzInvXmyyqk;
import com.apih5.service.ZjTzInvXmyyqkService;

@RestController
public class ZjTzInvXmyyqkController {

    @Autowired(required = true)
    private ZjTzInvXmyyqkService zjTzInvXmyyqkService;

    @ApiOperation(value="查询投资项目_项目运营情况", notes="查询投资项目_项目运营情况")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmyyqkList")
    public ResponseEntity getZjTzInvXmyyqkList(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
        return zjTzInvXmyyqkService.getZjTzInvXmyyqkListByCondition(zjTzInvXmyyqk);
    }

    @ApiOperation(value="查询详情投资项目_项目运营情况", notes="查询详情投资项目_项目运营情况")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmyyqkDetails")
    public ResponseEntity getZjTzInvXmyyqkDetails(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
        return zjTzInvXmyyqkService.getZjTzInvXmyyqkDetails(zjTzInvXmyyqk);
    }

    @ApiOperation(value="新增投资项目_项目运营情况", notes="新增投资项目_项目运营情况")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/addZjTzInvXmyyqk")
    public ResponseEntity addZjTzInvXmyyqk(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
        return zjTzInvXmyyqkService.saveZjTzInvXmyyqk(zjTzInvXmyyqk);
    }

    @ApiOperation(value="更新投资项目_项目运营情况", notes="更新投资项目_项目运营情况")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/updateZjTzInvXmyyqk")
    public ResponseEntity updateZjTzInvXmyyqk(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
        return zjTzInvXmyyqkService.updateZjTzInvXmyyqk(zjTzInvXmyyqk);
    }

    @ApiOperation(value="删除投资项目_项目运营情况", notes="删除投资项目_项目运营情况")
    @ApiImplicitParam(name = "zjTzInvXmyyqkList", value = "投资项目_项目运营情况List", dataType = "List<ZjTzInvXmyyqk>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzInvXmyyqk")
    public ResponseEntity batchDeleteUpdateZjTzInvXmyyqk(@RequestBody(required = false) List<ZjTzInvXmyyqk> zjTzInvXmyyqkList) {
        return zjTzInvXmyyqkService.batchDeleteUpdateZjTzInvXmyyqk(zjTzInvXmyyqkList);
    }
    
    @ApiOperation(value="项目运营情况基础数据列表", notes="项目运营情况基础数据列表")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmyyqkMonthlyReportListBasicData")
    public ResponseEntity getZjTzInvXmyyqkMonthlyReportListBasicData(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
        return zjTzInvXmyyqkService.getZjTzInvXmyyqkMonthlyReportListBasicData(zjTzInvXmyyqk);
    }
    
    @ApiOperation(value="项目运营情况基础数据详情", notes="项目运营情况基础数据详情")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmyyqkMonthlyReportListBasicDataDetail")
    public ResponseEntity getZjTzInvXmyyqkMonthlyReportListBasicDataDetail(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
    	return zjTzInvXmyyqkService.getZjTzInvXmyyqkMonthlyReportListBasicDataDetail(zjTzInvXmyyqk);
    }
    
    @ApiOperation(value="首页统计分析运营数据", notes="首页统计分析运营数据")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/getHomeChartOperateData")
    public ResponseEntity getHomeChartOperateData(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
    	return zjTzInvXmyyqkService.getHomeChartOperateData(zjTzInvXmyyqk);
    }
    
    @ApiOperation(value="运营页面投评与收入情况", notes="运营页面投评与收入情况")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/getOperatePageCommentAndIncome")
    public ResponseEntity getOperatePageCommentAndIncome(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
    	return zjTzInvXmyyqkService.getOperatePageCommentAndIncome(zjTzInvXmyyqk);
    }
    
    @ApiOperation(value="运营页面当前时期", notes="运营页面当前时期")
    @ApiImplicitParam(name = "zjTzInvXmyyqk", value = "投资项目_项目运营情况entity", dataType = "ZjTzInvXmyyqk")
    @RequireToken
    @PostMapping("/getOperatePageCurrentPeriod")
    public ResponseEntity getOperatePageCurrentPeriod(@RequestBody(required = false) ZjTzInvXmyyqk zjTzInvXmyyqk) {
    	return zjTzInvXmyyqkService.getOperatePageCurrentPeriod(zjTzInvXmyyqk);
    }
}
