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
import com.apih5.mybatis.pojo.ZxSkStockTransItemInitialReceipt;
import com.apih5.service.ZxSkStockTransItemInitialReceiptService;

@RestController
public class ZxSkStockTransItemInitialReceiptController {

    @Autowired(required = true)
    private ZxSkStockTransItemInitialReceiptService zxSkStockTransItemInitialReceiptService;

    @ApiOperation(value="查询预收初始单明细", notes="查询预收初始单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemInitialReceipt", value = "预收初始单明细entity", dataType = "ZxSkStockTransItemInitialReceipt")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemInitialReceiptList")
    public ResponseEntity getZxSkStockTransItemInitialReceiptList(@RequestBody(required = false) ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt) {
        return zxSkStockTransItemInitialReceiptService.getZxSkStockTransItemInitialReceiptListByCondition(zxSkStockTransItemInitialReceipt);
    }

    @ApiOperation(value="查询详情预收初始单明细", notes="查询详情预收初始单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemInitialReceipt", value = "预收初始单明细entity", dataType = "ZxSkStockTransItemInitialReceipt")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemInitialReceiptDetails")
    public ResponseEntity getZxSkStockTransItemInitialReceiptDetails(@RequestBody(required = false) ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt) {
        return zxSkStockTransItemInitialReceiptService.getZxSkStockTransItemInitialReceiptDetails(zxSkStockTransItemInitialReceipt);
    }

    @ApiOperation(value="新增预收初始单明细", notes="新增预收初始单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemInitialReceipt", value = "预收初始单明细entity", dataType = "ZxSkStockTransItemInitialReceipt")
    @RequireToken
    @PostMapping("/addZxSkStockTransItemInitialReceipt")
    public ResponseEntity addZxSkStockTransItemInitialReceipt(@RequestBody(required = false) ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt) {
        return zxSkStockTransItemInitialReceiptService.saveZxSkStockTransItemInitialReceipt(zxSkStockTransItemInitialReceipt);
    }

    @ApiOperation(value="更新预收初始单明细", notes="更新预收初始单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemInitialReceipt", value = "预收初始单明细entity", dataType = "ZxSkStockTransItemInitialReceipt")
    @RequireToken
    @PostMapping("/updateZxSkStockTransItemInitialReceipt")
    public ResponseEntity updateZxSkStockTransItemInitialReceipt(@RequestBody(required = false) ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt) {
        return zxSkStockTransItemInitialReceiptService.updateZxSkStockTransItemInitialReceipt(zxSkStockTransItemInitialReceipt);
    }

    @ApiOperation(value="删除预收初始单明细", notes="删除预收初始单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemInitialReceiptList", value = "预收初始单明细List", dataType = "List<ZxSkStockTransItemInitialReceipt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransItemInitialReceipt")
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemInitialReceipt(@RequestBody(required = false) List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList) {
        return zxSkStockTransItemInitialReceiptService.batchDeleteUpdateZxSkStockTransItemInitialReceipt(zxSkStockTransItemInitialReceiptList);
    }

}
