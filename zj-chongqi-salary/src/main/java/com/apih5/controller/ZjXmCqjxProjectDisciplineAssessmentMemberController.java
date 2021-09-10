package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessmentMember;
import com.apih5.service.ZjXmCqjxProjectDisciplineAssessmentMemberService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZjXmCqjxProjectDisciplineAssessmentMemberController {

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentMemberService zjXmCqjxProjectDisciplineAssessmentMemberService;

    @ApiOperation(value="查询重庆项目绩效员工纪律考核人员", notes="查询重庆项目绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentMember", value = "重庆项目绩效员工纪律考核人员entity", dataType = "ZjXmCqjxProjectDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDisciplineAssessmentMemberList")
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentMemberList(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
        return zjXmCqjxProjectDisciplineAssessmentMemberService.getZjXmCqjxProjectDisciplineAssessmentMemberListByCondition(zjXmCqjxProjectDisciplineAssessmentMember);
    }

    @ApiOperation(value="查询详情重庆项目绩效员工纪律考核人员", notes="查询详情重庆项目绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentMember", value = "重庆项目绩效员工纪律考核人员entity", dataType = "ZjXmCqjxProjectDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDisciplineAssessmentMemberDetails")
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentMemberDetails(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
        return zjXmCqjxProjectDisciplineAssessmentMemberService.getZjXmCqjxProjectDisciplineAssessmentMemberDetails(zjXmCqjxProjectDisciplineAssessmentMember);
    }

    @ApiOperation(value="新增重庆项目绩效员工纪律考核人员", notes="新增重庆项目绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentMember", value = "重庆项目绩效员工纪律考核人员entity", dataType = "ZjXmCqjxProjectDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectDisciplineAssessmentMember")
    public ResponseEntity addZjXmCqjxProjectDisciplineAssessmentMember(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
        return zjXmCqjxProjectDisciplineAssessmentMemberService.saveZjXmCqjxProjectDisciplineAssessmentMember(zjXmCqjxProjectDisciplineAssessmentMember);
    }

    @ApiOperation(value="更新重庆项目绩效员工纪律考核人员", notes="更新重庆项目绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentMember", value = "重庆项目绩效员工纪律考核人员entity", dataType = "ZjXmCqjxProjectDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectDisciplineAssessmentMember")
    public ResponseEntity updateZjXmCqjxProjectDisciplineAssessmentMember(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
        return zjXmCqjxProjectDisciplineAssessmentMemberService.updateZjXmCqjxProjectDisciplineAssessmentMember(zjXmCqjxProjectDisciplineAssessmentMember);
    }

    @ApiOperation(value="删除重庆项目绩效员工纪律考核人员", notes="删除重庆项目绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentMemberList", value = "重庆项目绩效员工纪律考核人员List", dataType = "List<ZjXmCqjxProjectDisciplineAssessmentMember>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentMember")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentMember(@RequestBody(required = false) List<ZjXmCqjxProjectDisciplineAssessmentMember> zjXmCqjxProjectDisciplineAssessmentMemberList) {
        return zjXmCqjxProjectDisciplineAssessmentMemberService.batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentMember(zjXmCqjxProjectDisciplineAssessmentMemberList);
    }
    
    @ApiOperation(value="重庆绩效员工纪律考核人员批量修改", notes="重庆绩效员工纪律考核人员批量修改")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentMemberList", value = "重庆绩效员工纪律考核人员List", dataType = "List<ZjXmCqjxProjectDisciplineAssessmentMember>")
    @RequireToken
    @PostMapping("/batchAddZjXmCqjxProjectDisciplineAssessmentMember")
    public ResponseEntity batchAddZjXmCqjxProjectDisciplineAssessmentMember(@RequestBody(required = false) List<ZjXmCqjxProjectDisciplineAssessmentMember> zjXmCqjxProjectDisciplineAssessmentMemberList) {
    	return zjXmCqjxProjectDisciplineAssessmentMemberService.batchAddZjXmCqjxDisciplineAssessmentMember(zjXmCqjxProjectDisciplineAssessmentMemberList);
    }
    
    @ApiOperation(value="重庆绩效员工纪律考核人员提交", notes="重庆绩效员工纪律考核人员批量修改")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentMember", value = "重庆绩效员工纪律考核人员List", dataType = "ZjXmCqjxProjectDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectDisciplineAssessmentSubmit")
    public ResponseEntity zjXmCqjxProjectDisciplineAssessmentSubmit(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentMember zjXmCqjxProjectDisciplineAssessmentMember) {
    	return zjXmCqjxProjectDisciplineAssessmentMemberService.zjXmCqjxDisciplineAssessmentSubmit(zjXmCqjxProjectDisciplineAssessmentMember);
    }
}