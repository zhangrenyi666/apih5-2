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
import com.apih5.mybatis.pojo.ZxEqEquipIntegratedQuery;
import com.apih5.mybatis.pojo.ZxEqToCurrentAnalyseQueryPageChart;
import com.apih5.service.ZxEqToCurrentAnalyseQueryPageChartService;

@RestController
public class ZxEqToCurrentAnalyseQueryPageChartController {

    @Autowired(required = true)
    private ZxEqToCurrentAnalyseQueryPageChartService zxEqToCurrentAnalyseQueryPageChartService;

    @ApiOperation(value="查询五年趋势分析图表", notes="查询五年趋势分析图表")
    @ApiImplicitParam(name = "zxEqToCurrentAnalyseQueryPageChart", value = "五年趋势分析图表entity", dataType = "ZxEqToCurrentAnalyseQueryPageChart")
    @RequireToken
    @PostMapping("/getZxEqToCurrentAnalyseQueryPageChartList")
    public ResponseEntity getZxEqToCurrentAnalyseQueryPageChartList(@RequestBody(required = false) ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        return zxEqToCurrentAnalyseQueryPageChartService.getZxEqToCurrentAnalyseQueryPageChartListByCondition(zxEqToCurrentAnalyseQueryPageChart);
    }

    @ApiOperation(value="查询详情五年趋势分析图表", notes="查询详情五年趋势分析图表")
    @ApiImplicitParam(name = "zxEqToCurrentAnalyseQueryPageChart", value = "五年趋势分析图表entity", dataType = "ZxEqToCurrentAnalyseQueryPageChart")
    @RequireToken
    @PostMapping("/getZxEqToCurrentAnalyseQueryPageChartDetail")
    public ResponseEntity getZxEqToCurrentAnalyseQueryPageChartDetail(@RequestBody(required = false) ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        return zxEqToCurrentAnalyseQueryPageChartService.getZxEqToCurrentAnalyseQueryPageChartDetail(zxEqToCurrentAnalyseQueryPageChart);
    }

    @ApiOperation(value="新增五年趋势分析图表", notes="新增五年趋势分析图表")
    @ApiImplicitParam(name = "zxEqToCurrentAnalyseQueryPageChart", value = "五年趋势分析图表entity", dataType = "ZxEqToCurrentAnalyseQueryPageChart")
    @RequireToken
    @PostMapping("/addZxEqToCurrentAnalyseQueryPageChart")
    public ResponseEntity addZxEqToCurrentAnalyseQueryPageChart(@RequestBody(required = false) ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        return zxEqToCurrentAnalyseQueryPageChartService.saveZxEqToCurrentAnalyseQueryPageChart(zxEqToCurrentAnalyseQueryPageChart);
    }

    @ApiOperation(value="更新五年趋势分析图表", notes="更新五年趋势分析图表")
    @ApiImplicitParam(name = "zxEqToCurrentAnalyseQueryPageChart", value = "五年趋势分析图表entity", dataType = "ZxEqToCurrentAnalyseQueryPageChart")
    @RequireToken
    @PostMapping("/updateZxEqToCurrentAnalyseQueryPageChart")
    public ResponseEntity updateZxEqToCurrentAnalyseQueryPageChart(@RequestBody(required = false) ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        return zxEqToCurrentAnalyseQueryPageChartService.updateZxEqToCurrentAnalyseQueryPageChart(zxEqToCurrentAnalyseQueryPageChart);
    }

    @ApiOperation(value="删除五年趋势分析图表", notes="删除五年趋势分析图表")
    @ApiImplicitParam(name = "zxEqToCurrentAnalyseQueryPageChartList", value = "五年趋势分析图表List", dataType = "List<ZxEqToCurrentAnalyseQueryPageChart>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqToCurrentAnalyseQueryPageChart")
    public ResponseEntity batchDeleteUpdateZxEqToCurrentAnalyseQueryPageChart(@RequestBody(required = false) List<ZxEqToCurrentAnalyseQueryPageChart> zxEqToCurrentAnalyseQueryPageChartList) {
        return zxEqToCurrentAnalyseQueryPageChartService.batchDeleteUpdateZxEqToCurrentAnalyseQueryPageChart(zxEqToCurrentAnalyseQueryPageChartList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表五年趋势分析图表", notes="报表五年趋势分析图表")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "设备台账entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/ureportZxEqToCurrentAnalyseQueryPageChartIdle")
    public ResponseEntity ureportZxEqToCurrentAnalyseQueryPageChartIdle(@RequestBody(required = false) ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        return zxEqToCurrentAnalyseQueryPageChartService.ureportZxEqToCurrentAnalyseQueryPageChartIdle(zxEqToCurrentAnalyseQueryPageChart);
    }
    
    @ApiOperation(value="报表五年趋势分析表", notes="报表五年趋势分析表")
    @ApiImplicitParam(name = "zxEqEquipIntegratedQuery", value = "设备台账entity", dataType = "ZxEqEquipIntegratedQuery")
    @RequireToken
    @PostMapping("/ureportZxEqToCurrentAnalyseQueryPageIdle")
    public ResponseEntity ureportZxEqToCurrentAnalyseQueryPageIdle(@RequestBody(required = false) ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        return zxEqToCurrentAnalyseQueryPageChartService.ureportZxEqToCurrentAnalyseQueryPageIdle(zxEqToCurrentAnalyseQueryPageChart);
    }
}
