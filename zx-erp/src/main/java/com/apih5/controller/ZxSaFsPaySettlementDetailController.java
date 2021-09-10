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
import com.apih5.mybatis.pojo.ZxSaFsPaySettlementDetail;
import com.apih5.service.ZxSaFsPaySettlementDetailService;

@RestController
public class ZxSaFsPaySettlementDetailController {

    @Autowired(required = true)
    private ZxSaFsPaySettlementDetailService zxSaFsPaySettlementDetailService;

    @ApiOperation(value="查询附属类支付项结算明细表", notes="查询附属类支付项结算明细表")
    @ApiImplicitParam(name = "zxSaFsPaySettlementDetail", value = "附属类支付项结算明细表entity", dataType = "ZxSaFsPaySettlementDetail")
    @RequireToken
    @PostMapping("/getZxSaFsPaySettlementDetailList")
    public ResponseEntity getZxSaFsPaySettlementDetailList(@RequestBody(required = false) ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) {
        return zxSaFsPaySettlementDetailService.getZxSaFsPaySettlementDetailListByCondition(zxSaFsPaySettlementDetail);
    }

    @ApiOperation(value="查询详情附属类支付项结算明细表", notes="查询详情附属类支付项结算明细表")
    @ApiImplicitParam(name = "zxSaFsPaySettlementDetail", value = "附属类支付项结算明细表entity", dataType = "ZxSaFsPaySettlementDetail")
    @RequireToken
    @PostMapping("/getZxSaFsPaySettlementDetailDetail")
    public ResponseEntity getZxSaFsPaySettlementDetailDetail(@RequestBody(required = false) ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) {
        return zxSaFsPaySettlementDetailService.getZxSaFsPaySettlementDetailDetail(zxSaFsPaySettlementDetail);
    }

    @ApiOperation(value="新增附属类支付项结算明细表", notes="新增附属类支付项结算明细表")
    @ApiImplicitParam(name = "zxSaFsPaySettlementDetail", value = "附属类支付项结算明细表entity", dataType = "ZxSaFsPaySettlementDetail")
    @RequireToken
    @PostMapping("/addZxSaFsPaySettlementDetail")
    public ResponseEntity addZxSaFsPaySettlementDetail(@RequestBody(required = false) ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception {
        return zxSaFsPaySettlementDetailService.saveZxSaFsPaySettlementDetail(zxSaFsPaySettlementDetail);
    }

    @ApiOperation(value="更新附属类支付项结算明细表", notes="更新附属类支付项结算明细表")
    @ApiImplicitParam(name = "zxSaFsPaySettlementDetail", value = "附属类支付项结算明细表entity", dataType = "ZxSaFsPaySettlementDetail")
    @RequireToken
    @PostMapping("/updateZxSaFsPaySettlementDetail")
    public ResponseEntity updateZxSaFsPaySettlementDetail(@RequestBody(required = false) ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception {
        return zxSaFsPaySettlementDetailService.updateZxSaFsPaySettlementDetail(zxSaFsPaySettlementDetail);
    }

    @ApiOperation(value="删除附属类支付项结算明细表", notes="删除附属类支付项结算明细表")
    @ApiImplicitParam(name = "zxSaFsPaySettlementDetailList", value = "附属类支付项结算明细表List", dataType = "List<ZxSaFsPaySettlementDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaFsPaySettlementDetail")
    public ResponseEntity batchDeleteUpdateZxSaFsPaySettlementDetail(@RequestBody(required = false) List<ZxSaFsPaySettlementDetail> zxSaFsPaySettlementDetailList) throws Exception{
        return zxSaFsPaySettlementDetailService.batchDeleteUpdateZxSaFsPaySettlementDetail(zxSaFsPaySettlementDetailList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
