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
import com.apih5.mybatis.pojo.ZxSaEquipPaySettle;
import com.apih5.service.ZxSaEquipPaySettleService;

@RestController
public class ZxSaEquipPaySettleController {

    @Autowired(required = true)
    private ZxSaEquipPaySettleService zxSaEquipPaySettleService;

    @ApiOperation(value="查询支付项结算表", notes="查询支付项结算表")
    @ApiImplicitParam(name = "zxSaEquipPaySettle", value = "支付项结算表entity", dataType = "ZxSaEquipPaySettle")
    @RequireToken
    @PostMapping("/getZxSaEquipPaySettleList")
    public ResponseEntity getZxSaEquipPaySettleList(@RequestBody(required = false) ZxSaEquipPaySettle zxSaEquipPaySettle) {
        return zxSaEquipPaySettleService.getZxSaEquipPaySettleListByCondition(zxSaEquipPaySettle);
    }

    @ApiOperation(value="查询详情支付项结算表", notes="查询详情支付项结算表")
    @ApiImplicitParam(name = "zxSaEquipPaySettle", value = "支付项结算表entity", dataType = "ZxSaEquipPaySettle")
    @RequireToken
    @PostMapping("/getZxSaEquipPaySettleDetail")
    public ResponseEntity getZxSaEquipPaySettleDetail(@RequestBody(required = false) ZxSaEquipPaySettle zxSaEquipPaySettle) {
        return zxSaEquipPaySettleService.getZxSaEquipPaySettleDetail(zxSaEquipPaySettle);
    }

    @ApiOperation(value="新增支付项结算表", notes="新增支付项结算表")
    @ApiImplicitParam(name = "zxSaEquipPaySettle", value = "支付项结算表entity", dataType = "ZxSaEquipPaySettle")
    @RequireToken
    @PostMapping("/addZxSaEquipPaySettle")
    public ResponseEntity addZxSaEquipPaySettle(@RequestBody(required = false) ZxSaEquipPaySettle zxSaEquipPaySettle) {
        return zxSaEquipPaySettleService.saveZxSaEquipPaySettle(zxSaEquipPaySettle);
    }

    @ApiOperation(value="更新支付项结算表", notes="更新支付项结算表")
    @ApiImplicitParam(name = "zxSaEquipPaySettle", value = "支付项结算表entity", dataType = "ZxSaEquipPaySettle")
    @RequireToken
    @PostMapping("/updateZxSaEquipPaySettle")
    public ResponseEntity updateZxSaEquipPaySettle(@RequestBody(required = false) ZxSaEquipPaySettle zxSaEquipPaySettle) {
        return zxSaEquipPaySettleService.updateZxSaEquipPaySettle(zxSaEquipPaySettle);
    }

    @ApiOperation(value="删除支付项结算表", notes="删除支付项结算表")
    @ApiImplicitParam(name = "zxSaEquipPaySettleList", value = "支付项结算表List", dataType = "List<ZxSaEquipPaySettle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaEquipPaySettle")
    public ResponseEntity batchDeleteUpdateZxSaEquipPaySettle(@RequestBody(required = false) List<ZxSaEquipPaySettle> zxSaEquipPaySettleList) {
        return zxSaEquipPaySettleService.batchDeleteUpdateZxSaEquipPaySettle(zxSaEquipPaySettleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
