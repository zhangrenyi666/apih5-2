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
import com.apih5.mybatis.pojo.ZxCtSuppliesMarginRatio;
import com.apih5.service.ZxCtSuppliesMarginRatioService;

@RestController
public class ZxCtSuppliesMarginRatioController {

    @Autowired(required = true)
    private ZxCtSuppliesMarginRatioService zxCtSuppliesMarginRatioService;

    @ApiOperation(value="查询物资保证金比例表", notes="查询物资保证金比例表")
    @ApiImplicitParam(name = "zxCtSuppliesMarginRatio", value = "物资保证金比例表entity", dataType = "ZxCtSuppliesMarginRatio")
    @RequireToken
    @PostMapping("/getZxCtSuppliesMarginRatioList")
    public ResponseEntity getZxCtSuppliesMarginRatioList(@RequestBody(required = false) ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        return zxCtSuppliesMarginRatioService.getZxCtSuppliesMarginRatioListByCondition(zxCtSuppliesMarginRatio);
    }

    @ApiOperation(value="查询详情物资保证金比例表", notes="查询详情物资保证金比例表")
    @ApiImplicitParam(name = "zxCtSuppliesMarginRatio", value = "物资保证金比例表entity", dataType = "ZxCtSuppliesMarginRatio")
    @RequireToken
    @PostMapping("/getZxCtSuppliesMarginRatioDetail")
    public ResponseEntity getZxCtSuppliesMarginRatioDetail(@RequestBody(required = false) ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        return zxCtSuppliesMarginRatioService.getZxCtSuppliesMarginRatioDetail(zxCtSuppliesMarginRatio);
    }

    @ApiOperation(value="新增物资保证金比例表", notes="新增物资保证金比例表")
    @ApiImplicitParam(name = "zxCtSuppliesMarginRatio", value = "物资保证金比例表entity", dataType = "ZxCtSuppliesMarginRatio")
    @RequireToken
    @PostMapping("/addZxCtSuppliesMarginRatio")
    public ResponseEntity addZxCtSuppliesMarginRatio(@RequestBody(required = false) ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        return zxCtSuppliesMarginRatioService.saveZxCtSuppliesMarginRatio(zxCtSuppliesMarginRatio);
    }

    @ApiOperation(value="更新物资保证金比例表", notes="更新物资保证金比例表")
    @ApiImplicitParam(name = "zxCtSuppliesMarginRatio", value = "物资保证金比例表entity", dataType = "ZxCtSuppliesMarginRatio")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesMarginRatio")
    public ResponseEntity updateZxCtSuppliesMarginRatio(@RequestBody(required = false) ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        return zxCtSuppliesMarginRatioService.updateZxCtSuppliesMarginRatio(zxCtSuppliesMarginRatio);
    }

    @ApiOperation(value="删除物资保证金比例表", notes="删除物资保证金比例表")
    @ApiImplicitParam(name = "zxCtSuppliesMarginRatioList", value = "物资保证金比例表List", dataType = "List<ZxCtSuppliesMarginRatio>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesMarginRatio")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesMarginRatio(@RequestBody(required = false) List<ZxCtSuppliesMarginRatio> zxCtSuppliesMarginRatioList) {
        return zxCtSuppliesMarginRatioService.batchDeleteUpdateZxCtSuppliesMarginRatio(zxCtSuppliesMarginRatioList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="新增物资保证金比例表", notes="新增物资保证金比例表")
    @ApiImplicitParam(name = "zxCtSuppliesMarginRatio", value = "物资保证金比例表entity", dataType = "ZxCtSuppliesMarginRatio")
    @RequireToken
    @PostMapping("/addZxCtSuppliesMarginRatioByConID")
    public ResponseEntity addZxCtSuppliesMarginRatioByConID(@RequestBody(required = false) ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        return zxCtSuppliesMarginRatioService.saveZxCtSuppliesMarginRatioByConID(zxCtSuppliesMarginRatio);
    }

    @ApiOperation(value="更新物资保证金比例表", notes="更新物资保证金比例表")
    @ApiImplicitParam(name = "zxCtSuppliesMarginRatio", value = "物资保证金比例表entity", dataType = "ZxCtSuppliesMarginRatio")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesMarginRatioByConID")
    public ResponseEntity updateZxCtSuppliesMarginRatioByConID(@RequestBody(required = false) ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        return zxCtSuppliesMarginRatioService.updateZxCtSuppliesMarginRatioByConID(zxCtSuppliesMarginRatio);
    }
}
