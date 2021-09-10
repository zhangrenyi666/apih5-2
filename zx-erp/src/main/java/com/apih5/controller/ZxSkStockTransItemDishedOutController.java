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
import com.apih5.mybatis.pojo.ZxSkStockTransItemDishedOut;
import com.apih5.service.ZxSkStockTransItemDishedOutService;

@RestController
public class ZxSkStockTransItemDishedOutController {

    @Autowired(required = true)
    private ZxSkStockTransItemDishedOutService zxSkStockTransItemDishedOutService;

    @ApiOperation(value="查询盘亏出库单明细", notes="查询盘亏出库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDishedOut", value = "盘亏出库单明细entity", dataType = "ZxSkStockTransItemDishedOut")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemDishedOutList")
    public ResponseEntity getZxSkStockTransItemDishedOutList(@RequestBody(required = false) ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut) {
        return zxSkStockTransItemDishedOutService.getZxSkStockTransItemDishedOutListByCondition(zxSkStockTransItemDishedOut);
    }

    @ApiOperation(value="查询详情盘亏出库单明细", notes="查询详情盘亏出库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDishedOut", value = "盘亏出库单明细entity", dataType = "ZxSkStockTransItemDishedOut")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemDishedOutDetails")
    public ResponseEntity getZxSkStockTransItemDishedOutDetails(@RequestBody(required = false) ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut) {
        return zxSkStockTransItemDishedOutService.getZxSkStockTransItemDishedOutDetails(zxSkStockTransItemDishedOut);
    }

    @ApiOperation(value="新增盘亏出库单明细", notes="新增盘亏出库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDishedOut", value = "盘亏出库单明细entity", dataType = "ZxSkStockTransItemDishedOut")
    @RequireToken
    @PostMapping("/addZxSkStockTransItemDishedOut")
    public ResponseEntity addZxSkStockTransItemDishedOut(@RequestBody(required = false) ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut) {
        return zxSkStockTransItemDishedOutService.saveZxSkStockTransItemDishedOut(zxSkStockTransItemDishedOut);
    }

    @ApiOperation(value="更新盘亏出库单明细", notes="更新盘亏出库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDishedOut", value = "盘亏出库单明细entity", dataType = "ZxSkStockTransItemDishedOut")
    @RequireToken
    @PostMapping("/updateZxSkStockTransItemDishedOut")
    public ResponseEntity updateZxSkStockTransItemDishedOut(@RequestBody(required = false) ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut) {
        return zxSkStockTransItemDishedOutService.updateZxSkStockTransItemDishedOut(zxSkStockTransItemDishedOut);
    }

    @ApiOperation(value="删除盘亏出库单明细", notes="删除盘亏出库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDishedOutList", value = "盘亏出库单明细List", dataType = "List<ZxSkStockTransItemDishedOut>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransItemDishedOut")
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemDishedOut(@RequestBody(required = false) List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList) {
        return zxSkStockTransItemDishedOutService.batchDeleteUpdateZxSkStockTransItemDishedOut(zxSkStockTransItemDishedOutList);
    }



}
