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
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettleItem;
import com.apih5.service.ZxCtSuppliesLeaseResSettleItemService;

@RestController
public class ZxCtSuppliesLeaseResSettleItemController {

    @Autowired(required = true)
    private ZxCtSuppliesLeaseResSettleItemService zxCtSuppliesLeaseResSettleItemService;

    @ApiOperation(value="查询物资细目结算明细", notes="查询物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesLeaseResSettleItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseResSettleItemList")
    public ResponseEntity getZxCtSuppliesLeaseResSettleItemList(@RequestBody(required = false) ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        return zxCtSuppliesLeaseResSettleItemService.getZxCtSuppliesLeaseResSettleItemListByCondition(zxCtSuppliesLeaseResSettleItem);
    }

    @ApiOperation(value="查询详情物资细目结算明细", notes="查询详情物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesLeaseResSettleItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseResSettleItemDetail")
    public ResponseEntity getZxCtSuppliesLeaseResSettleItemDetail(@RequestBody(required = false) ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        return zxCtSuppliesLeaseResSettleItemService.getZxCtSuppliesLeaseResSettleItemDetail(zxCtSuppliesLeaseResSettleItem);
    }

    @ApiOperation(value="新增物资细目结算明细", notes="新增物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesLeaseResSettleItem")
    @RequireToken
    @PostMapping("/addZxCtSuppliesLeaseResSettleItem")
    public ResponseEntity addZxCtSuppliesLeaseResSettleItem(@RequestBody(required = false) ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        return zxCtSuppliesLeaseResSettleItemService.saveZxCtSuppliesLeaseResSettleItem(zxCtSuppliesLeaseResSettleItem);
    }

    @ApiOperation(value="更新物资细目结算明细", notes="更新物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesLeaseResSettleItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeaseResSettleItem")
    public ResponseEntity updateZxCtSuppliesLeaseResSettleItem(@RequestBody(required = false) ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        return zxCtSuppliesLeaseResSettleItemService.updateZxCtSuppliesLeaseResSettleItem(zxCtSuppliesLeaseResSettleItem);
    }

    @ApiOperation(value="删除物资细目结算明细", notes="删除物资细目结算明细")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettleItemList", value = "物资细目结算明细List", dataType = "List<ZxCtSuppliesLeaseResSettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesLeaseResSettleItem")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseResSettleItem(@RequestBody(required = false) List<ZxCtSuppliesLeaseResSettleItem> zxCtSuppliesLeaseResSettleItemList) {
        return zxCtSuppliesLeaseResSettleItemService.batchDeleteUpdateZxCtSuppliesLeaseResSettleItem(zxCtSuppliesLeaseResSettleItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="根据合同ID查询清单", notes="根据合同ID查询清单")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesLeaseResSettleItem")
    @RequireToken
    @PostMapping("/getZxCtSuppliesLeaseResSettleItemListByConID")
    public ResponseEntity getZxCtSuppliesLeaseResSettleItemListByConID(@RequestBody(required = false) ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        return zxCtSuppliesLeaseResSettleItemService.getZxCtSuppliesLeaseResSettleItemListByConID(zxCtSuppliesLeaseResSettleItem);
    } 

    @ApiOperation(value="更新物资细目结算明细加算法", notes="更新物资细目结算明细加算法")
    @ApiImplicitParam(name = "zxCtSuppliesLeaseResSettleItem", value = "物资细目结算明细entity", dataType = "ZxCtSuppliesLeaseResSettleItem")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesLeaseResSettleItemByConID")
    public ResponseEntity updateZxCtSuppliesLeaseResSettleItemByConID(@RequestBody(required = false) ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem) {
        return zxCtSuppliesLeaseResSettleItemService.updateZxCtSuppliesLeaseResSettleItemByConID(zxCtSuppliesLeaseResSettleItem);
    }
    
}
