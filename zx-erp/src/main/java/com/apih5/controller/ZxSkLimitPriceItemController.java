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
import com.apih5.mybatis.pojo.ZxSkLimitPriceItem;
import com.apih5.service.ZxSkLimitPriceItemService;

@RestController
public class ZxSkLimitPriceItemController {

    @Autowired(required = true)
    private ZxSkLimitPriceItemService zxSkLimitPriceItemService;

    @ApiOperation(value="查询物资限价管理明细", notes="查询物资限价管理明细")
    @ApiImplicitParam(name = "zxSkLimitPriceItem", value = "物资限价管理明细entity", dataType = "ZxSkLimitPriceItem")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceItemList")
    public ResponseEntity getZxSkLimitPriceItemList(@RequestBody(required = false) ZxSkLimitPriceItem zxSkLimitPriceItem) {
        return zxSkLimitPriceItemService.getZxSkLimitPriceItemListByCondition(zxSkLimitPriceItem);
    }

    @ApiOperation(value="查询详情物资限价管理明细", notes="查询详情物资限价管理明细")
    @ApiImplicitParam(name = "zxSkLimitPriceItem", value = "物资限价管理明细entity", dataType = "ZxSkLimitPriceItem")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceItemDetails")
    public ResponseEntity getZxSkLimitPriceItemDetails(@RequestBody(required = false) ZxSkLimitPriceItem zxSkLimitPriceItem) {
        return zxSkLimitPriceItemService.getZxSkLimitPriceItemDetails(zxSkLimitPriceItem);
    }

    @ApiOperation(value="新增物资限价管理明细", notes="新增物资限价管理明细")
    @ApiImplicitParam(name = "zxSkLimitPriceItem", value = "物资限价管理明细entity", dataType = "ZxSkLimitPriceItem")
    @RequireToken
    @PostMapping("/addZxSkLimitPriceItem")
    public ResponseEntity addZxSkLimitPriceItem(@RequestBody(required = false) ZxSkLimitPriceItem zxSkLimitPriceItem) {
        return zxSkLimitPriceItemService.saveZxSkLimitPriceItem(zxSkLimitPriceItem);
    }

    @ApiOperation(value="更新物资限价管理明细", notes="更新物资限价管理明细")
    @ApiImplicitParam(name = "zxSkLimitPriceItem", value = "物资限价管理明细entity", dataType = "ZxSkLimitPriceItem")
    @RequireToken
    @PostMapping("/updateZxSkLimitPriceItem")
    public ResponseEntity updateZxSkLimitPriceItem(@RequestBody(required = false) ZxSkLimitPriceItem zxSkLimitPriceItem) {
        return zxSkLimitPriceItemService.updateZxSkLimitPriceItem(zxSkLimitPriceItem);
    }

    @ApiOperation(value="删除物资限价管理明细", notes="删除物资限价管理明细")
    @ApiImplicitParam(name = "zxSkLimitPriceItemList", value = "物资限价管理明细List", dataType = "List<ZxSkLimitPriceItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkLimitPriceItem")
    public ResponseEntity batchDeleteUpdateZxSkLimitPriceItem(@RequestBody(required = false) List<ZxSkLimitPriceItem> zxSkLimitPriceItemList) {
        return zxSkLimitPriceItemService.batchDeleteUpdateZxSkLimitPriceItem(zxSkLimitPriceItemList);
    }

}
