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
import com.apih5.mybatis.pojo.ZxEqAssetSell;
import com.apih5.service.ZxEqAssetSellService;

@RestController
public class ZxEqAssetSellController {

    @Autowired(required = true)
    private ZxEqAssetSellService zxEqAssetSellService;

    @ApiOperation(value="查询设备异动-资产转让", notes="查询设备异动-资产转让")
    @ApiImplicitParam(name = "zxEqAssetSell", value = "设备异动-资产转让entity", dataType = "ZxEqAssetSell")
    @RequireToken
    @PostMapping("/getZxEqAssetSellList")
    public ResponseEntity getZxEqAssetSellList(@RequestBody(required = false) ZxEqAssetSell zxEqAssetSell) {
        return zxEqAssetSellService.getZxEqAssetSellListByCondition(zxEqAssetSell);
    }

    @ApiOperation(value="查询详情设备异动-资产转让", notes="查询详情设备异动-资产转让")
    @ApiImplicitParam(name = "zxEqAssetSell", value = "设备异动-资产转让entity", dataType = "ZxEqAssetSell")
    @RequireToken
    @PostMapping("/getZxEqAssetSellDetails")
    public ResponseEntity getZxEqAssetSellDetails(@RequestBody(required = false) ZxEqAssetSell zxEqAssetSell) {
        return zxEqAssetSellService.getZxEqAssetSellDetails(zxEqAssetSell);
    }

    @ApiOperation(value="新增设备异动-资产转让", notes="新增设备异动-资产转让")
    @ApiImplicitParam(name = "zxEqAssetSell", value = "设备异动-资产转让entity", dataType = "ZxEqAssetSell")
    @RequireToken
    @PostMapping("/addZxEqAssetSell")
    public ResponseEntity addZxEqAssetSell(@RequestBody(required = false) ZxEqAssetSell zxEqAssetSell) {
        return zxEqAssetSellService.saveZxEqAssetSell(zxEqAssetSell);
    }

    @ApiOperation(value="更新设备异动-资产转让", notes="更新设备异动-资产转让")
    @ApiImplicitParam(name = "zxEqAssetSell", value = "设备异动-资产转让entity", dataType = "ZxEqAssetSell")
    @RequireToken
    @PostMapping("/updateZxEqAssetSell")
    public ResponseEntity updateZxEqAssetSell(@RequestBody(required = false) ZxEqAssetSell zxEqAssetSell) {
        return zxEqAssetSellService.updateZxEqAssetSell(zxEqAssetSell);
    }

    @ApiOperation(value="删除设备异动-资产转让", notes="删除设备异动-资产转让")
    @ApiImplicitParam(name = "zxEqAssetSellList", value = "设备异动-资产转让List", dataType = "List<ZxEqAssetSell>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqAssetSell")
    public ResponseEntity batchDeleteUpdateZxEqAssetSell(@RequestBody(required = false) List<ZxEqAssetSell> zxEqAssetSellList) {
        return zxEqAssetSellService.batchDeleteUpdateZxEqAssetSell(zxEqAssetSellList);
    }
    
    @ApiOperation(value="上报设备异动-资产转让", notes="上报设备异动-资产转让")
    @ApiImplicitParam(name = "zxEqAssetSell", value = "设备异动-资产转让entity", dataType = "ZxEqAssetSell")
    @RequireToken
    @PostMapping("/reportZxEqAssetSell")
    public ResponseEntity reportZxEqAssetSell(@RequestBody(required = false) ZxEqAssetSell zxEqAssetSell) {
        return zxEqAssetSellService.reportZxEqAssetSell(zxEqAssetSell);
    }
    
    @ApiOperation(value="审核通过设备异动-资产转让", notes="审核通过设备异动-资产转让")
    @ApiImplicitParam(name = "zxEqAssetSell", value = "设备异动-资产转让entity", dataType = "ZxEqAssetSell")
    @RequireToken
    @PostMapping("/auditAgreeZxEqAssetSell")
    public ResponseEntity auditAgreeZxEqAssetSell(@RequestBody(required = false) ZxEqAssetSell zxEqAssetSell) {
        return zxEqAssetSellService.auditAgreeZxEqAssetSell(zxEqAssetSell);
    }
    
    @ApiOperation(value="审核不通过设备异动-资产转让", notes="审核不通过设备异动-资产转让")
    @ApiImplicitParam(name = "zxEqAssetSell", value = "设备异动-资产转让entity", dataType = "ZxEqAssetSell")
    @RequireToken
    @PostMapping("/auditRefuseZxEqAssetSell")
    public ResponseEntity auditRefuseZxEqAssetSell(@RequestBody(required = false) ZxEqAssetSell zxEqAssetSell) {
        return zxEqAssetSellService.auditRefuseZxEqAssetSell(zxEqAssetSell);
    }

}
