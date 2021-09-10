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
import com.apih5.mybatis.pojo.ZxSkStockTransferWithdrawal;
import com.apih5.service.ZxSkStockTransferWithdrawalService;

@RestController
public class ZxSkStockTransferWithdrawalController {

    @Autowired(required = true)
    private ZxSkStockTransferWithdrawalService zxSkStockTransferWithdrawalService;

    @ApiOperation(value="查询退库单", notes="查询退库单")
    @ApiImplicitParam(name = "zxSkStockTransferWithdrawal", value = "退库单entity", dataType = "ZxSkStockTransferWithdrawal")
    @RequireToken
    @PostMapping("/getZxSkStockTransferWithdrawalList")
    public ResponseEntity getZxSkStockTransferWithdrawalList(@RequestBody(required = false) ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        return zxSkStockTransferWithdrawalService.getZxSkStockTransferWithdrawalListByCondition(zxSkStockTransferWithdrawal);
    }

    @ApiOperation(value="查询详情退库单", notes="查询详情退库单")
    @ApiImplicitParam(name = "zxSkStockTransferWithdrawal", value = "退库单entity", dataType = "ZxSkStockTransferWithdrawal")
    @RequireToken
    @PostMapping("/getZxSkStockTransferWithdrawalDetails")
    public ResponseEntity getZxSkStockTransferWithdrawalDetails(@RequestBody(required = false) ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        return zxSkStockTransferWithdrawalService.getZxSkStockTransferWithdrawalDetails(zxSkStockTransferWithdrawal);
    }

    @ApiOperation(value="新增退库单", notes="新增退库单")
    @ApiImplicitParam(name = "zxSkStockTransferWithdrawal", value = "退库单entity", dataType = "ZxSkStockTransferWithdrawal")
    @RequireToken
    @PostMapping("/addZxSkStockTransferWithdrawal")
    public ResponseEntity addZxSkStockTransferWithdrawal(@RequestBody(required = false) ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        return zxSkStockTransferWithdrawalService.saveZxSkStockTransferWithdrawal(zxSkStockTransferWithdrawal);
    }

    @ApiOperation(value="更新退库单", notes="更新退库单")
    @ApiImplicitParam(name = "zxSkStockTransferWithdrawal", value = "退库单entity", dataType = "ZxSkStockTransferWithdrawal")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferWithdrawal")
    public ResponseEntity updateZxSkStockTransferWithdrawal(@RequestBody(required = false) ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        return zxSkStockTransferWithdrawalService.updateZxSkStockTransferWithdrawal(zxSkStockTransferWithdrawal);
    }

    @ApiOperation(value="删除退库单", notes="删除退库单")
    @ApiImplicitParam(name = "zxSkStockTransferWithdrawalList", value = "退库单List", dataType = "List<ZxSkStockTransferWithdrawal>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferWithdrawal")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferWithdrawal(@RequestBody(required = false) List<ZxSkStockTransferWithdrawal> zxSkStockTransferWithdrawalList) {
        return zxSkStockTransferWithdrawalService.batchDeleteUpdateZxSkStockTransferWithdrawal(zxSkStockTransferWithdrawalList);
    }

    @ApiOperation(value="审核退库单", notes="审核退库单")
    @ApiImplicitParam(name = "zxSkStockTransferWithdrawal", value = "退库单entity", dataType = "ZxSkStockTransferWithdrawal")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferWithdrawal")
    public ResponseEntity checkZxSkStockTransferWithdrawal(@RequestBody(required = false) ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        return zxSkStockTransferWithdrawalService.checkZxSkStockTransferWithdrawal(zxSkStockTransferWithdrawal);
    }

    //获取退库单据编号
    @ApiOperation(value = "获取退库单据编号", notes = "获取退库单据编号")
    @ApiImplicitParam(name = "zxSkStockTransferWithdrawal", value = "退库单entity", dataType = "ZxSkStockTransferWithdrawal")
    @RequireToken
    @PostMapping("/getZxSkStockTransferWithdrawalNo")
    public ResponseEntity getZxSkStockTransferWithdrawalNo(@RequestBody(required = false) ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal) {
        return zxSkStockTransferWithdrawalService.getZxSkStockTransferWithdrawalNo(zxSkStockTransferWithdrawal);
    }




}
