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
import com.apih5.mybatis.pojo.ZxEqEquipLimitPrice;
import com.apih5.service.ZxEqEquipLimitPriceService;

@RestController
public class ZxEqEquipLimitPriceController {

    @Autowired(required = true)
    private ZxEqEquipLimitPriceService zxEqEquipLimitPriceService;

    @ApiOperation(value="查询设备租赁限价管理-设备租赁限价采集", notes="查询设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPrice", value = "设备租赁限价管理-设备租赁限价采集entity", dataType = "ZxEqEquipLimitPrice")
    @RequireToken
    @PostMapping("/getZxEqEquipLimitPriceList")
    public ResponseEntity getZxEqEquipLimitPriceList(@RequestBody(required = false) ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        return zxEqEquipLimitPriceService.getZxEqEquipLimitPriceListByCondition(zxEqEquipLimitPrice);
    }

    @ApiOperation(value="查询详情设备租赁限价管理-设备租赁限价采集", notes="查询详情设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPrice", value = "设备租赁限价管理-设备租赁限价采集entity", dataType = "ZxEqEquipLimitPrice")
    @RequireToken
    @PostMapping("/getZxEqEquipLimitPriceDetails")
    public ResponseEntity getZxEqEquipLimitPriceDetails(@RequestBody(required = false) ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        return zxEqEquipLimitPriceService.getZxEqEquipLimitPriceDetails(zxEqEquipLimitPrice);
    }

    @ApiOperation(value="新增设备租赁限价管理-设备租赁限价采集", notes="新增设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPrice", value = "设备租赁限价管理-设备租赁限价采集entity", dataType = "ZxEqEquipLimitPrice")
    @RequireToken
    @PostMapping("/addZxEqEquipLimitPrice")
    public ResponseEntity addZxEqEquipLimitPrice(@RequestBody(required = false) ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        return zxEqEquipLimitPriceService.saveZxEqEquipLimitPrice(zxEqEquipLimitPrice);
    }

    @ApiOperation(value="更新设备租赁限价管理-设备租赁限价采集", notes="更新设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPrice", value = "设备租赁限价管理-设备租赁限价采集entity", dataType = "ZxEqEquipLimitPrice")
    @RequireToken
    @PostMapping("/updateZxEqEquipLimitPrice")
    public ResponseEntity updateZxEqEquipLimitPrice(@RequestBody(required = false) ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        return zxEqEquipLimitPriceService.updateZxEqEquipLimitPrice(zxEqEquipLimitPrice);
    }

    @ApiOperation(value="删除设备租赁限价管理-设备租赁限价采集", notes="删除设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPriceList", value = "设备租赁限价管理-设备租赁限价采集List", dataType = "List<ZxEqEquipLimitPrice>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipLimitPrice")
    public ResponseEntity batchDeleteUpdateZxEqEquipLimitPrice(@RequestBody(required = false) List<ZxEqEquipLimitPrice> zxEqEquipLimitPriceList) {
        return zxEqEquipLimitPriceService.batchDeleteUpdateZxEqEquipLimitPrice(zxEqEquipLimitPriceList);
    }

    @ApiOperation(value="复制设备租赁限价管理-设备租赁限价采集", notes="复制设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPrice", value = "设备租赁限价管理-设备租赁限价采集entity", dataType = "ZxEqEquipLimitPrice")
    @RequireToken
    @PostMapping("/copyZxEqEquipLimitPrice")
    public ResponseEntity copyZxEqEquipLimitPrice(@RequestBody(required = false) ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        return zxEqEquipLimitPriceService.copyZxEqEquipLimitPrice(zxEqEquipLimitPrice);
    }
    
    @ApiOperation(value="查询详情设备租赁限价管理-设备租赁限价采集", notes="查询详情设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPrice", value = "设备租赁限价管理-设备租赁限价采集entity", dataType = "ZxEqEquipLimitPrice")
    @RequireToken
    @PostMapping("/getZxEqEquipLimitPriceApplyNo")
    public ResponseEntity getZxEqEquipLimitPriceApplyNo(@RequestBody(required = false) ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        return zxEqEquipLimitPriceService.getZxEqEquipLimitPriceApplyNo(zxEqEquipLimitPrice);
    }
    
    @ApiOperation(value="查询设备租赁限价管理-设备租赁限价采集", notes="查询设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPrice", value = "设备租赁限价管理-设备租赁限价采集entity", dataType = "ZxEqEquipLimitPrice")
    @PostMapping("/ureportZxEqEquipLimitPriceVO")
    public List<ZxEqEquipLimitPrice> ureportZxEqEquipLimitPriceVO(@RequestBody(required = false) ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
        return zxEqEquipLimitPriceService.ureportZxEqEquipLimitPriceVO(zxEqEquipLimitPrice);
    }
    @ApiOperation(value="分析查询列表-设备租赁限价采集", notes="查询设备租赁限价管理-设备租赁限价采集")
    @ApiImplicitParam(name = "zxEqEquipLimitPrice", value = "设备租赁限价管理-设备租赁限价采集entity", dataType = "ZxEqEquipLimitPrice")
    @RequireToken
    @PostMapping("/ureportZxEqEquipLimitPriceVOIdle")
    public ResponseEntity ureportZxEqEquipLimitPriceVOIdle(@RequestBody(required = false) ZxEqEquipLimitPrice zxEqEquipLimitPrice) {
    	return zxEqEquipLimitPriceService.ureportZxEqEquipLimitPriceVOIdle(zxEqEquipLimitPrice);
    }
}
