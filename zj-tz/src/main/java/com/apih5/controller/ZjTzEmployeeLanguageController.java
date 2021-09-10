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
import com.apih5.mybatis.pojo.ZjTzEmployeeLanguage;
import com.apih5.service.ZjTzEmployeeLanguageService;

@RestController
public class ZjTzEmployeeLanguageController {

    @Autowired(required = true)
    private ZjTzEmployeeLanguageService zjTzEmployeeLanguageService;

    @ApiOperation(value="查询人员库-语言情况", notes="查询人员库-语言情况")
    @ApiImplicitParam(name = "zjTzEmployeeLanguage", value = "人员库-语言情况entity", dataType = "ZjTzEmployeeLanguage")
    @RequireToken
    @PostMapping("/getZjTzEmployeeLanguageList")
    public ResponseEntity getZjTzEmployeeLanguageList(@RequestBody(required = false) ZjTzEmployeeLanguage zjTzEmployeeLanguage) {
        return zjTzEmployeeLanguageService.getZjTzEmployeeLanguageListByCondition(zjTzEmployeeLanguage);
    }

    @ApiOperation(value="查询详情人员库-语言情况", notes="查询详情人员库-语言情况")
    @ApiImplicitParam(name = "zjTzEmployeeLanguage", value = "人员库-语言情况entity", dataType = "ZjTzEmployeeLanguage")
    @RequireToken
    @PostMapping("/getZjTzEmployeeLanguageDetails")
    public ResponseEntity getZjTzEmployeeLanguageDetails(@RequestBody(required = false) ZjTzEmployeeLanguage zjTzEmployeeLanguage) {
        return zjTzEmployeeLanguageService.getZjTzEmployeeLanguageDetails(zjTzEmployeeLanguage);
    }

    @ApiOperation(value="新增人员库-语言情况", notes="新增人员库-语言情况")
    @ApiImplicitParam(name = "zjTzEmployeeLanguage", value = "人员库-语言情况entity", dataType = "ZjTzEmployeeLanguage")
    @RequireToken
    @PostMapping("/addZjTzEmployeeLanguage")
    public ResponseEntity addZjTzEmployeeLanguage(@RequestBody(required = false) ZjTzEmployeeLanguage zjTzEmployeeLanguage) {
        return zjTzEmployeeLanguageService.saveZjTzEmployeeLanguage(zjTzEmployeeLanguage);
    }

    @ApiOperation(value="更新人员库-语言情况", notes="更新人员库-语言情况")
    @ApiImplicitParam(name = "zjTzEmployeeLanguage", value = "人员库-语言情况entity", dataType = "ZjTzEmployeeLanguage")
    @RequireToken
    @PostMapping("/updateZjTzEmployeeLanguage")
    public ResponseEntity updateZjTzEmployeeLanguage(@RequestBody(required = false) ZjTzEmployeeLanguage zjTzEmployeeLanguage) {
        return zjTzEmployeeLanguageService.updateZjTzEmployeeLanguage(zjTzEmployeeLanguage);
    }

    @ApiOperation(value="删除人员库-语言情况", notes="删除人员库-语言情况")
    @ApiImplicitParam(name = "zjTzEmployeeLanguageList", value = "人员库-语言情况List", dataType = "List<ZjTzEmployeeLanguage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzEmployeeLanguage")
    public ResponseEntity batchDeleteUpdateZjTzEmployeeLanguage(@RequestBody(required = false) List<ZjTzEmployeeLanguage> zjTzEmployeeLanguageList) {
        return zjTzEmployeeLanguageService.batchDeleteUpdateZjTzEmployeeLanguage(zjTzEmployeeLanguageList);
    }

}