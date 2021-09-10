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
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentMember;
import com.apih5.service.ZjXmCqjxDisciplineAssessmentMemberService;

@RestController
public class ZjXmCqjxDisciplineAssessmentMemberController {

    @Autowired(required = true)
    private ZjXmCqjxDisciplineAssessmentMemberService zjXmCqjxDisciplineAssessmentMemberService;

    @ApiOperation(value="查询重庆绩效员工纪律考核人员", notes="查询重庆绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentMember", value = "重庆绩效员工纪律考核人员entity", dataType = "ZjXmCqjxDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssessmentMemberList")
    public ResponseEntity getZjXmCqjxDisciplineAssessmentMemberList(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        return zjXmCqjxDisciplineAssessmentMemberService.getZjXmCqjxDisciplineAssessmentMemberListByCondition(zjXmCqjxDisciplineAssessmentMember);
    }

    @ApiOperation(value="查询详情重庆绩效员工纪律考核人员", notes="查询详情重庆绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentMember", value = "重庆绩效员工纪律考核人员entity", dataType = "ZjXmCqjxDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxDisciplineAssessmentMemberDetails")
    public ResponseEntity getZjXmCqjxDisciplineAssessmentMemberDetails(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        return zjXmCqjxDisciplineAssessmentMemberService.getZjXmCqjxDisciplineAssessmentMemberDetails(zjXmCqjxDisciplineAssessmentMember);
    }

    @ApiOperation(value="新增重庆绩效员工纪律考核人员", notes="新增重庆绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentMember", value = "重庆绩效员工纪律考核人员entity", dataType = "ZjXmCqjxDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/addZjXmCqjxDisciplineAssessmentMember")
    public ResponseEntity addZjXmCqjxDisciplineAssessmentMember(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        return zjXmCqjxDisciplineAssessmentMemberService.saveZjXmCqjxDisciplineAssessmentMember(zjXmCqjxDisciplineAssessmentMember);
    }

    @ApiOperation(value="更新重庆绩效员工纪律考核人员", notes="更新重庆绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentMember", value = "重庆绩效员工纪律考核人员entity", dataType = "ZjXmCqjxDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/updateZjXmCqjxDisciplineAssessmentMember")
    public ResponseEntity updateZjXmCqjxDisciplineAssessmentMember(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
        return zjXmCqjxDisciplineAssessmentMemberService.updateZjXmCqjxDisciplineAssessmentMember(zjXmCqjxDisciplineAssessmentMember);
    }

    @ApiOperation(value="删除重庆绩效员工纪律考核人员", notes="删除重庆绩效员工纪律考核人员")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentMemberList", value = "重庆绩效员工纪律考核人员List", dataType = "List<ZjXmCqjxDisciplineAssessmentMember>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxDisciplineAssessmentMember")
    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessmentMember(@RequestBody(required = false) List<ZjXmCqjxDisciplineAssessmentMember> zjXmCqjxDisciplineAssessmentMemberList) {
        return zjXmCqjxDisciplineAssessmentMemberService.batchDeleteUpdateZjXmCqjxDisciplineAssessmentMember(zjXmCqjxDisciplineAssessmentMemberList);
    }
    
    @ApiOperation(value="重庆绩效员工纪律考核人员批量修改", notes="重庆绩效员工纪律考核人员批量修改")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentMemberList", value = "重庆绩效员工纪律考核人员List", dataType = "List<ZjXmCqjxDisciplineAssessmentMember>")
    @RequireToken
    @PostMapping("/batchAddZjXmCqjxDisciplineAssessmentMember")
    public ResponseEntity batchAddZjXmCqjxDisciplineAssessmentMember(@RequestBody(required = false) List<ZjXmCqjxDisciplineAssessmentMember> zjXmCqjxDisciplineAssessmentMemberList) {
    	return zjXmCqjxDisciplineAssessmentMemberService.batchAddZjXmCqjxDisciplineAssessmentMember(zjXmCqjxDisciplineAssessmentMemberList);
    }
    
    @ApiOperation(value="重庆绩效员工纪律考核人员提交", notes="重庆绩效员工纪律考核人员批量修改")
    @ApiImplicitParam(name = "zjXmCqjxDisciplineAssessmentMember", value = "重庆绩效员工纪律考核人员List", dataType = "ZjXmCqjxDisciplineAssessmentMember")
    @RequireToken
    @PostMapping("/zjXmCqjxDisciplineAssessmentSubmit")
    public ResponseEntity zjXmCqjxDisciplineAssessmentSubmit(@RequestBody(required = false) ZjXmCqjxDisciplineAssessmentMember zjXmCqjxDisciplineAssessmentMember) {
    	return zjXmCqjxDisciplineAssessmentMemberService.zjXmCqjxDisciplineAssessmentSubmit(zjXmCqjxDisciplineAssessmentMember);
    }

}
