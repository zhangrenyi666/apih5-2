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
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessment;
import com.apih5.service.ZjXmCqjxDisciplineAssessmentService;

@RestController
public class ZjXmCqjxDisciplineAssessmentController {

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentService zjXmCqjxDisciplineAssessmentService;

    @ApiOperation(value="查询重庆绩效考核员工纪律性考核", notes="查询重庆绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssessmentList")
    public ResponseEntity getZjXmCqjxDisciplineAssessmentList(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        return zjXmCqjxDisciplineAssessmentService.getZjXmCqjxDisciplineAssessmentListByCondition(zjXmCqjxDisciplineAssessment);
    }

    @ApiOperation(value="查询详情重庆绩效考核员工纪律性考核", notes="查询详情重庆绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssessmentDetails")
    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetails(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        return zjXmCqjxDisciplineAssessmentService.getZjXmCqjxDisciplineAssessmentDetails(zjXmCqjxDisciplineAssessment);
    }

    @ApiOperation(value="新增重庆绩效考核员工纪律性考核", notes="新增重庆绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/addZjXmCqjxDisciplineAssessment")
    public ResponseEntity addZjXmCqjxDisciplineAssessment(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        return zjXmCqjxDisciplineAssessmentService.saveZjXmCqjxDisciplineAssessment(zjXmCqjxDisciplineAssessment);
    }

    @ApiOperation(value="发起重庆绩效考核员工纪律性考核", notes="新增重庆绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxDisciplineAssessmentLaunch")
    public ResponseEntity zjXmCqjxDisciplineAssessmentLaunch(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
    	zjXmCqjxDisciplineAssessment.setDisciplineState("3");
        return zjXmCqjxDisciplineAssessmentService.zjXmCqjxDisciplineAssessmentLaunch(zjXmCqjxDisciplineAssessment);
    }

    @ApiOperation(value="发起重庆绩效考核员工纪律性部门负责人审核", notes="新增重庆绩效考核员工纪律性负责人审核")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxDisciplineAssessmentDeptApproval")
    public ResponseEntity zjXmCqjxDisciplineAssessmentDeptLaunch(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        return zjXmCqjxDisciplineAssessmentService.zjXmCqjxDisciplineAssessmentDeptLaunch(zjXmCqjxDisciplineAssessment);
    }
    
    @ApiOperation(value="纪律性待办列表", notes="纪律性待办列表")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxDisciplineAssessmentToDoList")
    public ResponseEntity zjXmCqjxDisciplineAssessmentToDoList(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
//    	zjXmCqjxDisciplineAssessment.setDisciplineState("3");
    	return zjXmCqjxDisciplineAssessmentService.zjXmCqjxDisciplineAssessmentToDoList(zjXmCqjxDisciplineAssessment);
    }

    @ApiOperation(value="重庆绩效考核员工纪律性主管领导审核", notes="新增重庆绩效考核员工纪律性领导审核")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/zjXmCqjxDisciplineAssessmentExecutiveApproval")
    public ResponseEntity zjXmCqjxDisciplineAssessmentExecutiveApproval(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
    	zjXmCqjxDisciplineAssessment.setDisciplineState("3");
        return zjXmCqjxDisciplineAssessmentService.zjXmCqjxDisciplineAssessmentExecutiveApproval(zjXmCqjxDisciplineAssessment);
    }
    
    @ApiOperation(value="更新重庆绩效考核员工纪律性考核", notes="更新重庆绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/updateZjXmCqjxDisciplineAssessment")
    public ResponseEntity updateZjXmCqjxDisciplineAssessment(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
        return zjXmCqjxDisciplineAssessmentService.updateZjXmCqjxDisciplineAssessment(zjXmCqjxDisciplineAssessment);
    }

    @ApiOperation(value="删除重庆绩效考核员工纪律性考核", notes="删除重庆绩效考核员工纪律性考核")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentList", value = "重庆绩效考核员工纪律性考核List", dataType = "List<ZjXmCqjxDisciplineAssessment>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxDisciplineAssessment")
    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessment(@RequestBody(required = false) List<ZjXmCqjxDisciplineAssessment> zjXmCqjxDisciplineAssessmentList) {
        return zjXmCqjxDisciplineAssessmentService.batchDeleteUpdateZjXmCqjxDisciplineAssessment(zjXmCqjxDisciplineAssessmentList);
    }
    
    @ApiOperation(value="纪律性已办列表", notes="纪律性已办列表")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessment", value = "重庆绩效考核员工纪律性考核entity", dataType = "ZjXmCqjxDisciplineAssessment")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssessmentDoneList")
    public ResponseEntity getZjXmCqjxDisciplineAssessmentDoneList(@RequestBody(required = false) ZjXmCqjxDisciplineAssessment zjXmCqjxDisciplineAssessment) {
//    	zjXmCqjxDisciplineAssessment.setDisciplineState("3");
    	return zjXmCqjxDisciplineAssessmentService.getZjXmCqjxDisciplineAssessmentDoneList(zjXmCqjxDisciplineAssessment);
    }

}
