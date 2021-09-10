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
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementItem;
import com.apih5.service.ZxCtSuppliesShopSettlementItemService;

@RestController
public class ZxCtSuppliesShopSettlementItemController {

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementItemService zxCtSuppliesShopSettlementItemService;

    @ApiOperation(value="查询物资结算单明细", notes="查询物资结算单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementItem", value = "物资结算单明细entity", dataType = "ZxCtSuppliesShopSettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopSettlementItemList")
    public ResponseEntity getZxCtSuppliesShopSettlementItemList(@RequestBody(required = false) ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        return zxCtSuppliesShopSettlementItemService.getZxCtSuppliesShopSettlementItemListByCondition(zxCtSuppliesShopSettlementItem);
    }

    @ApiOperation(value="查询详情物资结算单明细", notes="查询详情物资结算单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementItem", value = "物资结算单明细entity", dataType = "ZxCtSuppliesShopSettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopSettlementItemDetail")
    public ResponseEntity getZxCtSuppliesShopSettlementItemDetail(@RequestBody(required = false) ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        return zxCtSuppliesShopSettlementItemService.getZxCtSuppliesShopSettlementItemDetail(zxCtSuppliesShopSettlementItem);
    }

    @ApiOperation(value="新增物资结算单明细", notes="新增物资结算单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementItem", value = "物资结算单明细entity", dataType = "ZxCtSuppliesShopSettlementItem")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopSettlementItem")
    public ResponseEntity addZxCtSuppliesShopSettlementItem(@RequestBody(required = false) ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        return zxCtSuppliesShopSettlementItemService.saveZxCtSuppliesShopSettlementItem(zxCtSuppliesShopSettlementItem);
    }

    @ApiOperation(value="更新物资结算单明细", notes="更新物资结算单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementItem", value = "物资结算单明细entity", dataType = "ZxCtSuppliesShopSettlementItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopSettlementItem")
    public ResponseEntity updateZxCtSuppliesShopSettlementItem(@RequestBody(required = false) ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        return zxCtSuppliesShopSettlementItemService.updateZxCtSuppliesShopSettlementItem(zxCtSuppliesShopSettlementItem);
    }

    @ApiOperation(value="删除物资结算单明细", notes="删除物资结算单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementItemList", value = "物资结算单明细List", dataType = "List<ZxCtSuppliesShopSettlementItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesShopSettlementItem")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopSettlementItem(@RequestBody(required = false) List<ZxCtSuppliesShopSettlementItem> zxCtSuppliesShopSettlementItemList) {
        return zxCtSuppliesShopSettlementItemService.batchDeleteUpdateZxCtSuppliesShopSettlementItem(zxCtSuppliesShopSettlementItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="根据合同ID查询统计项", notes="根据合同ID查询统计项")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementItem", value = "物资结算单明细entity", dataType = "ZxCtSuppliesShopSettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopSettlementItemListByConID")
    public ResponseEntity getZxCtSuppliesShopSettlementItemListByConID(@RequestBody(required = false) ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        return zxCtSuppliesShopSettlementItemService.getZxCtSuppliesShopSettlementItemListByConID(zxCtSuppliesShopSettlementItem);
    }   

    @ApiOperation(value="更新物资结算单明细", notes="更新物资结算单明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementItem", value = "物资结算单明细entity", dataType = "ZxCtSuppliesShopSettlementItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopSettlementItemByConID")
    public ResponseEntity updateZxCtSuppliesShopSettlementItemByConID(@RequestBody(required = false) ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem) {
        return zxCtSuppliesShopSettlementItemService.updateZxCtSuppliesShopSettlementItemByConID(zxCtSuppliesShopSettlementItem);
    }    
}
