package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferReceiving;
import com.apih5.service.ZxSkStockTransferReceivingService;

@RestController
public class ZxSkStockTransferReceivingController {

    @Autowired(required = true)
    private ZxSkStockTransferReceivingService zxSkStockTransferReceivingService;

    @ApiOperation(value="查询收料单", notes="查询收料单")
    @ApiImplicitParam(name = "zxSkStockTransferReceiving", value = "收料单entity", dataType = "ZxSkStockTransferReceiving")
    @RequireToken
    @PostMapping("/getZxSkStockTransferReceivingList")
    public ResponseEntity getZxSkStockTransferReceivingList(@RequestBody(required = false) ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        return zxSkStockTransferReceivingService.getZxSkStockTransferReceivingListByCondition(zxSkStockTransferReceiving);
    }

    @ApiOperation(value="查询详情收料单", notes="查询详情收料单")
    @ApiImplicitParam(name = "zxSkStockTransferReceiving", value = "收料单entity", dataType = "ZxSkStockTransferReceiving")
    @RequireToken
    @PostMapping("/getZxSkStockTransferReceivingDetails")
    public ResponseEntity getZxSkStockTransferReceivingDetails(@RequestBody(required = false) ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        return zxSkStockTransferReceivingService.getZxSkStockTransferReceivingDetails(zxSkStockTransferReceiving);
    }

    @ApiOperation(value="新增收料单", notes="新增收料单")
    @ApiImplicitParam(name = "zxSkStockTransferReceiving", value = "收料单entity", dataType = "ZxSkStockTransferReceiving")
    @RequireToken
    @PostMapping("/addZxSkStockTransferReceiving")
    public ResponseEntity addZxSkStockTransferReceiving(@RequestBody(required = false) ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        return zxSkStockTransferReceivingService.saveZxSkStockTransferReceiving(zxSkStockTransferReceiving);
    }

    @ApiOperation(value="更新收料单", notes="更新收料单")
    @ApiImplicitParam(name = "zxSkStockTransferReceiving", value = "收料单entity", dataType = "ZxSkStockTransferReceiving")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferReceiving")
    public ResponseEntity updateZxSkStockTransferReceiving(@RequestBody(required = false) ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        return zxSkStockTransferReceivingService.updateZxSkStockTransferReceiving(zxSkStockTransferReceiving);
    }

    @ApiOperation(value="删除收料单", notes="删除收料单")
    @ApiImplicitParam(name = "zxSkStockTransferReceivingList", value = "收料单List", dataType = "List<ZxSkStockTransferReceiving>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferReceiving")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferReceiving(@RequestBody(required = false) List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList) {
        return zxSkStockTransferReceivingService.batchDeleteUpdateZxSkStockTransferReceiving(zxSkStockTransferReceivingList);
    }

    //审核
    @ApiOperation(value="审核收料单", notes="审核收料单")
    @ApiImplicitParam(name = "zxSkStockTransferReceiving", value = "审核收料单", dataType = "ZxSkStockTransferReceiving")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferReceiving")
    public ResponseEntity checkZxSkStockTransferReceiving(@RequestBody(required = false) ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        return zxSkStockTransferReceivingService.checkZxSkStockTransferReceiving(zxSkStockTransferReceiving);
    }

    //获取预收单据编号
    @ApiOperation(value = "获取收料单编号", notes = "获取收料单编号")
    @ApiImplicitParam(name = "zxSkStockTransferReceiving", value = "获取单据编号entity", dataType = "ZxSkStockTransferReceiving")
    @RequireToken
    @PostMapping("/getZxSkStockTransferReceivingNo")
    public ResponseEntity getZxSkStockTransferReceivingNo(@RequestBody(required = false) ZxSkStockTransferReceiving zxSkStockTransferReceiving) {
        return zxSkStockTransferReceivingService.getZxSkStockTransferReceivingNo(zxSkStockTransferReceiving);
    }










}
