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
import com.apih5.mybatis.pojo.ZxSkLimitPrice;
import com.apih5.service.ZxSkLimitPriceService;

@RestController
public class ZxSkLimitPriceController {

    @Autowired(required = true)
    private ZxSkLimitPriceService zxSkLimitPriceService;

    @ApiOperation(value="查询物资限价管理", notes="查询物资限价管理")
    @ApiImplicitParam(name = "zxSkLimitPrice", value = "物资限价管理entity", dataType = "ZxSkLimitPrice")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceList")
    public ResponseEntity getZxSkLimitPriceList(@RequestBody(required = false) ZxSkLimitPrice zxSkLimitPrice) {
        return zxSkLimitPriceService.getZxSkLimitPriceListByCondition(zxSkLimitPrice);
    }

    @ApiOperation(value="查询详情物资限价管理", notes="查询详情物资限价管理")
    @ApiImplicitParam(name = "zxSkLimitPrice", value = "物资限价管理entity", dataType = "ZxSkLimitPrice")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceDetails")
    public ResponseEntity getZxSkLimitPriceDetails(@RequestBody(required = false) ZxSkLimitPrice zxSkLimitPrice) {
        return zxSkLimitPriceService.getZxSkLimitPriceDetails(zxSkLimitPrice);
    }

    @ApiOperation(value="新增物资限价管理", notes="新增物资限价管理")
    @ApiImplicitParam(name = "zxSkLimitPrice", value = "物资限价管理entity", dataType = "ZxSkLimitPrice")
    @RequireToken
    @PostMapping("/addZxSkLimitPrice")
    public ResponseEntity addZxSkLimitPrice(@RequestBody(required = false) ZxSkLimitPrice zxSkLimitPrice) {
        return zxSkLimitPriceService.saveZxSkLimitPrice(zxSkLimitPrice);
    }

    @ApiOperation(value="更新物资限价管理", notes="更新物资限价管理")
    @ApiImplicitParam(name = "zxSkLimitPrice", value = "物资限价管理entity", dataType = "ZxSkLimitPrice")
    @RequireToken
    @PostMapping("/updateZxSkLimitPrice")
    public ResponseEntity updateZxSkLimitPrice(@RequestBody(required = false) ZxSkLimitPrice zxSkLimitPrice) {
        return zxSkLimitPriceService.updateZxSkLimitPrice(zxSkLimitPrice);
    }

    @ApiOperation(value="删除物资限价管理", notes="删除物资限价管理")
    @ApiImplicitParam(name = "zxSkLimitPriceList", value = "物资限价管理List", dataType = "List<ZxSkLimitPrice>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkLimitPrice")
    public ResponseEntity batchDeleteUpdateZxSkLimitPrice(@RequestBody(required = false) List<ZxSkLimitPrice> zxSkLimitPriceList) {
        return zxSkLimitPriceService.batchDeleteUpdateZxSkLimitPrice(zxSkLimitPriceList);
    }


    @ApiOperation(value="获取业主合同台账数据", notes="获取业主合同台账数据")
    @ApiImplicitParam(name = "zxSkLimitPrice", value = "物资限价管理entity", dataType = "ZxSkLimitPrice")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceBase")
    public ResponseEntity getZxSkLimitPriceBase(@RequestBody(required = false)ZxSkLimitPrice zxSkLimitPrice) {
        return zxSkLimitPriceService.getZxSkLimitPriceBase(zxSkLimitPrice);
    }


    @ApiOperation(value="查询物资限价管理", notes="查询物资限价管理")
    @ApiImplicitParam(name = "zxSkLimitPrice", value = "物资限价管理entity", dataType = "ZxSkLimitPrice")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceByLimitNoList")
    public ResponseEntity getZxSkLimitPriceByLimitNoList(@RequestBody(required = false) ZxSkLimitPrice zxSkLimitPrice) {
        return zxSkLimitPriceService.getZxSkLimitPriceByLimitNoList(zxSkLimitPrice);
    }


}
