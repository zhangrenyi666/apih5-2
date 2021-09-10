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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDepartmentHeadDetail;
import com.apih5.service.ZjXmCqjxProjectDepartmentHeadDetailService;

@RestController
public class ZjXmCqjxProjectDepartmentHeadDetailController {

    @Autowired(required = true)
    private ZjXmCqjxProjectDepartmentHeadDetailService zjXmCqjxProjectDepartmentHeadDetailService;

    @ApiOperation(value="查询重庆绩效项目部门负责人详细", notes="查询重庆绩效项目部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHeadDetail", value = "重庆绩效项目部门负责人详细entity", dataType = "ZjXmCqjxProjectDepartmentHeadDetail")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDepartmentHeadDetailList")
    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetailList(@RequestBody(required = false) ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail) {
        return zjXmCqjxProjectDepartmentHeadDetailService.getZjXmCqjxProjectDepartmentHeadDetailListByCondition(zjXmCqjxProjectDepartmentHeadDetail);
    }

    @ApiOperation(value="查询详情重庆绩效项目部门负责人详细", notes="查询详情重庆绩效项目部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHeadDetail", value = "重庆绩效项目部门负责人详细entity", dataType = "ZjXmCqjxProjectDepartmentHeadDetail")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDepartmentHeadDetailDetails")
    public ResponseEntity getZjXmCqjxProjectDepartmentHeadDetailDetails(@RequestBody(required = false) ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail) {
        return zjXmCqjxProjectDepartmentHeadDetailService.getZjXmCqjxProjectDepartmentHeadDetailDetails(zjXmCqjxProjectDepartmentHeadDetail);
    }

    @ApiOperation(value="新增重庆绩效项目部门负责人详细", notes="新增重庆绩效项目部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHeadDetail", value = "重庆绩效项目部门负责人详细entity", dataType = "ZjXmCqjxProjectDepartmentHeadDetail")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectDepartmentHeadDetail")
    public ResponseEntity addZjXmCqjxProjectDepartmentHeadDetail(@RequestBody(required = false) ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail) {
        return zjXmCqjxProjectDepartmentHeadDetailService.saveZjXmCqjxProjectDepartmentHeadDetail(zjXmCqjxProjectDepartmentHeadDetail);
    }

    @ApiOperation(value="更新重庆绩效项目部门负责人详细", notes="更新重庆绩效项目部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHeadDetail", value = "重庆绩效项目部门负责人详细entity", dataType = "ZjXmCqjxProjectDepartmentHeadDetail")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectDepartmentHeadDetail")
    public ResponseEntity updateZjXmCqjxProjectDepartmentHeadDetail(@RequestBody(required = false) ZjXmCqjxProjectDepartmentHeadDetail zjXmCqjxProjectDepartmentHeadDetail) {
        return zjXmCqjxProjectDepartmentHeadDetailService.updateZjXmCqjxProjectDepartmentHeadDetail(zjXmCqjxProjectDepartmentHeadDetail);
    }

    @ApiOperation(value="删除重庆绩效项目部门负责人详细", notes="删除重庆绩效项目部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDepartmentHeadDetailList", value = "重庆绩效项目部门负责人详细List", dataType = "List<ZjXmCqjxProjectDepartmentHeadDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(@RequestBody(required = false) List<ZjXmCqjxProjectDepartmentHeadDetail> zjXmCqjxProjectDepartmentHeadDetailList) {
        return zjXmCqjxProjectDepartmentHeadDetailService.batchDeleteUpdateZjXmCqjxProjectDepartmentHeadDetail(zjXmCqjxProjectDepartmentHeadDetailList);
    }

}