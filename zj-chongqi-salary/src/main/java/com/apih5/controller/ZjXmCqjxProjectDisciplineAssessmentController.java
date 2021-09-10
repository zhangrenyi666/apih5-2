package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessment;
import com.apih5.service.ZjXmCqjxProjectDisciplineAssessmentService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZjXmCqjxProjectDisciplineAssessmentController {

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentService zjXmCqjxProjectDisciplineAssessmentService;

    @ApiOperation(value="查询重庆项目绩效考核员工纪律性考核", notes="查询重庆项目绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆项目绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxProjectDisciplineAssessment")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDisciplineAssessmentList")
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentList(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        return zjXmCqjxProjectDisciplineAssessmentService.getZjXmCqjxProjectDisciplineAssessmentListByCondition(zjXmCqjxProjectDisciplineAssessment);
    }

    @ApiOperation(value="查询详情重庆项目绩效考核员工纪律性考核", notes="查询详情重庆项目绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆项目绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxProjectDisciplineAssessment")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDisciplineAssessmentDetails")
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentDetails(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        return zjXmCqjxProjectDisciplineAssessmentService.getZjXmCqjxProjectDisciplineAssessmentDetails(zjXmCqjxProjectDisciplineAssessment);
    }

    @ApiOperation(value="新增重庆项目绩效考核员工纪律性考核", notes="新增重庆项目绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆项目绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxProjectDisciplineAssessment")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectDisciplineAssessment")
    public ResponseEntity addZjXmCqjxProjectDisciplineAssessment(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        return zjXmCqjxProjectDisciplineAssessmentService.saveZjXmCqjxProjectDisciplineAssessment(zjXmCqjxProjectDisciplineAssessment);
    }

    @ApiOperation(value="更新重庆项目绩效考核员工纪律性考核", notes="更新重庆项目绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆项目绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxProjectDisciplineAssessment")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectDisciplineAssessment")
    public ResponseEntity updateZjXmCqjxProjectDisciplineAssessment(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        return zjXmCqjxProjectDisciplineAssessmentService.updateZjXmCqjxProjectDisciplineAssessment(zjXmCqjxProjectDisciplineAssessment);
    }

    @ApiOperation(value="删除重庆项目绩效考核员工纪律性考核", notes="删除重庆项目绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentList", value = "重庆项目绩效考核员工纪律性考核List", dataType = "List<ZjXmCqjxProjectDisciplineAssessment>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectDisciplineAssessment")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssessment(@RequestBody(required = false) List<ZjXmCqjxProjectDisciplineAssessment> zjXmCqjxProjectDisciplineAssessmentList) {
        return zjXmCqjxProjectDisciplineAssessmentService.batchDeleteUpdateZjXmCqjxProjectDisciplineAssessment(zjXmCqjxProjectDisciplineAssessmentList);
    }

    @ApiOperation(value="发起重庆绩效考核员工纪律性考核", notes="新增重庆绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxProjectDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDisciplineAssessmentLaunch")
    public ResponseEntity zjXmCqjxProjectDisciplineAssessmentLaunch(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
    	zjXmCqjxProjectDisciplineAssessment.setDisciplineState("3");
        return zjXmCqjxProjectDisciplineAssessmentService.zjXmCqjxDisciplineAssessmentLaunch(zjXmCqjxProjectDisciplineAssessment);
    }

    @ApiOperation(value="发起重庆绩效考核员工纪律性部门负责人审核", notes="新增重庆绩效考核员工纪律性负责人审核")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxProjectDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDisciplineAssessmentDeptApproval")
    public ResponseEntity zjXmCqjxProjectDisciplineAssessmentDeptLaunch(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
        return zjXmCqjxProjectDisciplineAssessmentService.zjXmCqjxDisciplineAssessmentDeptLaunch(zjXmCqjxProjectDisciplineAssessment);
    }
    
    @ApiOperation(value="纪律性待办列表", notes="纪律性待办列表")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDisciplineAssessmentToDoList")
    public ResponseEntity zjXmCqjxProjectDisciplineAssessmentToDoList(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
//    	zjXmCqjxDisciplineAssessment.setDisciplineState("3");
    	return zjXmCqjxProjectDisciplineAssessmentService.zjXmCqjxDisciplineAssessmentToDoList(zjXmCqjxProjectDisciplineAssessment);
    }

    @ApiOperation(value="重庆绩效考核员工纪律性主管领导审核", notes="新增重庆绩效考核员工纪律性领导审核")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxProjectDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDisciplineAssessmentExecutiveApproval")
    public ResponseEntity zjXmCqjxProjectDisciplineAssessmentExecutiveApproval(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
    	zjXmCqjxProjectDisciplineAssessment.setDisciplineState("3");
        return zjXmCqjxProjectDisciplineAssessmentService.zjXmCqjxDisciplineAssessmentExecutiveApproval(zjXmCqjxProjectDisciplineAssessment);
    }
    
    @ApiOperation(value="纪律性已办列表", notes="纪律性已办列表")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDisciplineAssessmentDoneList")
    public ResponseEntity zjXmCqjxProjectDisciplineAssessmentDoneList(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessment zjXmCqjxProjectDisciplineAssessment) {
//    	zjXmCqjxDisciplineAssessment.setDisciplineState("3");
    	return zjXmCqjxProjectDisciplineAssessmentService.zjXmCqjxProjectDisciplineAssessmentDoneList(zjXmCqjxProjectDisciplineAssessment);
    }
}