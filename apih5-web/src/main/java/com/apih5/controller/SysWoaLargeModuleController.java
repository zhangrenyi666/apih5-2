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
import com.apih5.mybatis.pojo.SysWoaLargeModule;
import com.apih5.service.SysWoaLargeModuleService;

@RestController
public class SysWoaLargeModuleController {

    @Autowired(required = true)
    private SysWoaLargeModuleService sysWoaLargeModuleService;

    @ApiOperation(value="查询移动端首页大模块", notes="查询移动端首页大模块")
    @ApiImplicitParam(name = "sysWoaLargeModule", value = "移动端首页大模块entity", dataType = "SysWoaLargeModule")
    @RequireToken
    @PostMapping("/getSysWoaLargeModuleList")
    public ResponseEntity getSysWoaLargeModuleList(@RequestBody(required = false) SysWoaLargeModule sysWoaLargeModule) {
        return sysWoaLargeModuleService.getSysWoaLargeModuleListByCondition(sysWoaLargeModule);
    }

    @ApiOperation(value="新增移动端首页大模块", notes="新增移动端首页大模块")
    @ApiImplicitParam(name = "sysWoaLargeModule", value = "移动端首页大模块entity", dataType = "SysWoaLargeModule")
    @RequireToken
    @PostMapping("/addSysWoaLargeModule")
    public ResponseEntity addSysWoaLargeModule(@RequestBody(required = false) SysWoaLargeModule sysWoaLargeModule) {
        return sysWoaLargeModuleService.saveSysWoaLargeModule(sysWoaLargeModule);
    }

    @ApiOperation(value="更新移动端首页大模块", notes="更新移动端首页大模块")
    @ApiImplicitParam(name = "sysWoaLargeModule", value = "移动端首页大模块entity", dataType = "SysWoaLargeModule")
    @RequireToken
    @PostMapping("/updateSysWoaLargeModule")
    public ResponseEntity updateSysWoaLargeModule(@RequestBody(required = false) SysWoaLargeModule sysWoaLargeModule) {
        return sysWoaLargeModuleService.updateSysWoaLargeModule(sysWoaLargeModule);
    }

    @ApiOperation(value="删除移动端首页大模块", notes="删除移动端首页大模块")
    @ApiImplicitParam(name = "sysWoaLargeModuleList", value = "移动端首页大模块List", dataType = "List<SysWoaLargeModule>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysWoaLargeModule")
    public ResponseEntity batchDeleteUpdateSysWoaLargeModule(@RequestBody(required = false) List<SysWoaLargeModule> sysWoaLargeModuleList) {
        return sysWoaLargeModuleService.batchDeleteUpdateSysWoaLargeModule(sysWoaLargeModuleList);
    }

    // --扩展
    @ApiOperation(value="移动端首页", notes="移动端首页")
    @ApiImplicitParam(name = "sysWoaLargeModule", value = "移动端首页entity", dataType = "sysWoaLargeModule")
    @RequireToken
    @PostMapping("/getSysMobilIndex")
    public ResponseEntity getSysMobilIndex(@RequestBody(required = false) SysWoaLargeModule sysWoaLargeModule) {
        return sysWoaLargeModuleService.getSysMobilIndex(sysWoaLargeModule);
    }

    @ApiOperation(value="移动端首页-数据渲染", notes="移动端首页-数据渲染")
    @ApiImplicitParam(name = "sysWoaLargeModule", value = "移动端首页entity", dataType = "SysWoaLargeModule")
    @RequireToken
    @PostMapping("/getSysMobilIndexByData")
    public ResponseEntity getSysMobilIndexByData(@RequestBody(required = false) SysWoaLargeModule sysWoaLargeModule) {
    	return sysWoaLargeModuleService.getSysMobilIndexByData(sysWoaLargeModule);
    }
}
