package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResInOutStockAllAmt;
import com.apih5.service.ZxSkResInOutStockAllAmtService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkResInOutStockAllAmtController {

    @Autowired(required = true)
    private ZxSkResInOutStockAllAmtService zxSkResInOutStockAllAmtService;

    @ApiOperation(value="查询物资购进、消费与库存总值表", notes="查询物资购进、消费与库存总值表")
    @ApiImplicitParam(name = "zxSkResInOutStockAllAmt", value = "物资购进、消费与库存总值表entity", dataType = "ZxSkResInOutStockAllAmt")
    @RequireToken
    @PostMapping("/getZxSkResInOutStockAllAmtList")
    public ResponseEntity getZxSkResInOutStockAllAmtList(@RequestBody(required = false) ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        return zxSkResInOutStockAllAmtService.getZxSkResInOutStockAllAmtListByCondition(zxSkResInOutStockAllAmt);
    }

    @ApiOperation(value="查询详情物资购进、消费与库存总值表", notes="查询详情物资购进、消费与库存总值表")
    @ApiImplicitParam(name = "zxSkResInOutStockAllAmt", value = "物资购进、消费与库存总值表entity", dataType = "ZxSkResInOutStockAllAmt")
    @RequireToken
    @PostMapping("/getZxSkResInOutStockAllAmtDetail")
    public ResponseEntity getZxSkResInOutStockAllAmtDetail(@RequestBody(required = false) ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        return zxSkResInOutStockAllAmtService.getZxSkResInOutStockAllAmtDetail(zxSkResInOutStockAllAmt);
    }

    @ApiOperation(value="新增物资购进、消费与库存总值表", notes="新增物资购进、消费与库存总值表")
    @ApiImplicitParam(name = "zxSkResInOutStockAllAmt", value = "物资购进、消费与库存总值表entity", dataType = "ZxSkResInOutStockAllAmt")
    @RequireToken
    @PostMapping("/addZxSkResInOutStockAllAmt")
    public ResponseEntity addZxSkResInOutStockAllAmt(@RequestBody(required = false) ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        return zxSkResInOutStockAllAmtService.saveZxSkResInOutStockAllAmt(zxSkResInOutStockAllAmt);
    }

    @ApiOperation(value="更新物资购进、消费与库存总值表", notes="更新物资购进、消费与库存总值表")
    @ApiImplicitParam(name = "zxSkResInOutStockAllAmt", value = "物资购进、消费与库存总值表entity", dataType = "ZxSkResInOutStockAllAmt")
    @RequireToken
    @PostMapping("/updateZxSkResInOutStockAllAmt")
    public ResponseEntity updateZxSkResInOutStockAllAmt(@RequestBody(required = false) ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        return zxSkResInOutStockAllAmtService.updateZxSkResInOutStockAllAmt(zxSkResInOutStockAllAmt);
    }

    @ApiOperation(value="删除物资购进、消费与库存总值表", notes="删除物资购进、消费与库存总值表")
    @ApiImplicitParam(name = "zxSkResInOutStockAllAmtList", value = "物资购进、消费与库存总值表List", dataType = "List<ZxSkResInOutStockAllAmt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkResInOutStockAllAmt")
    public ResponseEntity batchDeleteUpdateZxSkResInOutStockAllAmt(@RequestBody(required = false) List<ZxSkResInOutStockAllAmt> zxSkResInOutStockAllAmtList) {
        return zxSkResInOutStockAllAmtService.batchDeleteUpdateZxSkResInOutStockAllAmt(zxSkResInOutStockAllAmtList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出物资购进、消费与库存总值表", notes="报表导出物资购进、消费与库存总值表")
    @ApiImplicitParam(name = "zxSkResInOutStockAllAmt", value = "设备台账entity", dataType = "ZxSkResInOutStockAllAmt")
    @PostMapping("/ureportZxSkResInOutStockAllAmt")
    public List<ZxSkResInOutStockAllAmt> ureportZxSkResInOutStockAllAmt(@RequestBody(required = false) ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        return zxSkResInOutStockAllAmtService.ureportZxSkResInOutStockAllAmt(zxSkResInOutStockAllAmt);
    }
    
    @ApiOperation(value="报表导出物资购进、消费与库存总值表", notes="报表导出物资购进、消费与库存总值表")
    @ApiImplicitParam(name = "zxSkResInOutStockAllAmt", value = "设备台账entity", dataType = "ZxSkResInOutStockAllAmt")
    @RequireToken
    @PostMapping("/ureportZxSkResInOutStockAllAmtIdle")
    public ResponseEntity ureportZxSkResInOutStockAllAmtIdle(@RequestBody(required = false) ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        return zxSkResInOutStockAllAmtService.ureportZxSkResInOutStockAllAmtIdle(zxSkResInOutStockAllAmt);
    }
}
