package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessment;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;
import com.apih5.service.ZjXmCqjxCollaborationAssessmentService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZjXmCqjxCollaborationAssessmentController {

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentService zjXmCqjxCollaborationAssessmentService;

    @ApiOperation(value="查询重庆绩效考核员工协作性考核", notes="查询重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessment", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessment")
    @RequireToken
    @PostMapping("/getZjXmCqjxCollaborationAssessmentList")
    public ResponseEntity getZjXmCqjxCollaborationAssessmentList(@RequestBody(required = false) ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        return zjXmCqjxCollaborationAssessmentService.getZjXmCqjxCollaborationAssessmentListByCondition(zjXmCqjxCollaborationAssessment);
    }

    @ApiOperation(value="查询详情重庆绩效考核员工协作性考核", notes="查询详情重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessment", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessment")
    @RequireToken
    @PostMapping("/getZjXmCqjxCollaborationAssessmentDetails")
    public ResponseEntity getZjXmCqjxCollaborationAssessmentDetails(@RequestBody(required = false) ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        return zjXmCqjxCollaborationAssessmentService.getZjXmCqjxCollaborationAssessmentDetails(zjXmCqjxCollaborationAssessment);
    }

    @ApiOperation(value="新增重庆绩效考核员工协作性考核", notes="新增重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessment", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessment")
    @RequireToken
    @PostMapping("/addZjXmCqjxCollaborationAssessment")
    public ResponseEntity addZjXmCqjxCollaborationAssessment(@RequestBody(required = false) ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        return zjXmCqjxCollaborationAssessmentService.saveZjXmCqjxCollaborationAssessment(zjXmCqjxCollaborationAssessment);
    }

    @ApiOperation(value="更新重庆绩效考核员工协作性考核", notes="更新重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessment", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessment")
    @RequireToken
    @PostMapping("/updateZjXmCqjxCollaborationAssessment")
    public ResponseEntity updateZjXmCqjxCollaborationAssessment(@RequestBody(required = false) ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        return zjXmCqjxCollaborationAssessmentService.updateZjXmCqjxCollaborationAssessment(zjXmCqjxCollaborationAssessment);
    }

    @ApiOperation(value="删除重庆绩效考核员工协作性考核", notes="删除重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentList", value = "重庆绩效考核员工协作性考核List", dataType = "List<ZjXmCqjxCollaborationAssessment>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxCollaborationAssessment")
    public ResponseEntity batchDeleteUpdateZjXmCqjxCollaborationAssessment(@RequestBody(required = false) List<ZjXmCqjxCollaborationAssessment> zjXmCqjxCollaborationAssessmentList) {
        return zjXmCqjxCollaborationAssessmentService.batchDeleteUpdateZjXmCqjxCollaborationAssessment(zjXmCqjxCollaborationAssessmentList);
    }
    
    @ApiOperation(value="重庆绩效考核员工协作性考核批量发起", notes="重庆绩效考核员工协作性考核批量发起")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentList", value = "重庆绩效考核员工协作性考核List", dataType = "List<ZjXmCqjxCollaborationAssessment>")
    @RequireToken
    @PostMapping("/zjXmCqjxCollaborationAssessmentSendMessage")
    public ResponseEntity zjXmCqjxCollaborationAssessmentSendMessage(@RequestBody(required = false) List<ZjXmCqjxCollaborationAssessment> zjXmCqjxCollaborationAssessmentList) {
    	return zjXmCqjxCollaborationAssessmentService.zjXmCqjxCollaborationAssessmentSendMessage(zjXmCqjxCollaborationAssessmentList);
    }

    @ApiOperation(value="员工协作性超时定时任务", notes="员工协作性超时定时任务")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessment", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxCollaborationAssessmentTimeOutTask")
    public ResponseEntity zjXmCqjxCollaborationAssessmentTimeOutTask(@RequestBody(required = false) ZjXmCqjxCollaborationAssessment zjXmCqjxCollaborationAssessment) {
        return zjXmCqjxCollaborationAssessmentService.zjXmCqjxCollaborationAssessmentTimeOutTask(zjXmCqjxCollaborationAssessment);
    }
    
    @ApiOperation(value="员工协作性超时解锁", notes="员工协作性超时解锁")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/zjXmCqjxCollaborationAssistantReleaseLock")
    public ResponseEntity zjXmCqjxCollaborationAssistantReleaseLock(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        return zjXmCqjxCollaborationAssessmentService.zjXmCqjxCollaborationAssistantReleaseLock(zjXmCqjxCollaborationAssessmentMember);
    }
    
}
