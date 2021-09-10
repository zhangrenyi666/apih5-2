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
import com.apih5.mybatis.pojo.ZxEqMoveSupervisor;
import com.apih5.service.ZxEqMoveSupervisorService;

@RestController
public class ZxEqMoveSupervisorController {

    @Autowired(required = true)
    private ZxEqMoveSupervisorService zxEqMoveSupervisorService;

    @ApiOperation(value="查询设备异动-局内调拨", notes="查询设备异动-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisor", value = "设备异动-局内调拨entity", dataType = "ZxEqMoveSupervisor")
    @RequireToken
    @PostMapping("/getZxEqMoveSupervisorList")
    public ResponseEntity getZxEqMoveSupervisorList(@RequestBody(required = false) ZxEqMoveSupervisor zxEqMoveSupervisor) {
        return zxEqMoveSupervisorService.getZxEqMoveSupervisorListByCondition(zxEqMoveSupervisor);
    }

    @ApiOperation(value="查询详情设备异动-局内调拨", notes="查询详情设备异动-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisor", value = "设备异动-局内调拨entity", dataType = "ZxEqMoveSupervisor")
    @RequireToken
    @PostMapping("/getZxEqMoveSupervisorDetails")
    public ResponseEntity getZxEqMoveSupervisorDetails(@RequestBody(required = false) ZxEqMoveSupervisor zxEqMoveSupervisor) {
        return zxEqMoveSupervisorService.getZxEqMoveSupervisorDetails(zxEqMoveSupervisor);
    }

    @ApiOperation(value="新增设备异动-局内调拨", notes="新增设备异动-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisor", value = "设备异动-局内调拨entity", dataType = "ZxEqMoveSupervisor")
    @RequireToken
    @PostMapping("/addZxEqMoveSupervisor")
    public ResponseEntity addZxEqMoveSupervisor(@RequestBody(required = false) ZxEqMoveSupervisor zxEqMoveSupervisor) {
        return zxEqMoveSupervisorService.saveZxEqMoveSupervisor(zxEqMoveSupervisor);
    }

    @ApiOperation(value="更新设备异动-局内调拨", notes="更新设备异动-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisor", value = "设备异动-局内调拨entity", dataType = "ZxEqMoveSupervisor")
    @RequireToken
    @PostMapping("/updateZxEqMoveSupervisor")
    public ResponseEntity updateZxEqMoveSupervisor(@RequestBody(required = false) ZxEqMoveSupervisor zxEqMoveSupervisor) {
        return zxEqMoveSupervisorService.updateZxEqMoveSupervisor(zxEqMoveSupervisor);
    }

    @ApiOperation(value="删除设备异动-局内调拨", notes="删除设备异动-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisorList", value = "设备异动-局内调拨List", dataType = "List<ZxEqMoveSupervisor>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqMoveSupervisor")
    public ResponseEntity batchDeleteUpdateZxEqMoveSupervisor(@RequestBody(required = false) List<ZxEqMoveSupervisor> zxEqMoveSupervisorList) {
        return zxEqMoveSupervisorService.batchDeleteUpdateZxEqMoveSupervisor(zxEqMoveSupervisorList);
    }
    
    @ApiOperation(value="调出方确认-设备异动-局内调拨", notes="调出方确认-设备异动-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisor", value = "设备异动-局内调拨entity", dataType = "ZxEqMoveSupervisor")
    @RequireToken
    @PostMapping("/outConfirmZxEqMoveSupervisor")
    public ResponseEntity outConfirmZxEqMoveSupervisor(@RequestBody(required = false) ZxEqMoveSupervisor zxEqMoveSupervisor) {
        return zxEqMoveSupervisorService.outConfirmZxEqMoveSupervisor(zxEqMoveSupervisor);
    }
    
    @ApiOperation(value="调入方确认-设备异动-局内调拨", notes="调入方确认-设备异动-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisor", value = "设备异动-局内调拨entity", dataType = "ZxEqMoveSupervisor")
    @RequireToken
    @PostMapping("/inConfirmZxEqMoveSupervisor")
    public ResponseEntity inConfirmZxEqMoveSupervisor(@RequestBody(required = false) ZxEqMoveSupervisor zxEqMoveSupervisor) {
        return zxEqMoveSupervisorService.inConfirmZxEqMoveSupervisor(zxEqMoveSupervisor);
    }
    
    @ApiOperation(value="批量请求编号-局内调拨", notes="批量请求编号-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisorList", value = "设备异动-局内调拨List", dataType = "List<ZxEqMoveSupervisor>")
    @RequireToken
    @PostMapping("/batchRequestZxEqMoveSupervisorNum")
    public ResponseEntity batchRequestZxEqMoveSupervisorNum(@RequestBody(required = false) List<ZxEqMoveSupervisor> zxEqMoveSupervisorList) {
        return zxEqMoveSupervisorService.batchRequestZxEqMoveSupervisorNum(zxEqMoveSupervisorList);
    }
    
    @ApiOperation(value="填写编号-局内调拨", notes="填写编号-局内调拨")
    @ApiImplicitParam(name = "zxEqMoveSupervisor", value = "设备异动-局内调拨entity", dataType = "ZxEqMoveSupervisor")
    @RequireToken
    @PostMapping("/writeZxEqMoveSupervisorNum")
    public ResponseEntity writeZxEqMoveSupervisorNum(@RequestBody(required = false) ZxEqMoveSupervisor zxEqMoveSupervisor) {
        return zxEqMoveSupervisorService.writeZxEqMoveSupervisorNum(zxEqMoveSupervisor);
    }
    

}
