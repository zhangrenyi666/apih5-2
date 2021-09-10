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
import com.apih5.mybatis.pojo.SysWoaFlowSelect;
import com.apih5.service.SysWoaFlowSelectService;

@RestController
public class SysWoaFlowSelectController {

    @Autowired(required = true)
    private SysWoaFlowSelectService sysWoaFlowSelectService;

    @ApiOperation(value="查询移动端首页下拉", notes="查询移动端首页下拉")
    @ApiImplicitParam(name = "sysWoaFlowSelect", value = "移动端首页下拉entity", dataType = "SysWoaFlowSelect")
    @RequireToken
    @PostMapping("/getSysWoaFlowSelectList")
    public ResponseEntity getSysWoaFlowSelectList(@RequestBody(required = false) SysWoaFlowSelect sysWoaFlowSelect) {
        return sysWoaFlowSelectService.getSysWoaFlowSelectListByCondition(sysWoaFlowSelect);
    }

    @ApiOperation(value="新增移动端首页下拉", notes="新增移动端首页下拉")
    @ApiImplicitParam(name = "sysWoaFlowSelect", value = "移动端首页下拉entity", dataType = "SysWoaFlowSelect")
    @RequireToken
    @PostMapping("/addSysWoaFlowSelect")
    public ResponseEntity addSysWoaFlowSelect(@RequestBody(required = false) SysWoaFlowSelect sysWoaFlowSelect) {
        return sysWoaFlowSelectService.saveSysWoaFlowSelect(sysWoaFlowSelect);
    }

    @ApiOperation(value="更新移动端首页下拉", notes="更新移动端首页下拉")
    @ApiImplicitParam(name = "sysWoaFlowSelect", value = "移动端首页下拉entity", dataType = "SysWoaFlowSelect")
    @RequireToken
    @PostMapping("/updateSysWoaFlowSelect")
    public ResponseEntity updateSysWoaFlowSelect(@RequestBody(required = false) SysWoaFlowSelect sysWoaFlowSelect) {
        return sysWoaFlowSelectService.updateSysWoaFlowSelect(sysWoaFlowSelect);
    }

    @ApiOperation(value="删除移动端首页下拉", notes="删除移动端首页下拉")
    @ApiImplicitParam(name = "sysWoaFlowSelectList", value = "移动端首页下拉List", dataType = "List<SysWoaFlowSelect>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysWoaFlowSelect")
    public ResponseEntity batchDeleteUpdateSysWoaFlowSelect(@RequestBody(required = false) List<SysWoaFlowSelect> sysWoaFlowSelectList) {
        return sysWoaFlowSelectService.batchDeleteUpdateSysWoaFlowSelect(sysWoaFlowSelectList);
    }

    // ----↓↓下扩展↓↓↓----
    @ApiOperation(value="查询流程下拉", notes="查询流程下拉")
    @ApiImplicitParam(name = "sysWoaFlowSelect", value = "流程下拉entity", dataType = "sysWoaFlowSelect")
    @RequireToken
    @PostMapping("/getSysWoaFlowSelectDetail")
    public ResponseEntity getSysWoaFlowSelectDetail(@RequestBody(required = false) SysWoaFlowSelect sysWoaFlowSelect) {
    	return sysWoaFlowSelectService.getSysWoaFlowSelectDetailByCondition(sysWoaFlowSelect);
    }
}
