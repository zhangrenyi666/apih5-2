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
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlementItem;
import com.apih5.service.ZxCtSuppliesLeasePaySettlementItemService;

@RestController
public class ZxCtSuppliesLeasePaySettlementItemController {

    @Autowired(required = true)
    private ZxCtSuppliesLeasePaySettlementItemService zxCtSuppliesLeasePaySettlementItemService;

    @ApiOperation(value="查询物资租赁支付项结算明细", notes="查询物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItem", value = "物资租赁支付项结算明细entity", dataType = "ZxCtSuppliesLeasePaySettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeasePaySettlementItemList")
    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemList(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        return zxCtSuppliesLeasePaySettlementItemService.getZxCtSuppliesLeasePaySettlementItemListByCondition(zxCtSuppliesLeasePaySettlementItem);
    }

    @ApiOperation(value="查询详情物资租赁支付项结算明细", notes="查询详情物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItem", value = "物资租赁支付项结算明细entity", dataType = "ZxCtSuppliesLeasePaySettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeasePaySettlementItemDetail")
    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemDetail(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        return zxCtSuppliesLeasePaySettlementItemService.getZxCtSuppliesLeasePaySettlementItemDetail(zxCtSuppliesLeasePaySettlementItem);
    }

    @ApiOperation(value="新增物资租赁支付项结算明细", notes="新增物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItem", value = "物资租赁支付项结算明细entity", dataType = "ZxCtSuppliesLeasePaySettlementItem")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeasePaySettlementItem")
    public ResponseEntity addZxCtSuppliesLeasePaySettlementItem(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        return zxCtSuppliesLeasePaySettlementItemService.saveZxCtSuppliesLeasePaySettlementItem(zxCtSuppliesLeasePaySettlementItem);
    }

    @ApiOperation(value="更新物资租赁支付项结算明细", notes="更新物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItem", value = "物资租赁支付项结算明细entity", dataType = "ZxCtSuppliesLeasePaySettlementItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeasePaySettlementItem")
    public ResponseEntity updateZxCtSuppliesLeasePaySettlementItem(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        return zxCtSuppliesLeasePaySettlementItemService.updateZxCtSuppliesLeasePaySettlementItem(zxCtSuppliesLeasePaySettlementItem);
    }

    @ApiOperation(value="删除物资租赁支付项结算明细", notes="删除物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItemList", value = "物资租赁支付项结算明细List", dataType = "List<ZxCtSuppliesLeasePaySettlementItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem(@RequestBody(required = false) List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList) {
        return zxCtSuppliesLeasePaySettlementItemService.batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem(zxCtSuppliesLeasePaySettlementItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="新增物资租赁支付项结算明细", notes="新增物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItem", value = "物资租赁支付项结算明细entity", dataType = "ZxCtSuppliesLeasePaySettlementItem")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeasePaySettlementItemByPayId")
    public ResponseEntity addZxCtSuppliesLeasePaySettlementItemByPayId(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        return zxCtSuppliesLeasePaySettlementItemService.saveZxCtSuppliesLeasePaySettlementItemByPayId(zxCtSuppliesLeasePaySettlementItem);
    }

    @ApiOperation(value="更新物资租赁支付项结算明细", notes="更新物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItem", value = "物资租赁支付项结算明细entity", dataType = "ZxCtSuppliesLeasePaySettlementItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeasePaySettlementItemByPayId")
    public ResponseEntity updateZxCtSuppliesLeasePaySettlementItemByPayId(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        return zxCtSuppliesLeasePaySettlementItemService.updateZxCtSuppliesLeasePaySettlementItemByPayId(zxCtSuppliesLeasePaySettlementItem);
    }

    @ApiOperation(value="删除物资租赁支付项结算明细", notes="删除物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItemList", value = "物资租赁支付项结算明细List", dataType = "List<ZxCtSuppliesLeasePaySettlementItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesLeasePaySettlementItemByPayId")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlementItemByPayId(@RequestBody(required = false) List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList) {
        return zxCtSuppliesLeasePaySettlementItemService.batchDeleteUpdateZxCtSuppliesLeasePaySettlementItemByPayId(zxCtSuppliesLeasePaySettlementItemList);
    }

    @ApiOperation(value="查询物资租赁支付项结算明细", notes="查询物资租赁支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeasePaySettlementItem", value = "物资租赁支付项结算明细entity", dataType = "ZxCtSuppliesLeasePaySettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeasePaySettlementItemListByContID")
    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemListByContID(@RequestBody(required = false) ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem) {
        return zxCtSuppliesLeasePaySettlementItemService.getZxCtSuppliesLeasePaySettlementItemListByContID(zxCtSuppliesLeasePaySettlementItem);
    }
}
