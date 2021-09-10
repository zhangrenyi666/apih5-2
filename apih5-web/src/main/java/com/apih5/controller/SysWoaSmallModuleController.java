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
import com.apih5.mybatis.pojo.SysWoaSmallModule;
import com.apih5.service.SysWoaSmallModuleService;

@RestController
public class SysWoaSmallModuleController {

    @Autowired(required = true)
    private SysWoaSmallModuleService sysWoaSmallModuleService;

    @ApiOperation(value="查询移动端首页小模块", notes="查询移动端首页小模块")
    @ApiImplicitParam(name = "sysWoaSmallModule", value = "移动端首页小模块entity", dataType = "SysWoaSmallModule")
    @RequireToken
    @PostMapping("/getSysWoaSmallModuleList")
    public ResponseEntity getSysWoaSmallModuleList(@RequestBody(required = false) SysWoaSmallModule sysWoaSmallModule) {
        return sysWoaSmallModuleService.getSysWoaSmallModuleListByCondition(sysWoaSmallModule);
    }

    @ApiOperation(value="新增移动端首页小模块", notes="新增移动端首页小模块")
    @ApiImplicitParam(name = "sysWoaSmallModule", value = "移动端首页小模块entity", dataType = "SysWoaSmallModule")
    @RequireToken
    @PostMapping("/addSysWoaSmallModule")
    public ResponseEntity addSysWoaSmallModule(@RequestBody(required = false) SysWoaSmallModule sysWoaSmallModule) {
        return sysWoaSmallModuleService.saveSysWoaSmallModule(sysWoaSmallModule);
    }

    @ApiOperation(value="更新移动端首页小模块", notes="更新移动端首页小模块")
    @ApiImplicitParam(name = "sysWoaSmallModule", value = "移动端首页小模块entity", dataType = "SysWoaSmallModule")
    @RequireToken
    @PostMapping("/updateSysWoaSmallModule")
    public ResponseEntity updateSysWoaSmallModule(@RequestBody(required = false) SysWoaSmallModule sysWoaSmallModule) {
        return sysWoaSmallModuleService.updateSysWoaSmallModule(sysWoaSmallModule);
    }

    @ApiOperation(value="删除移动端首页小模块", notes="删除移动端首页小模块")
    @ApiImplicitParam(name = "sysWoaSmallModuleList", value = "移动端首页小模块List", dataType = "List<SysWoaSmallModule>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysWoaSmallModule")
    public ResponseEntity batchDeleteUpdateSysWoaSmallModule(@RequestBody(required = false) List<SysWoaSmallModule> sysWoaSmallModuleList) {
        return sysWoaSmallModuleService.batchDeleteUpdateSysWoaSmallModule(sysWoaSmallModuleList);
    }

}
