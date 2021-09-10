package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.SysUserGroup;
import com.apih5.framework.api.sysdb.service.SysUserGroupService;
import com.apih5.framework.entity.ResponseEntity;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class SysUserGroupController {

    @Autowired(required = true)
    private SysUserGroupService sysUserGroupService;

    @ApiOperation(value="查询用户组", notes="查询用户组")
    @ApiImplicitParam(name = "sysUserGroup", value = "用户组entity", dataType = "SysUserGroup")
    @RequireToken
    @PostMapping("/getSysUserGroupList")
    public ResponseEntity getSysUserGroupList(@RequestBody(required = false) SysUserGroup sysUserGroup) {
        return sysUserGroupService.getSysUserGroupListByCondition(sysUserGroup);
    }

    @ApiOperation(value="查询详情用户组", notes="查询详情用户组")
    @ApiImplicitParam(name = "sysUserGroup", value = "用户组entity", dataType = "SysUserGroup")
    @RequireToken
    @PostMapping("/getSysUserGroupDetails")
    public ResponseEntity getSysUserGroupDetails(@RequestBody(required = false) SysUserGroup sysUserGroup) {
        return sysUserGroupService.getSysUserGroupDetails(sysUserGroup);
    }

    @ApiOperation(value="新增用户组", notes="新增用户组")
    @ApiImplicitParam(name = "sysUserGroup", value = "用户组entity", dataType = "SysUserGroup")
    @RequireToken
    @PostMapping("/addSysUserGroup")
    public ResponseEntity addSysUserGroup(@RequestBody(required = false) SysUserGroup sysUserGroup) {
        return sysUserGroupService.saveSysUserGroup(sysUserGroup);
    }

    @ApiOperation(value="更新用户组", notes="更新用户组")
    @ApiImplicitParam(name = "sysUserGroup", value = "用户组entity", dataType = "SysUserGroup")
    @RequireToken
    @PostMapping("/updateSysUserGroup")
    public ResponseEntity updateSysUserGroup(@RequestBody(required = false) SysUserGroup sysUserGroup) {
        return sysUserGroupService.updateSysUserGroup(sysUserGroup);
    }

    @ApiOperation(value="删除用户组", notes="删除用户组")
    @ApiImplicitParam(name = "sysUserGroupList", value = "用户组List", dataType = "List<SysUserGroup>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysUserGroup")
    public ResponseEntity batchDeleteUpdateSysUserGroup(@RequestBody(required = false) List<SysUserGroup> sysUserGroupList) {
        return sysUserGroupService.batchDeleteUpdateSysUserGroup(sysUserGroupList);
    }
    
}
