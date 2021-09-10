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
import com.apih5.mybatis.pojo.ZxSkStockDifMonth;
import com.apih5.service.ZxSkStockDifMonthService;

@RestController
public class ZxSkStockDifMonthController {

    @Autowired(required = true)
    private ZxSkStockDifMonthService zxSkStockDifMonthService;

    @ApiOperation(value="查询物资价差量差核算(月)", notes="查询物资价差量差核算(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonth", value = "物资价差量差核算(月)entity", dataType = "ZxSkStockDifMonth")
    @RequireToken
    @PostMapping("/getZxSkStockDifMonthList")
    public ResponseEntity getZxSkStockDifMonthList(@RequestBody(required = false) ZxSkStockDifMonth zxSkStockDifMonth) {
        return zxSkStockDifMonthService.getZxSkStockDifMonthListByCondition(zxSkStockDifMonth);
    }

    @ApiOperation(value="查询详情物资价差量差核算(月)", notes="查询详情物资价差量差核算(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonth", value = "物资价差量差核算(月)entity", dataType = "ZxSkStockDifMonth")
    @RequireToken
    @PostMapping("/getZxSkStockDifMonthDetail")
    public ResponseEntity getZxSkStockDifMonthDetail(@RequestBody(required = false) ZxSkStockDifMonth zxSkStockDifMonth) {
        return zxSkStockDifMonthService.getZxSkStockDifMonthDetail(zxSkStockDifMonth);
    }

    @ApiOperation(value="新增物资价差量差核算(月)", notes="新增物资价差量差核算(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonth", value = "物资价差量差核算(月)entity", dataType = "ZxSkStockDifMonth")
    @RequireToken
    @PostMapping("/addZxSkStockDifMonth")
    public ResponseEntity addZxSkStockDifMonth(@RequestBody(required = false) ZxSkStockDifMonth zxSkStockDifMonth) {
        return zxSkStockDifMonthService.saveZxSkStockDifMonth(zxSkStockDifMonth);
    }

    @ApiOperation(value="更新物资价差量差核算(月)", notes="更新物资价差量差核算(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonth", value = "物资价差量差核算(月)entity", dataType = "ZxSkStockDifMonth")
    @RequireToken
    @PostMapping("/updateZxSkStockDifMonth")
    public ResponseEntity updateZxSkStockDifMonth(@RequestBody(required = false) ZxSkStockDifMonth zxSkStockDifMonth) {
        return zxSkStockDifMonthService.updateZxSkStockDifMonth(zxSkStockDifMonth);
    }

    @ApiOperation(value="删除物资价差量差核算(月)", notes="删除物资价差量差核算(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonthList", value = "物资价差量差核算(月)List", dataType = "List<ZxSkStockDifMonth>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockDifMonth")
    public ResponseEntity batchDeleteUpdateZxSkStockDifMonth(@RequestBody(required = false) List<ZxSkStockDifMonth> zxSkStockDifMonthList) {
        return zxSkStockDifMonthService.batchDeleteUpdateZxSkStockDifMonth(zxSkStockDifMonthList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓



    @ApiOperation(value="审核物资价差量差核算(月)", notes="审核物资价差量差核算(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonth", value = "物资价差量差核算(月)entity", dataType = "zxSkStockDifMonth")
    @RequireToken
    @PostMapping("/checkZxSkStockDifMonth")
    public ResponseEntity checkZxSkStockDifMonth(@RequestBody(required = false) ZxSkStockDifMonth zxSkStockDifMonth) {
        return zxSkStockDifMonthService.checkZxSkStockDifMonth(zxSkStockDifMonth);
    }

    @ApiOperation(value="反审核物资价差量差核算(月)", notes="反审核物资价差量差核算(月)")
    @ApiImplicitParam(name = "zxSkStockDifMonth", value = "物资价差量差核算(月)entity", dataType = "zxSkStockDifMonth")
    @RequireToken
    @PostMapping("/counterCheckZxSkStockDifMonth")
    public ResponseEntity counterCheckZxSkStockDifMonth(@RequestBody(required = false) ZxSkStockDifMonth zxSkStockDifMonth) {
        return zxSkStockDifMonthService.counterCheckZxSkStockDifMonth(zxSkStockDifMonth);
    }



}
