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
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import com.apih5.service.ZxSkStockTransferInitialReceiptService;

@RestController
public class ZxSkStockTransferInitialReceiptController {

    @Autowired(required = true)
    private ZxSkStockTransferInitialReceiptService zxSkStockTransferInitialReceiptService;

    @ApiOperation(value="查询预收初始单", notes="查询预收初始单")
    @ApiImplicitParam(name = "zxSkStockTransferInitialReceipt", value = "预收初始单entity", dataType = "ZxSkStockTransferInitialReceipt")
    @RequireToken
    @PostMapping("/getZxSkStockTransferInitialReceiptList")
    public ResponseEntity getZxSkStockTransferInitialReceiptList(@RequestBody(required = false) ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        return zxSkStockTransferInitialReceiptService.getZxSkStockTransferInitialReceiptListByCondition(zxSkStockTransferInitialReceipt);
    }

    @ApiOperation(value="查询详情预收初始单", notes="查询详情预收初始单")
    @ApiImplicitParam(name = "zxSkStockTransferInitialReceipt", value = "预收初始单entity", dataType = "ZxSkStockTransferInitialReceipt")
    @RequireToken
    @PostMapping("/getZxSkStockTransferInitialReceiptDetails")
    public ResponseEntity getZxSkStockTransferInitialReceiptDetails(@RequestBody(required = false) ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        return zxSkStockTransferInitialReceiptService.getZxSkStockTransferInitialReceiptDetails(zxSkStockTransferInitialReceipt);
    }

    @ApiOperation(value="新增预收初始单", notes="新增预收初始单")
    @ApiImplicitParam(name = "zxSkStockTransferInitialReceipt", value = "预收初始单entity", dataType = "ZxSkStockTransferInitialReceipt")
    @RequireToken
    @PostMapping("/addZxSkStockTransferInitialReceipt")
    public ResponseEntity addZxSkStockTransferInitialReceipt(@RequestBody(required = false) ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        return zxSkStockTransferInitialReceiptService.saveZxSkStockTransferInitialReceipt(zxSkStockTransferInitialReceipt);
    }

    @ApiOperation(value="更新预收初始单", notes="更新预收初始单")
    @ApiImplicitParam(name = "zxSkStockTransferInitialReceipt", value = "预收初始单entity", dataType = "ZxSkStockTransferInitialReceipt")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferInitialReceipt")
    public ResponseEntity updateZxSkStockTransferInitialReceipt(@RequestBody(required = false) ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        return zxSkStockTransferInitialReceiptService.updateZxSkStockTransferInitialReceipt(zxSkStockTransferInitialReceipt);
    }

    @ApiOperation(value="删除预收初始单", notes="删除预收初始单")
    @ApiImplicitParam(name = "zxSkStockTransferInitialReceiptList", value = "预收初始单List", dataType = "List<ZxSkStockTransferInitialReceipt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferInitialReceipt")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferInitialReceipt(@RequestBody(required = false) List<ZxSkStockTransferInitialReceipt> zxSkStockTransferInitialReceiptList) {
        return zxSkStockTransferInitialReceiptService.batchDeleteUpdateZxSkStockTransferInitialReceipt(zxSkStockTransferInitialReceiptList);
    }

    @ApiOperation(value = "获取预收单据编号", notes = "获取预收单据编号")
    @ApiImplicitParam(name = "zxSkStockTransferInitialReceipt", value = "获取单据编号entity", dataType = "ZxSkStockTransferInitialReceipt")
    @RequireToken
    @PostMapping("/getZxSkStockTransferInitialReceiptNo")
    public ResponseEntity getZxSkStockTransferInitialReceiptNo(@RequestBody(required = false)ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        return zxSkStockTransferInitialReceiptService.getZxSkStockTransferInitialReceiptNo(zxSkStockTransferInitialReceipt);
    }

    //审核
    @ApiOperation(value="审核预收初始单", notes="审核预收初始单")
    @ApiImplicitParam(name = "zxSkStockTransferInitialReceipt", value = "审核预收初始单entity", dataType = "ZxSkStockTransferInitialReceipt")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferInitialReceipt")
    public ResponseEntity checkZxSkStockTransferInitialReceipt(@RequestBody(required = false) ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt) {
        return zxSkStockTransferInitialReceiptService.checkZxSkStockTransferInitialReceipt(zxSkStockTransferInitialReceipt);
    }






}
