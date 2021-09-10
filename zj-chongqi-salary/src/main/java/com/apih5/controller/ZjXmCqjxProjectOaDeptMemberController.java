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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOaDeptMember;
import com.apih5.service.ZjXmCqjxProjectOaDeptMemberService;

@RestController
public class ZjXmCqjxProjectOaDeptMemberController {

    @Autowired(required = true)
    private ZjXmCqjxProjectOaDeptMemberService zjXmCqjxProjectOaDeptMemberService;

    @ApiOperation(value="查询重庆绩效项目oa部门人员", notes="查询重庆绩效项目oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectOaDeptMember", value = "重庆绩效项目oa部门人员entity", dataType = "ZjXmCqjxProjectOaDeptMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectOaDeptMemberList")
    public ResponseEntity getZjXmCqjxProjectOaDeptMemberList(@RequestBody(required = false) ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember) {
        return zjXmCqjxProjectOaDeptMemberService.getZjXmCqjxProjectOaDeptMemberListByCondition(zjXmCqjxProjectOaDeptMember);
    }

    @ApiOperation(value="查询详情重庆绩效项目oa部门人员", notes="查询详情重庆绩效项目oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectOaDeptMember", value = "重庆绩效项目oa部门人员entity", dataType = "ZjXmCqjxProjectOaDeptMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectOaDeptMemberDetails")
    public ResponseEntity getZjXmCqjxProjectOaDeptMemberDetails(@RequestBody(required = false) ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember) {
        return zjXmCqjxProjectOaDeptMemberService.getZjXmCqjxProjectOaDeptMemberDetails(zjXmCqjxProjectOaDeptMember);
    }

    @ApiOperation(value="新增重庆绩效项目oa部门人员", notes="新增重庆绩效项目oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectOaDeptMember", value = "重庆绩效项目oa部门人员entity", dataType = "ZjXmCqjxProjectOaDeptMember")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectOaDeptMember")
    public ResponseEntity addZjXmCqjxProjectOaDeptMember(@RequestBody(required = false) ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember) {
        return zjXmCqjxProjectOaDeptMemberService.saveZjXmCqjxProjectOaDeptMember(zjXmCqjxProjectOaDeptMember);
    }

    @ApiOperation(value="更新重庆绩效项目oa部门人员", notes="更新重庆绩效项目oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectOaDeptMember", value = "重庆绩效项目oa部门人员entity", dataType = "ZjXmCqjxProjectOaDeptMember")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectOaDeptMember")
    public ResponseEntity updateZjXmCqjxProjectOaDeptMember(@RequestBody(required = false) ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember) {
        return zjXmCqjxProjectOaDeptMemberService.updateZjXmCqjxProjectOaDeptMember(zjXmCqjxProjectOaDeptMember);
    }

    @ApiOperation(value="删除重庆绩效项目oa部门人员", notes="删除重庆绩效项目oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxProjectOaDeptMemberList", value = "重庆绩效项目oa部门人员List", dataType = "List<ZjXmCqjxProjectOaDeptMember>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectOaDeptMember")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectOaDeptMember(@RequestBody(required = false) List<ZjXmCqjxProjectOaDeptMember> zjXmCqjxProjectOaDeptMemberList) {
        return zjXmCqjxProjectOaDeptMemberService.batchDeleteUpdateZjXmCqjxProjectOaDeptMember(zjXmCqjxProjectOaDeptMemberList);
    }
}