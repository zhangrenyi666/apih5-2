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
import com.apih5.mybatis.pojo.ZxSkPurchase;
import com.apih5.service.ZxSkPurchaseService;

@RestController
public class ZxSkPurchaseController {

    @Autowired(required = true)
    private ZxSkPurchaseService zxSkPurchaseService;

    @ApiOperation(value="查询物资购置申请", notes="查询物资购置申请")
    @ApiImplicitParam(name = "zxSkPurchase", value = "物资购置申请entity", dataType = "ZxSkPurchase")
    @RequireToken
    @PostMapping("/getZxSkPurchaseList")
    public ResponseEntity getZxSkPurchaseList(@RequestBody(required = false) ZxSkPurchase zxSkPurchase) {
        return zxSkPurchaseService.getZxSkPurchaseListByCondition(zxSkPurchase);
    }

    @ApiOperation(value="查询详情物资购置申请", notes="查询详情物资购置申请")
    @ApiImplicitParam(name = "zxSkPurchase", value = "物资购置申请entity", dataType = "ZxSkPurchase")
    @RequireToken
    @PostMapping("/getZxSkPurchaseDetail")
    public ResponseEntity getZxSkPurchaseDetail(@RequestBody(required = false) ZxSkPurchase zxSkPurchase) {
        return zxSkPurchaseService.getZxSkPurchaseDetail(zxSkPurchase);
    }

    @ApiOperation(value="新增物资购置申请", notes="新增物资购置申请")
    @ApiImplicitParam(name = "zxSkPurchase", value = "物资购置申请entity", dataType = "ZxSkPurchase")
    @RequireToken
    @PostMapping("/addZxSkPurchase")
    public ResponseEntity addZxSkPurchase(@RequestBody(required = false) ZxSkPurchase zxSkPurchase) {
        return zxSkPurchaseService.saveZxSkPurchase(zxSkPurchase);
    }

    @ApiOperation(value="更新物资购置申请", notes="更新物资购置申请")
    @ApiImplicitParam(name = "zxSkPurchase", value = "物资购置申请entity", dataType = "ZxSkPurchase")
    @RequireToken
    @PostMapping("/updateZxSkPurchase")
    public ResponseEntity updateZxSkPurchase(@RequestBody(required = false) ZxSkPurchase zxSkPurchase) {
        return zxSkPurchaseService.updateZxSkPurchase(zxSkPurchase);
    }

    @ApiOperation(value="删除物资购置申请", notes="删除物资购置申请")
    @ApiImplicitParam(name = "zxSkPurchaseList", value = "物资购置申请List", dataType = "List<ZxSkPurchase>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkPurchase")
    public ResponseEntity batchDeleteUpdateZxSkPurchase(@RequestBody(required = false) List<ZxSkPurchase> zxSkPurchaseList) {
        return zxSkPurchaseService.batchDeleteUpdateZxSkPurchase(zxSkPurchaseList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //单据编号
    @ApiOperation(value = "获取物资购置申请单据编号", notes = "获取物资购置申请单据编号")
    @ApiImplicitParam(name = "zxSkPurchase", value = "领料单entity", dataType = "zxSkPurchase")
    @RequireToken
    @PostMapping("/getZxSkPurchaseNo")
    public ResponseEntity getZxSkPurchaseNo(@RequestBody(required = false) ZxSkPurchase zxSkPurchase) {
        return zxSkPurchaseService.getZxSkPurchaseNo(zxSkPurchase);
    }
}
