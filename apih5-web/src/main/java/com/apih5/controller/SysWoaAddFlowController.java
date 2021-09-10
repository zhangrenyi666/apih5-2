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
import com.apih5.mybatis.pojo.SysWoaAddFlow;
import com.apih5.service.SysWoaAddFlowService;

@RestController
public class SysWoaAddFlowController {

    @Autowired(required = true)
    private SysWoaAddFlowService sysWoaAddFlowService;

    @ApiOperation(value="查询移动端首页新增", notes="查询移动端首页新增")
    @ApiImplicitParam(name = "sysWoaAddFlow", value = "移动端首页新增entity", dataType = "SysWoaAddFlow")
    @RequireToken
    @PostMapping("/getSysWoaAddFlowList")
    public ResponseEntity getSysWoaAddFlowList(@RequestBody(required = false) SysWoaAddFlow sysWoaAddFlow) {
        return sysWoaAddFlowService.getSysWoaAddFlowListByCondition(sysWoaAddFlow);
    }

    @ApiOperation(value="新增移动端首页新增", notes="新增移动端首页新增")
    @ApiImplicitParam(name = "sysWoaAddFlow", value = "移动端首页新增entity", dataType = "SysWoaAddFlow")
    @RequireToken
    @PostMapping("/addSysWoaAddFlow")
    public ResponseEntity addSysWoaAddFlow(@RequestBody(required = false) SysWoaAddFlow sysWoaAddFlow) {
        return sysWoaAddFlowService.saveSysWoaAddFlow(sysWoaAddFlow);
    }

    @ApiOperation(value="更新移动端首页新增", notes="更新移动端首页新增")
    @ApiImplicitParam(name = "sysWoaAddFlow", value = "移动端首页新增entity", dataType = "SysWoaAddFlow")
    @RequireToken
    @PostMapping("/updateSysWoaAddFlow")
    public ResponseEntity updateSysWoaAddFlow(@RequestBody(required = false) SysWoaAddFlow sysWoaAddFlow) {
        return sysWoaAddFlowService.updateSysWoaAddFlow(sysWoaAddFlow);
    }

    @ApiOperation(value="删除移动端首页新增", notes="删除移动端首页新增")
    @ApiImplicitParam(name = "sysWoaAddFlowList", value = "移动端首页新增List", dataType = "List<SysWoaAddFlow>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysWoaAddFlow")
    public ResponseEntity batchDeleteUpdateSysWoaAddFlow(@RequestBody(required = false) List<SysWoaAddFlow> sysWoaAddFlowList) {
        return sysWoaAddFlowService.batchDeleteUpdateSysWoaAddFlow(sysWoaAddFlowList);
    }

    @ApiOperation(value="查询移动端首页新增-权限查询", notes="查询移动端首页新增-权限查询")
    @ApiImplicitParam(name = "sysWoaAddFlow", value = "移动端首页新增entity", dataType = "SysWoaAddFlow")
    @RequireToken
    @PostMapping("/getSysWoaAddFlowListByRole")
    public ResponseEntity getSysWoaAddFlowListByRole(@RequestBody(required = false) SysWoaAddFlow sysWoaAddFlow) {
        return sysWoaAddFlowService.getSysWoaAddFlowListByRole(sysWoaAddFlow);
    }
}
