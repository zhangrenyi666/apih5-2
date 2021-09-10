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
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.service.ZxCtSuppliesContractService;

@RestController
public class ZxCtSuppliesContractController {

    @Autowired(required = true)
    private ZxCtSuppliesContractService zxCtSuppliesContractService;

    @ApiOperation(value="查询物资管理类合同", notes="查询物资管理类合同")
    @ApiImplicitParam(name = "zxCtSuppliesContract", value = "物资管理类合同entity", dataType = "ZxCtSuppliesContract")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractList")
    public ResponseEntity getZxCtSuppliesContractList(@RequestBody(required = false) ZxCtSuppliesContract zxCtSuppliesContract) {
        return zxCtSuppliesContractService.getZxCtSuppliesContractListByCondition(zxCtSuppliesContract);
    }

    @ApiOperation(value="查询详情物资管理类合同", notes="查询详情物资管理类合同")
    @ApiImplicitParam(name = "zxCtSuppliesContract", value = "物资管理类合同entity", dataType = "ZxCtSuppliesContract")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractDetail")
    public ResponseEntity getZxCtSuppliesContractDetail(@RequestBody(required = false) ZxCtSuppliesContract zxCtSuppliesContract) {
        return zxCtSuppliesContractService.getZxCtSuppliesContractDetail(zxCtSuppliesContract);
    }

    @ApiOperation(value="新增物资管理类合同", notes="新增物资管理类合同")
    @ApiImplicitParam(name = "zxCtSuppliesContract", value = "物资管理类合同entity", dataType = "ZxCtSuppliesContract")
    @RequireToken
    @PostMapping("/addZxCtSuppliesContract")
    public ResponseEntity addZxCtSuppliesContract(@RequestBody(required = false) ZxCtSuppliesContract zxCtSuppliesContract) {
        return zxCtSuppliesContractService.saveZxCtSuppliesContract(zxCtSuppliesContract);
    }

    @ApiOperation(value="更新物资管理类合同", notes="更新物资管理类合同")
    @ApiImplicitParam(name = "zxCtSuppliesContract", value = "物资管理类合同entity", dataType = "ZxCtSuppliesContract")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContract")
    public ResponseEntity updateZxCtSuppliesContract(@RequestBody(required = false) ZxCtSuppliesContract zxCtSuppliesContract) {
        return zxCtSuppliesContractService.updateZxCtSuppliesContract(zxCtSuppliesContract);
    }

    @ApiOperation(value="删除物资管理类合同", notes="删除物资管理类合同")
    @ApiImplicitParam(name = "zxCtSuppliesContractList", value = "物资管理类合同List", dataType = "List<ZxCtSuppliesContract>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtSuppliesContract")
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContract(@RequestBody(required = false) List<ZxCtSuppliesContract> zxCtSuppliesContractList) {
        return zxCtSuppliesContractService.batchDeleteUpdateZxCtSuppliesContract(zxCtSuppliesContractList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="根据甲方乙方查询物资管理类合同", notes="根据甲方乙方查询物资管理类合同")
    @ApiImplicitParam(name = "zxCtSuppliesContract", value = "物资管理类合同entity", dataType = "ZxCtSuppliesContract")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractListByFirstId")
    public ResponseEntity getZxCtSuppliesContractListByFirstId(@RequestBody(required = false) ZxCtSuppliesContract zxCtSuppliesContract) {
        return zxCtSuppliesContractService.getZxCtSuppliesContractListByFirstId(zxCtSuppliesContract);
    }   
    
    @ApiOperation(value="查询物资管理类合同", notes="查询物资管理类合同")
    @ApiImplicitParam(name = "zxCtSuppliesContract", value = "物资管理类合同entity", dataType = "ZxCtSuppliesContract")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractListByOrgId")
    public ResponseEntity getZxCtSuppliesContractListByOrgId(@RequestBody(required = false) ZxCtSuppliesContract zxCtSuppliesContract) {
    	return zxCtSuppliesContractService.getZxCtSuppliesContractListByOrgId(zxCtSuppliesContract);
    }   

    @ApiOperation(value="更新物资管理类合同", notes="更新物资管理类合同")
    @ApiImplicitParam(name = "zxCtSuppliesContract", value = "物资管理类合同entity", dataType = "ZxCtSuppliesContract")
    @RequireToken
    @PostMapping("/updateZxCtSuppliesContractByContId")
    public ResponseEntity updateZxCtSuppliesContractByContId(@RequestBody(required = false) ZxCtSuppliesContract zxCtSuppliesContract) {
        return zxCtSuppliesContractService.updateZxCtSuppliesContractByContId(zxCtSuppliesContract);
    }  

    @ApiOperation(value="查询物资管理类合同（添加补充协议编号）", notes="查询物资管理类合同（添加补充协议编号）")
    @ApiImplicitParam(name = "zxCtSuppliesContract", value = "物资管理类合同entity", dataType = "ZxCtSuppliesContract")
    @RequireToken
    @PostMapping("/getZxCtSuppliesContractListAddAgreementNum")
    public ResponseEntity getZxCtSuppliesContractListAddAgreementNum(@RequestBody(required = false) ZxCtSuppliesContract zxCtSuppliesContract) {
        return zxCtSuppliesContractService.getZxCtSuppliesContractListAddAgreementNum(zxCtSuppliesContract);
    }    
}
