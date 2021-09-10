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
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettle;
import com.apih5.service.ZxSaOtherEquipPaySettleService;

@RestController
public class ZxSaOtherEquipPaySettleController {

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleService zxSaOtherEquipPaySettleService;

    @ApiOperation(value="查询其他类支付项结算", notes="查询其他类支付项结算")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettle", value = "其他类支付项结算entity", dataType = "ZxSaOtherEquipPaySettle")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipPaySettleList")
    public ResponseEntity getZxSaOtherEquipPaySettleList(@RequestBody(required = false) ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle) {
        return zxSaOtherEquipPaySettleService.getZxSaOtherEquipPaySettleListByCondition(zxSaOtherEquipPaySettle);
    }

    @ApiOperation(value="查询详情其他类支付项结算", notes="查询详情其他类支付项结算")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettle", value = "其他类支付项结算entity", dataType = "ZxSaOtherEquipPaySettle")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipPaySettleDetail")
    public ResponseEntity getZxSaOtherEquipPaySettleDetail(@RequestBody(required = false) ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle) {
        return zxSaOtherEquipPaySettleService.getZxSaOtherEquipPaySettleDetail(zxSaOtherEquipPaySettle);
    }

    @ApiOperation(value="新增其他类支付项结算", notes="新增其他类支付项结算")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettle", value = "其他类支付项结算entity", dataType = "ZxSaOtherEquipPaySettle")
    @RequireToken
    @PostMapping("/addZxSaOtherEquipPaySettle")
    public ResponseEntity addZxSaOtherEquipPaySettle(@RequestBody(required = false) ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle) {
        return zxSaOtherEquipPaySettleService.saveZxSaOtherEquipPaySettle(zxSaOtherEquipPaySettle);
    }

    @ApiOperation(value="更新其他类支付项结算", notes="更新其他类支付项结算")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettle", value = "其他类支付项结算entity", dataType = "ZxSaOtherEquipPaySettle")
    @RequireToken
    @PostMapping("/updateZxSaOtherEquipPaySettle")
    public ResponseEntity updateZxSaOtherEquipPaySettle(@RequestBody(required = false) ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle) {
        return zxSaOtherEquipPaySettleService.updateZxSaOtherEquipPaySettle(zxSaOtherEquipPaySettle);
    }

    @ApiOperation(value="删除其他类支付项结算", notes="删除其他类支付项结算")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettleList", value = "其他类支付项结算List", dataType = "List<ZxSaOtherEquipPaySettle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaOtherEquipPaySettle")
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipPaySettle(@RequestBody(required = false) List<ZxSaOtherEquipPaySettle> zxSaOtherEquipPaySettleList) {
        return zxSaOtherEquipPaySettleService.batchDeleteUpdateZxSaOtherEquipPaySettle(zxSaOtherEquipPaySettleList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
