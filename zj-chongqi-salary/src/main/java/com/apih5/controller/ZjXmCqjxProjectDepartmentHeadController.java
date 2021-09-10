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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHead;
import com.apih5.service.ZjXmCqjxProjectDepartmentHeadService;

@RestController
public class ZjXmCqjxProjectDepartmentHeadController {

    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadService zjXmCqjxProjectDepartmentHeadService;

    @ApiOperation(value="查询重庆绩效项目部门负责人", notes="查询重庆绩效项目部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHead", value = "重庆绩效项目部门负责人entity", dataType = "ZjXmCqjxProjectDepartmentHead")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDepartmentHeadList")
    public ResponseEntity getZjXmCqjxProjectDepartmentHeadList(@RequestBody(required = false) ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead) {
        return zjXmCqjxProjectDepartmentHeadService.getZjXmCqjxProjectDepartmentHeadListByCondition(zjXmCqjxProjectDepartmentHead);
    }

    @ApiOperation(value="查询详情重庆绩效项目部门负责人", notes="查询详情重庆绩效项目部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHead", value = "重庆绩效项目部门负责人entity", dataType = "ZjXmCqjxProjectDepartmentHead")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDepartmentHeadDetails")
    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetails(@RequestBody(required = false) ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead) {
        return zjXmCqjxProjectDepartmentHeadService.getZjXmCqjxProjectDepartmentHeadDetails(zjXmCqjxProjectDepartmentHead);
    }

    @ApiOperation(value="新增重庆绩效项目部门负责人", notes="新增重庆绩效项目部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHead", value = "重庆绩效项目部门负责人entity", dataType = "ZjXmCqjxProjectDepartmentHead")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectDepartmentHead")
    public ResponseEntity addZjXmCqjxProjectDepartmentHead(@RequestBody(required = false) ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead) {
        return zjXmCqjxProjectDepartmentHeadService.saveZjXmCqjxProjectDepartmentHead(zjXmCqjxProjectDepartmentHead);
    }

    @ApiOperation(value="更新重庆绩效项目部门负责人", notes="更新重庆绩效项目部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHead", value = "重庆绩效项目部门负责人entity", dataType = "ZjXmCqjxProjectDepartmentHead")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectDepartmentHead")
    public ResponseEntity updateZjXmCqjxProjectDepartmentHead(@RequestBody(required = false) ZjXmCqjxProjectDepartmentHead zjXmCqjxProjectDepartmentHead) {
        return zjXmCqjxProjectDepartmentHeadService.updateZjXmCqjxProjectDepartmentHead(zjXmCqjxProjectDepartmentHead);
    }

    @ApiOperation(value="删除重庆绩效项目部门负责人", notes="删除重庆绩效项目部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHeadList", value = "重庆绩效项目部门负责人List", dataType = "List<ZjXmCqjxProjectDepartmentHead>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectDepartmentHead")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDepartmentHead(@RequestBody(required = false) List<ZjXmCqjxProjectDepartmentHead> zjXmCqjxProjectDepartmentHeadList) {
        return zjXmCqjxProjectDepartmentHeadService.batchDeleteUpdateZjXmCqjxProjectDepartmentHead(zjXmCqjxProjectDepartmentHeadList);
    }
}