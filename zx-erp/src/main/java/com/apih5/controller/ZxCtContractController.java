package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.service.ZxCtContractService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxCtContractController {

    @Autowired(required = true)
    private ZxCtContractService zxCtContractService;

    @ApiOperation(value="查询业主合同管理-业主合同台", notes="查询业主合同管理-业主合同台")
    @ApiImplicitParam(name = "zxCtContract", value = "业主合同管理-业主合同台entity", dataType = "ZxCtContract")
    @RequireToken
    @PostMapping("/getZxCtContractList")
    public ResponseEntity getZxCtContractList(@RequestBody(required = false) ZxCtContract zxCtContract) {
        return zxCtContractService.getZxCtContractListByCondition(zxCtContract);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台", notes="查询详情业主合同管理-业主合同台")
    @ApiImplicitParam(name = "zxCtContract", value = "业主合同管理-业主合同台entity", dataType = "ZxCtContract")
    @RequireToken
    @PostMapping("/getZxCtContractDetail")
    public ResponseEntity getZxCtContractDetail(@RequestBody(required = false) ZxCtContract zxCtContract) {
        return zxCtContractService.getZxCtContractDetail(zxCtContract);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台", notes="新增业主合同管理-业主合同台")
    @ApiImplicitParam(name = "zxCtContract", value = "业主合同管理-业主合同台entity", dataType = "ZxCtContract")
    @RequireToken
    @PostMapping("/addZxCtContract")
    public ResponseEntity addZxCtContract(@RequestBody(required = false) ZxCtContract zxCtContract) {
        return zxCtContractService.saveZxCtContract(zxCtContract);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台", notes="更新业主合同管理-业主合同台")
    @ApiImplicitParam(name = "zxCtContract", value = "业主合同管理-业主合同台entity", dataType = "ZxCtContract")
    @RequireToken
    @PostMapping("/updateZxCtContract")
    public ResponseEntity updateZxCtContract(@RequestBody(required = false) ZxCtContract zxCtContract) {
        return zxCtContractService.updateZxCtContract(zxCtContract);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台", notes="删除业主合同管理-业主合同台")
    @ApiImplicitParam(name = "zxCtContractList", value = "业主合同管理-业主合同台List", dataType = "List<ZxCtContract>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtContract")
    public ResponseEntity batchDeleteUpdateZxCtContract(@RequestBody(required = false) List<ZxCtContract> zxCtContractList) {
        return zxCtContractService.batchDeleteUpdateZxCtContract(zxCtContractList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="基本信息审核（反审核）", notes="基本信息审核（反审核）")
    @ApiImplicitParam(name = "zxCtContractList", value = "业主合同管理-业主合同台List", dataType = "List<ZxCtContract>")
    @RequireToken
    @PostMapping("/updateZxCtContractContrStatus")
    public ResponseEntity updateZxCtContractContrStatus(@RequestBody(required = false) ZxCtContract zxCtContract) {
        return zxCtContractService.updateZxCtContractContrStatus(zxCtContract);
    }
    
    @ApiOperation(value="根据已审核-执行中-已确认合同数量-已确认核定数量获取甲方合同", notes="根据已审核-执行中-已确认合同数量-已确认核定数量获取甲方合同")
    @ApiImplicitParam(name = "zxCtContractList", value = "业主合同管理-业主合同台List", dataType = "List<ZxCtContract>")
    @RequireToken
    @PostMapping("/getZxCtContractListByStatus")
    public ResponseEntity getZxCtContractListByStatus(@RequestBody(required = false) ZxCtContract zxCtContract) {
    	return zxCtContractService.getZxCtContractListByStatus(zxCtContract);
    }
    
    @ApiOperation(value="项目立项--修改完工审核及业主合同台账信息", notes="项目立项--修改完工审核及业主合同台账信息")
    @ApiImplicitParam(name = "zxCtContract", value = "业主合同管理-业主合同台", dataType = "ZxCtContract")
//    @RequireToken
    @PostMapping("/updateZxCtContractFinishInfo")
    public ResponseEntity updateZxCtContractFinishInfo(@RequestBody(required = false) ZxCtContract zxCtContract) {
    	return zxCtContractService.updateZxCtContractFinishInfo(zxCtContract);
    }
    
    @ApiOperation(value="查询业主合同管理-业主合同台账", notes="查询业主合同管理-业主合同台账")
    @ApiImplicitParam(name = "zxCtContract", value = "业主合同管理-业主合同台entity", dataType = "ZxCtContract")
    @RequireToken
    @PostMapping("/getZxCtContractListByOrgId")
    public ResponseEntity getZxCtContractListByOrgId(@RequestBody(required = false) ZxCtContract zxCtContract) {
        return zxCtContractService.getZxCtContractListByOrgId(zxCtContract);
    }

    @ApiOperation(value="查询业主合同管理-业主合同台账", notes="查询业主合同管理-业主合同台账")
    @ApiImplicitParam(name = "zxCtContract", value = "业主合同管理-业主合同台entity", dataType = "ZxCtContract")
    @RequireToken
    @PostMapping("/getZxCtContractListByCompanyId")
    public ResponseEntity getZxCtContractListByCompanyId(@RequestBody(required = false) ZxCtContract zxCtContract) {
        return zxCtContractService.getZxCtContractListByCompanyId(zxCtContract);
    }

}
