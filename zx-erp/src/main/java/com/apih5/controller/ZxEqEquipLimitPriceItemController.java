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
import com.apih5.mybatis.pojo.ZxEqEquipLimitPriceItem;
import com.apih5.service.ZxEqEquipLimitPriceItemService;

@RestController
public class ZxEqEquipLimitPriceItemController {

    @Autowired(required = true)
    private ZxEqEquipLimitPriceItemService zxEqEquipLimitPriceItemService;

    @ApiOperation(value="查询设备租赁限价管理-设备租赁限价采集明细", notes="查询设备租赁限价管理-设备租赁限价采集明细")
    @ApiImplicitParam(name = "zxEqEquipLimitPriceItem", value = "设备租赁限价管理-设备租赁限价采集明细entity", dataType = "ZxEqEquipLimitPriceItem")
    @RequireToken
    @PostMapping("/getZxEqEquipLimitPriceItemList")
    public ResponseEntity getZxEqEquipLimitPriceItemList(@RequestBody(required = false) ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem) {
        return zxEqEquipLimitPriceItemService.getZxEqEquipLimitPriceItemListByCondition(zxEqEquipLimitPriceItem);
    }

    @ApiOperation(value="查询详情设备租赁限价管理-设备租赁限价采集明细", notes="查询详情设备租赁限价管理-设备租赁限价采集明细")
    @ApiImplicitParam(name = "zxEqEquipLimitPriceItem", value = "设备租赁限价管理-设备租赁限价采集明细entity", dataType = "ZxEqEquipLimitPriceItem")
    @RequireToken
    @PostMapping("/getZxEqEquipLimitPriceItemDetails")
    public ResponseEntity getZxEqEquipLimitPriceItemDetails(@RequestBody(required = false) ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem) {
        return zxEqEquipLimitPriceItemService.getZxEqEquipLimitPriceItemDetails(zxEqEquipLimitPriceItem);
    }

    @ApiOperation(value="新增设备租赁限价管理-设备租赁限价采集明细", notes="新增设备租赁限价管理-设备租赁限价采集明细")
    @ApiImplicitParam(name = "zxEqEquipLimitPriceItem", value = "设备租赁限价管理-设备租赁限价采集明细entity", dataType = "ZxEqEquipLimitPriceItem")
    @RequireToken
    @PostMapping("/addZxEqEquipLimitPriceItem")
    public ResponseEntity addZxEqEquipLimitPriceItem(@RequestBody(required = false) ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem) {
        return zxEqEquipLimitPriceItemService.saveZxEqEquipLimitPriceItem(zxEqEquipLimitPriceItem);
    }

    @ApiOperation(value="更新设备租赁限价管理-设备租赁限价采集明细", notes="更新设备租赁限价管理-设备租赁限价采集明细")
    @ApiImplicitParam(name = "zxEqEquipLimitPriceItem", value = "设备租赁限价管理-设备租赁限价采集明细entity", dataType = "ZxEqEquipLimitPriceItem")
    @RequireToken
    @PostMapping("/updateZxEqEquipLimitPriceItem")
    public ResponseEntity updateZxEqEquipLimitPriceItem(@RequestBody(required = false) ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem) {
        return zxEqEquipLimitPriceItemService.updateZxEqEquipLimitPriceItem(zxEqEquipLimitPriceItem);
    }

    @ApiOperation(value="删除设备租赁限价管理-设备租赁限价采集明细", notes="删除设备租赁限价管理-设备租赁限价采集明细")
    @ApiImplicitParam(name = "zxEqEquipLimitPriceItemList", value = "设备租赁限价管理-设备租赁限价采集明细List", dataType = "List<ZxEqEquipLimitPriceItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipLimitPriceItem")
    public ResponseEntity batchDeleteUpdateZxEqEquipLimitPriceItem(@RequestBody(required = false) List<ZxEqEquipLimitPriceItem> zxEqEquipLimitPriceItemList) {
        return zxEqEquipLimitPriceItemService.batchDeleteUpdateZxEqEquipLimitPriceItem(zxEqEquipLimitPriceItemList);
    }

    @ApiOperation(value="删除设备租赁限价管理-设备租赁限价采集明细", notes="删除设备租赁限价管理-设备租赁限价采集明细")
    @ApiImplicitParam(name = "zxEqEquipLimitPriceItem", value = "设备租赁限价管理-设备租赁限价采集明细", dataType = "ZxEqEquipLimitPriceItem")
    @PostMapping("/getLimitPriceForm")
    public List<ZxEqEquipLimitPriceItem> getLimitPriceForm( ){
       return zxEqEquipLimitPriceItemService.getLimitPriceForm();
    };

}
