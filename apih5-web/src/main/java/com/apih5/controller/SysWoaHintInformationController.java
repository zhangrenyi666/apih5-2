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
import com.apih5.mybatis.pojo.SysWoaHintInformation;
import com.apih5.service.SysWoaHintInformationService;

@RestController
public class SysWoaHintInformationController {

    @Autowired(required = true)
    private SysWoaHintInformationService sysWoaHintInformationService;

    @ApiOperation(value="查询中交WOA提示信息", notes="查询中交WOA提示信息")
    @ApiImplicitParam(name = "sysWoaHintInformation", value = "中交WOA提示信息entity", dataType = "SysWoaHintInformation")
    @RequireToken
    @PostMapping("/getSysWoaHintInformationList")
    public ResponseEntity getSysWoaHintInformationList(@RequestBody(required = false) SysWoaHintInformation sysWoaHintInformation) {
        return sysWoaHintInformationService.getSysWoaHintInformationListByCondition(sysWoaHintInformation);
    }

    @ApiOperation(value="新增中交WOA提示信息", notes="新增中交WOA提示信息")
    @ApiImplicitParam(name = "sysWoaHintInformation", value = "中交WOA提示信息entity", dataType = "SysWoaHintInformation")
    @RequireToken
    @PostMapping("/addSysWoaHintInformation")
    public ResponseEntity addSysWoaHintInformation(@RequestBody(required = false) SysWoaHintInformation sysWoaHintInformation) {
        return sysWoaHintInformationService.saveSysWoaHintInformation(sysWoaHintInformation);
    }

    @ApiOperation(value="更新中交WOA提示信息", notes="更新中交WOA提示信息")
    @ApiImplicitParam(name = "sysWoaHintInformation", value = "中交WOA提示信息entity", dataType = "SysWoaHintInformation")
    @RequireToken
    @PostMapping("/updateSysWoaHintInformation")
    public ResponseEntity updateSysWoaHintInformation(@RequestBody(required = false) SysWoaHintInformation sysWoaHintInformation) {
        return sysWoaHintInformationService.updateSysWoaHintInformation(sysWoaHintInformation);
    }

    @ApiOperation(value="删除中交WOA提示信息", notes="删除中交WOA提示信息")
    @ApiImplicitParam(name = "sysWoaHintInformationList", value = "中交WOA提示信息List", dataType = "List<SysWoaHintInformation>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysWoaHintInformation")
    public ResponseEntity batchDeleteUpdateSysWoaHintInformation(@RequestBody(required = false) List<SysWoaHintInformation> sysWoaHintInformationList) {
        return sysWoaHintInformationService.batchDeleteUpdateSysWoaHintInformation(sysWoaHintInformationList);
    }

}
