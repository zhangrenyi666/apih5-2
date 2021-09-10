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
import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import com.apih5.service.ZxSaFsSettlementService;

@RestController
public class ZxSaFsSettlementController {

    @Autowired(required = true)
    private ZxSaFsSettlementService zxSaFsSettlementService;

    @ApiOperation(value="查询附属类结算表", notes="查询附属类结算表")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算表entity", dataType = "ZxSaFsSettlement")
    @RequireToken
    @PostMapping("/getZxSaFsSettlementList")
    public ResponseEntity getZxSaFsSettlementList(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsSettlementService.getZxSaFsSettlementListByCondition(zxSaFsSettlement);
    }

    @ApiOperation(value="查询详情附属类结算表", notes="查询详情附属类结算表")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算表entity", dataType = "ZxSaFsSettlement")
    @RequireToken
    @PostMapping("/getZxSaFsSettlementDetail")
    public ResponseEntity getZxSaFsSettlementDetail(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsSettlementService.getZxSaFsSettlementDetail(zxSaFsSettlement);
    }

    @ApiOperation(value="新增附属类结算表", notes="新增附属类结算表")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算表entity", dataType = "ZxSaFsSettlement")
    @RequireToken
    @PostMapping("/addZxSaFsSettlement")
    public ResponseEntity addZxSaFsSettlement(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) throws Exception {
        return zxSaFsSettlementService.saveZxSaFsSettlement(zxSaFsSettlement);
    }

    @ApiOperation(value="更新附属类结算表", notes="更新附属类结算表")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算表entity", dataType = "ZxSaFsSettlement")
    @RequireToken
    @PostMapping("/updateZxSaFsSettlement")
    public ResponseEntity updateZxSaFsSettlement(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsSettlementService.updateZxSaFsSettlement(zxSaFsSettlement);
    }

    @ApiOperation(value="删除附属类结算表", notes="删除附属类结算表")
    @ApiImplicitParam(name = "zxSaFsSettlementList", value = "附属类结算表List", dataType = "List<ZxSaFsSettlement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaFsSettlement")
    public ResponseEntity batchDeleteUpdateZxSaFsSettlement(@RequestBody(required = false) List<ZxSaFsSettlement> zxSaFsSettlementList) throws Exception  {
        return zxSaFsSettlementService.batchDeleteUpdateZxSaFsSettlement(zxSaFsSettlementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="查询是否首次结算", notes="查询是否首次结算")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算表entity", dataType = "ZxSaFsSettlement")
    @RequireToken
    @PostMapping("/getZxSaFsSettlementCount")
    public ResponseEntity getZxSaFsSettlementCount(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsSettlementService.countByContract(zxSaFsSettlement);
    }

    @ApiOperation(value="结算单营改增一览查询", notes="结算单营改增一览查询")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算表entity", dataType = "ZxSaFsSettlement")
    @RequireToken
    @PostMapping("/getZxSaFsSettlementReport")
    public ResponseEntity getZxSaFsSettlementReport(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsSettlementService.getZxSaFsSettlementReport(zxSaFsSettlement);
    }

    @ApiOperation(value="添加附属类结算评审流程", notes="添加附属类结算评审流程")
    @ApiImplicitParam(name = "zxSaFsSettlement", value = "附属类结算表entity", dataType = "ZxSaFsSettlement")
    @RequireToken
    @PostMapping("/addZxSaFsSettlementApplyFlow")
    public ResponseEntity addZxSaFsSettlementApplyFlow(@RequestBody(required = false) ZxSaFsSettlement zxSaFsSettlement) {
        return zxSaFsSettlementService.addZxSaFsSettlementApplyFlow(zxSaFsSettlement);
    }

}
