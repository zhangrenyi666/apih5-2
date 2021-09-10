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
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.service.ZjXmCqjxExecutiveAssistantService;

@RestController
public class ZjXmCqjxExecutiveAssistantController {

    @Autowired(required = true)
    private ZjXmCqjxExecutiveAssistantService zjXmCqjxExecutiveAssistantService;

    @ApiOperation(value="查询副总师、总经理半年绩效考核", notes="查询副总师、总经理半年绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxExecutiveAssistantList")
    public ResponseEntity getZjXmCqjxExecutiveAssistantList(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantListByCondition(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="根据年度获取季度数据", notes="根据年度获取季度数据")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxExecutiveAssistantListByYear")
    public ResponseEntity getZjXmCqjxExecutiveAssistantListByYear(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantListByYear(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="检验分数是否合格", notes="检验分数是否合格")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/checkZjXmCqjxAssistantScoreQualified")
    public ResponseEntity checkZjXmCqjxAssistantScoreQualified(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.checkZjXmCqjxAssistantScoreQualified(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="修改副总师、总经理半年绩效考核填报", notes="修改副总师、总经理半年绩效考核填报")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/updateZjXmCqjxExecutiveAssistantFillIn")
    public ResponseEntity updateZjXmCqjxExecutiveAssistantFillIn(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantFillIn(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="更新副总师、总经理半年绩效考核", notes="更新副总师、总经理半年绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/updateZjXmCqjxExecutiveAssistant")
    public ResponseEntity updateZjXmCqjxExecutiveAssistant(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        return zjXmCqjxExecutiveAssistantService.updateZjXmCqjxExecutiveAssistant(zjXmCqjxExecutiveAssistant);
    }

    @ApiOperation(value="删除副总师、总经理半年绩效考核", notes="删除副总师、总经理半年绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistantList", value = "副总师、总经理半年绩效考核List", dataType = "List<ZjXmCqjxExecutiveAssistant>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxExecutiveAssistant")
    public ResponseEntity batchDeleteUpdateZjXmCqjxExecutiveAssistant(@RequestBody(required = false) List<ZjXmCqjxExecutiveAssistant> zjXmCqjxExecutiveAssistantList) {
        return zjXmCqjxExecutiveAssistantService.batchDeleteUpdateZjXmCqjxExecutiveAssistant(zjXmCqjxExecutiveAssistantList);
    }

    @ApiOperation(value="副总师、总经理半年绩效考核发起", notes="查询详情副总师、总经理半年绩效考核发起")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxExecutiveAssistantLaunch")
    public ResponseEntity zjXmCqjxExecutiveAssistantLaunch(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	zjXmCqjxExecutiveAssistant.setAssessmentState("1");
        return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantLaunch(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="副总师、总经理半年绩效评分发起", notes="查询详情副总师、总经理半年绩效考核发起")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxExecutiveScoreLaunch")
    public ResponseEntity zjXmCqjxExecutiveScoreLaunch(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	zjXmCqjxExecutiveAssistant.setAssessmentState("4");    	
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveScoreLaunch(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="查询详情副总师、总经理半年绩效考核", notes="查询详情副总师、总经理半年绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxExecutiveAssistantDetails")
    public ResponseEntity getZjXmCqjxExecutiveAssistantDetails(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantDetails(zjXmCqjxExecutiveAssistant);
    }

    @ApiOperation(value="新增副总师、总经理半年绩效考核", notes="新增副总师、总经理半年绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/addZjXmCqjxExecutiveAssistant")
    public ResponseEntity addZjXmCqjxExecutiveAssistant(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        return zjXmCqjxExecutiveAssistantService.saveZjXmCqjxExecutiveAssistant(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="副总师、总经理半年绩效考核填报", notes="副总师、总经理半年绩效考核填报")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxExecutiveAssistantFillIn")
    public ResponseEntity zjXmCqjxExecutiveAssistantFillIn(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantFillIn(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="获取审批领导集合", notes="获取审批领导集合")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssistantOaLeaderListByExecutiveId")
    public ResponseEntity getZjXmCqjxAssistantOaLeaderListByExecutiveId(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.getZjXmCqjxAssistantOaLeaderListByExecutiveId(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="部门负责人查询员工数据", notes="部门负责人查询员工数据")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssistantListByDeptLeader")
    public ResponseEntity getZjXmCqjxAssistantListByDeptLeader(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.getZjXmCqjxAssistantListByDeptLeader(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="领导查询待办列表", notes="领导查询待办列表")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssistantTodoList")
    public ResponseEntity getZjXmCqjxAssistantTodoList(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantTodoList(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="分管领导审批", notes="分管领导审批")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxAssistantChargeLeaderApproval")
    public ResponseEntity zjXmCqjxAssistantChargeLeaderApproval(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxAssistantChargeLeaderApproval(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="主管领导审批", notes="主管领导审批")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxAssistantExecutiveLeaderApproval")
    public ResponseEntity zjXmCqjxAssistantExecutiveLeaderApproval(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxAssistantExecutiveLeaderApproval(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="分管领导评分", notes="分管领导评分")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxAssistantChargeLeaderScore")
    public ResponseEntity zjXmCqjxAssistantChargeLeaderScore(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxAssistantChargeLeaderScore(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="主管领导评分", notes="主管领导评分")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxAssistantExecutiveLeaderScore")
    public ResponseEntity zjXmCqjxAssistantExecutiveLeaderScore(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxAssistantExecutiveLeaderScore(zjXmCqjxExecutiveAssistant);
    }

    @ApiOperation(value="查询部门正、副职季度绩效考核", notes="查询副总师、总经理半年绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxDeptLeaderAssistantList")
    public ResponseEntity getZjXmCqjxDeptLeaderAssistantList(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantListByCondition(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="部门正、副职季度绩效考核填报", notes="副总师、总经理半年绩效考核填报")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxDeptLeaderAssistantFillIn")
    public ResponseEntity zjXmCqjxDeptLeaderAssistantFillIn(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantFillIn(zjXmCqjxExecutiveAssistant);
    }

    @ApiOperation(value="部门正、副职季度绩效考核发起", notes="部门正、副职季度绩效考核发起")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxDeptLeaderAssistantLaunch")
    public ResponseEntity zjXmCqjxDeptLeaderAssistantLaunch(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	zjXmCqjxExecutiveAssistant.setAssessmentState("1");
        return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantLaunch(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="部门正、副职季度绩效评分发起", notes="部门正、副职季度绩效考核发起")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxDeptLeaderScoreLaunch")
    public ResponseEntity zjXmCqjxDeptLeaderScoreLaunch(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	zjXmCqjxExecutiveAssistant.setAssessmentState("4");    	
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveScoreLaunch(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="查询员工季度绩效考核", notes="查询副总师、总经理半年绩效考核")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxStaffAssistantList")
    public ResponseEntity getZjXmCqjxStaffAssistantList(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
        return zjXmCqjxExecutiveAssistantService.getZjXmCqjxExecutiveAssistantListByCondition(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="员工季度绩效考核填报", notes="副总师、总经理半年绩效考核填报")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxStaffAssistantFillIn")
    public ResponseEntity zjXmCqjxStaffAssistantFillIn(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantFillIn(zjXmCqjxExecutiveAssistant);
    }   

    @ApiOperation(value="员工季度绩效考核发起", notes="查询详情副总师、总经理半年绩效考核发起")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxStaffAssistantLaunch")
    public ResponseEntity zjXmCqjxStaffAssistantLaunch(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	zjXmCqjxExecutiveAssistant.setAssessmentState("1");
        return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantLaunch(zjXmCqjxExecutiveAssistant);
    }
    
    @ApiOperation(value="员工季度绩效评分发起", notes="查询详情副总师、总经理半年绩效考核发起")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxStaffScoreLaunch")
    public ResponseEntity zjXmCqjxStaffScoreLaunch(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	zjXmCqjxExecutiveAssistant.setAssessmentState("4");    	
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveScoreLaunch(zjXmCqjxExecutiveAssistant);
    }    
    
    @ApiOperation(value="检测提交考核是否超时", notes="检测提交考核是否超时")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/checkZjXmCqjxExecutiveAssistantLaunch")
    public ResponseEntity checkZjXmCqjxExecutiveAssistantLaunch(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.checkZjXmCqjxExecutiveAssistantLaunch(zjXmCqjxExecutiveAssistant);
    }    
    
    @ApiOperation(value="检测提交考核是否超时", notes="检测提交考核是否超时")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/batchCheckZjXmCqjxExecutiveAssistantLaunch")
    public ResponseEntity batchCheckZjXmCqjxExecutiveAssistantLaunch(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.batchCheckZjXmCqjxExecutiveAssistantLaunch(zjXmCqjxExecutiveAssistant);
    }    
    
    @ApiOperation(value="解除锁定", notes="解除锁定")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxExecutiveAssistantReleaseLock")
    public ResponseEntity zjXmCqjxExecutiveAssistantReleaseLock(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantReleaseLock(zjXmCqjxExecutiveAssistant);
    }    
    
    @ApiOperation(value="检查数据发起状态", notes="检查数据发起状态")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/checkZjXmCqjxAssessmentManageState")
    public ResponseEntity checkZjXmCqjxAssessmentManageState(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.checkZjXmCqjxAssessmentManageState(zjXmCqjxExecutiveAssistant);
    }    
    
    @ApiOperation(value="领导审批暂存功能", notes="领导审批暂存功能")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxExecutiveAssistantReleaseTempSave")
    public ResponseEntity zjXmCqjxExecutiveAssistantReleaseTempSave(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxExecutiveAssistantReleaseTempSave(zjXmCqjxExecutiveAssistant);
    }    
    
    @ApiOperation(value="定时计算员工最终得分任务", notes="定时计算员工最终得分任务")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
//    @RequireToken
    @PostMapping("/zjXmCqjxStaffAssistantQuarterScoreTask")
    public ResponseEntity zjXmCqjxStaffAssistantQuarterScoreTask(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.zjXmCqjxStaffAssistantQuarterScoreTask(zjXmCqjxExecutiveAssistant);
    }  
    
    @ApiOperation(value="公司机关任务已办", notes="公司机关任务已办")
    @ApiImplicitParam(name = "zjXmCqjxExecutiveAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxExecutiveAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssistantDoneList")
    public ResponseEntity getZjXmCqjxAssistantDoneList(@RequestBody(required = false) ZjXmCqjxExecutiveAssistant zjXmCqjxExecutiveAssistant) {
    	return zjXmCqjxExecutiveAssistantService.getZjXmCqjxAssistantDoneList(zjXmCqjxExecutiveAssistant);
    }
    
}
