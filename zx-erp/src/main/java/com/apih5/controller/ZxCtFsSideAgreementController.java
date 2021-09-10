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
import com.apih5.mybatis.pojo.ZxCtFsSideAgreement;
import com.apih5.service.ZxCtFsSideAgreementService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ZxCtFsSideAgreementController {

    @Autowired(required = true)
    private ZxCtFsSideAgreementService zxCtFsSideAgreementService;

    @ApiOperation(value="查询附属合同补充协议", notes="查询附属合同补充协议")
    @ApiImplicitParam(name = "zxCtFsSideAgreement", value = "附属合同补充协议entity", dataType = "ZxCtFsSideAgreement")
    @RequireToken
    @PostMapping("/getZxCtFsSideAgreementList")
    public ResponseEntity getZxCtFsSideAgreementList(@RequestBody(required = false) ZxCtFsSideAgreement zxCtFsSideAgreement) {
        return zxCtFsSideAgreementService.getZxCtFsSideAgreementListByCondition(zxCtFsSideAgreement);
    }

    @ApiOperation(value="查询详情附属合同补充协议", notes="查询详情附属合同补充协议")
    @ApiImplicitParam(name = "zxCtFsSideAgreement", value = "附属合同补充协议entity", dataType = "ZxCtFsSideAgreement")
    @RequireToken
    @PostMapping("/getZxCtFsSideAgreementDetail")
    public ResponseEntity getZxCtFsSideAgreementDetail(@RequestBody(required = false) ZxCtFsSideAgreement zxCtFsSideAgreement) {
        return zxCtFsSideAgreementService.getZxCtFsSideAgreementDetail(zxCtFsSideAgreement);
    }

    @ApiOperation(value="新增附属合同补充协议", notes="新增附属合同补充协议")
    @ApiImplicitParam(name = "zxCtFsSideAgreement", value = "附属合同补充协议entity", dataType = "ZxCtFsSideAgreement")
    @RequireToken
    @PostMapping("/addZxCtFsSideAgreement")
    public ResponseEntity addZxCtFsSideAgreement(@RequestBody(required = false) ZxCtFsSideAgreement zxCtFsSideAgreement) {
        return zxCtFsSideAgreementService.saveZxCtFsSideAgreement(zxCtFsSideAgreement);
    }

    @ApiOperation(value="更新附属合同补充协议", notes="更新附属合同补充协议")
    @ApiImplicitParam(name = "zxCtFsSideAgreement", value = "附属合同补充协议entity", dataType = "ZxCtFsSideAgreement")
    @RequireToken
    @PostMapping("/updateZxCtFsSideAgreement")
    public ResponseEntity updateZxCtFsSideAgreement(@RequestBody(required = false) ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception {
        return zxCtFsSideAgreementService.updateZxCtFsSideAgreement(zxCtFsSideAgreement);
    }

    @ApiOperation(value="删除附属合同补充协议", notes="删除附属合同补充协议")
    @ApiImplicitParam(name = "zxCtFsSideAgreementList", value = "附属合同补充协议List", dataType = "List<ZxCtFsSideAgreement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtFsSideAgreement")
    public ResponseEntity batchDeleteUpdateZxCtFsSideAgreement(@RequestBody(required = false) List<ZxCtFsSideAgreement> zxCtFsSideAgreementList) {
        return zxCtFsSideAgreementService.batchDeleteUpdateZxCtFsSideAgreement(zxCtFsSideAgreementList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 更新附属合同补充协议并批量更新补充协议清单
     * @author suncg
     *
     * */
    @ApiOperation(value="更新附属合同补充协议并批量更新补充协议清单", notes="更新附属合同补充协议并批量更新补充协议清单")
    @ApiImplicitParam(name = "ZxCtFsSideAgreement", value = "附属合同补充协议List", dataType = "ZxCtFsSideAgreement")
    @RequireToken
    @PostMapping("/updateAgreementAndInventoryList")
    public ResponseEntity updateAgreementAndInventoryList(@RequestBody(required = false)ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception{
        return zxCtFsSideAgreementService.updateAgreementAndInventoryList(zxCtFsSideAgreement);
    }

    @ApiOperation(value="导出附属合同补充协议", notes="导出附属合同补充协议")
    @ApiImplicitParam(name = "zxCtFsSideAgreement", value = "附属合同补充协议entity", dataType = "ZxCtFsSideAgreement")
    @RequireToken
    @PostMapping("/exportZxCtFsSideAgreement")
    public ResponseEntity exportZxCtFsSideAgreement(@RequestBody(required = false) ZxCtFsSideAgreement zxCtFsSideAgreement, HttpServletResponse response) {
        return zxCtFsSideAgreementService.exportSideAgreement(zxCtFsSideAgreement,response);
    }

    @ApiOperation(value="获取附属合同补充协议编号", notes="获取附属合同补充协议编号")
    @ApiImplicitParam(name = "zxCtFsSideAgreement", value = "附属合同补充协议entity", dataType = "ZxCtFsSideAgreement")
    @RequireToken
    @PostMapping("/getContractNo")
    public ResponseEntity getContractNo(@RequestBody(required = false) ZxCtFsSideAgreement zxCtFsSideAgreement) {
        return zxCtFsSideAgreementService.getContractNo(zxCtFsSideAgreement);
    }

}
