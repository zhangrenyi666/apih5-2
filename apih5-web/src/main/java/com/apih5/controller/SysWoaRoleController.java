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
import com.apih5.mybatis.pojo.SysWoaRole;
import com.apih5.service.SysWoaRoleService;

@RestController
public class SysWoaRoleController {

    @Autowired(required = true)
    private SysWoaRoleService sysWoaRoleService;

    @ApiOperation(value="查询移动端首页权限", notes="查询移动端首页权限")
    @ApiImplicitParam(name = "sysWoaRole", value = "移动端首页权限entity", dataType = "SysWoaRole")
    @RequireToken
    @PostMapping("/getSysWoaRoleList")
    public ResponseEntity getSysWoaRoleList(@RequestBody(required = false) SysWoaRole sysWoaRole) {
        return sysWoaRoleService.getSysWoaRoleListByCondition(sysWoaRole);
    }

    @ApiOperation(value="新增移动端首页权限", notes="新增移动端首页权限")
    @ApiImplicitParam(name = "sysWoaRole", value = "移动端首页权限entity", dataType = "SysWoaRole")
    @RequireToken
    @PostMapping("/addSysWoaRole")
    public ResponseEntity addSysWoaRole(@RequestBody(required = false) SysWoaRole sysWoaRole) {
        return sysWoaRoleService.saveSysWoaRole(sysWoaRole);
    }

    @ApiOperation(value="更新移动端首页权限", notes="更新移动端首页权限")
    @ApiImplicitParam(name = "sysWoaRole", value = "移动端首页权限entity", dataType = "SysWoaRole")
    @RequireToken
    @PostMapping("/updateSysWoaRole")
    public ResponseEntity updateSysWoaRole(@RequestBody(required = false) SysWoaRole sysWoaRole) {
        return sysWoaRoleService.updateSysWoaRole(sysWoaRole);
    }

    @ApiOperation(value="删除移动端首页权限", notes="删除移动端首页权限")
    @ApiImplicitParam(name = "sysWoaRoleList", value = "移动端首页权限List", dataType = "List<SysWoaRole>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysWoaRole")
    public ResponseEntity batchDeleteUpdateSysWoaRole(@RequestBody(required = false) List<SysWoaRole> sysWoaRoleList) {
        return sysWoaRoleService.batchDeleteUpdateSysWoaRole(sysWoaRoleList);
    }

}
