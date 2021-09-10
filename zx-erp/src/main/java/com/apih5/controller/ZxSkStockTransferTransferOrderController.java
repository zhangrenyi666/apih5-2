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
import com.apih5.mybatis.pojo.ZxSkStockTransferTransferOrder;
import com.apih5.service.ZxSkStockTransferTransferOrderService;

@RestController
public class ZxSkStockTransferTransferOrderController {

    @Autowired(required = true)
    private ZxSkStockTransferTransferOrderService zxSkStockTransferTransferOrderService;

    @ApiOperation(value="查询调拨单", notes="查询调拨单")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/getZxSkStockTransferTransferOrderList")
    public ResponseEntity getZxSkStockTransferTransferOrderList(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.getZxSkStockTransferTransferOrderListByCondition(zxSkStockTransferTransferOrder);
    }

    @ApiOperation(value="查询详情调拨单", notes="查询详情调拨单")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/getZxSkStockTransferTransferOrderDetails")
    public ResponseEntity getZxSkStockTransferTransferOrderDetails(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.getZxSkStockTransferTransferOrderDetails(zxSkStockTransferTransferOrder);
    }

    @ApiOperation(value="新增调拨单", notes="新增调拨单")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/addZxSkStockTransferTransferOrder")
    public ResponseEntity addZxSkStockTransferTransferOrder(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.saveZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder);
    }

    @ApiOperation(value="更新调拨单", notes="更新调拨单")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferTransferOrder")
    public ResponseEntity updateZxSkStockTransferTransferOrder(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.updateZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder);
    }

    @ApiOperation(value="删除调拨单", notes="删除调拨单")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrderList", value = "调拨单List", dataType = "List<ZxSkStockTransferTransferOrder>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferTransferOrder")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferTransferOrder(@RequestBody(required = false) List<ZxSkStockTransferTransferOrder> zxSkStockTransferTransferOrderList) {
        return zxSkStockTransferTransferOrderService.batchDeleteUpdateZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrderList);
    }

    @ApiOperation(value="审核调拨单", notes="审核调拨单")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferTransferOrder")
    public ResponseEntity checkZxSkStockTransferTransferOrder(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.checkZxSkStockTransferTransferOrder(zxSkStockTransferTransferOrder);
    }

    @ApiOperation(value="调拨单收料", notes="调拨单收料")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferTransferOrderIn")
    public ResponseEntity checkZxSkStockTransferTransferOrderIn(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.checkZxSkStockTransferTransferOrderIn(zxSkStockTransferTransferOrder);
    }

    @ApiOperation(value="调拨单回退", notes="调拨单回退")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferTransferOrderOut")
    public ResponseEntity checkZxSkStockTransferTransferOrderOut(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.checkZxSkStockTransferTransferOrderOut(zxSkStockTransferTransferOrder);
    }

    //获取调拨单据编号
    @ApiOperation(value = "获取调拨单编号", notes = "获取调拨单编号")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/getZxSkStockTransferTransferOrderNo")
    public ResponseEntity getZxSkStockTransferTransferOrderNo(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.getZxSkStockTransferTransferOrderNo(zxSkStockTransferTransferOrder);
    }


    @ApiOperation(value = "获取调拨单接收单位", notes = "获取调拨单接收单位")
    @ApiImplicitParam(name = "zxSkStockTransferTransferOrder", value = "调拨单entity", dataType = "ZxSkStockTransferTransferOrder")
    @RequireToken
    @PostMapping("/getZxSkStockTransferOrderReceiveOrg")
    public ResponseEntity getZxSkStockTransferOrderReceiveOrg(@RequestBody(required = false) ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder) {
        return zxSkStockTransferTransferOrderService.getZxSkStockTransferOrderReceiveOrg(zxSkStockTransferTransferOrder);
    }






}
