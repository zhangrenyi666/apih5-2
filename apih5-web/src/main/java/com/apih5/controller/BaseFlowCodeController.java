package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseFlowCode;
import com.apih5.service.BaseFlowCodeService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class BaseFlowCodeController {

    @Autowired(required = true)
    private BaseFlowCodeService baseFlowCodeService;

    @ApiOperation(value="查询流程节点Code", notes="查询流程节点Code")
    @ApiImplicitParam(name = "baseFlowCode", value = "流程节点Codeentity", dataType = "BaseFlowCode")
    @RequireToken
    @PostMapping("/getBaseFlowCodeList")
    public ResponseEntity getBaseFlowCodeList(@RequestBody(required = false) BaseFlowCode baseFlowCode) {
        return baseFlowCodeService.getBaseFlowCodeListByCondition(baseFlowCode);
    }

    @ApiOperation(value="新增流程节点Code", notes="新增流程节点Code")
    @ApiImplicitParam(name = "baseFlowCode", value = "流程节点Codeentity", dataType = "BaseFlowCode")
    @RequireToken
    @PostMapping("/addBaseFlowCode")
    public ResponseEntity addBaseFlowCode(@RequestBody(required = false) BaseFlowCode baseFlowCode) {
        return baseFlowCodeService.saveBaseFlowCode(baseFlowCode);
    }

    @ApiOperation(value="更新流程节点Code", notes="更新流程节点Code")
    @ApiImplicitParam(name = "baseFlowCode", value = "流程节点Codeentity", dataType = "BaseFlowCode")
    @RequireToken
    @PostMapping("/updateBaseFlowCode")
    public ResponseEntity updateBaseFlowCode(@RequestBody(required = false) BaseFlowCode baseFlowCode) {
        return baseFlowCodeService.updateBaseFlowCode(baseFlowCode);
    }

    @ApiOperation(value="删除流程节点Code", notes="删除流程节点Code")
    @ApiImplicitParam(name = "baseFlowCodeList", value = "流程节点CodeList", dataType = "List<BaseFlowCode>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseFlowCode")
    public ResponseEntity batchDeleteUpdateBaseFlowCode(@RequestBody(required = false) List<BaseFlowCode> baseFlowCodeList) {
        return baseFlowCodeService.batchDeleteUpdateBaseFlowCode(baseFlowCodeList);
    }

    @ApiOperation(value="导入XML文件", notes="导入XML文件")
    @ApiImplicitParam(name = "baseFlowCode", value = "流程节点Codeentity", dataType = "BaseFlowCode")
    @RequireToken
    @PostMapping("/baseFlowCodeImport")
    public ResponseEntity baseFlowCodeImport(@RequestBody(required = false) BaseFlowCode baseFlowCode) {
        return baseFlowCodeService.baseFlowCodeImport(baseFlowCode);
    }
    
    @ApiOperation(value="获取流程名称下拉集合", notes="获取流程名称下拉集合")
    @ApiImplicitParam(name = "baseFlowCode", value = "流程节点Codeentity", dataType = "BaseFlowCode")
    @RequireToken
    @PostMapping("/getFlowNameSelectList")
    public ResponseEntity getApih5FlowNameSelectList(@RequestBody(required = false) BaseFlowCode baseFlowCode) {
        return baseFlowCodeService.getFlowNameSelectList(baseFlowCode);
    }
    
    @ApiOperation(value="根据流程名称获取流程节点下拉集合", notes="根据流程名称获取流程节点下拉集合")
    @ApiImplicitParam(name = "baseFlowCode", value = "流程节点Codeentity", dataType = "BaseFlowCode")
    @RequireToken
    @PostMapping("/getNodeNameSelectListByFlowName")
    public ResponseEntity getNodeNameSelectListByFlowName(@RequestBody(required = false) BaseFlowCode baseFlowCode) {
        return baseFlowCodeService.getNodeNameSelectListByFlowName(baseFlowCode);
    }

}
