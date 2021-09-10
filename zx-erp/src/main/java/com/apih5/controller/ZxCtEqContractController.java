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
import com.apih5.mybatis.pojo.ZxCtEqContract;
import com.apih5.service.ZxCtEqContractService;

@RestController
public class ZxCtEqContractController {

    @Autowired(required = true)
    private ZxCtEqContractService zxCtEqContractService;

    @ApiOperation(value="查询设备合同台账表", notes="查询设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContract", value = "设备合同台账表entity", dataType = "ZxCtEqContract")
    @RequireToken
    @PostMapping("/getZxCtEqContractList")
    public ResponseEntity getZxCtEqContractList(@RequestBody(required = false) ZxCtEqContract zxCtEqContract) {
        return zxCtEqContractService.getZxCtEqContractListByCondition(zxCtEqContract);
    }

    @ApiOperation(value="查询详情设备合同台账表", notes="查询详情设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContract", value = "设备合同台账表entity", dataType = "ZxCtEqContract")
    @RequireToken
    @PostMapping("/getZxCtEqContractDetail")
    public ResponseEntity getZxCtEqContractDetail(@RequestBody(required = false) ZxCtEqContract zxCtEqContract) {
        return zxCtEqContractService.getZxCtEqContractDetail(zxCtEqContract);
    }

    @ApiOperation(value="新增设备合同台账表", notes="新增设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContract", value = "设备合同台账表entity", dataType = "ZxCtEqContract")
    @RequireToken
    @PostMapping("/addZxCtEqContract")
    public ResponseEntity addZxCtEqContract(@RequestBody(required = false) ZxCtEqContract zxCtEqContract) {
        return zxCtEqContractService.saveZxCtEqContract(zxCtEqContract);
    }

    @ApiOperation(value="更新设备合同台账表", notes="更新设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContract", value = "设备合同台账表entity", dataType = "ZxCtEqContract")
    @RequireToken
    @PostMapping("/updateZxCtEqContract")
    public ResponseEntity updateZxCtEqContract(@RequestBody(required = false) ZxCtEqContract zxCtEqContract) {
        return zxCtEqContractService.updateZxCtEqContract(zxCtEqContract);
    }

    @ApiOperation(value="删除设备合同台账表", notes="删除设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContractList", value = "设备合同台账表List", dataType = "List<ZxCtEqContract>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtEqContract")
    public ResponseEntity batchDeleteUpdateZxCtEqContract(@RequestBody(required = false) List<ZxCtEqContract> zxCtEqContractList) {
        return zxCtEqContractService.batchDeleteUpdateZxCtEqContract(zxCtEqContractList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="根据项目id查询设备合同台账表==仅限设备结算时使用的", notes="根据项目id查询设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContract", value = "设备合同台账表entity", dataType = "ZxCtEqContract")
    @RequireToken
    @PostMapping("/getZxCtEqContractListByOrgId")
    public ResponseEntity getZxCtEqContractListByOrgId(@RequestBody(required = false) ZxCtEqContract zxCtEqContract) {
        return zxCtEqContractService.getZxCtEqContractListByOrgId(zxCtEqContract);
    }
    
    @ApiOperation(value="恢复执行设备合同台账表", notes="恢复执行设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContract", value = "设备合同台账表entity", dataType = "ZxCtEqContract")
    @RequireToken
    @PostMapping("/recoverZxCtEqContract")
    public ResponseEntity recoverZxCtEqContract(@RequestBody(required = false) ZxCtEqContract zxCtEqContract) {
        return zxCtEqContractService.recoverZxCtEqContract(zxCtEqContract);
    }
    
    @ApiOperation(value="设备购置查询是设备采购类的设备合同台账表", notes="设备购置查询是设备采购类的设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContract", value = "设备合同台账表entity", dataType = "ZxCtEqContract")
    @RequireToken
    @PostMapping("/getZxCtEqContractListForEqMan")
    public ResponseEntity getZxCtEqContractListForEqMan(@RequestBody(required = false) ZxCtEqContract zxCtEqContract) {
        return zxCtEqContractService.getZxCtEqContractListForEqMan(zxCtEqContract);
    }
    
    @ApiOperation(value="租赁设备管理查询是设备购置类的设备合同台账表", notes="租赁设备管理查询是设备购置类的设备合同台账表")
    @ApiImplicitParam(name = "zxCtEqContract", value = "设备合同台账表entity", dataType = "ZxCtEqContract")
    @RequireToken
    @PostMapping("/getZxCtEqContractListForOuterEquip")
    public ResponseEntity getZxCtEqContractListForOuterEquip(@RequestBody(required = false) ZxCtEqContract zxCtEqContract) {
        return zxCtEqContractService.getZxCtEqContractListForOuterEquip(zxCtEqContract);
    }
}
