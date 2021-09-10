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
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHeadDetail;
import com.apih5.mybatis.pojo.ZjXmCqjxExecutiveAssistant;
import com.apih5.service.ZjXmCqjxDepartmentHeadDetailService;

@RestController
public class ZjXmCqjxDepartmentHeadDetailController {

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadDetailService zjXmCqjxDepartmentHeadDetailService;

    @ApiOperation(value="查询重庆绩效部门负责人详细", notes="查询重庆绩效部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHeadDetail", value = "重庆绩效部门负责人详细entity", dataType = "ZjXmCqjxDepartmentHeadDetail")
    @RequireToken
    @PostMapping("/getZjXmCqjxDepartmentHeadDetailList")
    public ResponseEntity getZjXmCqjxDepartmentHeadDetailList(@RequestBody(required = false) ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail) {
        return zjXmCqjxDepartmentHeadDetailService.getZjXmCqjxDepartmentHeadDetailListByCondition(zjXmCqjxDepartmentHeadDetail);
    }

    @ApiOperation(value="查询详情重庆绩效部门负责人详细", notes="查询详情重庆绩效部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHeadDetail", value = "重庆绩效部门负责人详细entity", dataType = "ZjXmCqjxDepartmentHeadDetail")
    @RequireToken
    @PostMapping("/getZjXmCqjxDepartmentHeadDetailDetails")
    public ResponseEntity getZjXmCqjxDepartmentHeadDetailDetails(@RequestBody(required = false) ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail) {
        return zjXmCqjxDepartmentHeadDetailService.getZjXmCqjxDepartmentHeadDetailDetails(zjXmCqjxDepartmentHeadDetail);
    }

    @ApiOperation(value="新增重庆绩效部门负责人详细", notes="新增重庆绩效部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHeadDetail", value = "重庆绩效部门负责人详细entity", dataType = "ZjXmCqjxDepartmentHeadDetail")
    @RequireToken
    @PostMapping("/addZjXmCqjxDepartmentHeadDetail")
    public ResponseEntity addZjXmCqjxDepartmentHeadDetail(@RequestBody(required = false) ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail) {
        return zjXmCqjxDepartmentHeadDetailService.saveZjXmCqjxDepartmentHeadDetail(zjXmCqjxDepartmentHeadDetail);
    }

    @ApiOperation(value="更新重庆绩效部门负责人详细", notes="更新重庆绩效部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHeadDetail", value = "重庆绩效部门负责人详细entity", dataType = "ZjXmCqjxDepartmentHeadDetail")
    @RequireToken
    @PostMapping("/updateZjXmCqjxDepartmentHeadDetail")
    public ResponseEntity updateZjXmCqjxDepartmentHeadDetail(@RequestBody(required = false) ZjXmCqjxDepartmentHeadDetail zjXmCqjxDepartmentHeadDetail) {
        return zjXmCqjxDepartmentHeadDetailService.updateZjXmCqjxDepartmentHeadDetail(zjXmCqjxDepartmentHeadDetail);
    }

    @ApiOperation(value="删除重庆绩效部门负责人详细", notes="删除重庆绩效部门负责人详细")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHeadDetailList", value = "重庆绩效部门负责人详细List", dataType = "List<ZjXmCqjxDepartmentHeadDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxDepartmentHeadDetail")
    public ResponseEntity batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(@RequestBody(required = false) List<ZjXmCqjxDepartmentHeadDetail> zjXmCqjxDepartmentHeadDetailList) {
        return zjXmCqjxDepartmentHeadDetailService.batchDeleteUpdateZjXmCqjxDepartmentHeadDetail(zjXmCqjxDepartmentHeadDetailList);
    }
    
}
