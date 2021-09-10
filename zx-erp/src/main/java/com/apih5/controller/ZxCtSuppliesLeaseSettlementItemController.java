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
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementItem;
import com.apih5.service.ZxCtSuppliesLeaseSettlementItemService;

@RestController
public class ZxCtSuppliesLeaseSettlementItemController {

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementItemService zxCtSuppliesLeaseSettlementItemService;

    @ApiOperation(value="查询物资租赁类结算明细", notes="查询物资租赁类结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementItem", value = "物资租赁类结算明细entity", dataType = "ZxCtSuppliesLeaseSettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseSettlementItemList")
    public ResponseEntity getZxCtSuppliesLeaseSettlementItemList(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        return zxCtSuppliesLeaseSettlementItemService.getZxCtSuppliesLeaseSettlementItemListByCondition(zxCtSuppliesLeaseSettlementItem);
    }

    @ApiOperation(value="查询详情物资租赁类结算明细", notes="查询详情物资租赁类结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementItem", value = "物资租赁类结算明细entity", dataType = "ZxCtSuppliesLeaseSettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseSettlementItemDetail")
    public ResponseEntity getZxCtSuppliesLeaseSettlementItemDetail(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        return zxCtSuppliesLeaseSettlementItemService.getZxCtSuppliesLeaseSettlementItemDetail(zxCtSuppliesLeaseSettlementItem);
    }

    @ApiOperation(value="新增物资租赁类结算明细", notes="新增物资租赁类结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementItem", value = "物资租赁类结算明细entity", dataType = "ZxCtSuppliesLeaseSettlementItem")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeaseSettlementItem")
    public ResponseEntity addZxCtSuppliesLeaseSettlementItem(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        return zxCtSuppliesLeaseSettlementItemService.saveZxCtSuppliesLeaseSettlementItem(zxCtSuppliesLeaseSettlementItem);
    }

    @ApiOperation(value="更新物资租赁类结算明细", notes="更新物资租赁类结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementItem", value = "物资租赁类结算明细entity", dataType = "ZxCtSuppliesLeaseSettlementItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeaseSettlementItem")
    public ResponseEntity updateZxCtSuppliesLeaseSettlementItem(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        return zxCtSuppliesLeaseSettlementItemService.updateZxCtSuppliesLeaseSettlementItem(zxCtSuppliesLeaseSettlementItem);
    }

    @ApiOperation(value="删除物资租赁类结算明细", notes="删除物资租赁类结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementItemList", value = "物资租赁类结算明细List", dataType = "List<ZxCtSuppliesLeaseSettlementItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesLeaseSettlementItem")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseSettlementItem(@RequestBody(required = false) List<ZxCtSuppliesLeaseSettlementItem> zxCtSuppliesLeaseSettlementItemList) {
        return zxCtSuppliesLeaseSettlementItemService.batchDeleteUpdateZxCtSuppliesLeaseSettlementItem(zxCtSuppliesLeaseSettlementItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="根据合同ID查询统计项", notes="根据合同ID查询统计项")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementItem", value = "物资租赁类结算明细entity", dataType = "ZxCtSuppliesLeaseSettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseSettlementItemListByConID")
    public ResponseEntity getZxCtSuppliesLeaseSettlementItemListByConID(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        return zxCtSuppliesLeaseSettlementItemService.getZxCtSuppliesLeaseSettlementItemListByConID(zxCtSuppliesLeaseSettlementItem);
    } 

    @ApiOperation(value="更新物资租赁类结算明细", notes="更新物资租赁类结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseSettlementItem", value = "物资租赁类结算明细entity", dataType = "ZxCtSuppliesLeaseSettlementItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeaseSettlementItemByConID")
    public ResponseEntity updateZxCtSuppliesLeaseSettlementItemByConID(@RequestBody(required = false) ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem) {
        return zxCtSuppliesLeaseSettlementItemService.updateZxCtSuppliesLeaseSettlementItemByConID(zxCtSuppliesLeaseSettlementItem);
    }
}
