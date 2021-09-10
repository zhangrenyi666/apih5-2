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
import com.apih5.mybatis.pojo.ZjTzEmployeePolitics;
import com.apih5.service.ZjTzEmployeePoliticsService;

@RestController
public class ZjTzEmployeePoliticsController {

    @Autowired(required = true)
    private ZjTzEmployeePoliticsService zjTzEmployeePoliticsService;

    @ApiOperation(value="查询人员库-政治经历", notes="查询人员库-政治经历")
    @ApiImplicitParam(name = "zjTzEmployeePolitics", value = "人员库-政治经历entity", dataType = "ZjTzEmployeePolitics")
    @RequireToken
    @PostMapping("/getZjTzEmployeePoliticsList")
    public ResponseEntity getZjTzEmployeePoliticsList(@RequestBody(required = false) ZjTzEmployeePolitics zjTzEmployeePolitics) {
        return zjTzEmployeePoliticsService.getZjTzEmployeePoliticsListByCondition(zjTzEmployeePolitics);
    }

    @ApiOperation(value="查询详情人员库-政治经历", notes="查询详情人员库-政治经历")
    @ApiImplicitParam(name = "zjTzEmployeePolitics", value = "人员库-政治经历entity", dataType = "ZjTzEmployeePolitics")
    @RequireToken
    @PostMapping("/getZjTzEmployeePoliticsDetails")
    public ResponseEntity getZjTzEmployeePoliticsDetails(@RequestBody(required = false) ZjTzEmployeePolitics zjTzEmployeePolitics) {
        return zjTzEmployeePoliticsService.getZjTzEmployeePoliticsDetails(zjTzEmployeePolitics);
    }

    @ApiOperation(value="新增人员库-政治经历", notes="新增人员库-政治经历")
    @ApiImplicitParam(name = "zjTzEmployeePolitics", value = "人员库-政治经历entity", dataType = "ZjTzEmployeePolitics")
    @RequireToken
    @PostMapping("/addZjTzEmployeePolitics")
    public ResponseEntity addZjTzEmployeePolitics(@RequestBody(required = false) ZjTzEmployeePolitics zjTzEmployeePolitics) {
        return zjTzEmployeePoliticsService.saveZjTzEmployeePolitics(zjTzEmployeePolitics);
    }

    @ApiOperation(value="更新人员库-政治经历", notes="更新人员库-政治经历")
    @ApiImplicitParam(name = "zjTzEmployeePolitics", value = "人员库-政治经历entity", dataType = "ZjTzEmployeePolitics")
    @RequireToken
    @PostMapping("/updateZjTzEmployeePolitics")
    public ResponseEntity updateZjTzEmployeePolitics(@RequestBody(required = false) ZjTzEmployeePolitics zjTzEmployeePolitics) {
        return zjTzEmployeePoliticsService.updateZjTzEmployeePolitics(zjTzEmployeePolitics);
    }

    @ApiOperation(value="删除人员库-政治经历", notes="删除人员库-政治经历")
    @ApiImplicitParam(name = "zjTzEmployeePoliticsList", value = "人员库-政治经历List", dataType = "List<ZjTzEmployeePolitics>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzEmployeePolitics")
    public ResponseEntity batchDeleteUpdateZjTzEmployeePolitics(@RequestBody(required = false) List<ZjTzEmployeePolitics> zjTzEmployeePoliticsList) {
        return zjTzEmployeePoliticsService.batchDeleteUpdateZjTzEmployeePolitics(zjTzEmployeePoliticsList);
    }

    @ApiOperation(value="导出人员库-政治经历", notes="导出人员库-政治经历")
    @ApiImplicitParam(name = "zjTzEmployeePolitics", value = "人员库-政治经历", dataType = "ZjTzEmployeePolitics")
    @PostMapping("/printZjTzEmployeePolitics")
    public ZjTzEmployeePolitics printZjTzEmployeePolitics(@RequestBody(required = false) ZjTzEmployeePolitics zjTzEmployeePolitics) {
        return zjTzEmployeePoliticsService.printZjTzEmployeePolitics(zjTzEmployeePolitics);
    }

}