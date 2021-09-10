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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssistantLeaderApproval;
import com.apih5.service.ZjXmCqjxProjectAssistantLeaderApprovalService;

@RestController
public class ZjXmCqjxProjectAssistantLeaderApprovalController {

    @Autowired(required = true)
    private ZjXmCqjxProjectAssistantLeaderApprovalService zjXmCqjxProjectAssistantLeaderApprovalService;

    @ApiOperation(value="查询重庆绩效项目oa领导审批", notes="查询重庆绩效项目oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssistantLeaderApproval", value = "重庆绩效项目oa领导审批entity", dataType = "ZjXmCqjxProjectAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssistantLeaderApprovalList")
    public ResponseEntity getZjXmCqjxProjectAssistantLeaderApprovalList(@RequestBody(required = false) ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval) {
        return zjXmCqjxProjectAssistantLeaderApprovalService.getZjXmCqjxProjectAssistantLeaderApprovalListByCondition(zjXmCqjxProjectAssistantLeaderApproval);
    }

    @ApiOperation(value="查询详情重庆绩效项目oa领导审批", notes="查询详情重庆绩效项目oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssistantLeaderApproval", value = "重庆绩效项目oa领导审批entity", dataType = "ZjXmCqjxProjectAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssistantLeaderApprovalDetails")
    public ResponseEntity getZjXmCqjxProjectAssistantLeaderApprovalDetails(@RequestBody(required = false) ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval) {
        return zjXmCqjxProjectAssistantLeaderApprovalService.getZjXmCqjxProjectAssistantLeaderApprovalDetails(zjXmCqjxProjectAssistantLeaderApproval);
    }

    @ApiOperation(value="新增重庆绩效项目oa领导审批", notes="新增重庆绩效项目oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssistantLeaderApproval", value = "重庆绩效项目oa领导审批entity", dataType = "ZjXmCqjxProjectAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectAssistantLeaderApproval")
    public ResponseEntity addZjXmCqjxProjectAssistantLeaderApproval(@RequestBody(required = false) ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval) {
        return zjXmCqjxProjectAssistantLeaderApprovalService.saveZjXmCqjxProjectAssistantLeaderApproval(zjXmCqjxProjectAssistantLeaderApproval);
    }

    @ApiOperation(value="更新重庆绩效项目oa领导审批", notes="更新重庆绩效项目oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssistantLeaderApproval", value = "重庆绩效项目oa领导审批entity", dataType = "ZjXmCqjxProjectAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectAssistantLeaderApproval")
    public ResponseEntity updateZjXmCqjxProjectAssistantLeaderApproval(@RequestBody(required = false) ZjXmCqjxProjectAssistantLeaderApproval zjXmCqjxProjectAssistantLeaderApproval) {
        return zjXmCqjxProjectAssistantLeaderApprovalService.updateZjXmCqjxProjectAssistantLeaderApproval(zjXmCqjxProjectAssistantLeaderApproval);
    }

    @ApiOperation(value="删除重庆绩效项目oa领导审批", notes="删除重庆绩效项目oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssistantLeaderApprovalList", value = "重庆绩效项目oa领导审批List", dataType = "List<ZjXmCqjxProjectAssistantLeaderApproval>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectAssistantLeaderApproval")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectAssistantLeaderApproval(@RequestBody(required = false) List<ZjXmCqjxProjectAssistantLeaderApproval> zjXmCqjxProjectAssistantLeaderApprovalList) {
        return zjXmCqjxProjectAssistantLeaderApprovalService.batchDeleteUpdateZjXmCqjxProjectAssistantLeaderApproval(zjXmCqjxProjectAssistantLeaderApprovalList);
    }

}