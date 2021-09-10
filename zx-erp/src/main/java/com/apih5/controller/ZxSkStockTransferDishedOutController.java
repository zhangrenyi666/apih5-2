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
import com.apih5.mybatis.pojo.ZxSkStockTransferDishedOut;
import com.apih5.service.ZxSkStockTransferDishedOutService;

@RestController
public class ZxSkStockTransferDishedOutController {

    @Autowired(required = true)
    private ZxSkStockTransferDishedOutService zxSkStockTransferDishedOutService;

    @ApiOperation(value="查询盘亏出库单", notes="查询盘亏出库单")
    @ApiImplicitParam(name = "zxSkStockTransferDishedOut", value = "盘亏出库单entity", dataType = "ZxSkStockTransferDishedOut")
    @RequireToken
    @PostMapping("/getZxSkStockTransferDishedOutList")
    public ResponseEntity getZxSkStockTransferDishedOutList(@RequestBody(required = false) ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        return zxSkStockTransferDishedOutService.getZxSkStockTransferDishedOutListByCondition(zxSkStockTransferDishedOut);
    }

    @ApiOperation(value="查询详情盘亏出库单", notes="查询详情盘亏出库单")
    @ApiImplicitParam(name = "zxSkStockTransferDishedOut", value = "盘亏出库单entity", dataType = "ZxSkStockTransferDishedOut")
    @RequireToken
    @PostMapping("/getZxSkStockTransferDishedOutDetails")
    public ResponseEntity getZxSkStockTransferDishedOutDetails(@RequestBody(required = false) ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        return zxSkStockTransferDishedOutService.getZxSkStockTransferDishedOutDetails(zxSkStockTransferDishedOut);
    }

    @ApiOperation(value="新增盘亏出库单", notes="新增盘亏出库单")
    @ApiImplicitParam(name = "zxSkStockTransferDishedOut", value = "盘亏出库单entity", dataType = "ZxSkStockTransferDishedOut")
    @RequireToken
    @PostMapping("/addZxSkStockTransferDishedOut")
    public ResponseEntity addZxSkStockTransferDishedOut(@RequestBody(required = false) ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        return zxSkStockTransferDishedOutService.saveZxSkStockTransferDishedOut(zxSkStockTransferDishedOut);
    }

    @ApiOperation(value="更新盘亏出库单", notes="更新盘亏出库单")
    @ApiImplicitParam(name = "zxSkStockTransferDishedOut", value = "盘亏出库单entity", dataType = "ZxSkStockTransferDishedOut")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferDishedOut")
    public ResponseEntity updateZxSkStockTransferDishedOut(@RequestBody(required = false) ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        return zxSkStockTransferDishedOutService.updateZxSkStockTransferDishedOut(zxSkStockTransferDishedOut);
    }

    @ApiOperation(value="删除盘亏出库单", notes="删除盘亏出库单")
    @ApiImplicitParam(name = "zxSkStockTransferDishedOutList", value = "盘亏出库单List", dataType = "List<ZxSkStockTransferDishedOut>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferDishedOut")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferDishedOut(@RequestBody(required = false) List<ZxSkStockTransferDishedOut> zxSkStockTransferDishedOutList) {
        return zxSkStockTransferDishedOutService.batchDeleteUpdateZxSkStockTransferDishedOut(zxSkStockTransferDishedOutList);
    }

    @ApiOperation(value="审核盘亏出库单", notes="审核盘亏出库单")
    @ApiImplicitParam(name = "zxSkStockTransferDishedOut", value = "盘亏出库单entity", dataType = "ZxSkStockTransferDishedOut")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferDishedOut")
    public ResponseEntity checkZxSkStockTransferDishedOut(@RequestBody(required = false) ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        return zxSkStockTransferDishedOutService.checkZxSkStockTransferDishedOut(zxSkStockTransferDishedOut);
    }

    //获取调拨单据编号
    @ApiOperation(value = "获取盘亏出库单编号", notes = "获取盘亏出库单编号")
    @ApiImplicitParam(name = "zxSkStockTransferDishedOut", value = "盘亏出库单entity", dataType = "ZxSkStockTransferDishedOut")
    @RequireToken
    @PostMapping("/getZxSkStockTransferDishedOutNo")
    public ResponseEntity getZxSkStockTransferDishedOutNo(@RequestBody(required = false) ZxSkStockTransferDishedOut zxSkStockTransferDishedOut) {
        return zxSkStockTransferDishedOutService.getZxSkStockTransferDishedOutNo(zxSkStockTransferDishedOut);
    }

}
