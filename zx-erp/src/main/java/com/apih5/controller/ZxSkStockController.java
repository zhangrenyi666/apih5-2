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
import com.apih5.mybatis.pojo.ZxSkStock;
import com.apih5.service.ZxSkStockService;

@RestController
public class ZxSkStockController {

    @Autowired(required = true)
    private ZxSkStockService zxSkStockService;

    @ApiOperation(value="查询库存", notes="查询库存")
    @ApiImplicitParam(name = "zxSkStock", value = "库存entity", dataType = "ZxSkStock")
    @RequireToken
    @PostMapping("/getZxSkStockList")
    public ResponseEntity getZxSkStockList(@RequestBody(required = false) ZxSkStock zxSkStock) {
        return zxSkStockService.getZxSkStockListByCondition(zxSkStock);
    }

    @ApiOperation(value="查询详情库存", notes="查询详情库存")
    @ApiImplicitParam(name = "zxSkStock", value = "库存entity", dataType = "ZxSkStock")
    @RequireToken
    @PostMapping("/getZxSkStockDetails")
    public ResponseEntity getZxSkStockDetails(@RequestBody(required = false) ZxSkStock zxSkStock) {
        return zxSkStockService.getZxSkStockDetails(zxSkStock);
    }

    @ApiOperation(value="新增库存", notes="新增库存")
    @ApiImplicitParam(name = "zxSkStock", value = "库存entity", dataType = "ZxSkStock")
    @RequireToken
    @PostMapping("/addZxSkStock")
    public ResponseEntity addZxSkStock(@RequestBody(required = false) ZxSkStock zxSkStock) {
        return zxSkStockService.saveZxSkStock(zxSkStock);
    }

    @ApiOperation(value="更新库存", notes="更新库存")
    @ApiImplicitParam(name = "zxSkStock", value = "库存entity", dataType = "ZxSkStock")
    @RequireToken
    @PostMapping("/updateZxSkStock")
    public ResponseEntity updateZxSkStock(@RequestBody(required = false) ZxSkStock zxSkStock) {
        return zxSkStockService.updateZxSkStock(zxSkStock);
    }

    @ApiOperation(value="删除库存", notes="删除库存")
    @ApiImplicitParam(name = "zxSkStockList", value = "库存List", dataType = "List<ZxSkStock>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStock")
    public ResponseEntity batchDeleteUpdateZxSkStock(@RequestBody(required = false) List<ZxSkStock> zxSkStockList) {
        return zxSkStockService.batchDeleteUpdateZxSkStock(zxSkStockList);
    }


    //根据公司ID 仓库ID 项目ID获取数据
    @ApiOperation(value="获取库存", notes="获取库存")
    @ApiImplicitParam(name = "zxSkStock", value = "库存entity", dataType = "ZxSkStock")
    @RequireToken
    @PostMapping("/getZxSkStockDataList")
    public ResponseEntity getZxSkStockDataList(@RequestBody(required = false) ZxSkStock zxSkStock) {
        return zxSkStockService.getZxSkStockDataList(zxSkStock);
    }


    @ApiOperation(value="获取库存分类", notes="获取库存分类")
    @ApiImplicitParam(name = "zxSkStock", value = "库存entity", dataType = "ZxSkStock")
    @RequireToken
    @PostMapping("/getZxSkStockResCategoryDataList")
    public ResponseEntity getZxSkStockResCategoryDataList(@RequestBody(required = false) ZxSkStock zxSkStock) {
        return zxSkStockService.getZxSkStockResCategoryDataList(zxSkStock);
    }





}
