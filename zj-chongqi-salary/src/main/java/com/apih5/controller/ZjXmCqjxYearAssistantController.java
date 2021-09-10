package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.mybatis.pojo.ZjXmCqjxYearAssistant;
import com.apih5.service.ZjXmCqjxYearAssistantService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZjXmCqjxYearAssistantController {

    @Autowired(required = true)
    private ZjXmCqjxYearAssistantService zjXmCqjxYearAssistantService;

    @ApiOperation(value="领导查询待办列表", notes="领导查询待办列表")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxEvaluationAssistantTodoList")
    public ResponseEntity getZjXmCqjxEvaluationAssistantTodoList(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	return zjXmCqjxYearAssistantService.getZjXmCqjxYearAssistantTodoList(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="项目办公室评分", notes="项目办公室评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxSurroundAssistantApproval")
    public ResponseEntity zjXmCqjxSurroundAssistantApproval(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	zjXmCqjxYearAssistant.setState("1");
    	return zjXmCqjxYearAssistantService.zjXmCqjxSurroundAssistantApproval(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="职能发挥评分", notes="职能发挥评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxServiceRolePlayApproval")
    public ResponseEntity zjXmCqjxServiceRolePlayApproval(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	zjXmCqjxYearAssistant.setState("2");
    	return zjXmCqjxYearAssistantService.zjXmCqjxSurroundAssistantApproval(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="工作业绩评分", notes="工作业绩评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxWorkPerformanceApproval")
    public ResponseEntity zjXmCqjxWorkPerformanceApproval(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	zjXmCqjxYearAssistant.setState("3");
    	return zjXmCqjxYearAssistantService.zjXmCqjxSurroundAssistantApproval(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="督办工作评分", notes="督办工作评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxSupervisoryWorkApproval")
    public ResponseEntity zjXmCqjxSupervisoryWorkApproval(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	zjXmCqjxYearAssistant.setState("4");
    	return zjXmCqjxYearAssistantService.zjXmCqjxSurroundAssistantApproval(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="定时计算年度最终评分", notes="定时计算年度最终评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxYearAssistant")
//    @RequireToken
    @PostMapping("/jobZjXmCqjxYearAssistantLastScore")
    public ResponseEntity jobZjXmCqjxYearAssistantLastScore(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	return zjXmCqjxYearAssistantService.jobZjXmCqjxYearAssistantLastScore(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="查询年度考核分数详情", notes="查询年度考核分数详情")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "公司机关年度考核计划entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxYearAssistantScoreDetail")
    public ResponseEntity getZjXmCqjxYearAssistantScoreDetail(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        return zjXmCqjxYearAssistantService.getZjXmCqjxYearAssistantScoreDetail(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="查询公司机关年度考核计划", notes="查询公司机关年度考核计划")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "公司机关年度考核计划entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxYearAssistantList")
    public ResponseEntity getZjXmCqjxYearAssistantList(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	return zjXmCqjxYearAssistantService.getZjXmCqjxYearAssistantListByCondition(zjXmCqjxYearAssistant);
    }

    @ApiOperation(value="查询详情公司机关年度考核计划", notes="查询详情公司机关年度考核计划")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "公司机关年度考核计划entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxYearAssistantDetails")
    public ResponseEntity getZjXmCqjxYearAssistantDetails(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        return zjXmCqjxYearAssistantService.getZjXmCqjxYearAssistantDetails(zjXmCqjxYearAssistant);
    }

    @ApiOperation(value="新增公司机关年度考核计划", notes="新增公司机关年度考核计划")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "公司机关年度考核计划entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/addZjXmCqjxYearAssistant")
    public ResponseEntity addZjXmCqjxYearAssistant(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        return zjXmCqjxYearAssistantService.saveZjXmCqjxYearAssistant(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="新增公司机关年度考核计划", notes="新增公司机关年度考核计划")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "公司机关年度考核计划entity", dataType = "ZjXmCqjxAssessmentManage")
    @RequireToken
    @PostMapping("/addZjXmCqjxYearAssistantByManagerId")
    public ResponseEntity addZjXmCqjxYearAssistantByManagerId(@RequestBody(required = false) ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
    	return zjXmCqjxYearAssistantService.saveZjXmCqjxYearAssistantByManagerId(zjXmCqjxAssessmentManage);
    }
    
    @ApiOperation(value="更新公司机关年度考核计划", notes="更新公司机关年度考核计划")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "公司机关年度考核计划entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/updateZjXmCqjxYearAssistant")
    public ResponseEntity updateZjXmCqjxYearAssistant(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
        return zjXmCqjxYearAssistantService.updateZjXmCqjxYearAssistant(zjXmCqjxYearAssistant);
    }

    @ApiOperation(value="删除公司机关年度考核计划", notes="删除公司机关年度考核计划")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistantList", value = "公司机关年度考核计划List", dataType = "List<ZjXmCqjxYearAssistant>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxYearAssistant")
    public ResponseEntity batchDeleteUpdateZjXmCqjxYearAssistant(@RequestBody(required = false) List<ZjXmCqjxYearAssistant> zjXmCqjxYearAssistantList) {
        return zjXmCqjxYearAssistantService.batchDeleteUpdateZjXmCqjxYearAssistant(zjXmCqjxYearAssistantList);
    }
    
    @ApiOperation(value="部门负责人查询员工数据", notes="部门负责人查询员工数据")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "副总师、总经理半年绩效考核entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxYearAssistantListByDeptLeader")
    public ResponseEntity getZjXmCqjxYearAssistantListByDeptLeader(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	return zjXmCqjxYearAssistantService.getZjXmCqjxYearAssistantListByDeptLeader(zjXmCqjxYearAssistant);
    }
    
    @ApiOperation(value="年度绩效考核流程已办", notes="年度绩效考核流程已办")
    @ApiImplicitParam(name = "zjXmCqjxYearAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxYearAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxEvaluationAssistantDoneList")
    public ResponseEntity getZjXmCqjxEvaluationAssistantDoneList(@RequestBody(required = false) ZjXmCqjxYearAssistant zjXmCqjxYearAssistant) {
    	return zjXmCqjxYearAssistantService.getZjXmCqjxYearAssistantDoneList(zjXmCqjxYearAssistant);
    }
}
