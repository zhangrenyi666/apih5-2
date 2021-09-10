package com.apih5.controller;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.mybatis.pojo.ZxSkStockTransferReceiving;
import com.apih5.service.ZxCtSuppliesShopResSettleItemService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxCtSuppliesShopResSettleItemController {

    @Autowired(required = true)
    private ZxCtSuppliesShopResSettleItemService zxCtSuppliesShopResSettleItemService;
    
    @ApiOperation(value="查询物资细目结算明细", notes="查询物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesShopResSettleItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopResSettleItemList")
    public ResponseEntity getZxCtSuppliesShopResSettleItemList(@RequestBody(required = false) ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        return zxCtSuppliesShopResSettleItemService.getZxCtSuppliesShopResSettleItemListByCondition(zxCtSuppliesShopResSettleItem);
    }

    @ApiOperation(value="查询详情物资细目结算明细", notes="查询详情物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesShopResSettleItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopResSettleItemDetail")
    public ResponseEntity getZxCtSuppliesShopResSettleItemDetail(@RequestBody(required = false) ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        return zxCtSuppliesShopResSettleItemService.getZxCtSuppliesShopResSettleItemDetail(zxCtSuppliesShopResSettleItem);
    }

    @ApiOperation(value="新增物资细目结算明细", notes="新增物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesShopResSettleItem")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopResSettleItem")
    public ResponseEntity addZxCtSuppliesShopResSettleItem(@RequestBody(required = false) ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        return zxCtSuppliesShopResSettleItemService.saveZxCtSuppliesShopResSettleItem(zxCtSuppliesShopResSettleItem);
    }

    @ApiOperation(value="更新物资细目结算明细", notes="更新物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesShopResSettleItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopResSettleItem")
    public ResponseEntity updateZxCtSuppliesShopResSettleItem(@RequestBody(required = false) ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        return zxCtSuppliesShopResSettleItemService.updateZxCtSuppliesShopResSettleItem(zxCtSuppliesShopResSettleItem);
    }

    @ApiOperation(value="删除物资细目结算明细", notes="删除物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItemList", value = "物资细目结算明细List", dataType = "List<ZxCtSuppliesShopResSettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesShopResSettleItem")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopResSettleItem(@RequestBody(required = false) List<ZxCtSuppliesShopResSettleItem> zxCtSuppliesShopResSettleItemList) {
        return zxCtSuppliesShopResSettleItemService.batchDeleteUpdateZxCtSuppliesShopResSettleItem(zxCtSuppliesShopResSettleItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询物资细目结算明细", notes="查询物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesShopResSettleItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesShopResSettleItemListByContID")
    public ResponseEntity getZxCtSuppliesShopResSettleItemListByContID(@RequestBody(required = false) ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        return zxCtSuppliesShopResSettleItemService.getZxCtSuppliesShopResSettleItemListByContID(zxCtSuppliesShopResSettleItem);
    }
    
    @ApiOperation(value="根据物资ID查询物资收料单和退货单", notes="根据物资ID查询物资收料单和退货单")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesShopResSettleItem")
    @RequireToken
    @PostMapping("/getZxCtSupReceivingAndReturnListByResID")
    public ResponseEntity getZxCtSupReceivingAndReturnListByResID(@RequestBody(required = false) ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
    	return zxCtSuppliesShopResSettleItemService.getZxCtSupReceivingAndReturnListByResID(zxSkStockTransferReceiving);
    }
    
    @ApiOperation(value="根据收料单ID新增数据（显示数据）", notes="根据收料单ID新增数据（显示数据）")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItemList", value = "物资细目结算明细entity", dataType = "List<ZxSkStockTransferInitialReceipt>")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopResSettleItemByStockId")
    public ResponseEntity addZxCtSuppliesShopResSettleItemByStockId(@RequestBody(required = false) ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItemList) {
        return zxCtSuppliesShopResSettleItemService.addZxCtSuppliesShopResSettleItemByStockId(zxCtSuppliesShopResSettleItemList);
    }    
    
    @ApiOperation(value="根据收料单ID新增数据（计算结果）", notes="根据收料单ID新增数据（计算结果）")
    @ApiImplicitParam(name = "zxCtSuppliesShopSettlementList", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesShopSettlementList")
    @RequireToken
    @PostMapping("/addZxCtSuppliesShopResSettleItemList")
    public ResponseEntity addZxCtSuppliesShopResSettleItemList(@RequestBody(required = false) ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList) {
    	return zxCtSuppliesShopResSettleItemService.addZxCtSuppliesShopResSettleItemList(zxCtSuppliesShopSettlementList);
    }    

    @ApiOperation(value="更新物资细目结算明细", notes="更新物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesShopResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesShopResSettleItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesShopResSettleItemByContID")
    public ResponseEntity updateZxCtSuppliesShopResSettleItemByContID(@RequestBody(required = false) ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem) {
        return zxCtSuppliesShopResSettleItemService.updateZxCtSuppliesShopResSettleItemByContID(zxCtSuppliesShopResSettleItem);
    }    
}
