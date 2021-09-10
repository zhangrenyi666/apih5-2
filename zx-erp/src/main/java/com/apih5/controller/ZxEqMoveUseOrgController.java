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
import com.apih5.mybatis.pojo.ZxEqMoveUseOrg;
import com.apih5.service.ZxEqMoveUseOrgService;

@RestController
public class ZxEqMoveUseOrgController {

    @Autowired(required = true)
    private ZxEqMoveUseOrgService zxEqMoveUseOrgService;

    @ApiOperation(value="查询设备异动-公司内调拨", notes="查询设备异动-公司内调拨")
    @ApiImplicitParam(name = "zxEqMoveUseOrg", value = "设备异动-公司内调拨entity", dataType = "ZxEqMoveUseOrg")
    @RequireToken
    @PostMapping("/getZxEqMoveUseOrgList")
    public ResponseEntity getZxEqMoveUseOrgList(@RequestBody(required = false) ZxEqMoveUseOrg zxEqMoveUseOrg) {
        return zxEqMoveUseOrgService.getZxEqMoveUseOrgListByCondition(zxEqMoveUseOrg);
    }

    @ApiOperation(value="查询详情设备异动-公司内调拨", notes="查询详情设备异动-公司内调拨")
    @ApiImplicitParam(name = "zxEqMoveUseOrg", value = "设备异动-公司内调拨entity", dataType = "ZxEqMoveUseOrg")
    @RequireToken
    @PostMapping("/getZxEqMoveUseOrgDetails")
    public ResponseEntity getZxEqMoveUseOrgDetails(@RequestBody(required = false) ZxEqMoveUseOrg zxEqMoveUseOrg) {
        return zxEqMoveUseOrgService.getZxEqMoveUseOrgDetails(zxEqMoveUseOrg);
    }

    @ApiOperation(value="新增设备异动-公司内调拨", notes="新增设备异动-公司内调拨")
    @ApiImplicitParam(name = "zxEqMoveUseOrg", value = "设备异动-公司内调拨entity", dataType = "ZxEqMoveUseOrg")
    @RequireToken
    @PostMapping("/addZxEqMoveUseOrg")
    public ResponseEntity addZxEqMoveUseOrg(@RequestBody(required = false) ZxEqMoveUseOrg zxEqMoveUseOrg) {
        return zxEqMoveUseOrgService.saveZxEqMoveUseOrg(zxEqMoveUseOrg);
    }

    @ApiOperation(value="更新设备异动-公司内调拨", notes="更新设备异动-公司内调拨")
    @ApiImplicitParam(name = "zxEqMoveUseOrg", value = "设备异动-公司内调拨entity", dataType = "ZxEqMoveUseOrg")
    @RequireToken
    @PostMapping("/updateZxEqMoveUseOrg")
    public ResponseEntity updateZxEqMoveUseOrg(@RequestBody(required = false) ZxEqMoveUseOrg zxEqMoveUseOrg) {
        return zxEqMoveUseOrgService.updateZxEqMoveUseOrg(zxEqMoveUseOrg);
    }

    @ApiOperation(value="删除设备异动-公司内调拨", notes="删除设备异动-公司内调拨")
    @ApiImplicitParam(name = "zxEqMoveUseOrgList", value = "设备异动-公司内调拨List", dataType = "List<ZxEqMoveUseOrg>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqMoveUseOrg")
    public ResponseEntity batchDeleteUpdateZxEqMoveUseOrg(@RequestBody(required = false) List<ZxEqMoveUseOrg> zxEqMoveUseOrgList) {
        return zxEqMoveUseOrgService.batchDeleteUpdateZxEqMoveUseOrg(zxEqMoveUseOrgList);
    }
    
    @ApiOperation(value="调出方确认-设备异动-公司内调拨", notes="调出方确认-设备异动-公司内调拨")
    @ApiImplicitParam(name = "zxEqMoveUseOrg", value = "设备异动-公司内调拨entity", dataType = "ZxEqMoveUseOrg")
    @RequireToken
    @PostMapping("/outConfirmZxEqMoveUseOrg")
    public ResponseEntity outConfirmZxEqMoveUseOrg(@RequestBody(required = false) ZxEqMoveUseOrg zxEqMoveUseOrg) {
        return zxEqMoveUseOrgService.outConfirmZxEqMoveUseOrg(zxEqMoveUseOrg);
    }

    @ApiOperation(value="调入方确认-设备异动-公司内调拨", notes="调入方确认-设备异动-公司内调拨")
    @ApiImplicitParam(name = "zxEqMoveUseOrg", value = "设备异动-公司内调拨entity", dataType = "ZxEqMoveUseOrg")
    @RequireToken
    @PostMapping("/inConfirmZxEqMoveUseOrg")
    public ResponseEntity inConfirmZxEqMoveUseOrg(@RequestBody(required = false) ZxEqMoveUseOrg zxEqMoveUseOrg) {
        return zxEqMoveUseOrgService.inConfirmZxEqMoveUseOrg(zxEqMoveUseOrg);
    }

}
