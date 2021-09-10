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
import com.apih5.mybatis.pojo.ZjTzInvXmzjqk;
import com.apih5.service.ZjTzInvXmtzqkService;
import com.apih5.service.ZjTzInvXmzjqkService;

import cn.hutool.core.date.DateUtil;

@RestController
public class ZjTzInvXmzjqkController {

    @Autowired(required = true)
    private ZjTzInvXmzjqkService zjTzInvXmzjqkService;
    
    @Autowired(required = true)
    private ZjTzInvXmtzqkService zjTzInvXmtzqkService;

    @ApiOperation(value="查询投资项目_项目资金情况", notes="查询投资项目_项目资金情况")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmzjqkList")
    public ResponseEntity getZjTzInvXmzjqkList(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
        return zjTzInvXmzjqkService.getZjTzInvXmzjqkListByCondition(zjTzInvXmzjqk);
    }

    @ApiOperation(value="查询详情投资项目_项目资金情况", notes="查询详情投资项目_项目资金情况")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmzjqkDetails")
    public ResponseEntity getZjTzInvXmzjqkDetails(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
        return zjTzInvXmzjqkService.getZjTzInvXmzjqkDetails(zjTzInvXmzjqk);
    }

    @ApiOperation(value="新增投资项目_项目资金情况", notes="新增投资项目_项目资金情况")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/addZjTzInvXmzjqk")
    public ResponseEntity addZjTzInvXmzjqk(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
        return zjTzInvXmzjqkService.saveZjTzInvXmzjqk(zjTzInvXmzjqk);
    }

    @ApiOperation(value="更新投资项目_项目资金情况", notes="更新投资项目_项目资金情况")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/updateZjTzInvXmzjqk")
    public ResponseEntity updateZjTzInvXmzjqk(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
        return zjTzInvXmzjqkService.updateZjTzInvXmzjqk(zjTzInvXmzjqk);
    }

    @ApiOperation(value="删除投资项目_项目资金情况", notes="删除投资项目_项目资金情况")
    @ApiImplicitParam(name = "zjTzInvXmzjqkList", value = "投资项目_项目资金情况List", dataType = "List<ZjTzInvXmzjqk>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzInvXmzjqk")
    public ResponseEntity batchDeleteUpdateZjTzInvXmzjqk(@RequestBody(required = false) List<ZjTzInvXmzjqk> zjTzInvXmzjqkList) {
        return zjTzInvXmzjqkService.batchDeleteUpdateZjTzInvXmzjqk(zjTzInvXmzjqkList);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="生成资金情况月报", notes="生成资金情况月报")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmzjqkMonthlyReportList")
    public ResponseEntity getZjTzInvXmzjqkMonthlyReportList(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
    	return zjTzInvXmzjqkService.getZjTzInvXmzjqkMonthlyReportList(zjTzInvXmzjqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="展示资金情况月报", notes="展示资金情况月报")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmzjqkMonthlyReportListBasicData")
    public ResponseEntity getZjTzInvXmzjqkMonthlyReportListBasicData(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
        return zjTzInvXmzjqkService.getZjTzInvXmzjqkMonthlyReportListBasicData(zjTzInvXmzjqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="资金情况月报详情", notes="资金情况月报详情")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/getZjTzInvXmzjqkMonthlyReportListBasicDataDetails")
    public ResponseEntity getZjTzInvXmzjqkMonthlyReportListBasicDataDetails(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
    	return zjTzInvXmzjqkService.getZjTzInvXmzjqkMonthlyReportList(zjTzInvXmzjqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="资金情况月报导出", notes="资金情况月报导出")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/exportZjTzInvXmzjqkMonthlyReportList")
    public List<ZjTzInvXmzjqk> exportZjTzInvXmzjqkMonthlyReportList(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
    	return zjTzInvXmzjqkService.exportZjTzInvXmzjqkMonthlyReportList(zjTzInvXmzjqk);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="首页统计分析资金情况", notes="首页统计分析资金情况")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/getHomeChartCapitalStatus")
    public ResponseEntity getHomeChartCapitalStatus(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
    	return zjTzInvXmzjqkService.getHomeChartCapitalStatus(zjTzInvXmzjqk);
    }
    
    @ApiOperation(value="建设页面资金情况", notes="建设页面资金情况")
    @ApiImplicitParam(name = "zjTzInvXmzjqk", value = "投资项目_项目资金情况entity", dataType = "ZjTzInvXmzjqk")
    @RequireToken
    @PostMapping("/getConstructPageCapital")
    public ResponseEntity getConstructPageCapital(@RequestBody(required = false) ZjTzInvXmzjqk zjTzInvXmzjqk) {
    	return zjTzInvXmzjqkService.getConstructPageCapital(zjTzInvXmzjqk);
    }
}
