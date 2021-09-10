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
import com.apih5.mybatis.pojo.ZxEqEquipDepreciation;
import com.apih5.service.ZxEqEquipDepreciationService;

@RestController
public class ZxEqEquipDepreciationController {

    @Autowired(required = true)
    private ZxEqEquipDepreciationService zxEqEquipDepreciationService;

    @ApiOperation(value="查询设备异动-资产折旧", notes="查询设备异动-资产折旧")
    @ApiImplicitParam(name = "zxEqEquipDepreciation", value = "设备异动-资产折旧entity", dataType = "ZxEqEquipDepreciation")
    @RequireToken
    @PostMapping("/getZxEqEquipDepreciationList")
    public ResponseEntity getZxEqEquipDepreciationList(@RequestBody(required = false) ZxEqEquipDepreciation zxEqEquipDepreciation) {
        return zxEqEquipDepreciationService.getZxEqEquipDepreciationListByCondition(zxEqEquipDepreciation);
    }

    @ApiOperation(value="查询详情设备异动-资产折旧", notes="查询详情设备异动-资产折旧")
    @ApiImplicitParam(name = "zxEqEquipDepreciation", value = "设备异动-资产折旧entity", dataType = "ZxEqEquipDepreciation")
    @RequireToken
    @PostMapping("/getZxEqEquipDepreciationDetails")
    public ResponseEntity getZxEqEquipDepreciationDetails(@RequestBody(required = false) ZxEqEquipDepreciation zxEqEquipDepreciation) {
        return zxEqEquipDepreciationService.getZxEqEquipDepreciationDetails(zxEqEquipDepreciation);
    }

    @ApiOperation(value="新增设备异动-资产折旧", notes="新增设备异动-资产折旧")
    @ApiImplicitParam(name = "zxEqEquipDepreciation", value = "设备异动-资产折旧entity", dataType = "ZxEqEquipDepreciation")
    @RequireToken
    @PostMapping("/addZxEqEquipDepreciation")
    public ResponseEntity addZxEqEquipDepreciation(@RequestBody(required = false) ZxEqEquipDepreciation zxEqEquipDepreciation) {
        return zxEqEquipDepreciationService.saveZxEqEquipDepreciation(zxEqEquipDepreciation);
    }

    @ApiOperation(value="更新设备异动-资产折旧", notes="更新设备异动-资产折旧")
    @ApiImplicitParam(name = "zxEqEquipDepreciation", value = "设备异动-资产折旧entity", dataType = "ZxEqEquipDepreciation")
    @RequireToken
    @PostMapping("/updateZxEqEquipDepreciation")
    public ResponseEntity updateZxEqEquipDepreciation(@RequestBody(required = false) ZxEqEquipDepreciation zxEqEquipDepreciation) {
        return zxEqEquipDepreciationService.updateZxEqEquipDepreciation(zxEqEquipDepreciation);
    }

    @ApiOperation(value="删除设备异动-资产折旧", notes="删除设备异动-资产折旧")
    @ApiImplicitParam(name = "zxEqEquipDepreciationList", value = "设备异动-资产折旧List", dataType = "List<ZxEqEquipDepreciation>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipDepreciation")
    public ResponseEntity batchDeleteUpdateZxEqEquipDepreciation(@RequestBody(required = false) List<ZxEqEquipDepreciation> zxEqEquipDepreciationList) {
        return zxEqEquipDepreciationService.batchDeleteUpdateZxEqEquipDepreciation(zxEqEquipDepreciationList);
    }
    
    @ApiOperation(value="审核设备异动-资产折旧", notes="审核设备异动-资产折旧")
    @ApiImplicitParam(name = "zxEqEquipDepreciation", value = "设备异动-资产折旧entity", dataType = "ZxEqEquipDepreciation")
    @RequireToken
    @PostMapping("/auditZxEqEquipDepreciation")
    public ResponseEntity auditZxEqEquipDepreciation(@RequestBody(required = false) ZxEqEquipDepreciation zxEqEquipDepreciation) {
        return zxEqEquipDepreciationService.auditZxEqEquipDepreciation(zxEqEquipDepreciation);
    }

}
