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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssistantLeaderApproval;
import com.apih5.service.ZjXmCqjxProjectDisciplineAssistantLeaderApprovalService;

@RestController
public class ZjXmCqjxProjectDisciplineAssistantLeaderApprovalController {

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssistantLeaderApprovalService zjXmCqjxProjectDisciplineAssistantLeaderApprovalService;

    @ApiOperation(value="查询重庆项目绩效纪律性oa领导审批", notes="查询重庆项目绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssistantLeaderApproval", value = "重庆项目绩效纪律性oa领导审批entity", dataType = "ZjXmCqjxProjectDisciplineAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDisciplineAssistantLeaderApprovalList")
    public ResponseEntity getZjXmCqjxProjectDisciplineAssistantLeaderApprovalList(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval) {
        return zjXmCqjxProjectDisciplineAssistantLeaderApprovalService.getZjXmCqjxProjectDisciplineAssistantLeaderApprovalListByCondition(zjXmCqjxProjectDisciplineAssistantLeaderApproval);
    }

    @ApiOperation(value="查询详情重庆项目绩效纪律性oa领导审批", notes="查询详情重庆项目绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssistantLeaderApproval", value = "重庆项目绩效纪律性oa领导审批entity", dataType = "ZjXmCqjxProjectDisciplineAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDisciplineAssistantLeaderApprovalDetails")
    public ResponseEntity getZjXmCqjxProjectDisciplineAssistantLeaderApprovalDetails(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval) {
        return zjXmCqjxProjectDisciplineAssistantLeaderApprovalService.getZjXmCqjxProjectDisciplineAssistantLeaderApprovalDetails(zjXmCqjxProjectDisciplineAssistantLeaderApproval);
    }

    @ApiOperation(value="新增重庆项目绩效纪律性oa领导审批", notes="新增重庆项目绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssistantLeaderApproval", value = "重庆项目绩效纪律性oa领导审批entity", dataType = "ZjXmCqjxProjectDisciplineAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectDisciplineAssistantLeaderApproval")
    public ResponseEntity addZjXmCqjxProjectDisciplineAssistantLeaderApproval(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval) {
        return zjXmCqjxProjectDisciplineAssistantLeaderApprovalService.saveZjXmCqjxProjectDisciplineAssistantLeaderApproval(zjXmCqjxProjectDisciplineAssistantLeaderApproval);
    }

    @ApiOperation(value="更新重庆项目绩效纪律性oa领导审批", notes="更新重庆项目绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssistantLeaderApproval", value = "重庆项目绩效纪律性oa领导审批entity", dataType = "ZjXmCqjxProjectDisciplineAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectDisciplineAssistantLeaderApproval")
    public ResponseEntity updateZjXmCqjxProjectDisciplineAssistantLeaderApproval(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssistantLeaderApproval zjXmCqjxProjectDisciplineAssistantLeaderApproval) {
        return zjXmCqjxProjectDisciplineAssistantLeaderApprovalService.updateZjXmCqjxProjectDisciplineAssistantLeaderApproval(zjXmCqjxProjectDisciplineAssistantLeaderApproval);
    }

    @ApiOperation(value="删除重庆项目绩效纪律性oa领导审批", notes="删除重庆项目绩效纪律性oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssistantLeaderApprovalList", value = "重庆项目绩效纪律性oa领导审批List", dataType = "List<ZjXmCqjxProjectDisciplineAssistantLeaderApproval>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectDisciplineAssistantLeaderApproval")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssistantLeaderApproval(@RequestBody(required = false) List<ZjXmCqjxProjectDisciplineAssistantLeaderApproval> zjXmCqjxProjectDisciplineAssistantLeaderApprovalList) {
        return zjXmCqjxProjectDisciplineAssistantLeaderApprovalService.batchDeleteUpdateZjXmCqjxProjectDisciplineAssistantLeaderApproval(zjXmCqjxProjectDisciplineAssistantLeaderApprovalList);
    }
}