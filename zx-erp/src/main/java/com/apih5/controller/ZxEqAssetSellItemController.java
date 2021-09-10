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
import com.apih5.mybatis.pojo.ZxEqAssetSellItem;
import com.apih5.service.ZxEqAssetSellItemService;

@RestController
public class ZxEqAssetSellItemController {

    @Autowired(required = true)
    private ZxEqAssetSellItemService zxEqAssetSellItemService;

    @ApiOperation(value="查询设备异动-资产转让明细", notes="查询设备异动-资产转让明细")
    @ApiImplicitParam(name = "zxEqAssetSellItem", value = "设备异动-资产转让明细entity", dataType = "ZxEqAssetSellItem")
    @RequireToken
    @PostMapping("/getZxEqAssetSellItemList")
    public ResponseEntity getZxEqAssetSellItemList(@RequestBody(required = false) ZxEqAssetSellItem zxEqAssetSellItem) {
        return zxEqAssetSellItemService.getZxEqAssetSellItemListByCondition(zxEqAssetSellItem);
    }

    @ApiOperation(value="查询详情设备异动-资产转让明细", notes="查询详情设备异动-资产转让明细")
    @ApiImplicitParam(name = "zxEqAssetSellItem", value = "设备异动-资产转让明细entity", dataType = "ZxEqAssetSellItem")
    @RequireToken
    @PostMapping("/getZxEqAssetSellItemDetails")
    public ResponseEntity getZxEqAssetSellItemDetails(@RequestBody(required = false) ZxEqAssetSellItem zxEqAssetSellItem) {
        return zxEqAssetSellItemService.getZxEqAssetSellItemDetails(zxEqAssetSellItem);
    }

    @ApiOperation(value="新增设备异动-资产转让明细", notes="新增设备异动-资产转让明细")
    @ApiImplicitParam(name = "zxEqAssetSellItem", value = "设备异动-资产转让明细entity", dataType = "ZxEqAssetSellItem")
    @RequireToken
    @PostMapping("/addZxEqAssetSellItem")
    public ResponseEntity addZxEqAssetSellItem(@RequestBody(required = false) ZxEqAssetSellItem zxEqAssetSellItem) {
        return zxEqAssetSellItemService.saveZxEqAssetSellItem(zxEqAssetSellItem);
    }

    @ApiOperation(value="更新设备异动-资产转让明细", notes="更新设备异动-资产转让明细")
    @ApiImplicitParam(name = "zxEqAssetSellItem", value = "设备异动-资产转让明细entity", dataType = "ZxEqAssetSellItem")
    @RequireToken
    @PostMapping("/updateZxEqAssetSellItem")
    public ResponseEntity updateZxEqAssetSellItem(@RequestBody(required = false) ZxEqAssetSellItem zxEqAssetSellItem) {
        return zxEqAssetSellItemService.updateZxEqAssetSellItem(zxEqAssetSellItem);
    }

    @ApiOperation(value="删除设备异动-资产转让明细", notes="删除设备异动-资产转让明细")
    @ApiImplicitParam(name = "zxEqAssetSellItemList", value = "设备异动-资产转让明细List", dataType = "List<ZxEqAssetSellItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqAssetSellItem")
    public ResponseEntity batchDeleteUpdateZxEqAssetSellItem(@RequestBody(required = false) List<ZxEqAssetSellItem> zxEqAssetSellItemList) {
        return zxEqAssetSellItemService.batchDeleteUpdateZxEqAssetSellItem(zxEqAssetSellItemList);
    }

    @ApiOperation(value="报表导出设备异动-资产转让明细", notes="报表导出设备异动-资产转让明细")
    @ApiImplicitParam(name = "zxEqAssetSellItem", value = "设备异动-资产转让明细entity", dataType = "ZxEqAssetSellItem")
    @PostMapping("/ureportZxEqAssetSellItemList")
    public List<ZxEqAssetSellItem> ureportZxEqAssetSellItemList(@RequestBody(required = false) ZxEqAssetSellItem zxEqAssetSellItem) {
        return zxEqAssetSellItemService.ureportZxEqAssetSellItemList(zxEqAssetSellItem);
    }

}
