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
import com.apih5.mybatis.pojo.ZxCtEqContrSupply;
import com.apih5.service.ZxCtEqContrSupplyService;

@RestController
public class ZxCtEqContrSupplyController {

    @Autowired(required = true)
    private ZxCtEqContrSupplyService zxCtEqContrSupplyService;

    @ApiOperation(value="查询设备合同补充协议表", notes="查询设备合同补充协议表")
    @ApiImplicitParam(name = "zxCtEqContrSupply", value = "设备合同补充协议表entity", dataType = "ZxCtEqContrSupply")
    @RequireToken
    @PostMapping("/getZxCtEqContrSupplyList")
    public ResponseEntity getZxCtEqContrSupplyList(@RequestBody(required = false) ZxCtEqContrSupply zxCtEqContrSupply) {
        return zxCtEqContrSupplyService.getZxCtEqContrSupplyListByCondition(zxCtEqContrSupply);
    }

    @ApiOperation(value="查询详情设备合同补充协议表", notes="查询详情设备合同补充协议表")
    @ApiImplicitParam(name = "zxCtEqContrSupply", value = "设备合同补充协议表entity", dataType = "ZxCtEqContrSupply")
    @RequireToken
    @PostMapping("/getZxCtEqContrSupplyDetail")
    public ResponseEntity getZxCtEqContrSupplyDetail(@RequestBody(required = false) ZxCtEqContrSupply zxCtEqContrSupply) {
        return zxCtEqContrSupplyService.getZxCtEqContrSupplyDetail(zxCtEqContrSupply);
    }

    @ApiOperation(value="新增设备合同补充协议表", notes="新增设备合同补充协议表")
    @ApiImplicitParam(name = "zxCtEqContrSupply", value = "设备合同补充协议表entity", dataType = "ZxCtEqContrSupply")
    @RequireToken
    @PostMapping("/addZxCtEqContrSupply")
    public ResponseEntity addZxCtEqContrSupply(@RequestBody(required = false) ZxCtEqContrSupply zxCtEqContrSupply) {
        return zxCtEqContrSupplyService.saveZxCtEqContrSupply(zxCtEqContrSupply);
    }

    @ApiOperation(value="更新设备合同补充协议表", notes="更新设备合同补充协议表")
    @ApiImplicitParam(name = "zxCtEqContrSupply", value = "设备合同补充协议表entity", dataType = "ZxCtEqContrSupply")
    @RequireToken
    @PostMapping("/updateZxCtEqContrSupply")
    public ResponseEntity updateZxCtEqContrSupply(@RequestBody(required = false) ZxCtEqContrSupply zxCtEqContrSupply) {
        return zxCtEqContrSupplyService.updateZxCtEqContrSupply(zxCtEqContrSupply);
    }

    @ApiOperation(value="删除设备合同补充协议表", notes="删除设备合同补充协议表")
    @ApiImplicitParam(name = "zxCtEqContrSupplyList", value = "设备合同补充协议表List", dataType = "List<ZxCtEqContrSupply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtEqContrSupply")
    public ResponseEntity batchDeleteUpdateZxCtEqContrSupply(@RequestBody(required = false) List<ZxCtEqContrSupply> zxCtEqContrSupplyList) {
        return zxCtEqContrSupplyService.batchDeleteUpdateZxCtEqContrSupply(zxCtEqContrSupplyList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="根据合同id查询设备合同补充协议表", notes="根据合同id查询设备合同补充协议表")
    @ApiImplicitParam(name = "zxCtEqContrSupply", value = "设备合同补充协议表entity", dataType = "ZxCtEqContrSupply")
    @RequireToken
    @PostMapping("/getZxCtEqContrSupplyListBycontractID")
    public ResponseEntity getZxCtEqContrSupplyListBycontractID(@RequestBody(required = false) ZxCtEqContrSupply zxCtEqContrSupply) {
        return zxCtEqContrSupplyService.getZxCtEqContrSupplyListBycontractID(zxCtEqContrSupply);
    }

    @ApiOperation(value="生成补充协议编号=设备合同补充协议表", notes="生成补充协议编号=设备合同补充协议表")
    @ApiImplicitParam(name = "zxCtEqContrSupply", value = "设备合同补充协议表entity", dataType = "ZxCtEqContrSupply")
    @RequireToken
    @PostMapping("/generateZxCtEqContrSupplyContractNo")
    public ResponseEntity generateZxCtEqContrSupplyContractNo(@RequestBody(required = false) ZxCtEqContrSupply zxCtEqContrSupply) {
        return zxCtEqContrSupplyService.generateZxCtEqContrSupplyContractNo(zxCtEqContrSupply);
    }
    
    @ApiOperation(value="更新设备合同补充协议表", notes="更新设备合同补充协议表")
    @ApiImplicitParam(name = "zxCtEqContrSupply", value = "设备合同补充协议表entity", dataType = "ZxCtEqContrSupply")
    @RequireToken
    @PostMapping("/updateZxCtEqContrSupplyForContractTab")
    public ResponseEntity updateZxCtEqContrSupplyForContractTab(@RequestBody(required = false) ZxCtEqContrSupply zxCtEqContrSupply) {
        return zxCtEqContrSupplyService.updateZxCtEqContrSupplyForContractTab(zxCtEqContrSupply);
    }
}
