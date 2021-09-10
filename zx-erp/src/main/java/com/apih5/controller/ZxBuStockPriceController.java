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
import com.apih5.mybatis.pojo.ZxBuStockPrice;
import com.apih5.service.ZxBuStockPriceService;

@RestController
public class ZxBuStockPriceController {

    @Autowired(required = true)
    private ZxBuStockPriceService zxBuStockPriceService;

    @ApiOperation(value="查询预算材料", notes="查询预算材料")
    @ApiImplicitParam(name = "zxBuStockPrice", value = "预算材料entity", dataType = "ZxBuStockPrice")
    @RequireToken
    @PostMapping("/getZxBuStockPriceList")
    public ResponseEntity getZxBuStockPriceList(@RequestBody(required = false) ZxBuStockPrice zxBuStockPrice) {
        return zxBuStockPriceService.getZxBuStockPriceListByCondition(zxBuStockPrice);
    }

    @ApiOperation(value="查询详情预算材料", notes="查询详情预算材料")
    @ApiImplicitParam(name = "zxBuStockPrice", value = "预算材料entity", dataType = "ZxBuStockPrice")
    @RequireToken
    @PostMapping("/getZxBuStockPriceDetail")
    public ResponseEntity getZxBuStockPriceDetail(@RequestBody(required = false) ZxBuStockPrice zxBuStockPrice) {
        return zxBuStockPriceService.getZxBuStockPriceDetail(zxBuStockPrice);
    }

    @ApiOperation(value="新增预算材料", notes="新增预算材料")
    @ApiImplicitParam(name = "zxBuStockPrice", value = "预算材料entity", dataType = "ZxBuStockPrice")
    @RequireToken
    @PostMapping("/addZxBuStockPrice")
    public ResponseEntity addZxBuStockPrice(@RequestBody(required = false) ZxBuStockPrice zxBuStockPrice) {
        return zxBuStockPriceService.saveZxBuStockPrice(zxBuStockPrice);
    }

    @ApiOperation(value="更新预算材料", notes="更新预算材料")
    @ApiImplicitParam(name = "zxBuStockPrice", value = "预算材料entity", dataType = "ZxBuStockPrice")
    @RequireToken
    @PostMapping("/updateZxBuStockPrice")
    public ResponseEntity updateZxBuStockPrice(@RequestBody(required = false) ZxBuStockPrice zxBuStockPrice) {
        return zxBuStockPriceService.updateZxBuStockPrice(zxBuStockPrice);
    }

    @ApiOperation(value="删除预算材料", notes="删除预算材料")
    @ApiImplicitParam(name = "zxBuStockPriceList", value = "预算材料List", dataType = "List<ZxBuStockPrice>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuStockPrice")
    public ResponseEntity batchDeleteUpdateZxBuStockPrice(@RequestBody(required = false) List<ZxBuStockPrice> zxBuStockPriceList) {
        return zxBuStockPriceService.batchDeleteUpdateZxBuStockPrice(zxBuStockPriceList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="导入预算材料", notes="导入预算材料")
    @ApiImplicitParam(name = "zxBuStockPrice", value = "预算材料", dataType = "ZxCtWorks")
    @RequireToken
    @PostMapping("/importZxBuStockPrice")
    public ResponseEntity importZxBuStockPrice(@RequestBody(required = false) ZxBuStockPrice zxBuStockPrice) {
        return zxBuStockPriceService.importZxBuStockPrice(zxBuStockPrice);
    }





}
