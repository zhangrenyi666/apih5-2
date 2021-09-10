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
import com.apih5.mybatis.pojo.ZxSaEquipSettleAudit;
import com.apih5.service.ZxSaEquipSettleAuditService;

@RestController
public class ZxSaEquipSettleAuditController {

    @Autowired(required = true)
    private ZxSaEquipSettleAuditService zxSaEquipSettleAuditService;

    @ApiOperation(value="查询结算单表", notes="查询结算单表")
    @ApiImplicitParam(name = "zxSaEquipSettleAudit", value = "结算单表entity", dataType = "ZxSaEquipSettleAudit")
    @RequireToken
    @PostMapping("/getZxSaEquipSettleAuditList")
    public ResponseEntity getZxSaEquipSettleAuditList(@RequestBody(required = false) ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        return zxSaEquipSettleAuditService.getZxSaEquipSettleAuditListByCondition(zxSaEquipSettleAudit);
    }

    @ApiOperation(value="查询详情结算单表", notes="查询详情结算单表")
    @ApiImplicitParam(name = "zxSaEquipSettleAudit", value = "结算单表entity", dataType = "ZxSaEquipSettleAudit")
    @RequireToken
    @PostMapping("/getZxSaEquipSettleAuditDetail")
    public ResponseEntity getZxSaEquipSettleAuditDetail(@RequestBody(required = false) ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        return zxSaEquipSettleAuditService.getZxSaEquipSettleAuditDetail(zxSaEquipSettleAudit);
    }

    @ApiOperation(value="新增结算单表", notes="新增结算单表")
    @ApiImplicitParam(name = "zxSaEquipSettleAudit", value = "结算单表entity", dataType = "ZxSaEquipSettleAudit")
    @RequireToken
    @PostMapping("/addZxSaEquipSettleAudit")
    public ResponseEntity addZxSaEquipSettleAudit(@RequestBody(required = false) ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        return zxSaEquipSettleAuditService.saveZxSaEquipSettleAudit(zxSaEquipSettleAudit);
    }

    @ApiOperation(value="更新结算单表", notes="更新结算单表")
    @ApiImplicitParam(name = "zxSaEquipSettleAudit", value = "结算单表entity", dataType = "ZxSaEquipSettleAudit")
    @RequireToken
    @PostMapping("/updateZxSaEquipSettleAudit")
    public ResponseEntity updateZxSaEquipSettleAudit(@RequestBody(required = false) ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        return zxSaEquipSettleAuditService.updateZxSaEquipSettleAudit(zxSaEquipSettleAudit);
    }
    
    @ApiOperation(value="更新结算单表for流程", notes="更新结算单表for流程")
    @ApiImplicitParam(name = "zxSaEquipSettleAudit", value = "结算单表entity", dataType = "ZxSaEquipSettleAudit")
    @RequireToken
    @PostMapping("/updateZxSaEquipSettleAuditForFlow")
    public ResponseEntity updateZxSaEquipSettleAuditForFlow(@RequestBody(required = false) ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        return zxSaEquipSettleAuditService.updateZxSaEquipSettleAuditForFlow(zxSaEquipSettleAudit);
    }

    @ApiOperation(value="删除结算单表", notes="删除结算单表")
    @ApiImplicitParam(name = "zxSaEquipSettleAuditList", value = "结算单表List", dataType = "List<ZxSaEquipSettleAudit>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaEquipSettleAudit")
    public ResponseEntity batchDeleteUpdateZxSaEquipSettleAudit(@RequestBody(required = false) List<ZxSaEquipSettleAudit> zxSaEquipSettleAuditList) {
        return zxSaEquipSettleAuditService.batchDeleteUpdateZxSaEquipSettleAudit(zxSaEquipSettleAuditList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="新增结算单表--结算编号和结算签认单编号的自动生成接口=改成前成编号，后台只给返回序列号", notes="新增结算单表--结算编号和结算签认单编号的自动生成接口=改成前成编号，后台只给返回序列号")
    @ApiImplicitParam(name = "zxSaEquipSettleAudit", value = "结算单表entity", dataType = "ZxSaEquipSettleAudit")
    @RequireToken
    @PostMapping("/generateZxSaEquipSettleAuditAutoNum")
    public ResponseEntity generateZxSaEquipSettleAuditAutoNum(@RequestBody(required = false) ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        return zxSaEquipSettleAuditService.generateZxSaEquipSettleAuditAutoNum(zxSaEquipSettleAudit);
    }
    
    @ApiOperation(value="营改增==查询详情结算单表", notes="营改增==查询详情结算单表")
    @ApiImplicitParam(name = "zxSaEquipSettleAudit", value = "结算单表entity", dataType = "ZxSaEquipSettleAudit")
    @RequireToken
    @PostMapping("/taxZxSaEquipSettleAudit")
    public ResponseEntity taxZxSaEquipSettleAudit(@RequestBody(required = false) ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        return zxSaEquipSettleAuditService.taxZxSaEquipSettleAudit (zxSaEquipSettleAudit);
    }
    
}