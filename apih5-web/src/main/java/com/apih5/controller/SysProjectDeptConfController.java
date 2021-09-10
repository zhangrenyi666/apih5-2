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
import com.apih5.mybatis.pojo.SysProjectDeptConf;
import com.apih5.service.SysProjectDeptConfService;

@RestController
public class SysProjectDeptConfController {

    @Autowired(required = true)
    private SysProjectDeptConfService sysProjectDeptConfService;

    @ApiOperation(value="查询项目部门人员配置表", notes="查询项目部门人员配置表")
    @ApiImplicitParam(name = "sysProjectDeptConf", value = "项目部门人员配置表entity", dataType = "SysProjectDeptConf")
    @RequireToken
    @PostMapping("/getSysProjectDeptConfList")
    public ResponseEntity getSysProjectDeptConfList(@RequestBody(required = false) SysProjectDeptConf sysProjectDeptConf) {
        return sysProjectDeptConfService.getSysProjectDeptConfListByCondition(sysProjectDeptConf);
    }

    @ApiOperation(value="查询详情项目部门人员配置表", notes="查询详情项目部门人员配置表")
    @ApiImplicitParam(name = "sysProjectDeptConf", value = "项目部门人员配置表entity", dataType = "SysProjectDeptConf")
    @RequireToken
    @PostMapping("/getSysProjectDeptConfDetail")
    public ResponseEntity getSysProjectDeptConfDetail(@RequestBody(required = false) SysProjectDeptConf sysProjectDeptConf) {
        return sysProjectDeptConfService.getSysProjectDeptConfDetail(sysProjectDeptConf);
    }

    @ApiOperation(value="新增项目部门人员配置表", notes="新增项目部门人员配置表")
    @ApiImplicitParam(name = "sysProjectDeptConf", value = "项目部门人员配置表entity", dataType = "SysProjectDeptConf")
    @RequireToken
    @PostMapping("/addSysProjectDeptConf")
    public ResponseEntity addSysProjectDeptConf(@RequestBody(required = false) SysProjectDeptConf sysProjectDeptConf) {
        return sysProjectDeptConfService.saveSysProjectDeptConf(sysProjectDeptConf);
    }

    @ApiOperation(value="更新项目部门人员配置表", notes="更新项目部门人员配置表")
    @ApiImplicitParam(name = "sysProjectDeptConf", value = "项目部门人员配置表entity", dataType = "SysProjectDeptConf")
    @RequireToken
    @PostMapping("/updateSysProjectDeptConf")
    public ResponseEntity updateSysProjectDeptConf(@RequestBody(required = false) SysProjectDeptConf sysProjectDeptConf) {
        return sysProjectDeptConfService.updateSysProjectDeptConf(sysProjectDeptConf);
    }

    @ApiOperation(value="删除项目部门人员配置表", notes="删除项目部门人员配置表")
    @ApiImplicitParam(name = "sysProjectDeptConfList", value = "项目部门人员配置表List", dataType = "List<SysProjectDeptConf>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysProjectDeptConf")
    public ResponseEntity batchDeleteUpdateSysProjectDeptConf(@RequestBody(required = false) List<SysProjectDeptConf> sysProjectDeptConfList) {
        return sysProjectDeptConfService.batchDeleteUpdateSysProjectDeptConf(sysProjectDeptConfList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
