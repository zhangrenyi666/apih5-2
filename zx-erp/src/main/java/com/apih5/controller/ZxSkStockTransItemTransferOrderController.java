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
import com.apih5.mybatis.pojo.ZxSkStockTransItemTransferOrder;
import com.apih5.service.ZxSkStockTransItemTransferOrderService;

@RestController
public class ZxSkStockTransItemTransferOrderController {

    @Autowired(required = true)
    private ZxSkStockTransItemTransferOrderService zxSkStockTransItemTransferOrderService;

    @ApiOperation(value="查询调拨单明细", notes="查询调拨单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemTransferOrder", value = "调拨单明细entity", dataType = "ZxSkStockTransItemTransferOrder")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemTransferOrderList")
    public ResponseEntity getZxSkStockTransItemTransferOrderList(@RequestBody(required = false) ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder) {
        return zxSkStockTransItemTransferOrderService.getZxSkStockTransItemTransferOrderListByCondition(zxSkStockTransItemTransferOrder);
    }

    @ApiOperation(value="查询详情调拨单明细", notes="查询详情调拨单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemTransferOrder", value = "调拨单明细entity", dataType = "ZxSkStockTransItemTransferOrder")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemTransferOrderDetails")
    public ResponseEntity getZxSkStockTransItemTransferOrderDetails(@RequestBody(required = false) ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder) {
        return zxSkStockTransItemTransferOrderService.getZxSkStockTransItemTransferOrderDetails(zxSkStockTransItemTransferOrder);
    }

    @ApiOperation(value="新增调拨单明细", notes="新增调拨单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemTransferOrder", value = "调拨单明细entity", dataType = "ZxSkStockTransItemTransferOrder")
    @RequireToken
    @PostMapping("/addZxSkStockTransItemTransferOrder")
    public ResponseEntity addZxSkStockTransItemTransferOrder(@RequestBody(required = false) ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder) {
        return zxSkStockTransItemTransferOrderService.saveZxSkStockTransItemTransferOrder(zxSkStockTransItemTransferOrder);
    }

    @ApiOperation(value="更新调拨单明细", notes="更新调拨单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemTransferOrder", value = "调拨单明细entity", dataType = "ZxSkStockTransItemTransferOrder")
    @RequireToken
    @PostMapping("/updateZxSkStockTransItemTransferOrder")
    public ResponseEntity updateZxSkStockTransItemTransferOrder(@RequestBody(required = false) ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder) {
        return zxSkStockTransItemTransferOrderService.updateZxSkStockTransItemTransferOrder(zxSkStockTransItemTransferOrder);
    }

    @ApiOperation(value="删除调拨单明细", notes="删除调拨单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemTransferOrderList", value = "调拨单明细List", dataType = "List<ZxSkStockTransItemTransferOrder>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransItemTransferOrder")
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemTransferOrder(@RequestBody(required = false) List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList) {
        return zxSkStockTransItemTransferOrderService.batchDeleteUpdateZxSkStockTransItemTransferOrder(zxSkStockTransItemTransferOrderList);
    }

}
