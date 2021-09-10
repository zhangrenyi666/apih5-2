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
import com.apih5.mybatis.pojo.ZjXmJxAnnualSummaryFlow;
import com.apih5.service.ZjXmJxAnnualSummaryFlowService;

@RestController
public class ZjXmJxAnnualSummaryFlowController {

    @Autowired(required = true)
    private ZjXmJxAnnualSummaryFlowService zjXmJxAnnualSummaryFlowService;

    @ApiOperation(value="查询年度考核评分汇总审核", notes="查询年度考核评分汇总审核")
    @ApiImplicitParam(name = "zjXmJxAnnualSummaryFlow", value = "年度考核评分汇总审核entity", dataType = "ZjXmJxAnnualSummaryFlow")
    @RequireToken
    @PostMapping("/getZjXmJxAnnualSummaryFlowList")
    public ResponseEntity getZjXmJxAnnualSummaryFlowList(@RequestBody(required = false) ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow) {
        return zjXmJxAnnualSummaryFlowService.getZjXmJxAnnualSummaryFlowListByCondition(zjXmJxAnnualSummaryFlow);
    }

    @ApiOperation(value="查询详情年度考核评分汇总审核", notes="查询详情年度考核评分汇总审核")
    @ApiImplicitParam(name = "zjXmJxAnnualSummaryFlow", value = "年度考核评分汇总审核entity", dataType = "ZjXmJxAnnualSummaryFlow")
    @RequireToken
    @PostMapping("/getZjXmJxAnnualSummaryFlowDetails")
    public ResponseEntity getZjXmJxAnnualSummaryFlowDetails(@RequestBody(required = false) ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow) {
        return zjXmJxAnnualSummaryFlowService.getZjXmJxAnnualSummaryFlowDetails(zjXmJxAnnualSummaryFlow);
    }

    @ApiOperation(value="新增年度考核评分汇总审核", notes="新增年度考核评分汇总审核")
    @ApiImplicitParam(name = "zjXmJxAnnualSummaryFlow", value = "年度考核评分汇总审核entity", dataType = "ZjXmJxAnnualSummaryFlow")
    @RequireToken
    @PostMapping("/addZjXmJxAnnualSummaryFlow")
    public ResponseEntity addZjXmJxAnnualSummaryFlow(@RequestBody(required = false) ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow) {
        return zjXmJxAnnualSummaryFlowService.saveZjXmJxAnnualSummaryFlow(zjXmJxAnnualSummaryFlow);
    }

    @ApiOperation(value="更新年度考核评分汇总审核", notes="更新年度考核评分汇总审核")
    @ApiImplicitParam(name = "zjXmJxAnnualSummaryFlow", value = "年度考核评分汇总审核entity", dataType = "ZjXmJxAnnualSummaryFlow")
    @RequireToken
    @PostMapping("/updateZjXmJxAnnualSummaryFlow")
    public ResponseEntity updateZjXmJxAnnualSummaryFlow(@RequestBody(required = false) ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow) {
        return zjXmJxAnnualSummaryFlowService.updateZjXmJxAnnualSummaryFlow(zjXmJxAnnualSummaryFlow);
    }

    @ApiOperation(value="删除年度考核评分汇总审核", notes="删除年度考核评分汇总审核")
    @ApiImplicitParam(name = "zjXmJxAnnualSummaryFlowList", value = "年度考核评分汇总审核List", dataType = "List<ZjXmJxAnnualSummaryFlow>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmJxAnnualSummaryFlow")
    public ResponseEntity batchDeleteUpdateZjXmJxAnnualSummaryFlow(@RequestBody(required = false) List<ZjXmJxAnnualSummaryFlow> zjXmJxAnnualSummaryFlowList) {
        return zjXmJxAnnualSummaryFlowService.batchDeleteUpdateZjXmJxAnnualSummaryFlow(zjXmJxAnnualSummaryFlowList);
    }

}