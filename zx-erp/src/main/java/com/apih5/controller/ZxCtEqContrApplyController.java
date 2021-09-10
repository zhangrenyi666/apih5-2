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
import com.apih5.mybatis.pojo.ZxCtEqContrApply;
import com.apih5.service.ZxCtEqContrApplyService;

import cn.hutool.json.JSONObject;

@RestController
public class ZxCtEqContrApplyController {

    @Autowired(required = true)
    private ZxCtEqContrApplyService zxCtEqContrApplyService;

    @ApiOperation(value="查询设备管理合同评审表", notes="查询设备管理合同评审表")
    @ApiImplicitParam(name = "zxCtEqContrApply", value = "设备管理合同评审表entity", dataType = "ZxCtEqContrApply")
    @RequireToken
    @PostMapping("/getZxCtEqContrApplyList")
    public ResponseEntity getZxCtEqContrApplyList(@RequestBody(required = false) ZxCtEqContrApply zxCtEqContrApply) {
        return zxCtEqContrApplyService.getZxCtEqContrApplyListByCondition(zxCtEqContrApply);
    }

    @ApiOperation(value="查询详情设备管理合同评审表", notes="查询详情设备管理合同评审表")
    @ApiImplicitParam(name = "zxCtEqContrApply", value = "设备管理合同评审表entity", dataType = "ZxCtEqContrApply")
    @RequireToken
    @PostMapping("/getZxCtEqContrApplyDetail")
    public ResponseEntity getZxCtEqContrApplyDetail(@RequestBody(required = false) ZxCtEqContrApply zxCtEqContrApply) {
        return zxCtEqContrApplyService.getZxCtEqContrApplyDetail(zxCtEqContrApply);
    }

    @ApiOperation(value="新增设备管理合同评审表", notes="新增设备管理合同评审表")
    @ApiImplicitParam(name = "zxCtEqContrApply", value = "设备管理合同评审表entity", dataType = "ZxCtEqContrApply")
    @RequireToken
    @PostMapping("/addZxCtEqContrApply")
    public ResponseEntity addZxCtEqContrApply(@RequestBody(required = false) ZxCtEqContrApply zxCtEqContrApply) {
        return zxCtEqContrApplyService.saveZxCtEqContrApply(zxCtEqContrApply);
    }

    @ApiOperation(value="更新设备管理合同评审表", notes="更新设备管理合同评审表")
    @ApiImplicitParam(name = "zxCtEqContrApply", value = "设备管理合同评审表entity", dataType = "ZxCtEqContrApply")
    @RequireToken
    @PostMapping("/updateZxCtEqContrApply")
    public ResponseEntity updateZxCtEqContrApply(@RequestBody(required = false) ZxCtEqContrApply zxCtEqContrApply) {
        return zxCtEqContrApplyService.updateZxCtEqContrApply(zxCtEqContrApply);
    }

    @ApiOperation(value="删除设备管理合同评审表", notes="删除设备管理合同评审表")
    @ApiImplicitParam(name = "zxCtEqContrApplyList", value = "设备管理合同评审表List", dataType = "List<ZxCtEqContrApply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtEqContrApply")
    public ResponseEntity batchDeleteUpdateZxCtEqContrApply(@RequestBody(required = false) List<ZxCtEqContrApply> zxCtEqContrApplyList) {
        return zxCtEqContrApplyService.batchDeleteUpdateZxCtEqContrApply(zxCtEqContrApplyList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="发起设备管理合同评审时判断业主合同管理-业主合同台账是否审核完成", notes="发起设备管理合同评审时判断业主合同管理-业主合同台账是否审核完成")
    @ApiImplicitParam(name = "zxCtEqContrApply", value = "设备管理合同评审表entity", dataType = "ZxCtEqContrApply")
    @RequireToken
    @PostMapping("/appZxCtEqContrApplyJudgeZxCtContractByContractStatus")
    public ResponseEntity appZxCtEqContrApplyJudgeZxCtContractByContractStatus(@RequestBody(required = false) ZxCtEqContrApply zxCtEqContrApply) {
        return zxCtEqContrApplyService.appZxCtEqContrApplyJudgeZxCtContractByContractStatus(zxCtEqContrApply);
    }
    
    @ApiOperation(value="生成合同编号=设备管理合同评审表", notes="生成合同编号=设备管理合同评审表")
    @ApiImplicitParam(name = "zxCtEqContrApply", value = "设备管理合同评审表entity", dataType = "ZxCtEqContrApply")
    @RequireToken
    @PostMapping("/generateZxCtEqContrApplyContractNo")
    public ResponseEntity generateZxCtEqContrApplyContractNo(@RequestBody(required = false) ZxCtEqContrApply zxCtEqContrApply) {
        return zxCtEqContrApplyService.generateZxCtEqContrApplyContractNo(zxCtEqContrApply);
    }
    
    @ApiOperation(value="流程分支判断=设备管理合同评审表", notes="流程分支判断=设备管理合同评审表")
    @ApiImplicitParam(name = "zxCtEqContrApply", value = "设备管理合同评审表entity", dataType = "ZxCtEqContrApply")
    @RequireToken
    @PostMapping("/checkZxCtEqContrApplyByFlow")
    public JSONObject checkZxCtEqContrApplyByFlow(@RequestBody(required = false) ZxCtEqContrApply zxCtEqContrApply) {
        return zxCtEqContrApplyService.checkZxCtEqContrApplyByFlow(zxCtEqContrApply);
    }
    
}
