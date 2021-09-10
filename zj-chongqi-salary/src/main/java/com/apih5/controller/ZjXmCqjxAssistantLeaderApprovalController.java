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
import com.apih5.mybatis.pojo.ZjXmCqjxAssistantLeaderApproval;
import com.apih5.service.ZjXmCqjxAssistantLeaderApprovalService;

@RestController
public class ZjXmCqjxAssistantLeaderApprovalController {

    @Autowired(required = true)
    private ZjXmCqjxAssistantLeaderApprovalService zjXmCqjxAssistantLeaderApprovalService;

    @ApiOperation(value="查询重庆绩效oa领导审批", notes="查询重庆绩效oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxAssistantLeaderApproval", value = "重庆绩效oa领导审批entity", dataType = "ZjXmCqjxAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssistantLeaderApprovalList")
    public ResponseEntity getZjXmCqjxAssistantLeaderApprovalList(@RequestBody(required = false) ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval) {
        return zjXmCqjxAssistantLeaderApprovalService.getZjXmCqjxAssistantLeaderApprovalListByCondition(zjXmCqjxAssistantLeaderApproval);
    }

    @ApiOperation(value="查询详情重庆绩效oa领导审批", notes="查询详情重庆绩效oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxAssistantLeaderApproval", value = "重庆绩效oa领导审批entity", dataType = "ZjXmCqjxAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssistantLeaderApprovalDetails")
    public ResponseEntity getZjXmCqjxAssistantLeaderApprovalDetails(@RequestBody(required = false) ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval) {
        return zjXmCqjxAssistantLeaderApprovalService.getZjXmCqjxAssistantLeaderApprovalDetails(zjXmCqjxAssistantLeaderApproval);
    }

    @ApiOperation(value="新增重庆绩效oa领导审批", notes="新增重庆绩效oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxAssistantLeaderApproval", value = "重庆绩效oa领导审批entity", dataType = "ZjXmCqjxAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/addZjXmCqjxAssistantLeaderApproval")
    public ResponseEntity addZjXmCqjxAssistantLeaderApproval(@RequestBody(required = false) ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval) {
        return zjXmCqjxAssistantLeaderApprovalService.saveZjXmCqjxAssistantLeaderApproval(zjXmCqjxAssistantLeaderApproval);
    }

    @ApiOperation(value="更新重庆绩效oa领导审批", notes="更新重庆绩效oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxAssistantLeaderApproval", value = "重庆绩效oa领导审批entity", dataType = "ZjXmCqjxAssistantLeaderApproval")
    @RequireToken
    @PostMapping("/updateZjXmCqjxAssistantLeaderApproval")
    public ResponseEntity updateZjXmCqjxAssistantLeaderApproval(@RequestBody(required = false) ZjXmCqjxAssistantLeaderApproval zjXmCqjxAssistantLeaderApproval) {
        return zjXmCqjxAssistantLeaderApprovalService.updateZjXmCqjxAssistantLeaderApproval(zjXmCqjxAssistantLeaderApproval);
    }

    @ApiOperation(value="删除重庆绩效oa领导审批", notes="删除重庆绩效oa领导审批")
    @ApiImplicitParam(name = "zjXmCqjxAssistantLeaderApprovalList", value = "重庆绩效oa领导审批List", dataType = "List<ZjXmCqjxAssistantLeaderApproval>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxAssistantLeaderApproval")
    public ResponseEntity batchDeleteUpdateZjXmCqjxAssistantLeaderApproval(@RequestBody(required = false) List<ZjXmCqjxAssistantLeaderApproval> zjXmCqjxAssistantLeaderApprovalList) {
        return zjXmCqjxAssistantLeaderApprovalService.batchDeleteUpdateZjXmCqjxAssistantLeaderApproval(zjXmCqjxAssistantLeaderApprovalList);
    }

}
