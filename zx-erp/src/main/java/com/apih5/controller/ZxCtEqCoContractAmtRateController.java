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
import com.apih5.mybatis.pojo.ZxCtEqCoContractAmtRate;
import com.apih5.service.ZxCtEqCoContractAmtRateService;

@RestController
public class ZxCtEqCoContractAmtRateController {

    @Autowired(required = true)
    private ZxCtEqCoContractAmtRateService zxCtEqCoContractAmtRateService;

    @ApiOperation(value="查询设备合同保证金比例表", notes="查询设备合同保证金比例表")
    @ApiImplicitParam(name = "zxCtEqCoContractAmtRate", value = "设备合同保证金比例表entity", dataType = "ZxCtEqCoContractAmtRate")
    @RequireToken
    @PostMapping("/getZxCtEqCoContractAmtRateList")
    public ResponseEntity getZxCtEqCoContractAmtRateList(@RequestBody(required = false) ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate) {
        return zxCtEqCoContractAmtRateService.getZxCtEqCoContractAmtRateListByCondition(zxCtEqCoContractAmtRate);
    }

    @ApiOperation(value="查询详情设备合同保证金比例表", notes="查询详情设备合同保证金比例表")
    @ApiImplicitParam(name = "zxCtEqCoContractAmtRate", value = "设备合同保证金比例表entity", dataType = "ZxCtEqCoContractAmtRate")
    @RequireToken
    @PostMapping("/getZxCtEqCoContractAmtRateDetail")
    public ResponseEntity getZxCtEqCoContractAmtRateDetail(@RequestBody(required = false) ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate) {
        return zxCtEqCoContractAmtRateService.getZxCtEqCoContractAmtRateDetail(zxCtEqCoContractAmtRate);
    }

    @ApiOperation(value="新增设备合同保证金比例表", notes="新增设备合同保证金比例表")
    @ApiImplicitParam(name = "zxCtEqCoContractAmtRate", value = "设备合同保证金比例表entity", dataType = "ZxCtEqCoContractAmtRate")
    @RequireToken
    @PostMapping("/addZxCtEqCoContractAmtRate")
    public ResponseEntity addZxCtEqCoContractAmtRate(@RequestBody(required = false) ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate) {
        return zxCtEqCoContractAmtRateService.saveZxCtEqCoContractAmtRate(zxCtEqCoContractAmtRate);
    }

    @ApiOperation(value="更新设备合同保证金比例表", notes="更新设备合同保证金比例表")
    @ApiImplicitParam(name = "zxCtEqCoContractAmtRate", value = "设备合同保证金比例表entity", dataType = "ZxCtEqCoContractAmtRate")
    @RequireToken
    @PostMapping("/updateZxCtEqCoContractAmtRate")
    public ResponseEntity updateZxCtEqCoContractAmtRate(@RequestBody(required = false) ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate) {
        return zxCtEqCoContractAmtRateService.updateZxCtEqCoContractAmtRate(zxCtEqCoContractAmtRate);
    }

    @ApiOperation(value="删除设备合同保证金比例表", notes="删除设备合同保证金比例表")
    @ApiImplicitParam(name = "zxCtEqCoContractAmtRateList", value = "设备合同保证金比例表List", dataType = "List<ZxCtEqCoContractAmtRate>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtEqCoContractAmtRate")
    public ResponseEntity batchDeleteUpdateZxCtEqCoContractAmtRate(@RequestBody(required = false) List<ZxCtEqCoContractAmtRate> zxCtEqCoContractAmtRateList) {
        return zxCtEqCoContractAmtRateService.batchDeleteUpdateZxCtEqCoContractAmtRate(zxCtEqCoContractAmtRateList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
