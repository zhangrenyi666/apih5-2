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
import com.apih5.mybatis.pojo.ZxEqEquipDepreciationItem;
import com.apih5.service.ZxEqEquipDepreciationItemService;

@RestController
public class ZxEqEquipDepreciationItemController {

    @Autowired(required = true)
    private ZxEqEquipDepreciationItemService zxEqEquipDepreciationItemService;

    @ApiOperation(value="查询设备异动-资产折旧明细", notes="查询设备异动-资产折旧明细")
    @ApiImplicitParam(name = "zxEqEquipDepreciationItem", value = "设备异动-资产折旧明细entity", dataType = "ZxEqEquipDepreciationItem")
    @RequireToken
    @PostMapping("/getZxEqEquipDepreciationItemList")
    public ResponseEntity getZxEqEquipDepreciationItemList(@RequestBody(required = false) ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        return zxEqEquipDepreciationItemService.getZxEqEquipDepreciationItemListByCondition(zxEqEquipDepreciationItem);
    }

    @ApiOperation(value="查询详情设备异动-资产折旧明细", notes="查询详情设备异动-资产折旧明细")
    @ApiImplicitParam(name = "zxEqEquipDepreciationItem", value = "设备异动-资产折旧明细entity", dataType = "ZxEqEquipDepreciationItem")
    @RequireToken
    @PostMapping("/getZxEqEquipDepreciationItemDetails")
    public ResponseEntity getZxEqEquipDepreciationItemDetails(@RequestBody(required = false) ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        return zxEqEquipDepreciationItemService.getZxEqEquipDepreciationItemDetails(zxEqEquipDepreciationItem);
    }

    @ApiOperation(value="新增设备异动-资产折旧明细", notes="新增设备异动-资产折旧明细")
    @ApiImplicitParam(name = "zxEqEquipDepreciationItem", value = "设备异动-资产折旧明细entity", dataType = "ZxEqEquipDepreciationItem")
    @RequireToken
    @PostMapping("/addZxEqEquipDepreciationItem")
    public ResponseEntity addZxEqEquipDepreciationItem(@RequestBody(required = false) ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        return zxEqEquipDepreciationItemService.saveZxEqEquipDepreciationItem(zxEqEquipDepreciationItem);
    }

    @ApiOperation(value="更新设备异动-资产折旧明细", notes="更新设备异动-资产折旧明细")
    @ApiImplicitParam(name = "zxEqEquipDepreciationItem", value = "设备异动-资产折旧明细entity", dataType = "ZxEqEquipDepreciationItem")
    @RequireToken
    @PostMapping("/updateZxEqEquipDepreciationItem")
    public ResponseEntity updateZxEqEquipDepreciationItem(@RequestBody(required = false) ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        return zxEqEquipDepreciationItemService.updateZxEqEquipDepreciationItem(zxEqEquipDepreciationItem);
    }

    @ApiOperation(value="删除设备异动-资产折旧明细", notes="删除设备异动-资产折旧明细")
    @ApiImplicitParam(name = "zxEqEquipDepreciationItemList", value = "设备异动-资产折旧明细List", dataType = "List<ZxEqEquipDepreciationItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipDepreciationItem")
    public ResponseEntity batchDeleteUpdateZxEqEquipDepreciationItem(@RequestBody(required = false) List<ZxEqEquipDepreciationItem> zxEqEquipDepreciationItemList) {
        return zxEqEquipDepreciationItemService.batchDeleteUpdateZxEqEquipDepreciationItem(zxEqEquipDepreciationItemList);
    }
    
    @ApiOperation(value="设备台账tab查询设备异动-资产折旧明细", notes="设备台账tab查询设备异动-资产折旧明细")
    @ApiImplicitParam(name = "zxEqEquipDepreciationItem", value = "设备异动-资产折旧明细entity", dataType = "ZxEqEquipDepreciationItem")
    @RequireToken
    @PostMapping("/getZxEqEquipDepreciationItemListForTab")
    public ResponseEntity getZxEqEquipDepreciationItemListForTab(@RequestBody(required = false) ZxEqEquipDepreciationItem zxEqEquipDepreciationItem) {
        return zxEqEquipDepreciationItemService.getZxEqEquipDepreciationItemListForTab(zxEqEquipDepreciationItem);
    }
    

}
