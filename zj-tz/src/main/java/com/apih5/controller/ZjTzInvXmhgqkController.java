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
import com.apih5.mybatis.pojo.ZjTzInvXmhgqk;
import com.apih5.service.ZjTzInvXmhgqkService;
import com.apih5.service.ZjTzInvXmtzqkService;

import cn.hutool.core.date.DateUtil;

@RestController
public class ZjTzInvXmhgqkController {

    @Autowired(required = true)
    private ZjTzInvXmhgqkService zjTzInvXmhgqkService;
    
    @Autowired(required = true)
    private ZjTzInvXmtzqkService zjTzInvXmtzqkService;

    @ApiOperation(value="查询投资项目_项目回购情况", notes="查询投资项目_项目回购情况")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmhgqkList")
    public ResponseEntity getZjTzInvXmhgqkList(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
        return zjTzInvXmhgqkService.getZjTzInvXmhgqkListByCondition(zjTzInvXmhgqk);
    }

    @ApiOperation(value="查询详情投资项目_项目回购情况", notes="查询详情投资项目_项目回购情况")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmhgqkDetails")
    public ResponseEntity getZjTzInvXmhgqkDetails(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
        return zjTzInvXmhgqkService.getZjTzInvXmhgqkDetails(zjTzInvXmhgqk);
    }

    @ApiOperation(value="新增投资项目_项目回购情况", notes="新增投资项目_项目回购情况")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/addZjTzInvXmhgqk")
    public ResponseEntity addZjTzInvXmhgqk(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
        return zjTzInvXmhgqkService.saveZjTzInvXmhgqk(zjTzInvXmhgqk);
    }

    @ApiOperation(value="更新投资项目_项目回购情况", notes="更新投资项目_项目回购情况")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/updateZjTzInvXmhgqk")
    public ResponseEntity updateZjTzInvXmhgqk(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
        return zjTzInvXmhgqkService.updateZjTzInvXmhgqk(zjTzInvXmhgqk);
    }

    @ApiOperation(value="删除投资项目_项目回购情况", notes="删除投资项目_项目回购情况")
    @ApiImplicitParam(name = "zjTzInvXmhgqkList", value = "投资项目_项目回购情况List", dataType = "List<ZjTzInvXmhgqk>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzInvXmhgqk")
    public ResponseEntity batchDeleteUpdateZjTzInvXmhgqk(@RequestBody(required = false) List<ZjTzInvXmhgqk> zjTzInvXmhgqkList) {
        return zjTzInvXmhgqkService.batchDeleteUpdateZjTzInvXmhgqk(zjTzInvXmhgqkList);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="生成回购情况月报", notes="生成回购情况月报")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmhgqkMonthlyReportList")
    public ResponseEntity getZjTzInvXmhgqkMonthlyReportList(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
    	return zjTzInvXmhgqkService.getZjTzInvXmhgqkMonthlyReportList(zjTzInvXmhgqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="展示回购情况月报", notes="展示回购情况月报")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmhgqkMonthlyReportListBasicData")
    public ResponseEntity getZjTzInvXmhgqkMonthlyReportListBasicData(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
        return zjTzInvXmhgqkService.getZjTzInvXmhgqkMonthlyReportListBasicData(zjTzInvXmhgqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="回购情况月报详情", notes="回购情况月报详情")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmhgqkMonthlyReportListBasicDataDetails")
    public ResponseEntity getZjTzInvXmhgqkMonthlyReportListBasicDataDetails(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
    	return zjTzInvXmhgqkService.getZjTzInvXmhgqkMonthlyReportList(zjTzInvXmhgqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="回购情况月报导出", notes="回购情况月报导出")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/exportZjTzInvXmhgqkMonthlyReportList")
    public List<ZjTzInvXmhgqk> exportZjTzInvXmhgqkMonthlyReportList(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
    	return zjTzInvXmhgqkService.exportZjTzInvXmhgqkMonthlyReportList(zjTzInvXmhgqk);
    }
 // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="首页图表数据回购数据", notes="首页图表数据回购数据")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/getHomeChartHgData")
    public ResponseEntity getHomeChartHgData(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
    	return zjTzInvXmhgqkService.getHomeChartHgData(zjTzInvXmhgqk);
    }
    
    @ApiOperation(value="回购页面回购情况", notes="回购页面回购情况")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/getHgPageHgStatus")
    public ResponseEntity getHgPageHgStatus(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
    	return zjTzInvXmhgqkService.getHgPageHgStatus(zjTzInvXmhgqk);
    }
    
    @ApiOperation(value="回购页面当前时期", notes="回购页面当前时期")
    @ApiImplicitParam(name = "zjTzInvXmhgqk", value = "投资项目_项目回购情况entity", dataType = "ZjTzInvXmhgqk")
    @RequireToken
    @PostMapping("/getHgPageCurrentPeriod")
    public ResponseEntity getHgPageCurrentPeriod(@RequestBody(required = false) ZjTzInvXmhgqk zjTzInvXmhgqk) {
    	return zjTzInvXmhgqkService.getHgPageCurrentPeriod(zjTzInvXmhgqk);
    }
}
