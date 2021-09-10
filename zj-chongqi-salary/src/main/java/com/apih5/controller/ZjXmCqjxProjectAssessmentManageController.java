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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectAssessmentManage;
import com.apih5.service.ZjXmCqjxProjectAssessmentManageService;

@RestController
public class ZjXmCqjxProjectAssessmentManageController {

    @Autowired(required = true)
    private ZjXmCqjxProjectAssessmentManageService zjXmCqjxProjectAssessmentManageService;

    @ApiOperation(value="查询重庆绩效项目考核计划", notes="查询重庆绩效项目考核计划")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManage", value = "重庆绩效项目考核计划entity", dataType = "ZjXmCqjxProjectAssessmentManage")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssessmentManageList")
    public ResponseEntity getZjXmCqjxProjectAssessmentManageList(@RequestBody(required = false) ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
        return zjXmCqjxProjectAssessmentManageService.getZjXmCqjxProjectAssessmentManageListByCondition(zjXmCqjxProjectAssessmentManage);
    }

    @ApiOperation(value="查询详情重庆绩效项目考核计划", notes="查询详情重庆绩效项目考核计划")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManage", value = "重庆绩效项目考核计划entity", dataType = "ZjXmCqjxProjectAssessmentManage")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssessmentManageDetails")
    public ResponseEntity getZjXmCqjxProjectAssessmentManageDetails(@RequestBody(required = false) ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
        return zjXmCqjxProjectAssessmentManageService.getZjXmCqjxProjectAssessmentManageDetails(zjXmCqjxProjectAssessmentManage);
    }

    @ApiOperation(value="新增重庆绩效项目考核计划", notes="新增重庆绩效项目考核计划")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManage", value = "重庆绩效项目考核计划entity", dataType = "ZjXmCqjxProjectAssessmentManage")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectAssessmentManage")
    public ResponseEntity addZjXmCqjxProjectAssessmentManage(@RequestBody(required = false) ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
        return zjXmCqjxProjectAssessmentManageService.saveZjXmCqjxProjectAssessmentManage(zjXmCqjxProjectAssessmentManage);
    }

    @ApiOperation(value="更新重庆绩效项目考核计划", notes="更新重庆绩效项目考核计划")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManage", value = "重庆绩效项目考核计划entity", dataType = "ZjXmCqjxProjectAssessmentManage")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectAssessmentManage")
    public ResponseEntity updateZjXmCqjxProjectAssessmentManage(@RequestBody(required = false) ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
        return zjXmCqjxProjectAssessmentManageService.updateZjXmCqjxProjectAssessmentManage(zjXmCqjxProjectAssessmentManage);
    }

    @ApiOperation(value="删除重庆绩效项目考核计划", notes="删除重庆绩效项目考核计划")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManageList", value = "重庆绩效项目考核计划List", dataType = "List<ZjXmCqjxProjectAssessmentManage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectAssessmentManage")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectAssessmentManage(@RequestBody(required = false) List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxProjectAssessmentManageList) {
        return zjXmCqjxProjectAssessmentManageService.batchDeleteUpdateZjXmCqjxProjectAssessmentManage(zjXmCqjxProjectAssessmentManageList);
    }
    
    @ApiOperation(value="批量发起考核计划", notes="批量发起考核计划")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManageList", value = "重庆绩效考核计划List", dataType = "List<ZjXmCqjxProjectAssessmentManage>")
    @RequireToken
    @PostMapping("/batchZjXmCqjxProjectAssessmentManageSendMessage")
    public ResponseEntity batchZjXmCqjxAssessmentManageSendMessage(@RequestBody(required = false) List<ZjXmCqjxProjectAssessmentManage> zjXmCqjxProjectAssessmentManageList) {
        return zjXmCqjxProjectAssessmentManageService.batchZjXmCqjxProjectAssessmentManageSendMessage(zjXmCqjxProjectAssessmentManageList);
    }    
//    
//    @ApiOperation(value="批量发起考核计划", notes="批量发起考核计划")
//    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManage", value = "重庆绩效考核计划List", dataType = "ZjXmCqjxProjectAssessmentManage")
//    @RequireToken
//    @PostMapping("/zjXmCqjxAssessmentManageSendMessage")
//    public ResponseEntity zjXmCqjxAssessmentManageSendMessage(@RequestBody(required = false) ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
//    	return zjXmCqjxProjectAssessmentManageService.zjXmCqjxProjectAssessmentManageSendMessage(zjXmCqjxProjectAssessmentManage);
//    }    
    
    @ApiOperation(value="重庆绩效考核计划详情", notes="重庆绩效考核计划详情")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManage", value = "重庆绩效考核计划entity", dataType = "ZjXmCqjxProjectAssessmentManage")
    @RequireToken
    @PostMapping("/zjXmCqjxProjectAssessmentManageDetailByQuarter")
    public ResponseEntity zjXmCqjxAssessmentManageDetailByQuarter(@RequestBody(required = false) ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
    	return zjXmCqjxProjectAssessmentManageService.zjXmCqjxProjectAssessmentManageDetailByQuarter(zjXmCqjxProjectAssessmentManage);
    }
    
    @ApiOperation(value="重庆绩效考核计划详情", notes="重庆绩效考核计划详情")
    @ApiImplicitParam(name = "zjXmCqjxProjectAssessmentManage", value = "重庆绩效考核计划entity", dataType = "ZjXmCqjxProjectAssessmentManage")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectAssessmentManageListByDeptHeader")
    public ResponseEntity getZjXmCqjxAssessmentManageListByDeptHeader(@RequestBody(required = false) ZjXmCqjxProjectAssessmentManage zjXmCqjxProjectAssessmentManage) {
    	return zjXmCqjxProjectAssessmentManageService.getZjXmCqjxProjectAssessmentManageListByDeptHeader(zjXmCqjxProjectAssessmentManage);
    }
    
}