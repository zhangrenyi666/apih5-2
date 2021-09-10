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
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssistantLeaderApproval;
import com.apih5.service.ZjXmCqjxDisciplineAssistantLeaderApprovalService;

@RestController
public class ZjXmCqjxDisciplineAssistantLeaderApprovalController {

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssistantLeaderApprovalService zjXmCqjxDisciplineAssistantLeaderApprovalService;

    @ApiOperation(value="查询重庆绩效纪律性oa领导审批", notes="查询重庆绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssistantLeaderApproval", value = "重庆绩效纪律性oa领导审批entity", dataType = "ZjXmCqjxDisciplineAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssistantLeaderApprovalList")
    public ResponseEntity getZjXmCqjxDisciplineAssistantLeaderApprovalList(@RequestBody(required = false) ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval) {
        return zjXmCqjxDisciplineAssistantLeaderApprovalService.getZjXmCqjxDisciplineAssistantLeaderApprovalListByCondition(zjXmCqjxDisciplineAssistantLeaderApproval);
    }

    @ApiOperation(value="查询详情重庆绩效纪律性oa领导审批", notes="查询详情重庆绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssistantLeaderApproval", value = "重庆绩效纪律性oa领导审批entity", dataType = "ZjXmCqjxDisciplineAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssistantLeaderApprovalDetails")
    public ResponseEntity getZjXmCqjxDisciplineAssistantLeaderApprovalDetails(@RequestBody(required = false) ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval) {
        return zjXmCqjxDisciplineAssistantLeaderApprovalService.getZjXmCqjxDisciplineAssistantLeaderApprovalDetails(zjXmCqjxDisciplineAssistantLeaderApproval);
    }

    @ApiOperation(value="新增重庆绩效纪律性oa领导审批", notes="新增重庆绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssistantLeaderApproval", value = "重庆绩效纪律性oa领导审批entity", dataType = "ZjXmCqjxDisciplineAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/addZjXmCqjxDisciplineAssistantLeaderApproval")
    public ResponseEntity addZjXmCqjxDisciplineAssistantLeaderApproval(@RequestBody(required = false) ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval) {
        return zjXmCqjxDisciplineAssistantLeaderApprovalService.saveZjXmCqjxDisciplineAssistantLeaderApproval(zjXmCqjxDisciplineAssistantLeaderApproval);
    }

    @ApiOperation(value="更新重庆绩效纪律性oa领导审批", notes="更新重庆绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssistantLeaderApproval", value = "重庆绩效纪律性oa领导审批entity", dataType = "ZjXmCqjxDisciplineAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/updateZjXmCqjxDisciplineAssistantLeaderApproval")
    public ResponseEntity updateZjXmCqjxDisciplineAssistantLeaderApproval(@RequestBody(required = false) ZjXmCqjxDisciplineAssistantLeaderApproval zjXmCqjxDisciplineAssistantLeaderApproval) {
        return zjXmCqjxDisciplineAssistantLeaderApprovalService.updateZjXmCqjxDisciplineAssistantLeaderApproval(zjXmCqjxDisciplineAssistantLeaderApproval);
    }

    @ApiOperation(value="删除重庆绩效纪律性oa领导审批", notes="删除重庆绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssistantLeaderApprovalList", value = "重庆绩效纪律性oa领导审批List", dataType = "List<ZjXmCqjxDisciplineAssistantLeaderApproval>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxDisciplineAssistantLeaderApproval")
    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssistantLeaderApproval(@RequestBody(required = false) List<ZjXmCqjxDisciplineAssistantLeaderApproval> zjXmCqjxDisciplineAssistantLeaderApprovalList) {
        return zjXmCqjxDisciplineAssistantLeaderApprovalService.batchDeleteUpdateZjXmCqjxDisciplineAssistantLeaderApproval(zjXmCqjxDisciplineAssistantLeaderApprovalList);
    }

}
