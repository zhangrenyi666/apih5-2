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
import com.apih5.mybatis.pojo.ZjTzEmployeeQualification;
import com.apih5.service.ZjTzEmployeeQualificationService;

@RestController
public class ZjTzEmployeeQualificationController {

    @Autowired(required = true)
    private ZjTzEmployeeQualificationService zjTzEmployeeQualificationService;

    @ApiOperation(value="查询人员库-职（执）业资格", notes="查询人员库-职（执）业资格")
    @ApiImplicitParam(name = "zjTzEmployeeQualification", value = "人员库-职（执）业资格entity", dataType = "ZjTzEmployeeQualification")
    @RequireToken
    @PostMapping("/getZjTzEmployeeQualificationList")
    public ResponseEntity getZjTzEmployeeQualificationList(@RequestBody(required = false) ZjTzEmployeeQualification zjTzEmployeeQualification) {
        return zjTzEmployeeQualificationService.getZjTzEmployeeQualificationListByCondition(zjTzEmployeeQualification);
    }

    @ApiOperation(value="查询详情人员库-职（执）业资格", notes="查询详情人员库-职（执）业资格")
    @ApiImplicitParam(name = "zjTzEmployeeQualification", value = "人员库-职（执）业资格entity", dataType = "ZjTzEmployeeQualification")
    @RequireToken
    @PostMapping("/getZjTzEmployeeQualificationDetails")
    public ResponseEntity getZjTzEmployeeQualificationDetails(@RequestBody(required = false) ZjTzEmployeeQualification zjTzEmployeeQualification) {
        return zjTzEmployeeQualificationService.getZjTzEmployeeQualificationDetails(zjTzEmployeeQualification);
    }

    @ApiOperation(value="新增人员库-职（执）业资格", notes="新增人员库-职（执）业资格")
    @ApiImplicitParam(name = "zjTzEmployeeQualification", value = "人员库-职（执）业资格entity", dataType = "ZjTzEmployeeQualification")
    @RequireToken
    @PostMapping("/addZjTzEmployeeQualification")
    public ResponseEntity addZjTzEmployeeQualification(@RequestBody(required = false) ZjTzEmployeeQualification zjTzEmployeeQualification) {
        return zjTzEmployeeQualificationService.saveZjTzEmployeeQualification(zjTzEmployeeQualification);
    }

    @ApiOperation(value="更新人员库-职（执）业资格", notes="更新人员库-职（执）业资格")
    @ApiImplicitParam(name = "zjTzEmployeeQualification", value = "人员库-职（执）业资格entity", dataType = "ZjTzEmployeeQualification")
    @RequireToken
    @PostMapping("/updateZjTzEmployeeQualification")
    public ResponseEntity updateZjTzEmployeeQualification(@RequestBody(required = false) ZjTzEmployeeQualification zjTzEmployeeQualification) {
        return zjTzEmployeeQualificationService.updateZjTzEmployeeQualification(zjTzEmployeeQualification);
    }

    @ApiOperation(value="删除人员库-职（执）业资格", notes="删除人员库-职（执）业资格")
    @ApiImplicitParam(name = "zjTzEmployeeQualificationList", value = "人员库-职（执）业资格List", dataType = "List<ZjTzEmployeeQualification>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzEmployeeQualification")
    public ResponseEntity batchDeleteUpdateZjTzEmployeeQualification(@RequestBody(required = false) List<ZjTzEmployeeQualification> zjTzEmployeeQualificationList) {
        return zjTzEmployeeQualificationService.batchDeleteUpdateZjTzEmployeeQualification(zjTzEmployeeQualificationList);
    }

}