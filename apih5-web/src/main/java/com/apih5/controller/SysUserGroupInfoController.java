package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.SysUserGroupInfo;
import com.apih5.framework.api.sysdb.service.SysUserGroupInfoService;
import com.apih5.framework.entity.ResponseEntity;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class SysUserGroupInfoController {

    @Autowired(required = true)
    private SysUserGroupInfoService sysUserGroupInfoService;

    @ApiOperation(value="查询用户组明细", notes="查询用户组明细")
    @ApiImplicitParam(name = "sysUserGroupInfo", value = "用户组明细entity", dataType = "SysUserGroupInfo")
    @RequireToken
    @PostMapping("/getSysUserGroupInfoList")
    public ResponseEntity getSysUserGroupInfoList(@RequestBody(required = false) SysUserGroupInfo sysUserGroupInfo) {
        return sysUserGroupInfoService.getSysUserGroupInfoListByCondition(sysUserGroupInfo);
    }

    @ApiOperation(value="查询详情用户组明细", notes="查询详情用户组明细")
    @ApiImplicitParam(name = "sysUserGroupInfo", value = "用户组明细entity", dataType = "SysUserGroupInfo")
    @RequireToken
    @PostMapping("/getSysUserGroupInfoDetails")
    public ResponseEntity getSysUserGroupInfoDetails(@RequestBody(required = false) SysUserGroupInfo sysUserGroupInfo) {
        return sysUserGroupInfoService.getSysUserGroupInfoDetails(sysUserGroupInfo);
    }

    @ApiOperation(value="新增用户组明细", notes="新增用户组明细")
    @ApiImplicitParam(name = "sysUserGroupInfo", value = "用户组明细entity", dataType = "SysUserGroupInfo")
    @RequireToken
    @PostMapping("/addSysUserGroupInfo")
    public ResponseEntity addSysUserGroupInfo(@RequestBody(required = false) SysUserGroupInfo sysUserGroupInfo) {
        return sysUserGroupInfoService.saveSysUserGroupInfo(sysUserGroupInfo);
    }

    @ApiOperation(value="更新用户组明细", notes="更新用户组明细")
    @ApiImplicitParam(name = "sysUserGroupInfo", value = "用户组明细entity", dataType = "SysUserGroupInfo")
    @RequireToken
    @PostMapping("/updateSysUserGroupInfo")
    public ResponseEntity updateSysUserGroupInfo(@RequestBody(required = false) SysUserGroupInfo sysUserGroupInfo) {
        return sysUserGroupInfoService.updateSysUserGroupInfo(sysUserGroupInfo);
    }

    @ApiOperation(value="删除用户组明细", notes="删除用户组明细")
    @ApiImplicitParam(name = "sysUserGroupInfoList", value = "用户组明细List", dataType = "List<SysUserGroupInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysUserGroupInfo")
    public ResponseEntity batchDeleteUpdateSysUserGroupInfo(@RequestBody(required = false) List<SysUserGroupInfo> sysUserGroupInfoList) {
        return sysUserGroupInfoService.batchDeleteUpdateSysUserGroupInfo(sysUserGroupInfoList);
    }

}
