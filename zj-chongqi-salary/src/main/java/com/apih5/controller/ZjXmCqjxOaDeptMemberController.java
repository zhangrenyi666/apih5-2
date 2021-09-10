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
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;
import com.apih5.service.ZjXmCqjxOaDeptMemberService;

@RestController
public class ZjXmCqjxOaDeptMemberController {

    @Autowired(required = true)
    private ZjXmCqjxOaDeptMemberService zjXmCqjxOaDeptMemberService;

    @ApiOperation(value="查询重庆绩效oa部门人员", notes="查询重庆绩效oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxOaDeptMember", value = "重庆绩效oa部门人员entity", dataType = "ZjXmCqjxOaDeptMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxOaDeptMemberList")
    public ResponseEntity getZjXmCqjxOaDeptMemberList(@RequestBody(required = false) ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember) {
        return zjXmCqjxOaDeptMemberService.getZjXmCqjxOaDeptMemberListByCondition(zjXmCqjxOaDeptMember);
    }

    @ApiOperation(value="查询详情重庆绩效oa部门人员", notes="查询详情重庆绩效oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxOaDeptMember", value = "重庆绩效oa部门人员entity", dataType = "ZjXmCqjxOaDeptMember")
    @RequireToken
    @PostMapping("/getZjXmCqjxOaDeptMemberDetails")
    public ResponseEntity getZjXmCqjxOaDeptMemberDetails(@RequestBody(required = false) ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember) {
        return zjXmCqjxOaDeptMemberService.getZjXmCqjxOaDeptMemberDetails(zjXmCqjxOaDeptMember);
    }

    @ApiOperation(value="新增重庆绩效oa部门人员", notes="新增重庆绩效oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxOaDeptMember", value = "重庆绩效oa部门人员entity", dataType = "ZjXmCqjxOaDeptMember")
    @RequireToken
    @PostMapping("/addZjXmCqjxOaDeptMember")
    public ResponseEntity addZjXmCqjxOaDeptMember(@RequestBody(required = false) ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember) {
        return zjXmCqjxOaDeptMemberService.saveZjXmCqjxOaDeptMember(zjXmCqjxOaDeptMember);
    }

    @ApiOperation(value="更新重庆绩效oa部门人员", notes="更新重庆绩效oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxOaDeptMember", value = "重庆绩效oa部门人员entity", dataType = "ZjXmCqjxOaDeptMember")
    @RequireToken
    @PostMapping("/updateZjXmCqjxOaDeptMember")
    public ResponseEntity updateZjXmCqjxOaDeptMember(@RequestBody(required = false) ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember) {
        return zjXmCqjxOaDeptMemberService.updateZjXmCqjxOaDeptMember(zjXmCqjxOaDeptMember);
    }

    @ApiOperation(value="删除重庆绩效oa部门人员", notes="删除重庆绩效oa部门人员")
    @ApiImplicitParam(name = "zjXmCqjxOaDeptMemberList", value = "重庆绩效oa部门人员List", dataType = "List<ZjXmCqjxOaDeptMember>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxOaDeptMember")
    public ResponseEntity batchDeleteUpdateZjXmCqjxOaDeptMember(@RequestBody(required = false) List<ZjXmCqjxOaDeptMember> zjXmCqjxOaDeptMemberList) {
        return zjXmCqjxOaDeptMemberService.batchDeleteUpdateZjXmCqjxOaDeptMember(zjXmCqjxOaDeptMemberList);
    }

}
