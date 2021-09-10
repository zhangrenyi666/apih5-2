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
import com.apih5.mybatis.pojo.ZxCtFsContract;
import com.apih5.service.ZxCtFsContractService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ZxCtFsContractController {

    @Autowired(required = true)
    private ZxCtFsContractService zxCtFsContractService;

    @ApiOperation(value="查询附属合同表", notes="查询附属合同表")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同表entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/getZxCtFsContractList")
    public ResponseEntity getZxCtFsContractList(@RequestBody(required = false) ZxCtFsContract zxCtFsContract) {
        return zxCtFsContractService.getZxCtFsContractListByCondition(zxCtFsContract);
    }

    @ApiOperation(value="查询详情附属合同表", notes="查询详情附属合同表")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同表entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/getZxCtFsContractDetail")
    public ResponseEntity getZxCtFsContractDetail(@RequestBody(required = false) ZxCtFsContract zxCtFsContract) {
        return zxCtFsContractService.getZxCtFsContractDetail(zxCtFsContract);
    }

    @ApiOperation(value="新增附属合同表", notes="新增附属合同表")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同表entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/addZxCtFsContract")
    public ResponseEntity addZxCtFsContract(@RequestBody(required = false) ZxCtFsContract zxCtFsContract) {
        return zxCtFsContractService.saveZxCtFsContract(zxCtFsContract);
    }

    @ApiOperation(value="更新附属合同表", notes="更新附属合同表")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同表entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/updateZxCtFsContract")
    public ResponseEntity updateZxCtFsContract(@RequestBody(required = false) ZxCtFsContract zxCtFsContract) {
        return zxCtFsContractService.updateZxCtFsContract(zxCtFsContract);
    }

    @ApiOperation(value="删除附属合同表", notes="删除附属合同表")
    @ApiImplicitParam(name = "zxCtFsContractList", value = "附属合同表List", dataType = "List<ZxCtFsContract>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtFsContract")
    public ResponseEntity batchDeleteUpdateZxCtFsContract(@RequestBody(required = false) List<ZxCtFsContract> zxCtFsContractList) {
        return zxCtFsContractService.batchDeleteUpdateZxCtFsContract(zxCtFsContractList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @ApiOperation(value="查询项目列表", notes="查询项目列表")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同表entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/getOrgList")
    public ResponseEntity getOrgList(@RequestBody(required = false) ZxCtFsContract zxCtFsContract) {
        return zxCtFsContractService.getOrg(zxCtFsContract);
    }

    @ApiOperation(value="导出附属合同列表", notes="导出附属合同列表")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/exportZxCtFsContract")
    public ResponseEntity exportZxCtFsContractReview(@RequestBody(required = false) ZxCtFsContract zxCtFsContract, HttpServletResponse response) {
        return zxCtFsContractService.exportContract(zxCtFsContract, response );
    }

    @ApiOperation(value="查询附属合同下拉", notes="查询附属合同下拉")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同表entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/getZxCtFsContractXlList")
    public ResponseEntity getZxCtFsContractXlList(@RequestBody(required = false) ZxCtFsContract zxCtFsContract) {
        return zxCtFsContractService.getZxCtFsContractList(zxCtFsContract);
    }

    @ApiOperation(value="查询附属合同运输类", notes="查询附属合同运输类")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同表entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/getYunShuContractList")
    public ResponseEntity getYunShuContractList(@RequestBody(required = false) ZxCtFsContract zxCtFsContract){
        return zxCtFsContractService.getYunShuZxCtFsContractList(zxCtFsContract);
    }

    @ApiOperation(value="查询附属合同其他类", notes="查询附属合同其他类")
    @ApiImplicitParam(name = "zxCtFsContract", value = "附属合同表entity", dataType = "ZxCtFsContract")
    @RequireToken
    @PostMapping("/getQiTaContractList")
    public ResponseEntity getQiTaContractList(@RequestBody(required = false) ZxCtFsContract zxCtFsContract){
        return zxCtFsContractService.getQiTaZxCtFsContractList(zxCtFsContract);
    }
}
