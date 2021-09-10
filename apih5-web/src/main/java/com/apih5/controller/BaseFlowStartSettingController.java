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
import com.apih5.mybatis.pojo.BaseFlowStartSetting;
import com.apih5.service.BaseFlowStartSettingService;

@RestController
public class BaseFlowStartSettingController {

    @Autowired(required = true)
    private BaseFlowStartSettingService baseFlowStartSettingService;

    @ApiOperation(value="查询流程发起人所有节点", notes="查询流程发起人所有节点")
    @ApiImplicitParam(name = "baseFlowStartSetting", value = "流程发起人所有节点entity", dataType = "BaseFlowStartSetting")
    @RequireToken
    @PostMapping("/getBaseFlowStartSettingList")
    public ResponseEntity getBaseFlowStartSettingList(@RequestBody(required = false) BaseFlowStartSetting baseFlowStartSetting) {
        return baseFlowStartSettingService.getBaseFlowStartSettingListByCondition(baseFlowStartSetting);
    }

    @ApiOperation(value="新增流程发起人所有节点", notes="新增流程发起人所有节点")
    @ApiImplicitParam(name = "baseFlowStartSetting", value = "流程发起人所有节点entity", dataType = "BaseFlowStartSetting")
    @RequireToken
    @PostMapping("/addBaseFlowStartSetting")
    public ResponseEntity addBaseFlowStartSetting(@RequestBody(required = false) BaseFlowStartSetting baseFlowStartSetting) {
        return baseFlowStartSettingService.saveBaseFlowStartSetting(baseFlowStartSetting);
    }

    @ApiOperation(value="更新流程发起人所有节点", notes="更新流程发起人所有节点")
    @ApiImplicitParam(name = "baseFlowStartSetting", value = "流程发起人所有节点entity", dataType = "BaseFlowStartSetting")
    @RequireToken
    @PostMapping("/updateBaseFlowStartSetting")
    public ResponseEntity updateBaseFlowStartSetting(@RequestBody(required = false) BaseFlowStartSetting baseFlowStartSetting) {
        return baseFlowStartSettingService.updateBaseFlowStartSetting(baseFlowStartSetting);
    }

    @ApiOperation(value="删除流程发起人所有节点", notes="删除流程发起人所有节点")
    @ApiImplicitParam(name = "baseFlowStartSettingList", value = "流程发起人所有节点List", dataType = "List<BaseFlowStartSetting>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseFlowStartSetting")
    public ResponseEntity batchDeleteUpdateBaseFlowStartSetting(@RequestBody(required = false) List<BaseFlowStartSetting> baseFlowStartSettingList) {
        return baseFlowStartSettingService.batchDeleteUpdateBaseFlowStartSetting(baseFlowStartSettingList);
    }

    @ApiOperation(value="查询流程发起人所有节点", notes="查询流程发起人所有节点")
    @ApiImplicitParam(name = "baseFlowStartSetting", value = "流程发起人所有节点entity", dataType = "BaseFlowStartSetting")
    @RequireToken
    @PostMapping("/getBaseFlowStartSettingListByFlow")
    public ResponseEntity getBaseFlowStartSettingListByFlow(@RequestBody(required = false) BaseFlowStartSetting baseFlowStartSetting) {
        return baseFlowStartSettingService.getBaseFlowStartSettingListByFlow(baseFlowStartSetting);
    }
    
    @ApiOperation(value="新增流程发起人所有节点", notes="新增流程发起人所有节点")
    @ApiImplicitParam(name = "baseFlowStartSetting", value = "流程发起人所有节点entity", dataType = "BaseFlowStartSetting")
    @RequireToken
    @PostMapping("/addBaseFlowStartSettingByFlow")
    public ResponseEntity addBaseFlowStartSettingByFlow(@RequestBody(required = false) BaseFlowStartSetting baseFlowStartSetting) {
        return baseFlowStartSettingService.saveBaseFlowStartSettingByFlow(baseFlowStartSetting);
    }
}
