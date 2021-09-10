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
import com.apih5.mybatis.pojo.ZxCtOtherSupplyAgreement;
import com.apih5.service.ZxCtOtherSupplyAgreementService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ZxCtOtherSupplyAgreementController {

    @Autowired(required = true)
    private ZxCtOtherSupplyAgreementService zxCtOtherSupplyAgreementService;

    @ApiOperation(value="查询其他合同补充协议", notes="查询其他合同补充协议")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreement", value = "其他合同补充协议entity", dataType = "ZxCtOtherSupplyAgreement")
    @RequireToken
    @PostMapping("/getZxCtOtherSupplyAgreementList")
    public ResponseEntity getZxCtOtherSupplyAgreementList(@RequestBody(required = false) ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        return zxCtOtherSupplyAgreementService.getZxCtOtherSupplyAgreementListByCondition(zxCtOtherSupplyAgreement);
    }

    @ApiOperation(value="查询详情其他合同补充协议", notes="查询详情其他合同补充协议")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreement", value = "其他合同补充协议entity", dataType = "ZxCtOtherSupplyAgreement")
    @RequireToken
    @PostMapping("/getZxCtOtherSupplyAgreementDetail")
    public ResponseEntity getZxCtOtherSupplyAgreementDetail(@RequestBody(required = false) ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        return zxCtOtherSupplyAgreementService.getZxCtOtherSupplyAgreementDetail(zxCtOtherSupplyAgreement);
    }

    @ApiOperation(value="新增其他合同补充协议", notes="新增其他合同补充协议")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreement", value = "其他合同补充协议entity", dataType = "ZxCtOtherSupplyAgreement")
    @RequireToken
    @PostMapping("/addZxCtOtherSupplyAgreement")
    public ResponseEntity addZxCtOtherSupplyAgreement(@RequestBody(required = false) ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        return zxCtOtherSupplyAgreementService.saveZxCtOtherSupplyAgreement(zxCtOtherSupplyAgreement);
    }

    @ApiOperation(value="更新其他合同补充协议", notes="更新其他合同补充协议")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreement", value = "其他合同补充协议entity", dataType = "ZxCtOtherSupplyAgreement")
    @RequireToken
    @PostMapping("/updateZxCtOtherSupplyAgreement")
    public ResponseEntity updateZxCtOtherSupplyAgreement(@RequestBody(required = false) ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        return zxCtOtherSupplyAgreementService.updateZxCtOtherSupplyAgreement(zxCtOtherSupplyAgreement);
    }

    @ApiOperation(value="删除其他合同补充协议", notes="删除其他合同补充协议")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreementList", value = "其他合同补充协议List", dataType = "List<ZxCtOtherSupplyAgreement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtOtherSupplyAgreement")
    public ResponseEntity batchDeleteUpdateZxCtOtherSupplyAgreement(@RequestBody(required = false) List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList) {
        return zxCtOtherSupplyAgreementService.batchDeleteUpdateZxCtOtherSupplyAgreement(zxCtOtherSupplyAgreementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="其他合同补充协议评审申请", notes="其他合同补充协议评审申请")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreement", value = "其他合同补充协议entity", dataType = "ZxCtOtherSupplyAgreement")
    @RequireToken
    @PostMapping("/zxCtOtherSupplyAgreementReviewApply")
    public ResponseEntity zxCtOtherSupplyAgreementReviewApply(@RequestBody(required = false) ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        return zxCtOtherSupplyAgreementService.zxCtOtherSupplyAgreementReviewApply(zxCtOtherSupplyAgreement);
    }

    @ApiOperation(value="其他合同补充协议评审申请之前校验", notes="其他合同补充协议评审申请之前校验")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreement", value = "其他合同补充协议entity", dataType = "ZxCtOtherSupplyAgreement")
    @RequireToken
    @PostMapping("/zxCtOtherSupplyAgreementReviewApplyCheck")
    public ResponseEntity zxCtOtherSupplyAgreementReviewApplyCheck(@RequestBody(required = false) ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        return zxCtOtherSupplyAgreementService.zxCtOtherSupplyAgreementReviewApplyCheck(zxCtOtherSupplyAgreement);
    }


    @ApiOperation(value="根据合同编号自动生成其他合同补充协议编号", notes="根据合同编号自动生成其他合同补充协议编号")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreement", value = "其他合同补充协议entity", dataType = "ZxCtOtherSupplyAgreement")
    @RequireToken
    @PostMapping("/getZxCtOtherSupplyAgreementContractNo")
    public ResponseEntity getZxCtOtherSupplyAgreementContractNo(@RequestBody(required = false) ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        return zxCtOtherSupplyAgreementService.getZxCtOtherSupplyAgreementContractNo(zxCtOtherSupplyAgreement);
    }

    @ApiOperation(value="其他合同补充协议导出", notes="其他合同补充协议导出")
    @ApiImplicitParam(name = "zxCtOtherSupplyAgreement", value = "其他合同补充协议entity", dataType = "ZxCtOtherSupplyAgreement")
    @RequireToken
    @PostMapping("/exportZxCtOtherSupplyAgreement")
    public void exportZxCtOtherSupplyAgreement(@RequestBody(required = false) ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement,HttpServletResponse response) {
        zxCtOtherSupplyAgreementService.exportZxCtOtherSupplyAgreement(zxCtOtherSupplyAgreement,response);
    }
}
