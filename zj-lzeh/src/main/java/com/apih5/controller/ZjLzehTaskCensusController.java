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
import com.apih5.mybatis.pojo.ZjLzehTaskCensus;
import com.apih5.service.ZjLzehTaskCensusService;

@RestController
public class ZjLzehTaskCensusController {

    @Autowired(required = true)
    private ZjLzehTaskCensusService zjLzehTaskCensusService;

    @ApiOperation(value="查询任务统计", notes="查询任务统计")
    @ApiImplicitParam(name = "zjLzehTaskCensus", value = "任务统计entity", dataType = "ZjLzehTaskCensus")
    @RequireToken
    @PostMapping("/getZjLzehTaskCensusList")
    public ResponseEntity getZjLzehTaskCensusList(@RequestBody(required = false) ZjLzehTaskCensus zjLzehTaskCensus) {
        return zjLzehTaskCensusService.getZjLzehTaskCensusListByCondition(zjLzehTaskCensus);
    }

    @ApiOperation(value="查询详情任务统计", notes="查询详情任务统计")
    @ApiImplicitParam(name = "zjLzehTaskCensus", value = "任务统计entity", dataType = "ZjLzehTaskCensus")
    @RequireToken
    @PostMapping("/getZjLzehTaskCensusDetail")
    public ResponseEntity getZjLzehTaskCensusDetail(@RequestBody(required = false) ZjLzehTaskCensus zjLzehTaskCensus) {
        return zjLzehTaskCensusService.getZjLzehTaskCensusDetail(zjLzehTaskCensus);
    }

    @ApiOperation(value="新增任务统计", notes="新增任务统计")
    @ApiImplicitParam(name = "zjLzehTaskCensus", value = "任务统计entity", dataType = "ZjLzehTaskCensus")
    @RequireToken
    @PostMapping("/addZjLzehTaskCensus")
    public ResponseEntity addZjLzehTaskCensus(@RequestBody(required = false) ZjLzehTaskCensus zjLzehTaskCensus) {
        return zjLzehTaskCensusService.saveZjLzehTaskCensus(zjLzehTaskCensus);
    }

    @ApiOperation(value="更新任务统计", notes="更新任务统计")
    @ApiImplicitParam(name = "zjLzehTaskCensus", value = "任务统计entity", dataType = "ZjLzehTaskCensus")
    @RequireToken
    @PostMapping("/updateZjLzehTaskCensus")
    public ResponseEntity updateZjLzehTaskCensus(@RequestBody(required = false) ZjLzehTaskCensus zjLzehTaskCensus) {
        return zjLzehTaskCensusService.updateZjLzehTaskCensus(zjLzehTaskCensus);
    }

    @ApiOperation(value="删除任务统计", notes="删除任务统计")
    @ApiImplicitParam(name = "zjLzehTaskCensusList", value = "任务统计List", dataType = "List<ZjLzehTaskCensus>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehTaskCensus")
    public ResponseEntity batchDeleteUpdateZjLzehTaskCensus(@RequestBody(required = false) List<ZjLzehTaskCensus> zjLzehTaskCensusList) {
        return zjLzehTaskCensusService.batchDeleteUpdateZjLzehTaskCensus(zjLzehTaskCensusList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
