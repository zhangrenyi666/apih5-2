package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZxBuWorksToStockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuStockPriceItem;
import com.apih5.service.ZxBuStockPriceItemService;

@RestController
public class ZxBuStockPriceItemController {

    @Autowired(required = true)
    private ZxBuStockPriceItemService zxBuStockPriceItemService;

    @ApiOperation(value="查询预算材料明细", notes="查询预算材料明细")
    @ApiImplicitParam(name = "zxBuStockPriceItem", value = "预算材料明细entity", dataType = "ZxBuStockPriceItem")
    @RequireToken
    @PostMapping("/getZxBuStockPriceItemList")
    public ResponseEntity getZxBuStockPriceItemList(@RequestBody(required = false) ZxBuStockPriceItem zxBuStockPriceItem) {
        return zxBuStockPriceItemService.getZxBuStockPriceItemListByCondition(zxBuStockPriceItem);
    }

    @ApiOperation(value="查询详情预算材料明细", notes="查询详情预算材料明细")
    @ApiImplicitParam(name = "zxBuStockPriceItem", value = "预算材料明细entity", dataType = "ZxBuStockPriceItem")
    @RequireToken
    @PostMapping("/getZxBuStockPriceItemDetail")
    public ResponseEntity getZxBuStockPriceItemDetail(@RequestBody(required = false) ZxBuStockPriceItem zxBuStockPriceItem) {
        return zxBuStockPriceItemService.getZxBuStockPriceItemDetail(zxBuStockPriceItem);
    }

    @ApiOperation(value="新增预算材料明细", notes="新增预算材料明细")
    @ApiImplicitParam(name = "zxBuStockPriceItem", value = "预算材料明细entity", dataType = "ZxBuStockPriceItem")
    @RequireToken
    @PostMapping("/addZxBuStockPriceItem")
    public ResponseEntity addZxBuStockPriceItem(@RequestBody(required = false) ZxBuStockPriceItem zxBuStockPriceItem) {
        return zxBuStockPriceItemService.saveZxBuStockPriceItem(zxBuStockPriceItem);
    }

    @ApiOperation(value="更新预算材料明细", notes="更新预算材料明细")
    @ApiImplicitParam(name = "zxBuStockPriceItem", value = "预算材料明细entity", dataType = "ZxBuStockPriceItem")
    @RequireToken
    @PostMapping("/updateZxBuStockPriceItem")
    public ResponseEntity updateZxBuStockPriceItem(@RequestBody(required = false) ZxBuStockPriceItem zxBuStockPriceItem) {
        return zxBuStockPriceItemService.updateZxBuStockPriceItem(zxBuStockPriceItem);
    }

    @ApiOperation(value="删除预算材料明细", notes="删除预算材料明细")
    @ApiImplicitParam(name = "zxBuStockPriceItemList", value = "预算材料明细List", dataType = "List<ZxBuStockPriceItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuStockPriceItem")
    public ResponseEntity batchDeleteUpdateZxBuStockPriceItem(@RequestBody(required = false) List<ZxBuStockPriceItem> zxBuStockPriceItemList) {
        return zxBuStockPriceItemService.batchDeleteUpdateZxBuStockPriceItem(zxBuStockPriceItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="获取基础预算材料明细", notes="获取基础预算材料明细")
    @ApiImplicitParam(name = "zxBuStockPriceItem", value = "预算材料明细entity", dataType = "ZxBuStockPriceItem")
    @RequireToken
    @PostMapping("/getBaseZxBuStockPriceItemList")
    public ResponseEntity getBaseZxBuStockPriceItemList(@RequestBody(required = false) ZxBuStockPriceItem zxBuStockPriceItem) {
        return zxBuStockPriceItemService.getBaseZxBuStockPriceItemList(zxBuStockPriceItem);
    }

    @ApiOperation(value="获取材料和复合材料汇总", notes="获取材料和复合材料汇总")
    @ApiImplicitParam(name = "zxBuStockPriceItem", value = "预算材料明细entity", dataType = "ZxBuStockPriceItem")
    @RequireToken
    @PostMapping("/getZxBuStockPriceItemResAll")
    public ResponseEntity getZxBuStockPriceItemResAll(@RequestBody(required = false) ZxBuStockPriceItem zxBuStockPriceItem) {
        return zxBuStockPriceItemService.getZxBuStockPriceItemResAll(zxBuStockPriceItem);
    }


}
