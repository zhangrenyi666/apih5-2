package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishAgreement;
import com.apih5.service.ZxCtSuppliesContrReplenishAgreementService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxCtSuppliesContrReplenishAgreementController {

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishAgreementService zxCtSuppliesContrReplenishAgreementService;

    @ApiOperation(value="查询物资管理类补充协议", notes="查询物资管理类补充协议")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishAgreementList")
    public ResponseEntity getZxCtSuppliesContrReplenishAgreementList(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        return zxCtSuppliesContrReplenishAgreementService.getZxCtSuppliesContrReplenishAgreementListByCondition(zxCtSuppliesContrReplenishAgreement);
    }

    @ApiOperation(value="查询详情物资管理类补充协议", notes="查询详情物资管理类补充协议")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishAgreementDetail")
    public ResponseEntity getZxCtSuppliesContrReplenishAgreementDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        return zxCtSuppliesContrReplenishAgreementService.getZxCtSuppliesContrReplenishAgreementDetail(zxCtSuppliesContrReplenishAgreement);
    }

    @ApiOperation(value="新增物资管理类补充协议", notes="新增物资管理类补充协议")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrReplenishAgreement")
    public ResponseEntity addZxCtSuppliesContrReplenishAgreement(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        return zxCtSuppliesContrReplenishAgreementService.saveZxCtSuppliesContrReplenishAgreement(zxCtSuppliesContrReplenishAgreement);
    }

    @ApiOperation(value="更新物资管理类补充协议", notes="更新物资管理类补充协议")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrReplenishAgreement")
    public ResponseEntity updateZxCtSuppliesContrReplenishAgreement(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        return zxCtSuppliesContrReplenishAgreementService.updateZxCtSuppliesContrReplenishAgreement(zxCtSuppliesContrReplenishAgreement);
    }

    @ApiOperation(value="删除物资管理类补充协议", notes="删除物资管理类补充协议")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreementList", value = "物资管理类补充协议List", dataType = "List<ZxCtSuppliesContrReplenishAgreement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrReplenishAgreement")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishAgreement(@RequestBody(required = false) List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList) {
        return zxCtSuppliesContrReplenishAgreementService.batchDeleteUpdateZxCtSuppliesContrReplenishAgreement(zxCtSuppliesContrReplenishAgreementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="更新物资管理类补充协议", notes="更新物资管理类补充协议")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrReplenishAgreementByContrId")
    public ResponseEntity updateZxCtSuppliesContrReplenishAgreementByContrId(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
    	return zxCtSuppliesContrReplenishAgreementService.updateZxCtSuppliesContrReplenishAgreementByContrId(zxCtSuppliesContrReplenishAgreement);
    }

    @ApiOperation(value="新增物资管理类补充协议", notes="新增物资管理类补充协议")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrReplenishAgreementByContrId")
    public ResponseEntity addZxCtSuppliesContrReplenishAgreementByContrId(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        return zxCtSuppliesContrReplenishAgreementService.saveZxCtSuppliesContrReplenishAgreementByContrId(zxCtSuppliesContrReplenishAgreement);
    }
    
    @ApiOperation(value="新增物资管理类补充协议明细", notes="新增物资管理类补充协议明细")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/addZxCtSupContrReplLeaseDetailBycontrAlterID")
    public ResponseEntity addZxCtSupContrReplLeaseDetailBycontrAlterID(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
    	return zxCtSuppliesContrReplenishAgreementService.saveZxCtSuppliesContrReplenishAgreementBycontrAlterID(zxCtSuppliesContrReplenishAgreement);
    }
    
    @ApiOperation(value="更改物资管理类补充协议明细", notes="更改物资管理类补充协议明细")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/updateZxCtSupContrReplLeaseDetailBycontrAlterID")
    public ResponseEntity updateZxCtSupContrReplLeaseDetailBycontrAlterID(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
    	return zxCtSuppliesContrReplenishAgreementService.updateZxCtSupContrReplLeaseDetailBycontrAlterID(zxCtSuppliesContrReplenishAgreement);
    }
//    
//    @ApiOperation(value="新增物资管理类补充协议明细", notes="新增物资管理类补充协议明细")
//    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
//    @RequireToken
//    @PostMapping("/addZxCtSuppliesContrReplenishAgreementByContrId")
//    public ResponseEntity addZxCtSuppliesContrReplenishAgreementByContrId(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
//    	return zxCtSuppliesContrReplenishAgreementService.saveZxCtSuppliesContrReplenishAgreementByContrId(zxCtSuppliesContrReplenishAgreement);
//    }

    @ApiOperation(value="查询物资管理类补充协议", notes="查询物资管理类补充协议")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishAgreementListByContrId")
    public ResponseEntity getZxCtSuppliesContrReplenishAgreementListByContrId(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        return zxCtSuppliesContrReplenishAgreementService.getZxCtSuppliesContrReplenishAgreementListByContrId(zxCtSuppliesContrReplenishAgreement);
    }

    @ApiOperation(value="获取物资管理类补充协议流程详情", notes="获取物资管理类补充协议流程详情")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContrReplenishFlowDetail")
    public ResponseEntity getZxCtSuppliesContrReplenishFlowDetail(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
        return zxCtSuppliesContrReplenishAgreementService.getZxCtSuppliesContrReplenishFlowDetail(zxCtSuppliesContrReplenishAgreement);
    }
    
    @ApiOperation(value="新增物资管理类补充协议明细流程", notes="新增物资管理类补充协议明细流程")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContrReplenishAgreementFlow")
    public ResponseEntity addZxCtSuppliesContrReplenishAgreementFlow(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
    	return zxCtSuppliesContrReplenishAgreementService.addZxCtSuppliesContrReplenishAgreementFlow(zxCtSuppliesContrReplenishAgreement);
    }
    
    @ApiOperation(value="更改物资管理类补充协议明细流程", notes="更改物资管理类补充协议明细流程")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreement", value = "物资管理类补充协议entity", dataType = "ZxCtSuppliesContrReplenishAgreement")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContrReplenishAgreementFlow")
    public ResponseEntity updateZxCtSuppliesContrReplenishAgreementFlow(@RequestBody(required = false) ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement) {
    	return zxCtSuppliesContrReplenishAgreementService.updateZxCtSuppliesContrReplenishAgreementFlow(zxCtSuppliesContrReplenishAgreement);
    }

    @ApiOperation(value="删除物资管理类补充协议及流程", notes="删除物资管理类补充协议及流程")
    @ApiImplicitParam(name = "zxCtSuppliesContrReplenishAgreementList", value = "物资管理类补充协议List", dataType = "List<ZxCtSuppliesContrReplenishAgreement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContrReplenishAgreementFlow")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishAgreementFlow(@RequestBody(required = false) List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList) {
        return zxCtSuppliesContrReplenishAgreementService.batchDeleteUpdateZxCtSuppliesContrReplenishAgreementFlow(zxCtSuppliesContrReplenishAgreementList);
    }
}
