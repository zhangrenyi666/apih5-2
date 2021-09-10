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
import com.apih5.mybatis.pojo.ZxEqEemployee;
import com.apih5.service.ZxEqEemployeeService;

@RestController
public class ZxEqEemployeeController {

    @Autowired(required = true)
    private ZxEqEemployeeService zxEqEemployeeService;

    @ApiOperation(value="查询项目设备管理-机械设备管理人员", notes="查询项目设备管理-机械设备管理人员")
    @ApiImplicitParam(name = "zxEqEemployee", value = "项目设备管理-机械设备管理人员entity", dataType = "ZxEqEemployee")
    @RequireToken
    @PostMapping("/getZxEqEemployeeList")
    public ResponseEntity getZxEqEemployeeList(@RequestBody(required = false) ZxEqEemployee zxEqEemployee) {
        return zxEqEemployeeService.getZxEqEemployeeListByCondition(zxEqEemployee);
    }

    @ApiOperation(value="查询详情项目设备管理-机械设备管理人员", notes="查询详情项目设备管理-机械设备管理人员")
    @ApiImplicitParam(name = "zxEqEemployee", value = "项目设备管理-机械设备管理人员entity", dataType = "ZxEqEemployee")
    @RequireToken
    @PostMapping("/getZxEqEemployeeDetails")
    public ResponseEntity getZxEqEemployeeDetails(@RequestBody(required = false) ZxEqEemployee zxEqEemployee) {
        return zxEqEemployeeService.getZxEqEemployeeDetails(zxEqEemployee);
    }

    @ApiOperation(value="新增项目设备管理-机械设备管理人员", notes="新增项目设备管理-机械设备管理人员")
    @ApiImplicitParam(name = "zxEqEemployee", value = "项目设备管理-机械设备管理人员entity", dataType = "ZxEqEemployee")
    @RequireToken
    @PostMapping("/addZxEqEemployee")
    public ResponseEntity addZxEqEemployee(@RequestBody(required = false) ZxEqEemployee zxEqEemployee) {
        return zxEqEemployeeService.saveZxEqEemployee(zxEqEemployee);
    }

    @ApiOperation(value="更新项目设备管理-机械设备管理人员", notes="更新项目设备管理-机械设备管理人员")
    @ApiImplicitParam(name = "zxEqEemployee", value = "项目设备管理-机械设备管理人员entity", dataType = "ZxEqEemployee")
    @RequireToken
    @PostMapping("/updateZxEqEemployee")
    public ResponseEntity updateZxEqEemployee(@RequestBody(required = false) ZxEqEemployee zxEqEemployee) {
        return zxEqEemployeeService.updateZxEqEemployee(zxEqEemployee);
    }

    @ApiOperation(value="删除项目设备管理-机械设备管理人员", notes="删除项目设备管理-机械设备管理人员")
    @ApiImplicitParam(name = "zxEqEemployeeList", value = "项目设备管理-机械设备管理人员List", dataType = "List<ZxEqEemployee>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEemployee")
    public ResponseEntity batchDeleteUpdateZxEqEemployee(@RequestBody(required = false) List<ZxEqEemployee> zxEqEemployeeList) {
        return zxEqEemployeeService.batchDeleteUpdateZxEqEemployee(zxEqEemployeeList);
    }

    @ApiOperation(value="报表导出机械设备人员表", notes="报表导出机械设备人员表")
    @ApiImplicitParam(name = "zxEqEemployee", value = "项目设备管理-机械设备管理人员entity", dataType = "ZxEqEemployee")
    @PostMapping("/ureportZxEqEemployeeList")
    public List<ZxEqEemployee> ureportZxEqEemployeeList(@RequestBody(required = false) ZxEqEemployee zxEqEemployee) {
        return zxEqEemployeeService.ureportZxEqEemployeeList(zxEqEemployee);
    }
    // 报表查询
    @ApiOperation(value="报表机械设备人员表", notes="报表机械设备人员表")
    @ApiImplicitParam(name = "zxEqEemployee", value = "项目设备管理-机械设备管理人员entity", dataType = "ZxEqEemployee")
    @RequireToken
    @PostMapping("/ureportZxEqEemployeeListIdle")
    public ResponseEntity ureportZxEqEemployeeListIdle(@RequestBody(required = false) ZxEqEemployee zxEqEemployee) {
        return zxEqEemployeeService.ureportZxEqEemployeeListIdle(zxEqEemployee);
    }
}
