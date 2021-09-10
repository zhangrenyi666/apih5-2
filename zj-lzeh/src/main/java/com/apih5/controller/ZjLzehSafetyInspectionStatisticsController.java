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
import com.apih5.mybatis.pojo.ZjLzehSafetyInspectionStatistics;
import com.apih5.service.ZjLzehSafetyInspectionStatisticsService;

@RestController
public class ZjLzehSafetyInspectionStatisticsController {

    @Autowired(required = true)
    private ZjLzehSafetyInspectionStatisticsService zjLzehSafetyInspectionStatisticsService;

    @ApiOperation(value="查询安全检查统计", notes="查询安全检查统计")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionStatistics", value = "安全检查统计entity", dataType = "ZjLzehSafetyInspectionStatistics")
    @RequireToken
    @PostMapping("/getZjLzehSafetyInspectionStatisticsList")
    public ResponseEntity getZjLzehSafetyInspectionStatisticsList(@RequestBody(required = false) ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics) {
        return zjLzehSafetyInspectionStatisticsService.getZjLzehSafetyInspectionStatisticsListByCondition(zjLzehSafetyInspectionStatistics);
    }

    @ApiOperation(value="查询详情安全检查统计", notes="查询详情安全检查统计")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionStatistics", value = "安全检查统计entity", dataType = "ZjLzehSafetyInspectionStatistics")
    @RequireToken
    @PostMapping("/getZjLzehSafetyInspectionStatisticsDetails")
    public ResponseEntity getZjLzehSafetyInspectionStatisticsDetails(@RequestBody(required = false) ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics) {
        return zjLzehSafetyInspectionStatisticsService.getZjLzehSafetyInspectionStatisticsDetails(zjLzehSafetyInspectionStatistics);
    }

    @ApiOperation(value="新增安全检查统计", notes="新增安全检查统计")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionStatistics", value = "安全检查统计entity", dataType = "ZjLzehSafetyInspectionStatistics")
    @RequireToken
    @PostMapping("/addZjLzehSafetyInspectionStatistics")
    public ResponseEntity addZjLzehSafetyInspectionStatistics(@RequestBody(required = false) ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics) {
        return zjLzehSafetyInspectionStatisticsService.saveZjLzehSafetyInspectionStatistics(zjLzehSafetyInspectionStatistics);
    }

    @ApiOperation(value="更新安全检查统计", notes="更新安全检查统计")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionStatistics", value = "安全检查统计entity", dataType = "ZjLzehSafetyInspectionStatistics")
    @RequireToken
    @PostMapping("/updateZjLzehSafetyInspectionStatistics")
    public ResponseEntity updateZjLzehSafetyInspectionStatistics(@RequestBody(required = false) ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics) {
        return zjLzehSafetyInspectionStatisticsService.updateZjLzehSafetyInspectionStatistics(zjLzehSafetyInspectionStatistics);
    }

    @ApiOperation(value="删除安全检查统计", notes="删除安全检查统计")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionStatisticsList", value = "安全检查统计List", dataType = "List<ZjLzehSafetyInspectionStatistics>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehSafetyInspectionStatistics")
    public ResponseEntity batchDeleteUpdateZjLzehSafetyInspectionStatistics(@RequestBody(required = false) List<ZjLzehSafetyInspectionStatistics> zjLzehSafetyInspectionStatisticsList) {
        return zjLzehSafetyInspectionStatisticsService.batchDeleteUpdateZjLzehSafetyInspectionStatistics(zjLzehSafetyInspectionStatisticsList);
    }

}
