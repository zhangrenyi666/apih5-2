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
import com.apih5.mybatis.pojo.ZxSkStockTransItemReceiving;
import com.apih5.service.ZxSkStockTransItemReceivingService;

@RestController
public class ZxSkStockTransItemReceivingController {

    @Autowired(required = true)
    private ZxSkStockTransItemReceivingService zxSkStockTransItemReceivingService;

    @ApiOperation(value="查询收料单明细", notes="查询收料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemReceiving", value = "收料单明细entity", dataType = "ZxSkStockTransItemReceiving")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemReceivingList")
    public ResponseEntity getZxSkStockTransItemReceivingList(@RequestBody(required = false) ZxSkStockTransItemReceiving zxSkStockTransItemReceiving) {
        return zxSkStockTransItemReceivingService.getZxSkStockTransItemReceivingListByCondition(zxSkStockTransItemReceiving);
    }

    @ApiOperation(value="查询详情收料单明细", notes="查询详情收料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemReceiving", value = "收料单明细entity", dataType = "ZxSkStockTransItemReceiving")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemReceivingDetails")
    public ResponseEntity getZxSkStockTransItemReceivingDetails(@RequestBody(required = false) ZxSkStockTransItemReceiving zxSkStockTransItemReceiving) {
        return zxSkStockTransItemReceivingService.getZxSkStockTransItemReceivingDetails(zxSkStockTransItemReceiving);
    }

    @ApiOperation(value="新增收料单明细", notes="新增收料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemReceiving", value = "收料单明细entity", dataType = "ZxSkStockTransItemReceiving")
    @RequireToken
    @PostMapping("/addZxSkStockTransItemReceiving")
    public ResponseEntity addZxSkStockTransItemReceiving(@RequestBody(required = false) ZxSkStockTransItemReceiving zxSkStockTransItemReceiving) {
        return zxSkStockTransItemReceivingService.saveZxSkStockTransItemReceiving(zxSkStockTransItemReceiving);
    }

    @ApiOperation(value="更新收料单明细", notes="更新收料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemReceiving", value = "收料单明细entity", dataType = "ZxSkStockTransItemReceiving")
    @RequireToken
    @PostMapping("/updateZxSkStockTransItemReceiving")
    public ResponseEntity updateZxSkStockTransItemReceiving(@RequestBody(required = false) ZxSkStockTransItemReceiving zxSkStockTransItemReceiving) {
        return zxSkStockTransItemReceivingService.updateZxSkStockTransItemReceiving(zxSkStockTransItemReceiving);
    }

    @ApiOperation(value="删除收料单明细", notes="删除收料单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemReceivingList", value = "收料单明细List", dataType = "List<ZxSkStockTransItemReceiving>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransItemReceiving")
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemReceiving(@RequestBody(required = false) List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList) {
        return zxSkStockTransItemReceivingService.batchDeleteUpdateZxSkStockTransItemReceiving(zxSkStockTransItemReceivingList);
    }

}
