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
import com.apih5.mybatis.pojo.ZjTzComplianceBase;
import com.apih5.service.ZjTzComplianceBaseService;

@RestController
public class ZjTzComplianceBaseController {

    @Autowired(required = true)
    private ZjTzComplianceBaseService zjTzComplianceBaseService;

    @ApiOperation(value="查询合规环节设置", notes="查询合规环节设置")
    @ApiImplicitParam(name = "zjTzComplianceBase", value = "合规环节设置entity", dataType = "ZjTzComplianceBase")
    @RequireToken
    @PostMapping("/getZjTzComplianceBaseList")
    public ResponseEntity getZjTzComplianceBaseList(@RequestBody(required = false) ZjTzComplianceBase zjTzComplianceBase) {
        return zjTzComplianceBaseService.getZjTzComplianceBaseListByCondition(zjTzComplianceBase);
    }

    @ApiOperation(value="查询详情合规环节设置", notes="查询详情合规环节设置")
    @ApiImplicitParam(name = "zjTzComplianceBase", value = "合规环节设置entity", dataType = "ZjTzComplianceBase")
    @RequireToken
    @PostMapping("/getZjTzComplianceBaseDetails")
    public ResponseEntity getZjTzComplianceBaseDetails(@RequestBody(required = false) ZjTzComplianceBase zjTzComplianceBase) {
        return zjTzComplianceBaseService.getZjTzComplianceBaseDetails(zjTzComplianceBase);
    }

    @ApiOperation(value="新增合规环节设置", notes="新增合规环节设置")
    @ApiImplicitParam(name = "zjTzComplianceBase", value = "合规环节设置entity", dataType = "ZjTzComplianceBase")
    @RequireToken
    @PostMapping("/addZjTzComplianceBase")
    public ResponseEntity addZjTzComplianceBase(@RequestBody(required = false) ZjTzComplianceBase zjTzComplianceBase) {
        return zjTzComplianceBaseService.saveZjTzComplianceBase(zjTzComplianceBase);
    }

    @ApiOperation(value="更新合规环节设置", notes="更新合规环节设置")
    @ApiImplicitParam(name = "zjTzComplianceBase", value = "合规环节设置entity", dataType = "ZjTzComplianceBase")
    @RequireToken
    @PostMapping("/updateZjTzComplianceBase")
    public ResponseEntity updateZjTzComplianceBase(@RequestBody(required = false) ZjTzComplianceBase zjTzComplianceBase) {
        return zjTzComplianceBaseService.updateZjTzComplianceBase(zjTzComplianceBase);
    }

    @ApiOperation(value="删除合规环节设置", notes="删除合规环节设置")
    @ApiImplicitParam(name = "zjTzComplianceBaseList", value = "合规环节设置List", dataType = "List<ZjTzComplianceBase>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzComplianceBase")
    public ResponseEntity batchDeleteUpdateZjTzComplianceBase(@RequestBody(required = false) List<ZjTzComplianceBase> zjTzComplianceBaseList) {
        return zjTzComplianceBaseService.batchDeleteUpdateZjTzComplianceBase(zjTzComplianceBaseList);
    }

}