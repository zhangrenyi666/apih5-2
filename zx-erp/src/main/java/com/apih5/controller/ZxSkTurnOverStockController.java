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
import com.apih5.mybatis.pojo.ZxSkTurnOverStock;
import com.apih5.service.ZxSkTurnOverStockService;

@RestController
public class ZxSkTurnOverStockController {

    @Autowired(required = true)
    private ZxSkTurnOverStockService zxSkTurnOverStockService;

    @ApiOperation(value="查询周材库存", notes="查询周材库存")
    @ApiImplicitParam(name = "zxSkTurnOverStock", value = "周材库存entity", dataType = "ZxSkTurnOverStock")
    @RequireToken
    @PostMapping("/getZxSkTurnOverStockList")
    public ResponseEntity getZxSkTurnOverStockList(@RequestBody(required = false) ZxSkTurnOverStock zxSkTurnOverStock) {
        return zxSkTurnOverStockService.getZxSkTurnOverStockListByCondition(zxSkTurnOverStock);
    }

    @ApiOperation(value="查询详情周材库存", notes="查询详情周材库存")
    @ApiImplicitParam(name = "zxSkTurnOverStock", value = "周材库存entity", dataType = "ZxSkTurnOverStock")
    @RequireToken
    @PostMapping("/getZxSkTurnOverStockDetail")
    public ResponseEntity getZxSkTurnOverStockDetail(@RequestBody(required = false) ZxSkTurnOverStock zxSkTurnOverStock) {
        return zxSkTurnOverStockService.getZxSkTurnOverStockDetail(zxSkTurnOverStock);
    }

    @ApiOperation(value="新增周材库存", notes="新增周材库存")
    @ApiImplicitParam(name = "zxSkTurnOverStock", value = "周材库存entity", dataType = "ZxSkTurnOverStock")
    @RequireToken
    @PostMapping("/addZxSkTurnOverStock")
    public ResponseEntity addZxSkTurnOverStock(@RequestBody(required = false) ZxSkTurnOverStock zxSkTurnOverStock) {
        return zxSkTurnOverStockService.saveZxSkTurnOverStock(zxSkTurnOverStock);
    }

    @ApiOperation(value="更新周材库存", notes="更新周材库存")
    @ApiImplicitParam(name = "zxSkTurnOverStock", value = "周材库存entity", dataType = "ZxSkTurnOverStock")
    @RequireToken
    @PostMapping("/updateZxSkTurnOverStock")
    public ResponseEntity updateZxSkTurnOverStock(@RequestBody(required = false) ZxSkTurnOverStock zxSkTurnOverStock) {
        return zxSkTurnOverStockService.updateZxSkTurnOverStock(zxSkTurnOverStock);
    }

    @ApiOperation(value="删除周材库存", notes="删除周材库存")
    @ApiImplicitParam(name = "zxSkTurnOverStockList", value = "周材库存List", dataType = "List<ZxSkTurnOverStock>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnOverStock")
    public ResponseEntity batchDeleteUpdateZxSkTurnOverStock(@RequestBody(required = false) List<ZxSkTurnOverStock> zxSkTurnOverStockList) {
        return zxSkTurnOverStockService.batchDeleteUpdateZxSkTurnOverStock(zxSkTurnOverStockList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
