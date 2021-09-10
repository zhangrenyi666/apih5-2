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
import com.apih5.mybatis.pojo.ZxSkStockTransItemDiskFillingStorage;
import com.apih5.service.ZxSkStockTransItemDiskFillingStorageService;

@RestController
public class ZxSkStockTransItemDiskFillingStorageController {

    @Autowired(required = true)
    private ZxSkStockTransItemDiskFillingStorageService zxSkStockTransItemDiskFillingStorageService;

    @ApiOperation(value="查询盘盈入库单明细", notes="查询盘盈入库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDiskFillingStorage", value = "盘盈入库单明细entity", dataType = "ZxSkStockTransItemDiskFillingStorage")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemDiskFillingStorageList")
    public ResponseEntity getZxSkStockTransItemDiskFillingStorageList(@RequestBody(required = false) ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage) {
        return zxSkStockTransItemDiskFillingStorageService.getZxSkStockTransItemDiskFillingStorageListByCondition(zxSkStockTransItemDiskFillingStorage);
    }

    @ApiOperation(value="查询详情盘盈入库单明细", notes="查询详情盘盈入库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDiskFillingStorage", value = "盘盈入库单明细entity", dataType = "ZxSkStockTransItemDiskFillingStorage")
    @RequireToken
    @PostMapping("/getZxSkStockTransItemDiskFillingStorageDetails")
    public ResponseEntity getZxSkStockTransItemDiskFillingStorageDetails(@RequestBody(required = false) ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage) {
        return zxSkStockTransItemDiskFillingStorageService.getZxSkStockTransItemDiskFillingStorageDetails(zxSkStockTransItemDiskFillingStorage);
    }

    @ApiOperation(value="新增盘盈入库单明细", notes="新增盘盈入库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDiskFillingStorage", value = "盘盈入库单明细entity", dataType = "ZxSkStockTransItemDiskFillingStorage")
    @RequireToken
    @PostMapping("/addZxSkStockTransItemDiskFillingStorage")
    public ResponseEntity addZxSkStockTransItemDiskFillingStorage(@RequestBody(required = false) ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage) {
        return zxSkStockTransItemDiskFillingStorageService.saveZxSkStockTransItemDiskFillingStorage(zxSkStockTransItemDiskFillingStorage);
    }

    @ApiOperation(value="更新盘盈入库单明细", notes="更新盘盈入库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDiskFillingStorage", value = "盘盈入库单明细entity", dataType = "ZxSkStockTransItemDiskFillingStorage")
    @RequireToken
    @PostMapping("/updateZxSkStockTransItemDiskFillingStorage")
    public ResponseEntity updateZxSkStockTransItemDiskFillingStorage(@RequestBody(required = false) ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage) {
        return zxSkStockTransItemDiskFillingStorageService.updateZxSkStockTransItemDiskFillingStorage(zxSkStockTransItemDiskFillingStorage);
    }

    @ApiOperation(value="删除盘盈入库单明细", notes="删除盘盈入库单明细")
    @ApiImplicitParam(name = "zxSkStockTransItemDiskFillingStorageList", value = "盘盈入库单明细List", dataType = "List<ZxSkStockTransItemDiskFillingStorage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransItemDiskFillingStorage")
    public ResponseEntity batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(@RequestBody(required = false) List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList) {
        return zxSkStockTransItemDiskFillingStorageService.batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(zxSkStockTransItemDiskFillingStorageList);
    }

}
