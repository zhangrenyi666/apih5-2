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
import com.apih5.mybatis.pojo.ZxSkStockTransItemWithdrawal;
import com.apih5.service.ZxSkStockTransItemWithdrawalService;

@RestController
public class ZxSkStockTransItemWithdrawalController {

    @Autowired(required = true)
    private ZxSkStockTransItemWithdrawalService zxSkStockTransItemWithdrawalService;

    @ApiOperation(value="查询退库单明细", notes="查询退库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemWithdrawal", value = "退库单明细entity", dataType = "ZxSkStockTransItemWithdrawal")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemWithdrawalList")
    public ResponseEntity getZxSkStockTransItemWithdrawalList(@RequestBody(required = false) ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal) {
        return zxSkStockTransItemWithdrawalService.getZxSkStockTransItemWithdrawalListByCondition(zxSkStockTransItemWithdrawal);
    }

    @ApiOperation(value="查询详情退库单明细", notes="查询详情退库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemWithdrawal", value = "退库单明细entity", dataType = "ZxSkStockTransItemWithdrawal")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemWithdrawalDetails")
    public ResponseEntity getZxSkStockTransItemWithdrawalDetails(@RequestBody(required = false) ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal) {
        return zxSkStockTransItemWithdrawalService.getZxSkStockTransItemWithdrawalDetails(zxSkStockTransItemWithdrawal);
    }

    @ApiOperation(value="新增退库单明细", notes="新增退库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemWithdrawal", value = "退库单明细entity", dataType = "ZxSkStockTransItemWithdrawal")
    @RequireToken
    @PostMapping("/addZxSkStockTransItemWithdrawal")
    public ResponseEntity addZxSkStockTransItemWithdrawal(@RequestBody(required = false) ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal) {
        return zxSkStockTransItemWithdrawalService.saveZxSkStockTransItemWithdrawal(zxSkStockTransItemWithdrawal);
    }

    @ApiOperation(value="更新退库单明细", notes="更新退库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemWithdrawal", value = "退库单明细entity", dataType = "ZxSkStockTransItemWithdrawal")
    @RequireToken
    @PostMapping("/updateZxSkStockTransItemWithdrawal")
    public ResponseEntity updateZxSkStockTransItemWithdrawal(@RequestBody(required = false) ZxSkStockTransItemWithdrawal zxSkStockTransItemWithdrawal) {
        return zxSkStockTransItemWithdrawalService.updateZxSkStockTransItemWithdrawal(zxSkStockTransItemWithdrawal);
    }

    @ApiOperation(value="删除退库单明细", notes="删除退库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemWithdrawalList", value = "退库单明细List", dataType = "List<ZxSkStockTransItemWithdrawal>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransItemWithdrawal")
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemWithdrawal(@RequestBody(required = false) List<ZxSkStockTransItemWithdrawal> zxSkStockTransItemWithdrawalList) {
        return zxSkStockTransItemWithdrawalService.batchDeleteUpdateZxSkStockTransItemWithdrawal(zxSkStockTransItemWithdrawalList);
    }

}
