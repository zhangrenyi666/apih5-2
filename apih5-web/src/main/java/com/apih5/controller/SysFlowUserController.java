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
import com.apih5.mybatis.pojo.SysFlowUser;
import com.apih5.service.SysFlowUserService;

@RestController
public class SysFlowUserController {

    @Autowired(required = true)
    private SysFlowUserService sysFlowUserService;

    @ApiOperation(value="查询流程用户选择表", notes="查询流程用户选择表")
    @ApiImplicitParam(name = "sysFlowUser", value = "流程用户选择表entity", dataType = "SysFlowUser")
    @RequireToken
    @PostMapping("/getSysFlowUserList")
    public ResponseEntity getSysFlowUserList(@RequestBody(required = false) SysFlowUser sysFlowUser) {
        return sysFlowUserService.getSysFlowUserListByCondition(sysFlowUser);
    }

    @ApiOperation(value="查询详情流程用户选择表", notes="查询详情流程用户选择表")
    @ApiImplicitParam(name = "sysFlowUser", value = "流程用户选择表entity", dataType = "SysFlowUser")
    @RequireToken
    @PostMapping("/getSysFlowUserDetail")
    public ResponseEntity getSysFlowUserDetail(@RequestBody(required = false) SysFlowUser sysFlowUser) {
        return sysFlowUserService.getSysFlowUserDetail(sysFlowUser);
    }

    @ApiOperation(value="新增流程用户选择表", notes="新增流程用户选择表")
    @ApiImplicitParam(name = "sysFlowUser", value = "流程用户选择表entity", dataType = "SysFlowUser")
    @RequireToken
    @PostMapping("/addSysFlowUser")
    public ResponseEntity addSysFlowUser(@RequestBody(required = false) SysFlowUser sysFlowUser) {
        return sysFlowUserService.saveSysFlowUser(sysFlowUser);
    }

    @ApiOperation(value="更新流程用户选择表", notes="更新流程用户选择表")
    @ApiImplicitParam(name = "sysFlowUser", value = "流程用户选择表entity", dataType = "SysFlowUser")
    @RequireToken
    @PostMapping("/updateSysFlowUser")
    public ResponseEntity updateSysFlowUser(@RequestBody(required = false) SysFlowUser sysFlowUser) {
        return sysFlowUserService.updateSysFlowUser(sysFlowUser);
    }

    @ApiOperation(value="删除流程用户选择表", notes="删除流程用户选择表")
    @ApiImplicitParam(name = "sysFlowUserList", value = "流程用户选择表List", dataType = "List<SysFlowUser>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysFlowUser")
    public ResponseEntity batchDeleteUpdateSysFlowUser(@RequestBody(required = false) List<SysFlowUser> sysFlowUserList) {
        return sysFlowUserService.batchDeleteUpdateSysFlowUser(sysFlowUserList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="新增流程用户选择表", notes="新增流程用户选择表")
    @ApiImplicitParam(name = "sysFlowUser", value = "流程用户选择表entity", dataType = "SysFlowUser")
    @RequireToken
    @PostMapping("/addSysFlowUserByFlow")
    public ResponseEntity addSysFlowUserByFlow(@RequestBody(required = false) SysFlowUser sysFlowUser) {
        return sysFlowUserService.saveSysFlowUserByFlow(sysFlowUser);
    }
    
    @ApiOperation(value="更新流程用户选择表", notes="更新流程用户选择表")
    @ApiImplicitParam(name = "sysFlowUser", value = "流程用户选择表entity", dataType = "SysFlowUser")
    @RequireToken
    @PostMapping("/deleteUpdateSysFlowUser")
    public ResponseEntity deleteUpdateSysFlowUser(@RequestBody(required = false) SysFlowUser sysFlowUser) {
        return sysFlowUserService.deleteUpdateSysFlowUser(sysFlowUser);
    }
}
