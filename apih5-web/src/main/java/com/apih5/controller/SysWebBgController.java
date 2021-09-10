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
import com.apih5.mybatis.pojo.SysWebBg;
import com.apih5.service.SysWebBgService;

@RestController
public class SysWebBgController {

    @Autowired(required = true)
    private SysWebBgService sysWebBgService;

    @ApiOperation(value="查询webConfig", notes="查询webConfig")
    @ApiImplicitParam(name = "sysWebBg", value = "webConfigentity", dataType = "SysWebBg")
    @RequireToken
    @PostMapping("/getSysWebBgList")
    public ResponseEntity getSysWebBgList(@RequestBody(required = false) SysWebBg sysWebBg) {
        return sysWebBgService.getSysWebBgListByCondition(sysWebBg);
    }

    @ApiOperation(value="查询详情webConfig", notes="查询详情webConfig")
    @ApiImplicitParam(name = "sysWebBg", value = "webConfigentity", dataType = "SysWebBg")
    @RequireToken
    @PostMapping("/getSysWebBgDetails")
    public ResponseEntity getSysWebBgDetails(@RequestBody(required = false) SysWebBg sysWebBg) {
        return sysWebBgService.getSysWebBgDetails(sysWebBg);
    }

    @ApiOperation(value="新增webConfig", notes="新增webConfig")
    @ApiImplicitParam(name = "sysWebBg", value = "webConfigentity", dataType = "SysWebBg")
    @RequireToken
    @PostMapping("/addSysWebBg")
    public ResponseEntity addSysWebBg(@RequestBody(required = false) SysWebBg sysWebBg) {
        return sysWebBgService.saveSysWebBg(sysWebBg);
    }

    @ApiOperation(value="更新webConfig", notes="更新webConfig")
    @ApiImplicitParam(name = "sysWebBg", value = "webConfigentity", dataType = "SysWebBg")
    @RequireToken
    @PostMapping("/updateSysWebBg")
    public ResponseEntity updateSysWebBg(@RequestBody(required = false) SysWebBg sysWebBg) {
        return sysWebBgService.updateSysWebBg(sysWebBg);
    }

    @ApiOperation(value="删除webConfig", notes="删除webConfig")
    @ApiImplicitParam(name = "sysWebBgList", value = "webConfigList", dataType = "List<SysWebBg>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysWebBg")
    public ResponseEntity batchDeleteUpdateSysWebBg(@RequestBody(required = false) List<SysWebBg> sysWebBgList) {
        return sysWebBgService.batchDeleteUpdateSysWebBg(sysWebBgList);
    }

    @ApiOperation(value="查询详情webConfig", notes="查询详情webConfig")
    @ApiImplicitParam(name = "sysWebBg", value = "webConfigentity", dataType = "SysWebBg")
    @PostMapping("/getSysWebBg")
    public ResponseEntity getSysWebBg(@RequestBody(required = false) SysWebBg sysWebBg) {
        return sysWebBgService.getSysWebBg(sysWebBg);
    }
}
