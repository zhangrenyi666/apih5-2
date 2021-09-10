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
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistantDetailed;
import com.apih5.service.ZjXmCqjxExecutiveAssistantDetailedService;

@RestController
public class ZjXmCqjxExecutiveAssistantDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantDetailedService zjXmCqjxExecutiveAssistantDetailedService;

    @ApiOperation(value="查询副总师、总经理半年绩效考核明细", notes="查询副总师、总经理半年绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistantDetailed", value = "副总师、总经理半年绩效考核明细entity", dataType = "ZjXmCqjxExecutiveAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxExecutiveAssistantDetailedList")
    public ResponseEntity getZjXmCqjxExecutiveAssistantDetailedList(@RequestBody(required = false) ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed) {
        return zjXmCqjxExecutiveAssistantDetailedService.getZjXmCqjxExecutiveAssistantDetailedListByCondition(zjXmCqjxExecutiveAssistantDetailed);
    }

    @ApiOperation(value="查询详情副总师、总经理半年绩效考核明细", notes="查询详情副总师、总经理半年绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistantDetailed", value = "副总师、总经理半年绩效考核明细entity", dataType = "ZjXmCqjxExecutiveAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxExecutiveAssistantDetailedDetails")
    public ResponseEntity getZjXmCqjxExecutiveAssistantDetailedDetails(@RequestBody(required = false) ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed) {
        return zjXmCqjxExecutiveAssistantDetailedService.getZjXmCqjxExecutiveAssistantDetailedDetails(zjXmCqjxExecutiveAssistantDetailed);
    }

    @ApiOperation(value="新增副总师、总经理半年绩效考核明细", notes="新增副总师、总经理半年绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistantDetailed", value = "副总师、总经理半年绩效考核明细entity", dataType = "ZjXmCqjxExecutiveAssistantDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxExecutiveAssistantDetailed")
    public ResponseEntity addZjXmCqjxExecutiveAssistantDetailed(@RequestBody(required = false) ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed) {
        return zjXmCqjxExecutiveAssistantDetailedService.saveZjXmCqjxExecutiveAssistantDetailed(zjXmCqjxExecutiveAssistantDetailed);
    }

    @ApiOperation(value="更新副总师、总经理半年绩效考核明细", notes="更新副总师、总经理半年绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistantDetailed", value = "副总师、总经理半年绩效考核明细entity", dataType = "ZjXmCqjxExecutiveAssistantDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxExecutiveAssistantDetailed")
    public ResponseEntity updateZjXmCqjxExecutiveAssistantDetailed(@RequestBody(required = false) ZjXmCqjxExecutiveAssistantDetailed zjXmCqjxExecutiveAssistantDetailed) {
        return zjXmCqjxExecutiveAssistantDetailedService.updateZjXmCqjxExecutiveAssistantDetailed(zjXmCqjxExecutiveAssistantDetailed);
    }

    @ApiOperation(value="删除副总师、总经理半年绩效考核明细", notes="删除副总师、总经理半年绩效考核明细")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistantDetailedList", value = "副总师、总经理半年绩效考核明细List", dataType = "List<ZjXmCqjxExecutiveAssistantDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed(@RequestBody(required = false) List<ZjXmCqjxExecutiveAssistantDetailed> zjXmCqjxExecutiveAssistantDetailedList) {
        return zjXmCqjxExecutiveAssistantDetailedService.batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed(zjXmCqjxExecutiveAssistantDetailedList);
    }

}
