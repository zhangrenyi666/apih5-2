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
import com.apih5.mybatis.pojo.ZjXmSalaryOfficeSalary;
import com.apih5.service.ZjXmSalaryOfficeSalaryService;

@RestController
public class ZjXmSalaryOfficeSalaryController {

    @Autowired(required = true)
    private ZjXmSalaryOfficeSalaryService zjXmSalaryOfficeSalaryService;

    @ApiOperation(value="查询机关工资", notes="查询机关工资")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalary", value = "机关工资entity", dataType = "ZjXmSalaryOfficeSalary")
    @RequireToken
    @PostMapping("/getZjXmSalaryOfficeSalaryList")
    public ResponseEntity getZjXmSalaryOfficeSalaryList(@RequestBody(required = false) ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary) {
        return zjXmSalaryOfficeSalaryService.getZjXmSalaryOfficeSalaryListByCondition(zjXmSalaryOfficeSalary);
    }

    @ApiOperation(value="查询详情机关工资", notes="查询详情机关工资")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalary", value = "机关工资entity", dataType = "ZjXmSalaryOfficeSalary")
    @RequireToken
    @PostMapping("/getZjXmSalaryOfficeSalaryDetails")
    public ResponseEntity getZjXmSalaryOfficeSalaryDetails(@RequestBody(required = false) ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary) {
        return zjXmSalaryOfficeSalaryService.getZjXmSalaryOfficeSalaryDetails(zjXmSalaryOfficeSalary);
    }

    @ApiOperation(value="新增机关工资", notes="新增机关工资")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalary", value = "机关工资entity", dataType = "ZjXmSalaryOfficeSalary")
    @RequireToken
    @PostMapping("/addZjXmSalaryOfficeSalary")
    public ResponseEntity addZjXmSalaryOfficeSalary(@RequestBody(required = false) ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary) {
        return zjXmSalaryOfficeSalaryService.saveZjXmSalaryOfficeSalary(zjXmSalaryOfficeSalary);
    }

    @ApiOperation(value="更新机关工资", notes="更新机关工资")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalary", value = "机关工资entity", dataType = "ZjXmSalaryOfficeSalary")
    @RequireToken
    @PostMapping("/updateZjXmSalaryOfficeSalary")
    public ResponseEntity updateZjXmSalaryOfficeSalary(@RequestBody(required = false) ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary) {
        return zjXmSalaryOfficeSalaryService.updateZjXmSalaryOfficeSalary(zjXmSalaryOfficeSalary);
    }

    @ApiOperation(value="删除机关工资", notes="删除机关工资")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalaryList", value = "机关工资List", dataType = "List<ZjXmSalaryOfficeSalary>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmSalaryOfficeSalary")
    public ResponseEntity batchDeleteUpdateZjXmSalaryOfficeSalary(@RequestBody(required = false) List<ZjXmSalaryOfficeSalary> zjXmSalaryOfficeSalaryList) {
        return zjXmSalaryOfficeSalaryService.batchDeleteUpdateZjXmSalaryOfficeSalary(zjXmSalaryOfficeSalaryList);
    }

}