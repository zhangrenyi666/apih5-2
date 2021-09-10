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
import com.apih5.mybatis.pojo.ZxBuWorksToStockPrice;
import com.apih5.service.ZxBuWorksToStockPriceService;

@RestController
public class ZxBuWorksToStockPriceController {

    @Autowired(required = true)
    private ZxBuWorksToStockPriceService zxBuWorksToStockPriceService;

    @ApiOperation(value="查询清单和预算材料中间表", notes="查询清单和预算材料中间表")
    @ApiImplicitParam(name = "zxBuWorksToStockPrice", value = "清单和预算材料中间表entity", dataType = "ZxBuWorksToStockPrice")
    @RequireToken
    @PostMapping("/getZxBuWorksToStockPriceList")
    public ResponseEntity getZxBuWorksToStockPriceList(@RequestBody(required = false) ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        return zxBuWorksToStockPriceService.getZxBuWorksToStockPriceListByCondition(zxBuWorksToStockPrice);
    }

    @ApiOperation(value="查询详情清单和预算材料中间表", notes="查询详情清单和预算材料中间表")
    @ApiImplicitParam(name = "zxBuWorksToStockPrice", value = "清单和预算材料中间表entity", dataType = "ZxBuWorksToStockPrice")
    @RequireToken
    @PostMapping("/getZxBuWorksToStockPriceDetail")
    public ResponseEntity getZxBuWorksToStockPriceDetail(@RequestBody(required = false) ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        return zxBuWorksToStockPriceService.getZxBuWorksToStockPriceDetail(zxBuWorksToStockPrice);
    }

    @ApiOperation(value="新增清单和预算材料中间表", notes="新增清单和预算材料中间表")
    @ApiImplicitParam(name = "zxBuWorksToStockPrice", value = "清单和预算材料中间表entity", dataType = "ZxBuWorksToStockPrice")
    @RequireToken
    @PostMapping("/addZxBuWorksToStockPrice")
    public ResponseEntity addZxBuWorksToStockPrice(@RequestBody(required = false) ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        return zxBuWorksToStockPriceService.saveZxBuWorksToStockPrice(zxBuWorksToStockPrice);
    }

    @ApiOperation(value="更新清单和预算材料中间表", notes="更新清单和预算材料中间表")
    @ApiImplicitParam(name = "zxBuWorksToStockPrice", value = "清单和预算材料中间表entity", dataType = "ZxBuWorksToStockPrice")
    @RequireToken
    @PostMapping("/updateZxBuWorksToStockPrice")
    public ResponseEntity updateZxBuWorksToStockPrice(@RequestBody(required = false) ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        return zxBuWorksToStockPriceService.updateZxBuWorksToStockPrice(zxBuWorksToStockPrice);
    }

    @ApiOperation(value="删除清单和预算材料中间表", notes="删除清单和预算材料中间表")
    @ApiImplicitParam(name = "zxBuWorksToStockPriceList", value = "清单和预算材料中间表List", dataType = "List<ZxBuWorksToStockPrice>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuWorksToStockPrice")
    public ResponseEntity batchDeleteUpdateZxBuWorksToStockPrice(@RequestBody(required = false) List<ZxBuWorksToStockPrice> zxBuWorksToStockPriceList) {
        return zxBuWorksToStockPriceService.batchDeleteUpdateZxBuWorksToStockPrice(zxBuWorksToStockPriceList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //关联  项目清单,挂接清单标准库
    @ApiOperation(value="清单材料关联", notes="清单材料关联")
    @ApiImplicitParam(name = "zxBuWorksToStockPrice", value = "清单和预算材料中间表entity", dataType = "ZxBuWorksToStockPrice")
    @RequireToken
    @PostMapping("/relevanceZxBuWorksToStockPrice")
    public ResponseEntity relevanceZxBuWorksToStockPrice(@RequestBody(required = false) ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        return zxBuWorksToStockPriceService.relevanceZxBuWorksToStockPrice(zxBuWorksToStockPrice);
    }

    //清除关联
    @ApiOperation(value="清除清单关联", notes="清除清单关联")
    @ApiImplicitParam(name = "zxBuWorksToStockPrice", value = "清单和预算材料中间表entity", dataType = "ZxBuWorksToStockPrice")
    @RequireToken
    @PostMapping("/removeRelevanceZxBuWorksToStockPrice")
    public ResponseEntity removeRelevanceZxBuWorksToStockPrice(@RequestBody(required = false) ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        return zxBuWorksToStockPriceService.removeRelevanceZxBuWorksToStockPrice(zxBuWorksToStockPrice);
    }


}
