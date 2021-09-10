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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDeputyLeaderAssistantDetailed;
import com.apih5.service.ZjXmCqjxProjectDeputyLeaderAssistantDetailedService;

@RestController
public class ZjXmCqjxProjectDeputyLeaderAssistantDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxProjectDeputyLeaderAssistantDetailedService zjXmCqjxProjectDeputyLeaderAssistantDetailedService;

    @ApiOperation(value="查询领导班子项目绩效考核明细", notes="查询领导班子项目绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDeputyLeaderAssistantDetailed", value = "领导班子项目绩效考核明细entity", dataType = "ZjXmCqjxProjectDeputyLeaderAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDeputyLeaderAssistantDetailedList")
    public ResponseEntity getZjXmCqjxProjectDeputyLeaderAssistantDetailedList(@RequestBody(required = false) ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed) {
        return zjXmCqjxProjectDeputyLeaderAssistantDetailedService.getZjXmCqjxProjectDeputyLeaderAssistantDetailedListByCondition(zjXmCqjxProjectDeputyLeaderAssistantDetailed);
    }

    @ApiOperation(value="查询详情领导班子项目绩效考核明细", notes="查询详情领导班子项目绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDeputyLeaderAssistantDetailed", value = "领导班子项目绩效考核明细entity", dataType = "ZjXmCqjxProjectDeputyLeaderAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDeputyLeaderAssistantDetailedDetails")
    public ResponseEntity getZjXmCqjxProjectDeputyLeaderAssistantDetailedDetails(@RequestBody(required = false) ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed) {
        return zjXmCqjxProjectDeputyLeaderAssistantDetailedService.getZjXmCqjxProjectDeputyLeaderAssistantDetailedDetails(zjXmCqjxProjectDeputyLeaderAssistantDetailed);
    }

    @ApiOperation(value="新增领导班子项目绩效考核明细", notes="新增领导班子项目绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDeputyLeaderAssistantDetailed", value = "领导班子项目绩效考核明细entity", dataType = "ZjXmCqjxProjectDeputyLeaderAssistantDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectDeputyLeaderAssistantDetailed")
    public ResponseEntity addZjXmCqjxProjectDeputyLeaderAssistantDetailed(@RequestBody(required = false) ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed) {
        return zjXmCqjxProjectDeputyLeaderAssistantDetailedService.saveZjXmCqjxProjectDeputyLeaderAssistantDetailed(zjXmCqjxProjectDeputyLeaderAssistantDetailed);
    }

    @ApiOperation(value="更新领导班子项目绩效考核明细", notes="更新领导班子项目绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDeputyLeaderAssistantDetailed", value = "领导班子项目绩效考核明细entity", dataType = "ZjXmCqjxProjectDeputyLeaderAssistantDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectDeputyLeaderAssistantDetailed")
    public ResponseEntity updateZjXmCqjxProjectDeputyLeaderAssistantDetailed(@RequestBody(required = false) ZjXmCqjxProjectDeputyLeaderAssistantDetailed zjXmCqjxProjectDeputyLeaderAssistantDetailed) {
        return zjXmCqjxProjectDeputyLeaderAssistantDetailedService.updateZjXmCqjxProjectDeputyLeaderAssistantDetailed(zjXmCqjxProjectDeputyLeaderAssistantDetailed);
    }

    @ApiOperation(value="删除领导班子项目绩效考核明细", notes="删除领导班子项目绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDeputyLeaderAssistantDetailedList", value = "领导班子项目绩效考核明细List", dataType = "List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectDeputyLeaderAssistantDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDeputyLeaderAssistantDetailed(@RequestBody(required = false) List<ZjXmCqjxProjectDeputyLeaderAssistantDetailed> zjXmCqjxProjectDeputyLeaderAssistantDetailedList) {
        return zjXmCqjxProjectDeputyLeaderAssistantDetailedService.batchDeleteUpdateZjXmCqjxProjectDeputyLeaderAssistantDetailed(zjXmCqjxProjectDeputyLeaderAssistantDetailedList);
    }

}