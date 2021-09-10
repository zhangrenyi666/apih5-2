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
import com.apih5.mybatis.pojo.ZxSkStockTransferDiskFillingStorage;
import com.apih5.service.ZxSkStockTransferDiskFillingStorageService;

@RestController
public class ZxSkStockTransferDiskFillingStorageController {

    @Autowired(required = true)
    private ZxSkStockTransferDiskFillingStorageService zxSkStockTransferDiskFillingStorageService;

    @ApiOperation(value="查询盘盈入库单", notes="查询盘盈入库单")
    @ApiImplicitParam(name = "zxSkStockTransferDiskFillingStorage", value = "盘盈入库单entity", dataType = "ZxSkStockTransferDiskFillingStorage")
    @RequireToken
    @PostMapping("/getZxSkStockTransferDiskFillingStorageList")
    public ResponseEntity getZxSkStockTransferDiskFillingStorageList(@RequestBody(required = false) ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        return zxSkStockTransferDiskFillingStorageService.getZxSkStockTransferDiskFillingStorageListByCondition(zxSkStockTransferDiskFillingStorage);
    }

    @ApiOperation(value="查询详情盘盈入库单", notes="查询详情盘盈入库单")
    @ApiImplicitParam(name = "zxSkStockTransferDiskFillingStorage", value = "盘盈入库单entity", dataType = "ZxSkStockTransferDiskFillingStorage")
    @RequireToken
    @PostMapping("/getZxSkStockTransferDiskFillingStorageDetails")
    public ResponseEntity getZxSkStockTransferDiskFillingStorageDetails(@RequestBody(required = false) ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        return zxSkStockTransferDiskFillingStorageService.getZxSkStockTransferDiskFillingStorageDetails(zxSkStockTransferDiskFillingStorage);
    }

    @ApiOperation(value="新增盘盈入库单", notes="新增盘盈入库单")
    @ApiImplicitParam(name = "zxSkStockTransferDiskFillingStorage", value = "盘盈入库单entity", dataType = "ZxSkStockTransferDiskFillingStorage")
    @RequireToken
    @PostMapping("/addZxSkStockTransferDiskFillingStorage")
    public ResponseEntity addZxSkStockTransferDiskFillingStorage(@RequestBody(required = false) ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        return zxSkStockTransferDiskFillingStorageService.saveZxSkStockTransferDiskFillingStorage(zxSkStockTransferDiskFillingStorage);
    }

    @ApiOperation(value="更新盘盈入库单", notes="更新盘盈入库单")
    @ApiImplicitParam(name = "zxSkStockTransferDiskFillingStorage", value = "盘盈入库单entity", dataType = "ZxSkStockTransferDiskFillingStorage")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferDiskFillingStorage")
    public ResponseEntity updateZxSkStockTransferDiskFillingStorage(@RequestBody(required = false) ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        return zxSkStockTransferDiskFillingStorageService.updateZxSkStockTransferDiskFillingStorage(zxSkStockTransferDiskFillingStorage);
    }

    @ApiOperation(value="删除盘盈入库单", notes="删除盘盈入库单")
    @ApiImplicitParam(name = "zxSkStockTransferDiskFillingStorageList", value = "盘盈入库单List", dataType = "List<ZxSkStockTransferDiskFillingStorage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferDiskFillingStorage")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferDiskFillingStorage(@RequestBody(required = false) List<ZxSkStockTransferDiskFillingStorage> zxSkStockTransferDiskFillingStorageList) {
        return zxSkStockTransferDiskFillingStorageService.batchDeleteUpdateZxSkStockTransferDiskFillingStorage(zxSkStockTransferDiskFillingStorageList);
    }

    @ApiOperation(value="审核盘盈入库单", notes="审核盘盈入库单")
    @ApiImplicitParam(name = "zxSkStockTransferDiskFillingStorage", value = "盘盈入库单entity", dataType = "ZxSkStockTransferDiskFillingStorage")
    @RequireToken
    @PostMapping("/checkZxSkStockTransferDiskFillingStorage")
    public ResponseEntity checkZxSkStockTransferDiskFillingStorage(@RequestBody(required = false) ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        return zxSkStockTransferDiskFillingStorageService.checkZxSkStockTransferDiskFillingStorage(zxSkStockTransferDiskFillingStorage);
    }

    //获取调拨单据编号
    @ApiOperation(value = "获取盘盈入库单编号", notes = "获取盘盈入库单编号")
    @ApiImplicitParam(name = "zxSkStockTransferDiskFillingStorage", value = "盘盈入库单entity", dataType = "ZxSkStockTransferDiskFillingStorage")
    @RequireToken
    @PostMapping("/getZxSkStockTransferDiskFillingStorageNo")
    public ResponseEntity getZxSkStockTransferDiskFillingStorageNo(@RequestBody(required = false) ZxSkStockTransferDiskFillingStorage zxSkStockTransferDiskFillingStorage) {
        return zxSkStockTransferDiskFillingStorageService.getZxSkStockTransferDiskFillingStorageNo(zxSkStockTransferDiskFillingStorage);
    }





}
