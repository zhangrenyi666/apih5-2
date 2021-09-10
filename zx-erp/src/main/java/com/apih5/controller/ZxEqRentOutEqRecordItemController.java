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
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecordItem;
import com.apih5.service.ZxEqRentOutEqRecordItemService;

@RestController
public class ZxEqRentOutEqRecordItemController {

    @Autowired(required = true)
    private ZxEqRentOutEqRecordItemService zxEqRentOutEqRecordItemService;

    @ApiOperation(value="查询租赁设备管理-外租机械设备运转记录明细", notes="查询租赁设备管理-外租机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqRentOutEqRecordItem", value = "租赁设备管理-外租机械设备运转记录明细entity", dataType = "ZxEqRentOutEqRecordItem")
    @RequireToken
    @PostMapping("/getZxEqRentOutEqRecordItemList")
    public ResponseEntity getZxEqRentOutEqRecordItemList(@RequestBody(required = false) ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem) {
        return zxEqRentOutEqRecordItemService.getZxEqRentOutEqRecordItemListByCondition(zxEqRentOutEqRecordItem);
    }

    @ApiOperation(value="查询详情租赁设备管理-外租机械设备运转记录明细", notes="查询详情租赁设备管理-外租机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqRentOutEqRecordItem", value = "租赁设备管理-外租机械设备运转记录明细entity", dataType = "ZxEqRentOutEqRecordItem")
    @RequireToken
    @PostMapping("/getZxEqRentOutEqRecordItemDetails")
    public ResponseEntity getZxEqRentOutEqRecordItemDetails(@RequestBody(required = false) ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem) {
        return zxEqRentOutEqRecordItemService.getZxEqRentOutEqRecordItemDetails(zxEqRentOutEqRecordItem);
    }

    @ApiOperation(value="新增租赁设备管理-外租机械设备运转记录明细", notes="新增租赁设备管理-外租机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqRentOutEqRecordItem", value = "租赁设备管理-外租机械设备运转记录明细entity", dataType = "ZxEqRentOutEqRecordItem")
    @RequireToken
    @PostMapping("/addZxEqRentOutEqRecordItem")
    public ResponseEntity addZxEqRentOutEqRecordItem(@RequestBody(required = false) ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem) {
        return zxEqRentOutEqRecordItemService.saveZxEqRentOutEqRecordItem(zxEqRentOutEqRecordItem);
    }

    @ApiOperation(value="更新租赁设备管理-外租机械设备运转记录明细", notes="更新租赁设备管理-外租机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqRentOutEqRecordItem", value = "租赁设备管理-外租机械设备运转记录明细entity", dataType = "ZxEqRentOutEqRecordItem")
    @RequireToken
    @PostMapping("/updateZxEqRentOutEqRecordItem")
    public ResponseEntity updateZxEqRentOutEqRecordItem(@RequestBody(required = false) ZxEqRentOutEqRecordItem zxEqRentOutEqRecordItem) {
        return zxEqRentOutEqRecordItemService.updateZxEqRentOutEqRecordItem(zxEqRentOutEqRecordItem);
    }

    @ApiOperation(value="删除租赁设备管理-外租机械设备运转记录明细", notes="删除租赁设备管理-外租机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqRentOutEqRecordItemList", value = "租赁设备管理-外租机械设备运转记录明细List", dataType = "List<ZxEqRentOutEqRecordItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqRentOutEqRecordItem")
    public ResponseEntity batchDeleteUpdateZxEqRentOutEqRecordItem(@RequestBody(required = false) List<ZxEqRentOutEqRecordItem> zxEqRentOutEqRecordItemList) {
        return zxEqRentOutEqRecordItemService.batchDeleteUpdateZxEqRentOutEqRecordItem(zxEqRentOutEqRecordItemList);
    }

}
