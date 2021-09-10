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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOverallEvaluationAssistant;
import com.apih5.service.ZjXmCqjxProjectOverallEvaluationAssistantService;

@RestController
public class ZjXmCqjxProjectOverallEvaluationAssistantController {

    @Autowired(required = true)
    private ZjXmCqjxProjectOverallEvaluationAssistantService zjXmCqjxProjectOverallEvaluationAssistantService;

    @ApiOperation(value="领导查询待办列表", notes="领导查询待办列表")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectEvaluationAssistantTodoList")
    public ResponseEntity getZjXmCqjxProjectEvaluationAssistantTodoList(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
    	return zjXmCqjxProjectOverallEvaluationAssistantService.getZjXmCqjxProjectEvaluationAssistantTodoList(zjXmCqjxProjectOverallEvaluationAssistant);
    }
    
    @ApiOperation(value="一级审批人评分", notes="一级审批人评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectEvaluationCadreScore")
    public ResponseEntity zjXmCqjxProjectEvaluationCadreScore(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
    	zjXmCqjxProjectOverallEvaluationAssistant.setState("2");
    	return zjXmCqjxProjectOverallEvaluationAssistantService.zjXmCqjxProjectEvaluationAssistantApproval(zjXmCqjxProjectOverallEvaluationAssistant);
    }
    
    @ApiOperation(value="二级审批人评分", notes="二级审批人评分评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectEvaluationOtherLeaderScore")
    public ResponseEntity zjXmCqjxProjectEvaluationOtherLeaderScore(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
    	zjXmCqjxProjectOverallEvaluationAssistant.setState("3");
    	return zjXmCqjxProjectOverallEvaluationAssistantService.zjXmCqjxProjectEvaluationAssistantApproval(zjXmCqjxProjectOverallEvaluationAssistant);
    }
    
    @ApiOperation(value="三级审批人评分", notes="三级审批人评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectEvaluationComChargeScore")
    public ResponseEntity zjXmCqjxProjectEvaluationComChargeScore(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
    	zjXmCqjxProjectOverallEvaluationAssistant.setState("4");
    	return zjXmCqjxProjectOverallEvaluationAssistantService.zjXmCqjxProjectEvaluationAssistantApproval(zjXmCqjxProjectOverallEvaluationAssistant);
    }
    
    @ApiOperation(value="四级审批人评分", notes="四级审批人评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectEvaluationComExecutiveScore")
    public ResponseEntity zjXmCqjxProjectEvaluationComExecutiveScore(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
    	zjXmCqjxProjectOverallEvaluationAssistant.setState("5");
    	return zjXmCqjxProjectOverallEvaluationAssistantService.zjXmCqjxProjectEvaluationAssistantApproval(zjXmCqjxProjectOverallEvaluationAssistant);
    }
    
    @ApiOperation(value="项目办公室评分", notes="项目办公室评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectEvaluationOfficeScore")
    public ResponseEntity zjXmCqjxProjectEvaluationOfficeScore(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
    	zjXmCqjxProjectOverallEvaluationAssistant.setState("1");
    	return zjXmCqjxProjectOverallEvaluationAssistantService.zjXmCqjxProjectOfficeAssistantApproval(zjXmCqjxProjectOverallEvaluationAssistant);
    }

    @ApiOperation(value="根据主键ID查询所有选项", notes="根据主键ID查询所有选项")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectOverallAssistantListByPrimaryKey")
    public ResponseEntity getZjXmCqjxProjectOverallAssistantListByPrimaryKey(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        return zjXmCqjxProjectOverallEvaluationAssistantService.getZjXmCqjxProjectOverallAssistantListByPrimaryKey(zjXmCqjxProjectOverallEvaluationAssistant);
    }
    
    @ApiOperation(value="查询项目绩效总体评价考核", notes="查询项目绩效总体评价考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectOverallEvaluationAssistantList")
    public ResponseEntity getZjXmCqjxProjectOverallEvaluationAssistantList(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        return zjXmCqjxProjectOverallEvaluationAssistantService.getZjXmCqjxProjectOverallEvaluationAssistantListByCondition(zjXmCqjxProjectOverallEvaluationAssistant);
    }

    @ApiOperation(value="查询详情项目绩效总体评价考核", notes="查询详情项目绩效总体评价考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectOverallEvaluationAssistantDetails")
    public ResponseEntity getZjXmCqjxProjectOverallEvaluationAssistantDetails(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        return zjXmCqjxProjectOverallEvaluationAssistantService.getZjXmCqjxProjectOverallEvaluationAssistantDetails(zjXmCqjxProjectOverallEvaluationAssistant);
    }

    @ApiOperation(value="新增项目绩效总体评价考核", notes="新增项目绩效总体评价考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectOverallEvaluationAssistant")
    public ResponseEntity addZjXmCqjxProjectOverallEvaluationAssistant(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        return zjXmCqjxProjectOverallEvaluationAssistantService.saveZjXmCqjxProjectOverallEvaluationAssistant(zjXmCqjxProjectOverallEvaluationAssistant);
    }

    @ApiOperation(value="更新项目绩效总体评价考核", notes="更新项目绩效总体评价考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectOverallEvaluationAssistant")
    public ResponseEntity updateZjXmCqjxProjectOverallEvaluationAssistant(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
        return zjXmCqjxProjectOverallEvaluationAssistantService.updateZjXmCqjxProjectOverallEvaluationAssistant(zjXmCqjxProjectOverallEvaluationAssistant);
    }

    @ApiOperation(value="删除项目绩效总体评价考核", notes="删除项目绩效总体评价考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistantList", value = "项目绩效总体评价考核List", dataType = "List<ZjXmCqjxProjectOverallEvaluationAssistant>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectOverallEvaluationAssistant")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectOverallEvaluationAssistant(@RequestBody(required = false) List<ZjXmCqjxProjectOverallEvaluationAssistant> zjXmCqjxProjectOverallEvaluationAssistantList) {
        return zjXmCqjxProjectOverallEvaluationAssistantService.batchDeleteUpdateZjXmCqjxProjectOverallEvaluationAssistant(zjXmCqjxProjectOverallEvaluationAssistantList);
    }
    
    @ApiOperation(value="项目绩效总体评价考核已办", notes="项目绩效总体评价考核已办")
    @ApiImplicitParam(name = "zjXmCqjxProjectOverallEvaluationAssistant", value = "项目绩效总体评价考核entity", dataType = "ZjXmCqjxProjectOverallEvaluationAssistant")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectEvaluationAssistantDoneList")
    public ResponseEntity getZjXmCqjxProjectEvaluationAssistantDoneList(@RequestBody(required = false) ZjXmCqjxProjectOverallEvaluationAssistant zjXmCqjxProjectOverallEvaluationAssistant) {
    	return zjXmCqjxProjectOverallEvaluationAssistantService.getZjXmCqjxProjectEvaluationAssistantDoneList(zjXmCqjxProjectOverallEvaluationAssistant);
    }
}