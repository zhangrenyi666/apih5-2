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
import com.apih5.mybatis.pojo.ZjXmCqjxAssessmentManage;
import com.apih5.service.ZjXmCqjxAssessmentManageService;

@RestController
public class ZjXmCqjxAssessmentManageController {

    @Autowired(required = true)
    private ZjXmCqjxAssessmentManageService zjXmCqjxAssessmentManageService;

    @ApiOperation(value="查询重庆绩效考核计划", notes="查询重庆绩效考核计划")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManage", value = "重庆绩效考核计划entity", dataType = "ZjXmCqjxAssessmentManage")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssessmentManageList")
    public ResponseEntity getZjXmCqjxAssessmentManageList(@RequestBody(required = false) ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
        return zjXmCqjxAssessmentManageService.getZjXmCqjxAssessmentManageListByCondition(zjXmCqjxAssessmentManage);
    }

    @ApiOperation(value="查询详情重庆绩效考核计划", notes="查询详情重庆绩效考核计划")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManage", value = "重庆绩效考核计划entity", dataType = "ZjXmCqjxAssessmentManage")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssessmentManageDetails")
    public ResponseEntity getZjXmCqjxAssessmentManageDetails(@RequestBody(required = false) ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
        return zjXmCqjxAssessmentManageService.getZjXmCqjxAssessmentManageDetails(zjXmCqjxAssessmentManage);
    }

    @ApiOperation(value="新增重庆绩效考核计划", notes="新增重庆绩效考核计划")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManage", value = "重庆绩效考核计划entity", dataType = "ZjXmCqjxAssessmentManage")
    @RequireToken
    @PostMapping("/addZjXmCqjxAssessmentManage")
    public ResponseEntity addZjXmCqjxAssessmentManage(@RequestBody(required = false) ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
        return zjXmCqjxAssessmentManageService.saveZjXmCqjxAssessmentManage(zjXmCqjxAssessmentManage);
    }

    @ApiOperation(value="更新重庆绩效考核计划", notes="更新重庆绩效考核计划")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManage", value = "重庆绩效考核计划entity", dataType = "ZjXmCqjxAssessmentManage")
    @RequireToken
    @PostMapping("/updateZjXmCqjxAssessmentManage")
    public ResponseEntity updateZjXmCqjxAssessmentManage(@RequestBody(required = false) ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
        return zjXmCqjxAssessmentManageService.updateZjXmCqjxAssessmentManage(zjXmCqjxAssessmentManage);
    }

    @ApiOperation(value="删除重庆绩效考核计划", notes="删除重庆绩效考核计划")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManageList", value = "重庆绩效考核计划List", dataType = "List<ZjXmCqjxAssessmentManage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxAssessmentManage")
    public ResponseEntity batchDeleteUpdateZjXmCqjxAssessmentManage(@RequestBody(required = false) List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList) {
        return zjXmCqjxAssessmentManageService.batchDeleteUpdateZjXmCqjxAssessmentManage(zjXmCqjxAssessmentManageList);
    }
    
    @ApiOperation(value="批量发起考核计划", notes="批量发起考核计划")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManageList", value = "重庆绩效考核计划List", dataType = "List<ZjXmCqjxAssessmentManage>")
    @RequireToken
    @PostMapping("/batchZjXmCqjxAssessmentManageSendMessage")
    public ResponseEntity batchZjXmCqjxAssessmentManageSendMessage(@RequestBody(required = false) List<ZjXmCqjxAssessmentManage> zjXmCqjxAssessmentManageList) {
        return zjXmCqjxAssessmentManageService.batchZjXmCqjxAssessmentManageSendMessage(zjXmCqjxAssessmentManageList);
    }    
    
    @ApiOperation(value="批量发起考核计划", notes="批量发起考核计划")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManage", value = "重庆绩效考核计划List", dataType = "ZjXmCqjxAssessmentManage")
    @RequireToken
    @PostMapping("/zjXmCqjxAssessmentManageSendMessage")
    public ResponseEntity zjXmCqjxAssessmentManageSendMessage(@RequestBody(required = false) ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
    	return zjXmCqjxAssessmentManageService.zjXmCqjxAssessmentManageSendMessage(zjXmCqjxAssessmentManage);
    }    
    
    @ApiOperation(value="重庆绩效考核计划详情", notes="重庆绩效考核计划详情")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManage", value = "重庆绩效考核计划entity", dataType = "ZjXmCqjxAssessmentManage")
    @RequireToken
    @PostMapping("/zjXmCqjxAssessmentManageDetailByQuarter")
    public ResponseEntity zjXmCqjxAssessmentManageDetailByQuarter(@RequestBody(required = false) ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
    	return zjXmCqjxAssessmentManageService.zjXmCqjxAssessmentManageDetailByQuarter(zjXmCqjxAssessmentManage);
    }
    
    @ApiOperation(value="重庆绩效考核计划详情", notes="重庆绩效考核计划详情")
    @ApiImplicitParam(name = "zjXmCqjxAssessmentManage", value = "重庆绩效考核计划entity", dataType = "ZjXmCqjxAssessmentManage")
    @RequireToken
    @PostMapping("/getZjXmCqjxAssessmentManageListByDeptHeader")
    public ResponseEntity getZjXmCqjxAssessmentManageListByDeptHeader(@RequestBody(required = false) ZjXmCqjxAssessmentManage zjXmCqjxAssessmentManage) {
    	return zjXmCqjxAssessmentManageService.getZjXmCqjxAssessmentManageListByDeptHeader(zjXmCqjxAssessmentManage);
    }

}
