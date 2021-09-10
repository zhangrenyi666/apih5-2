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
import com.apih5.mybatis.pojo.ZxSkStockFeeItem;
import com.apih5.service.ZxSkStockFeeItemService;

@RestController
public class ZxSkStockFeeItemController {

    @Autowired(required = true)
    private ZxSkStockFeeItemService zxSkStockFeeItemService;

    @ApiOperation(value="查询物资其它费运输费明细", notes="查询物资其它费运输费明细")
    @ApiImplicitParam(name = "zxSkStockFeeItem", value = "物资其它费运输费明细entity", dataType = "ZxSkStockFeeItem")
    @RequireToken
    @PostMapping("/getZxSkStockFeeItemList")
    public ResponseEntity getZxSkStockFeeItemList(@RequestBody(required = false) ZxSkStockFeeItem zxSkStockFeeItem) {
        return zxSkStockFeeItemService.getZxSkStockFeeItemListByCondition(zxSkStockFeeItem);
    }

    @ApiOperation(value="查询详情物资其它费运输费明细", notes="查询详情物资其它费运输费明细")
    @ApiImplicitParam(name = "zxSkStockFeeItem", value = "物资其它费运输费明细entity", dataType = "ZxSkStockFeeItem")
    @RequireToken
    @PostMapping("/getZxSkStockFeeItemDetail")
    public ResponseEntity getZxSkStockFeeItemDetail(@RequestBody(required = false) ZxSkStockFeeItem zxSkStockFeeItem) {
        return zxSkStockFeeItemService.getZxSkStockFeeItemDetail(zxSkStockFeeItem);
    }

    @ApiOperation(value="新增物资其它费运输费明细", notes="新增物资其它费运输费明细")
    @ApiImplicitParam(name = "zxSkStockFeeItem", value = "物资其它费运输费明细entity", dataType = "ZxSkStockFeeItem")
    @RequireToken
    @PostMapping("/addZxSkStockFeeItem")
    public ResponseEntity addZxSkStockFeeItem(@RequestBody(required = false) ZxSkStockFeeItem zxSkStockFeeItem) {
        return zxSkStockFeeItemService.saveZxSkStockFeeItem(zxSkStockFeeItem);
    }

    @ApiOperation(value="更新物资其它费运输费明细", notes="更新物资其它费运输费明细")
    @ApiImplicitParam(name = "zxSkStockFeeItem", value = "物资其它费运输费明细entity", dataType = "ZxSkStockFeeItem")
    @RequireToken
    @PostMapping("/updateZxSkStockFeeItem")
    public ResponseEntity updateZxSkStockFeeItem(@RequestBody(required = false) ZxSkStockFeeItem zxSkStockFeeItem) {
        return zxSkStockFeeItemService.updateZxSkStockFeeItem(zxSkStockFeeItem);
    }

    @ApiOperation(value="删除物资其它费运输费明细", notes="删除物资其它费运输费明细")
    @ApiImplicitParam(name = "zxSkStockFeeItemList", value = "物资其它费运输费明细List", dataType = "List<ZxSkStockFeeItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockFeeItem")
    public ResponseEntity batchDeleteUpdateZxSkStockFeeItem(@RequestBody(required = false) List<ZxSkStockFeeItem> zxSkStockFeeItemList) {
        return zxSkStockFeeItemService.batchDeleteUpdateZxSkStockFeeItem(zxSkStockFeeItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
