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
import com.apih5.mybatis.pojo.ZxSkLimitPriceAdjust;
import com.apih5.service.ZxSkLimitPriceAdjustService;

@RestController
public class ZxSkLimitPriceAdjustController {

    @Autowired(required = true)
    private ZxSkLimitPriceAdjustService zxSkLimitPriceAdjustService;

    @ApiOperation(value="查询物资限价调整", notes="查询物资限价调整")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjust", value = "物资限价调整entity", dataType = "ZxSkLimitPriceAdjust")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceAdjustList")
    public ResponseEntity getZxSkLimitPriceAdjustList(@RequestBody(required = false) ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        return zxSkLimitPriceAdjustService.getZxSkLimitPriceAdjustListByCondition(zxSkLimitPriceAdjust);
    }

    @ApiOperation(value="查询详情物资限价调整", notes="查询详情物资限价调整")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjust", value = "物资限价调整entity", dataType = "ZxSkLimitPriceAdjust")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceAdjustDetails")
    public ResponseEntity getZxSkLimitPriceAdjustDetails(@RequestBody(required = false) ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        return zxSkLimitPriceAdjustService.getZxSkLimitPriceAdjustDetails(zxSkLimitPriceAdjust);
    }

    @ApiOperation(value="新增物资限价调整", notes="新增物资限价调整")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjust", value = "物资限价调整entity", dataType = "ZxSkLimitPriceAdjust")
    @RequireToken
    @PostMapping("/addZxSkLimitPriceAdjust")
    public ResponseEntity addZxSkLimitPriceAdjust(@RequestBody(required = false) ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        return zxSkLimitPriceAdjustService.saveZxSkLimitPriceAdjust(zxSkLimitPriceAdjust);
    }

    @ApiOperation(value="更新物资限价调整", notes="更新物资限价调整")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjust", value = "物资限价调整entity", dataType = "ZxSkLimitPriceAdjust")
    @RequireToken
    @PostMapping("/updateZxSkLimitPriceAdjust")
    public ResponseEntity updateZxSkLimitPriceAdjust(@RequestBody(required = false) ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        return zxSkLimitPriceAdjustService.updateZxSkLimitPriceAdjust(zxSkLimitPriceAdjust);
    }

    @ApiOperation(value="删除物资限价调整", notes="删除物资限价调整")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjustList", value = "物资限价调整List", dataType = "List<ZxSkLimitPriceAdjust>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkLimitPriceAdjust")
    public ResponseEntity batchDeleteUpdateZxSkLimitPriceAdjust(@RequestBody(required = false) List<ZxSkLimitPriceAdjust> zxSkLimitPriceAdjustList) {
        return zxSkLimitPriceAdjustService.batchDeleteUpdateZxSkLimitPriceAdjust(zxSkLimitPriceAdjustList);
    }

    @ApiOperation(value = "获取调整编号", notes = "获取调整编号")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjust", value = "获取调整编号entity", dataType = "ZxSkLimitPriceAdjust")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceAdjustNo")
    public ResponseEntity getZxSkLimitPriceAdjustNo(@RequestBody(required = false)ZxSkLimitPriceAdjust zxSkLimitPriceAdjust) {
        return zxSkLimitPriceAdjustService.getZxSkLimitPriceAdjustNo(zxSkLimitPriceAdjust);
    }
}
