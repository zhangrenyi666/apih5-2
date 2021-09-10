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
import com.apih5.mybatis.pojo.ZjXmSalaryOfficeSalaryDetails;
import com.apih5.service.ZjXmSalaryOfficeSalaryDetailsService;

@RestController
public class ZjXmSalaryOfficeSalaryDetailsController {

    @Autowired(required = true)
    private ZjXmSalaryOfficeSalaryDetailsService zjXmSalaryOfficeSalaryDetailsService;

    @ApiOperation(value="查询机关工资明细", notes="查询机关工资明细")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalaryDetails", value = "机关工资明细entity", dataType = "ZjXmSalaryOfficeSalaryDetails")
    @RequireToken
    @PostMapping("/getZjXmSalaryOfficeSalaryDetailsList")
    public ResponseEntity getZjXmSalaryOfficeSalaryDetailsList(@RequestBody(required = false) ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails) {
        return zjXmSalaryOfficeSalaryDetailsService.getZjXmSalaryOfficeSalaryDetailsListByCondition(zjXmSalaryOfficeSalaryDetails);
    }

    @ApiOperation(value="查询详情机关工资明细", notes="查询详情机关工资明细")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalaryDetails", value = "机关工资明细entity", dataType = "ZjXmSalaryOfficeSalaryDetails")
    @RequireToken
    @PostMapping("/getZjXmSalaryOfficeSalaryDetailsDetails")
    public ResponseEntity getZjXmSalaryOfficeSalaryDetailsDetails(@RequestBody(required = false) ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails) {
        return zjXmSalaryOfficeSalaryDetailsService.getZjXmSalaryOfficeSalaryDetailsDetails(zjXmSalaryOfficeSalaryDetails);
    }

    @ApiOperation(value="新增机关工资明细", notes="新增机关工资明细")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalaryDetails", value = "机关工资明细entity", dataType = "ZjXmSalaryOfficeSalaryDetails")
    @RequireToken
    @PostMapping("/addZjXmSalaryOfficeSalaryDetails")
    public ResponseEntity addZjXmSalaryOfficeSalaryDetails(@RequestBody(required = false) ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails) {
        return zjXmSalaryOfficeSalaryDetailsService.saveZjXmSalaryOfficeSalaryDetails(zjXmSalaryOfficeSalaryDetails);
    }

    @ApiOperation(value="更新机关工资明细", notes="更新机关工资明细")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalaryDetails", value = "机关工资明细entity", dataType = "ZjXmSalaryOfficeSalaryDetails")
    @RequireToken
    @PostMapping("/updateZjXmSalaryOfficeSalaryDetails")
    public ResponseEntity updateZjXmSalaryOfficeSalaryDetails(@RequestBody(required = false) ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails) {
        return zjXmSalaryOfficeSalaryDetailsService.updateZjXmSalaryOfficeSalaryDetails(zjXmSalaryOfficeSalaryDetails);
    }

    @ApiOperation(value="删除机关工资明细", notes="删除机关工资明细")
    @ApiImplicitParam(name = "zjXmSalaryOfficeSalaryDetailsList", value = "机关工资明细List", dataType = "List<ZjXmSalaryOfficeSalaryDetails>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmSalaryOfficeSalaryDetails")
    public ResponseEntity batchDeleteUpdateZjXmSalaryOfficeSalaryDetails(@RequestBody(required = false) List<ZjXmSalaryOfficeSalaryDetails> zjXmSalaryOfficeSalaryDetailsList) {
        return zjXmSalaryOfficeSalaryDetailsService.batchDeleteUpdateZjXmSalaryOfficeSalaryDetails(zjXmSalaryOfficeSalaryDetailsList);
    }

}