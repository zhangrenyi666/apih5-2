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
import com.apih5.mybatis.pojo.ZjXmCqjxDeptLeaderAssistantDetailed;
import com.apih5.service.ZjXmCqjxDeptLeaderAssistantDetailedService;

@RestController
public class ZjXmCqjxDeptLeaderAssistantDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxDeptLeaderAssistantDetailedService zjXmCqjxDeptLeaderAssistantDetailedService;

    @ApiOperation(value="查询部门正、副职季度考核明细", notes="查询部门正、副职季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDeptLeaderAssistantDetailed", value = "部门正、副职季度考核明细entity", dataType = "ZjXmCqjxDeptLeaderAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxDeptLeaderAssistantDetailedList")
    public ResponseEntity getZjXmCqjxDeptLeaderAssistantDetailedList(@RequestBody(required = false) ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed) {
        return zjXmCqjxDeptLeaderAssistantDetailedService.getZjXmCqjxDeptLeaderAssistantDetailedListByCondition(zjXmCqjxDeptLeaderAssistantDetailed);
    }

    @ApiOperation(value="查询详情部门正、副职季度考核明细", notes="查询详情部门正、副职季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDeptLeaderAssistantDetailed", value = "部门正、副职季度考核明细entity", dataType = "ZjXmCqjxDeptLeaderAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxDeptLeaderAssistantDetailedDetails")
    public ResponseEntity getZjXmCqjxDeptLeaderAssistantDetailedDetails(@RequestBody(required = false) ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed) {
        return zjXmCqjxDeptLeaderAssistantDetailedService.getZjXmCqjxDeptLeaderAssistantDetailedDetails(zjXmCqjxDeptLeaderAssistantDetailed);
    }

    @ApiOperation(value="新增部门正、副职季度考核明细", notes="新增部门正、副职季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDeptLeaderAssistantDetailed", value = "部门正、副职季度考核明细entity", dataType = "ZjXmCqjxDeptLeaderAssistantDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxDeptLeaderAssistantDetailed")
    public ResponseEntity addZjXmCqjxDeptLeaderAssistantDetailed(@RequestBody(required = false) ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed) {
        return zjXmCqjxDeptLeaderAssistantDetailedService.saveZjXmCqjxDeptLeaderAssistantDetailed(zjXmCqjxDeptLeaderAssistantDetailed);
    }

    @ApiOperation(value="更新部门正、副职季度考核明细", notes="更新部门正、副职季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDeptLeaderAssistantDetailed", value = "部门正、副职季度考核明细entity", dataType = "ZjXmCqjxDeptLeaderAssistantDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxDeptLeaderAssistantDetailed")
    public ResponseEntity updateZjXmCqjxDeptLeaderAssistantDetailed(@RequestBody(required = false) ZjXmCqjxDeptLeaderAssistantDetailed zjXmCqjxDeptLeaderAssistantDetailed) {
        return zjXmCqjxDeptLeaderAssistantDetailedService.updateZjXmCqjxDeptLeaderAssistantDetailed(zjXmCqjxDeptLeaderAssistantDetailed);
    }

    @ApiOperation(value="删除部门正、副职季度考核明细", notes="删除部门正、副职季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxDeptLeaderAssistantDetailedList", value = "部门正、副职季度考核明细List", dataType = "List<ZjXmCqjxDeptLeaderAssistantDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed(@RequestBody(required = false) List<ZjXmCqjxDeptLeaderAssistantDetailed> zjXmCqjxDeptLeaderAssistantDetailedList) {
        return zjXmCqjxDeptLeaderAssistantDetailedService.batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed(zjXmCqjxDeptLeaderAssistantDetailedList);
    }

}
