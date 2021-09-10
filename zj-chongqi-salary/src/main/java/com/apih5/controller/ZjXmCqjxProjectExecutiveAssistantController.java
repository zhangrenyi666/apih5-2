package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectExecutiveAssistant;
import com.apih5.service.ZjXmCqjxProjectExecutiveAssistantService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZjXmCqjxProjectExecutiveAssistantController {

    @Autowired(required = true)
    private ZjXmCqjxProjectExecutiveAssistantService zjXmCqjxProjectExecutiveAssistantService;

    @ApiOperation(value="查询项目绩效考核", notes="查询项目绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectExecutiveAssistant", value = "项目绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectExecutiveAssistantList")
    public ResponseEntity getZjXmCqjxProjectExecutiveAssistantList(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
        return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxProjectExecutiveAssistantListByCondition(zjXmCqjxProjectExecutiveAssistant);
    }

    @ApiOperation(value="查询详情项目绩效考核", notes="查询详情项目绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectExecutiveAssistant", value = "项目绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectExecutiveAssistantDetails")
    public ResponseEntity getZjXmCqjxProjectExecutiveAssistantDetails(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
        return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxProjectExecutiveAssistantDetails(zjXmCqjxProjectExecutiveAssistant);
    }

    @ApiOperation(value="新增项目绩效考核", notes="新增项目绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectExecutiveAssistant", value = "项目绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectExecutiveAssistant")
    public ResponseEntity addZjXmCqjxProjectExecutiveAssistant(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
        return zjXmCqjxProjectExecutiveAssistantService.saveZjXmCqjxProjectExecutiveAssistant(zjXmCqjxProjectExecutiveAssistant);
    }

    @ApiOperation(value="更新项目绩效考核", notes="更新项目绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectExecutiveAssistant", value = "项目绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectExecutiveAssistant")
    public ResponseEntity updateZjXmCqjxProjectExecutiveAssistant(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant zjXmCqjxProjectExecutiveAssistant) {
        return zjXmCqjxProjectExecutiveAssistantService.updateZjXmCqjxProjectExecutiveAssistant(zjXmCqjxProjectExecutiveAssistant);
    }

    @ApiOperation(value="删除项目绩效考核", notes="删除项目绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectExecutiveAssistantList", value = "项目绩效考核List", dataType = "List<ZjXmCqjxProjectExecutiveAssistant>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectExecutiveAssistant")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectExecutiveAssistant(@RequestBody(required = false) List<ZjXmCqjxProjectExecutiveAssistant> zjXmCqjxProjectExecutiveAssistantList) {
        return zjXmCqjxProjectExecutiveAssistantService.batchDeleteUpdateZjXmCqjxProjectExecutiveAssistant(zjXmCqjxProjectExecutiveAssistantList);
    }
    
    @ApiOperation(value="检验分数是否合格", notes="检验分数是否合格")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/checkZjXmCqjxProjectAssistantScoreQualified")
    public ResponseEntity checkZjXmCqjxProjectAssistantScoreQualified(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.checkZjXmCqjxAssistantScoreQualified(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="修改副总师、总经理半年绩效考核填报", notes="修改副总师、总经理半年绩效考核填报")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectExecutiveAssistantFillIn")
    public ResponseEntity updateZjXmCqjxProjectExecutiveAssistantFillIn(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantFillIn(ZjXmCqjxProjectExecutiveAssistant);
    }

    @ApiOperation(value="副总师、总经理半年绩效考核发起", notes="查询详情副总师、总经理半年绩效考核发起")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectExecutiveAssistantLaunch")
    public ResponseEntity zjXmCqjxProjectExecutiveAssistantLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("1");
        return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="副总师、总经理半年绩效考核发起（特殊情况）", notes="查询详情副总师、总经理半年绩效考核发起")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectExecutiveAssistantSpecialLaunch")
    public ResponseEntity zjXmCqjxProjectExecutiveAssistantSpecialLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
//    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("1");
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantSpecialLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="副总师、总经理半年绩效评分发起", notes="查询详情副总师、总经理半年绩效考核发起")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectExecutiveScoreLaunch")
    public ResponseEntity zjXmCqjxProjectExecutiveScoreLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("4");    	
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveScoreLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="副总师、总经理半年绩效考核填报", notes="副总师、总经理半年绩效考核填报")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectExecutiveAssistantFillIn")
    public ResponseEntity zjXmCqjxProjectExecutiveAssistantFillIn(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantFillIn(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="获取审批领导集合", notes="获取审批领导集合")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssistantOaLeaderListByExecutiveId")
    public ResponseEntity getZjXmCqjxProjectAssistantOaLeaderListByExecutiveId(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxAssistantOaLeaderListByExecutiveId(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="部门负责人查询员工数据", notes="部门负责人查询员工数据")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssistantListByDeptLeader")
    public ResponseEntity getZjXmCqjxProjectAssistantListByDeptLeader(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxAssistantListByDeptLeader(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="领导查询待办列表", notes="领导查询待办列表")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssistantTodoList")
    public ResponseEntity getZjXmCqjxProjectAssistantTodoList(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoList(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="分管领导审批", notes="分管领导审批")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectAssistantChargeLeaderApproval")
    public ResponseEntity zjXmCqjxProjectAssistantChargeLeaderApproval(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxAssistantChargeLeaderApproval(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="主管领导审批", notes="主管领导审批")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectAssistantExecutiveLeaderApproval")
    public ResponseEntity zjXmCqjxProjectAssistantExecutiveLeaderApproval(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxAssistantExecutiveLeaderApproval(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="分管领导评分", notes="分管领导评分")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectAssistantChargeLeaderScore")
    public ResponseEntity zjXmCqjxProjectAssistantChargeLeaderScore(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxAssistantChargeLeaderScore(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="主管领导评分", notes="主管领导评分")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectAssistantExecutiveLeaderScore")
    public ResponseEntity zjXmCqjxProjectAssistantExecutiveLeaderScore(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxAssistantExecutiveLeaderScore(ZjXmCqjxProjectExecutiveAssistant);
    }

    @ApiOperation(value="查询部门正、副职季度绩效考核", notes="查询副总师、总经理半年绩效考核")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDeptLeaderAssistantList")
    public ResponseEntity getZjXmCqjxProjectDeptLeaderAssistantList(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
        return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxProjectExecutiveAssistantListByCondition(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="部门正、副职季度绩效考核填报", notes="副总师、总经理半年绩效考核填报")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDeptLeaderAssistantFillIn")
    public ResponseEntity zjXmCqjxProjectDeptLeaderAssistantFillIn(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantFillIn(ZjXmCqjxProjectExecutiveAssistant);
    }

    @ApiOperation(value="部门正、副职季度绩效考核发起", notes="部门正、副职季度绩效考核发起")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDeptLeaderAssistantLaunch")
    public ResponseEntity zjXmCqjxProjectDeptLeaderAssistantLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("1");
        return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="部门正、副职季度绩效考核发起（特殊情况）", notes="部门正、副职季度绩效考核发起（特殊情况）")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDeptLeaderAssistantSpecialLaunch")
    public ResponseEntity zjXmCqjxProjectDeptLeaderAssistantSpecialLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
//    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("1");
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantSpecialLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="部门正、副职季度绩效评分发起", notes="部门正、副职季度绩效考核发起")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDeptLeaderScoreLaunch")
    public ResponseEntity zjXmCqjxProjectDeptLeaderScoreLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("4");    	
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveScoreLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="查询员工季度绩效考核", notes="查询员工季度绩效考核")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectStaffAssistantList")
    public ResponseEntity getZjXmCqjxProjectStaffAssistantList(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
        return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxProjectExecutiveAssistantListByCondition(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="员工季度绩效考核填报", notes="员工季度绩效考核填报")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectStaffAssistantFillIn")
    public ResponseEntity zjXmCqjxProjectStaffAssistantFillIn(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantFillIn(ZjXmCqjxProjectExecutiveAssistant);
    }   

    @ApiOperation(value="员工季度绩效考核发起", notes="员工季度绩效考核发起")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectStaffAssistantLaunch")
    public ResponseEntity zjXmCqjxProjectStaffAssistantLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("1");
        return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="员工季度绩效考核发起（特殊情况）", notes="员工季度绩效考核发起（特殊情况）")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectStaffAssistantSpecialLaunch")
    public ResponseEntity zjXmCqjxProjectStaffAssistantSpecialLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
//    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("1");
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantSpecialLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }
    
    @ApiOperation(value="员工季度绩效评分发起", notes="查询详情副总师、总经理半年绩效考核发起")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectStaffScoreLaunch")
    public ResponseEntity zjXmCqjxProjectStaffScoreLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	ZjXmCqjxProjectExecutiveAssistant.setAssessmentState("4");    	
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveScoreLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="检测提交考核是否超时", notes="检测提交考核是否超时")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/checkZjXmCqjxProjectExecutiveAssistantLaunch")
    public ResponseEntity checkZjXmCqjxProjectExecutiveAssistantLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.checkZjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="检测提交考核是否超时", notes="检测提交考核是否超时")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/batchCheckZjXmCqjxProjectExecutiveAssistantLaunch")
    public ResponseEntity batchCheckZjXmCqjxProjectExecutiveAssistantLaunch(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.batchCheckZjXmCqjxExecutiveAssistantLaunch(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="解除锁定", notes="解除锁定")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectExecutiveAssistantReleaseLock")
    public ResponseEntity zjXmCqjxProjectExecutiveAssistantReleaseLock(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxExecutiveAssistantReleaseLock(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="检查数据发起状态", notes="检查数据发起状态")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/checkZjXmCqjxProjectAssessmentManageState")
    public ResponseEntity checkZjXmCqjxProjectAssessmentManageState(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.checkZjXmCqjxAssessmentManageState(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="领导审批暂存功能", notes="领导审批暂存功能")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectExecutiveAssistantReleaseTempSave")
    public ResponseEntity zjXmCqjxProjectExecutiveAssistantReleaseTempSave(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxProjectExecutiveAssistantReleaseTempSave(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="项目人员选择领导审批", notes="项目人员选择领导审批")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectLeaderApprovalAllList")
    public ResponseEntity getZjXmCqjxProjectLeaderApprovalAllList(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxProjectLeaderApprovalAllList(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="检查计划下的人员是否完成计划流程", notes="检查计划下的人员是否完成计划流程")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/checkZjXmCqjxProjectFinishPlanAssistant")
    public ResponseEntity checkZjXmCqjxProjectFinishPlanAssistant(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.checkZjXmCqjxProjectFinishPlanAssistant(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="项目月度考核综合排名", notes="项目月度考核综合排名")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectComprehensiveRanking")
    public ResponseEntity getZjXmCqjxProjectComprehensiveRanking(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxProjectComprehensiveRanking(ZjXmCqjxProjectExecutiveAssistant);
    }    
    
    @ApiOperation(value="定时计算最终得分任务", notes="定时计算最终得分任务")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
//    @RequireToken
    @PostMapping("/zjXmCqjxProjectAssistantMonthScoreTask")
    public ResponseEntity zjXmCqjxProjectAssistantMonthScoreTask(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.zjXmCqjxProjectAssistantMonthScoreTask(ZjXmCqjxProjectExecutiveAssistant);
    }  
    
    @ApiOperation(value="项目绩效考核任务已办", notes="项目绩效考核任务已办")
    @ApiImplicitParam(name = "ZjXmCqjxProjectExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxProjectExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssistantDoneList")
    public ResponseEntity getZjXmCqjxProjectAssistantDoneList(@RequestBody(required = false) ZjXmCqjxProjectExecutiveAssistant ZjXmCqjxProjectExecutiveAssistant) {
    	return zjXmCqjxProjectExecutiveAssistantService.getZjXmCqjxProjectAssistantDoneList(ZjXmCqjxProjectExecutiveAssistant);
    }
}