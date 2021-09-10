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
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlementItem;
import com.apih5.service.ZxCtSuppliesShopPaySettlementItemService;

@RestController
public class ZxCtSuppliesShopPaySettlementItemController {

    @Autowired(required = true)
    private ZxCtSuppliesShopPaySettlementItemService zxCtSuppliesShopPaySettlementItemService;

    @ApiOperation(value="查询物资支付项结算明细", notes="查询物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItem", value = "物资支付项结算明细entity", dataType = "ZxCtSuppliesShopPaySettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopPaySettlementItemList")
    public ResponseEntity getZxCtSuppliesShopPaySettlementItemList(@RequestBody(required = false) ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        return zxCtSuppliesShopPaySettlementItemService.getZxCtSuppliesShopPaySettlementItemListByCondition(zxCtSuppliesShopPaySettlementItem);
    }

    @ApiOperation(value="查询详情物资支付项结算明细", notes="查询详情物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItem", value = "物资支付项结算明细entity", dataType = "ZxCtSuppliesShopPaySettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopPaySettlementItemDetail")
    public ResponseEntity getZxCtSuppliesShopPaySettlementItemDetail(@RequestBody(required = false) ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        return zxCtSuppliesShopPaySettlementItemService.getZxCtSuppliesShopPaySettlementItemDetail(zxCtSuppliesShopPaySettlementItem);
    }

    @ApiOperation(value="新增物资支付项结算明细", notes="新增物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItem", value = "物资支付项结算明细entity", dataType = "ZxCtSuppliesShopPaySettlementItem")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopPaySettlementItem")
    public ResponseEntity addZxCtSuppliesShopPaySettlementItem(@RequestBody(required = false) ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        return zxCtSuppliesShopPaySettlementItemService.saveZxCtSuppliesShopPaySettlementItem(zxCtSuppliesShopPaySettlementItem);
    }

    @ApiOperation(value="更新物资支付项结算明细", notes="更新物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItem", value = "物资支付项结算明细entity", dataType = "ZxCtSuppliesShopPaySettlementItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopPaySettlementItem")
    public ResponseEntity updateZxCtSuppliesShopPaySettlementItem(@RequestBody(required = false) ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        return zxCtSuppliesShopPaySettlementItemService.updateZxCtSuppliesShopPaySettlementItem(zxCtSuppliesShopPaySettlementItem);
    }

    @ApiOperation(value="删除物资支付项结算明细", notes="删除物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItemList", value = "物资支付项结算明细List", dataType = "List<ZxCtSuppliesShopPaySettlementItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesShopPaySettlementItem")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlementItem(@RequestBody(required = false) List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList) {
        return zxCtSuppliesShopPaySettlementItemService.batchDeleteUpdateZxCtSuppliesShopPaySettlementItem(zxCtSuppliesShopPaySettlementItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询物资支付项结算明细", notes="查询物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItem", value = "物资支付项结算明细entity", dataType = "ZxCtSuppliesShopPaySettlementItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopPaySettlementItemListByContID")
    public ResponseEntity getZxCtSuppliesShopPaySettlementItemListByContID(@RequestBody(required = false) ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        return zxCtSuppliesShopPaySettlementItemService.getZxCtSuppliesShopPaySettlementItemListByContID(zxCtSuppliesShopPaySettlementItem);
    }
    
    @ApiOperation(value="新增物资支付项结算明细", notes="新增物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItem", value = "物资支付项结算明细entity", dataType = "ZxCtSuppliesShopPaySettlementItem")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopPaySettlementItemByPayId")
    public ResponseEntity addZxCtSuppliesShopPaySettlementItemByPayId(@RequestBody(required = false) ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        return zxCtSuppliesShopPaySettlementItemService.saveZxCtSuppliesShopPaySettlementItemByPayId(zxCtSuppliesShopPaySettlementItem);
    }

    @ApiOperation(value="更新物资支付项结算明细", notes="更新物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItem", value = "物资支付项结算明细entity", dataType = "ZxCtSuppliesShopPaySettlementItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopPaySettlementItemByPayId")
    public ResponseEntity updateZxCtSuppliesShopPaySettlementItemByPayId(@RequestBody(required = false) ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem) {
        return zxCtSuppliesShopPaySettlementItemService.updateZxCtSuppliesShopPaySettlementItemByPayId(zxCtSuppliesShopPaySettlementItem);
    }

    @ApiOperation(value="删除物资支付项结算明细", notes="删除物资支付项结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopPaySettlementItemList", value = "物资支付项结算明细List", dataType = "List<ZxCtSuppliesShopPaySettlementItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesShopPaySettlementItemByPayId")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlementItemByPayId(@RequestBody(required = false) List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList) {
        return zxCtSuppliesShopPaySettlementItemService.batchDeleteUpdateZxCtSuppliesShopPaySettlementItemByPayId(zxCtSuppliesShopPaySettlementItemList);
    }    
}
