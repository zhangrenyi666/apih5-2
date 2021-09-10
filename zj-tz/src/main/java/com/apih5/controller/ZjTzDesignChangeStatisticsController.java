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
import com.apih5.mybatis.pojo.ZjTzDesignChangeStatistics;
import com.apih5.service.ZjTzDesignChangeStatisticsService;

@RestController
public class ZjTzDesignChangeStatisticsController {

    @Autowired(required = true)
    private ZjTzDesignChangeStatisticsService zjTzDesignChangeStatisticsService;

    @ApiOperation(value="查询变更统计", notes="查询变更统计")
    @ApiImplicitParam(name = "zjTzDesignChangeStatistics", value = "变更统计entity", dataType = "ZjTzDesignChangeStatistics")
    @RequireToken
    @PostMapping("/getZjTzDesignChangeStatisticsList")
    public ResponseEntity getZjTzDesignChangeStatisticsList(@RequestBody(required = false) ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        return zjTzDesignChangeStatisticsService.getZjTzDesignChangeStatisticsListByCondition(zjTzDesignChangeStatistics);
    }

    @ApiOperation(value="查询详情变更统计", notes="查询详情变更统计")
    @ApiImplicitParam(name = "zjTzDesignChangeStatistics", value = "变更统计entity", dataType = "ZjTzDesignChangeStatistics")
    @RequireToken
    @PostMapping("/getZjTzDesignChangeStatisticsDetails")
    public ResponseEntity getZjTzDesignChangeStatisticsDetails(@RequestBody(required = false) ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        return zjTzDesignChangeStatisticsService.getZjTzDesignChangeStatisticsDetails(zjTzDesignChangeStatistics);
    }

    @ApiOperation(value="新增变更统计", notes="新增变更统计")
    @ApiImplicitParam(name = "zjTzDesignChangeStatistics", value = "变更统计entity", dataType = "ZjTzDesignChangeStatistics")
    @RequireToken
    @PostMapping("/addZjTzDesignChangeStatistics")
    public ResponseEntity addZjTzDesignChangeStatistics(@RequestBody(required = false) ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        return zjTzDesignChangeStatisticsService.saveZjTzDesignChangeStatistics(zjTzDesignChangeStatistics);
    }

    @ApiOperation(value="更新变更统计", notes="更新变更统计")
    @ApiImplicitParam(name = "zjTzDesignChangeStatistics", value = "变更统计entity", dataType = "ZjTzDesignChangeStatistics")
    @RequireToken
    @PostMapping("/updateZjTzDesignChangeStatistics")
    public ResponseEntity updateZjTzDesignChangeStatistics(@RequestBody(required = false) ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        return zjTzDesignChangeStatisticsService.updateZjTzDesignChangeStatistics(zjTzDesignChangeStatistics);
    }

    @ApiOperation(value="删除变更统计", notes="删除变更统计")
    @ApiImplicitParam(name = "zjTzDesignChangeStatisticsList", value = "变更统计List", dataType = "List<ZjTzDesignChangeStatistics>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDesignChangeStatistics")
    public ResponseEntity batchDeleteUpdateZjTzDesignChangeStatistics(@RequestBody(required = false) List<ZjTzDesignChangeStatistics> zjTzDesignChangeStatisticsList) {
        return zjTzDesignChangeStatisticsService.batchDeleteUpdateZjTzDesignChangeStatistics(zjTzDesignChangeStatisticsList);
    }
    
    @ApiOperation(value="报表导出变更统计", notes="报表导出变更统计")
    @ApiImplicitParam(name = "zjTzDesignChangeStatistics", value = "变更统计entity", dataType = "ZjTzDesignChangeStatistics")
    @PostMapping("/reportZjTzDesignChangeStatisticsList")
    public List<ZjTzDesignChangeStatistics> reportZjTzDesignChangeStatisticsList(@RequestBody(required = false) ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        return zjTzDesignChangeStatisticsService.reportZjTzDesignChangeStatisticsList(zjTzDesignChangeStatistics);
    }

}
