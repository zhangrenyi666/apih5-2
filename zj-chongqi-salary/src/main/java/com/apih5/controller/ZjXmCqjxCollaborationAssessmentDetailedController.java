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
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentDetailed;
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;
import com.apih5.service.ZjXmCqjxCollaborationAssessmentDetailedService;

@RestController
public class ZjXmCqjxCollaborationAssessmentDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentDetailedService zjXmCqjxCollaborationAssessmentDetailedService;

    @ApiOperation(value="查询重庆绩效考核员工协作性考核明细", notes="查询重庆绩效考核员工协作性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentDetailed", value = "重庆绩效考核员工协作性考核明细entity", dataType = "ZjXmCqjxCollaborationAssessmentDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxCollaborationAssessmentDetailedList")
    public ResponseEntity getZjXmCqjxCollaborationAssessmentDetailedList(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        return zjXmCqjxCollaborationAssessmentDetailedService.getZjXmCqjxCollaborationAssessmentDetailedListByCondition(zjXmCqjxCollaborationAssessmentDetailed);
    }

    @ApiOperation(value="查询详情重庆绩效考核员工协作性考核明细", notes="查询详情重庆绩效考核员工协作性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentDetailed", value = "重庆绩效考核员工协作性考核明细entity", dataType = "ZjXmCqjxCollaborationAssessmentDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxCollaborationAssessmentDetailedDetails")
    public ResponseEntity getZjXmCqjxCollaborationAssessmentDetailedDetails(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        return zjXmCqjxCollaborationAssessmentDetailedService.getZjXmCqjxCollaborationAssessmentDetailedDetails(zjXmCqjxCollaborationAssessmentDetailed);
    }

    @ApiOperation(value="新增重庆绩效考核员工协作性考核明细", notes="新增重庆绩效考核员工协作性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentDetailed", value = "重庆绩效考核员工协作性考核明细entity", dataType = "ZjXmCqjxCollaborationAssessmentDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxCollaborationAssessmentDetailed")
    public ResponseEntity addZjXmCqjxCollaborationAssessmentDetailed(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        return zjXmCqjxCollaborationAssessmentDetailedService.saveZjXmCqjxCollaborationAssessmentDetailed(zjXmCqjxCollaborationAssessmentDetailed);
    }
    
    @ApiOperation(value="新增重庆绩效考核员工协作性考核明细", notes="新增重庆绩效考核员工协作性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核明细entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/addZjXmCqjxCollaborationAssessmentDetailedByMember")
    public ResponseEntity addZjXmCqjxCollaborationAssessmentDetailedByMember(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
    	return zjXmCqjxCollaborationAssessmentDetailedService.saveZjXmCqjxCollaborationAssessmentDetailedByMember(zjXmCqjxCollaborationAssessmentMember);
    }

    @ApiOperation(value="更新重庆绩效考核员工协作性考核明细", notes="更新重庆绩效考核员工协作性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentDetailed", value = "重庆绩效考核员工协作性考核明细entity", dataType = "ZjXmCqjxCollaborationAssessmentDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxCollaborationAssessmentDetailed")
    public ResponseEntity updateZjXmCqjxCollaborationAssessmentDetailed(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        return zjXmCqjxCollaborationAssessmentDetailedService.updateZjXmCqjxCollaborationAssessmentDetailed(zjXmCqjxCollaborationAssessmentDetailed);
    }

    @ApiOperation(value="重庆绩效考核员工协作性考核明细定时任务", notes="重庆绩效考核员工协作性考核明细定时任务")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentDetailed", value = "重庆绩效考核员工协作性考核明细entity", dataType = "ZjXmCqjxCollaborationAssessmentDetailed")
    @RequireToken
    @PostMapping("/zjXmCqjxCollaborationAssessmentDetailedTask")
    public ResponseEntity zjXmCqjxCollaborationAssessmentDetailedTask(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
        return zjXmCqjxCollaborationAssessmentDetailedService.zjXmCqjxCollaborationAssessmentDetailedTask(zjXmCqjxCollaborationAssessmentDetailed);
    }
    
    @ApiOperation(value="重庆绩效考核员工协作性考核明细定时任务", notes="重庆绩效考核员工协作性考核明细定时任务")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentDetailed", value = "重庆绩效考核员工协作性考核明细entity", dataType = "ZjXmCqjxCollaborationAssessmentDetailed")
    @RequireToken
    @PostMapping("/zjXmCqjxCollaborationAssessmentScoreTask")
    public ResponseEntity zjXmCqjxCollaborationAssessmentScoreTask(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentDetailed zjXmCqjxCollaborationAssessmentDetailed) {
    	return zjXmCqjxCollaborationAssessmentDetailedService.zjXmCqjxCollaborationAssessmentScoreTask(zjXmCqjxCollaborationAssessmentDetailed);
    }
    
    @ApiOperation(value="删除重庆绩效考核员工协作性考核明细", notes="删除重庆绩效考核员工协作性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentDetailedList", value = "重庆绩效考核员工协作性考核明细List", dataType = "List<ZjXmCqjxCollaborationAssessmentDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxCollaborationAssessmentDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxCollaborationAssessmentDetailed(@RequestBody(required = false) List<ZjXmCqjxCollaborationAssessmentDetailed> zjXmCqjxCollaborationAssessmentDetailedList) {
        return zjXmCqjxCollaborationAssessmentDetailedService.batchDeleteUpdateZjXmCqjxCollaborationAssessmentDetailed(zjXmCqjxCollaborationAssessmentDetailedList);
    }

    @ApiOperation(value="删除重庆绩效考核员工协作性考核明细", notes="删除重庆绩效考核员工协作性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMemberList", value = "重庆绩效考核员工协作性考核明细List", dataType = "List<ZjXmCqjxCollaborationAssessmentMember>")
    @RequireToken
    @PostMapping("/batchAddZjXmCqjxCollaborationAssessmentDetailed")
    public ResponseEntity batchAddZjXmCqjxCollaborationAssessmentDetailed(@RequestBody(required = false) List<ZjXmCqjxCollaborationAssessmentMember> zjXmCqjxCollaborationAssessmentMemberList) {
        return zjXmCqjxCollaborationAssessmentDetailedService.batchAddZjXmCqjxCollaborationAssessmentDetailed(zjXmCqjxCollaborationAssessmentMemberList);
    }
}
