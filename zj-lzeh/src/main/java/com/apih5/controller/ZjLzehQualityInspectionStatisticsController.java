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
import com.apih5.mybatis.pojo.ZjLzehQualityInspectionStatistics;
import com.apih5.service.ZjLzehQualityInspectionStatisticsService;

@RestController
public class ZjLzehQualityInspectionStatisticsController {

    @Autowired(required = true)
    private ZjLzehQualityInspectionStatisticsService zjLzehQualityInspectionStatisticsService;

    @ApiOperation(value="查询质量检查统计", notes="查询质量检查统计")
    @ApiImplicitParam(name = "zjLzehQualityInspectionStatistics", value = "质量检查统计entity", dataType = "ZjLzehQualityInspectionStatistics")
    @RequireToken
    @PostMapping("/getZjLzehQualityInspectionStatisticsList")
    public ResponseEntity getZjLzehQualityInspectionStatisticsList(@RequestBody(required = false) ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics) {
        return zjLzehQualityInspectionStatisticsService.getZjLzehQualityInspectionStatisticsListByCondition(zjLzehQualityInspectionStatistics);
    }

    @ApiOperation(value="查询详情质量检查统计", notes="查询详情质量检查统计")
    @ApiImplicitParam(name = "zjLzehQualityInspectionStatistics", value = "质量检查统计entity", dataType = "ZjLzehQualityInspectionStatistics")
    @RequireToken
    @PostMapping("/getZjLzehQualityInspectionStatisticsDetails")
    public ResponseEntity getZjLzehQualityInspectionStatisticsDetails(@RequestBody(required = false) ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics) {
        return zjLzehQualityInspectionStatisticsService.getZjLzehQualityInspectionStatisticsDetails(zjLzehQualityInspectionStatistics);
    }

    @ApiOperation(value="新增质量检查统计", notes="新增质量检查统计")
    @ApiImplicitParam(name = "zjLzehQualityInspectionStatistics", value = "质量检查统计entity", dataType = "ZjLzehQualityInspectionStatistics")
    @RequireToken
    @PostMapping("/addZjLzehQualityInspectionStatistics")
    public ResponseEntity addZjLzehQualityInspectionStatistics(@RequestBody(required = false) ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics) {
        return zjLzehQualityInspectionStatisticsService.saveZjLzehQualityInspectionStatistics(zjLzehQualityInspectionStatistics);
    }

    @ApiOperation(value="更新质量检查统计", notes="更新质量检查统计")
    @ApiImplicitParam(name = "zjLzehQualityInspectionStatistics", value = "质量检查统计entity", dataType = "ZjLzehQualityInspectionStatistics")
    @RequireToken
    @PostMapping("/updateZjLzehQualityInspectionStatistics")
    public ResponseEntity updateZjLzehQualityInspectionStatistics(@RequestBody(required = false) ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics) {
        return zjLzehQualityInspectionStatisticsService.updateZjLzehQualityInspectionStatistics(zjLzehQualityInspectionStatistics);
    }

    @ApiOperation(value="删除质量检查统计", notes="删除质量检查统计")
    @ApiImplicitParam(name = "zjLzehQualityInspectionStatisticsList", value = "质量检查统计List", dataType = "List<ZjLzehQualityInspectionStatistics>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehQualityInspectionStatistics")
    public ResponseEntity batchDeleteUpdateZjLzehQualityInspectionStatistics(@RequestBody(required = false) List<ZjLzehQualityInspectionStatistics> zjLzehQualityInspectionStatisticsList) {
        return zjLzehQualityInspectionStatisticsService.batchDeleteUpdateZjLzehQualityInspectionStatistics(zjLzehQualityInspectionStatisticsList);
    }

}
