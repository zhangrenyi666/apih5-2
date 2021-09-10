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
import com.apih5.mybatis.pojo.ZjXmCqjxCollaborationAssessmentMember;
import com.apih5.service.ZjXmCqjxCollaborationAssessmentMemberService;

@RestController
public class ZjXmCqjxCollaborationAssessmentMemberController {

    @Autowired(required = true)
    private ZjXmCqjxCollaborationAssessmentMemberService zjXmCqjxCollaborationAssessmentMemberService;

    @ApiOperation(value="查询重庆绩效考核员工协作性考核", notes="查询重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxCollaborationAssessmentMemberList")
    public ResponseEntity getZjXmCqjxCollaborationAssessmentMemberList(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        return zjXmCqjxCollaborationAssessmentMemberService.getZjXmCqjxCollaborationAssessmentMemberListByCondition(zjXmCqjxCollaborationAssessmentMember);
    }
    
    @ApiOperation(value="查询重庆绩效考核员工协作性考核", notes="查询重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxCollaborationAssessmentListByUser")
    public ResponseEntity getZjXmCqjxCollaborationAssessmentListByUser(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
    	return zjXmCqjxCollaborationAssessmentMemberService.getZjXmCqjxCollaborationAssessmentListByUser(zjXmCqjxCollaborationAssessmentMember);
    }
    
    @ApiOperation(value="查询重庆绩效考核员工协作性考核", notes="查询重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/checkZjXmCqjxCollaborationAssessmentScore")
    public ResponseEntity checkZjXmCqjxCollaborationAssessmentScore(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
    	return zjXmCqjxCollaborationAssessmentMemberService.checkZjXmCqjxCollaborationAssessmentScore(zjXmCqjxCollaborationAssessmentMember);
    }
    
    @ApiOperation(value="查询详情重庆绩效考核员工协作性考核", notes="查询详情重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxCollaborationAssessmentMemberDetails")
    public ResponseEntity getZjXmCqjxCollaborationAssessmentMemberDetails(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        return zjXmCqjxCollaborationAssessmentMemberService.getZjXmCqjxCollaborationAssessmentMemberDetails(zjXmCqjxCollaborationAssessmentMember);
    }
    
    @ApiOperation(value="查询详情重庆绩效考核员工协作性考核（除自己以外）", notes="查询详情重庆绩效考核员工协作性考核（除自己以外）")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxCollaborationMemberDetailsNoSelf")
    public ResponseEntity getZjXmCqjxCollaborationMemberDetailsNoSelf(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
    	return zjXmCqjxCollaborationAssessmentMemberService.getZjXmCqjxCollaborationMemberDetailsNoSelf(zjXmCqjxCollaborationAssessmentMember);
    }

    @ApiOperation(value="新增重庆绩效考核员工协作性考核", notes="新增重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/addZjXmCqjxCollaborationAssessmentMember")
    public ResponseEntity addZjXmCqjxCollaborationAssessmentMember(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        return zjXmCqjxCollaborationAssessmentMemberService.saveZjXmCqjxCollaborationAssessmentMember(zjXmCqjxCollaborationAssessmentMember);
    }

    @ApiOperation(value="更新重庆绩效考核员工协作性考核", notes="更新重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMember", value = "重庆绩效考核员工协作性考核entity", dataType = "ZjXmCqjxCollaborationAssessmentMember")
    @RequireToken
    @PostMapping("/updateZjXmCqjxCollaborationAssessmentMember")
    public ResponseEntity updateZjXmCqjxCollaborationAssessmentMember(@RequestBody(required = false) ZjXmCqjxCollaborationAssessmentMember zjXmCqjxCollaborationAssessmentMember) {
        return zjXmCqjxCollaborationAssessmentMemberService.updateZjXmCqjxCollaborationAssessmentMember(zjXmCqjxCollaborationAssessmentMember);
    }

    @ApiOperation(value="删除重庆绩效考核员工协作性考核", notes="删除重庆绩效考核员工协作性考核")
    @ApiImplicitParam(name = "zjXmCqjxCollaborationAssessmentMemberList", value = "重庆绩效考核员工协作性考核List", dataType = "List<ZjXmCqjxCollaborationAssessmentMember>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxCollaborationAssessmentMember")
    public ResponseEntity batchDeleteUpdateZjXmCqjxCollaborationAssessmentMember(@RequestBody(required = false) List<ZjXmCqjxCollaborationAssessmentMember> zjXmCqjxCollaborationAssessmentMemberList) {
        return zjXmCqjxCollaborationAssessmentMemberService.batchDeleteUpdateZjXmCqjxCollaborationAssessmentMember(zjXmCqjxCollaborationAssessmentMemberList);
    }

}
