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
import com.apih5.mybatis.pojo.ZxEqEProjectEmployee;
import com.apih5.service.ZxEqEProjectEmployeeService;

@RestController
public class ZxEqEProjectEmployeeController {

    @Autowired(required = true)
    private ZxEqEProjectEmployeeService zxEqEProjectEmployeeService;

    @ApiOperation(value="查询项目设备管理-机械设备人员", notes="查询项目设备管理-机械设备人员")
    @ApiImplicitParam(name = "zxEqEProjectEmployee", value = "项目设备管理-机械设备人员entity", dataType = "ZxEqEProjectEmployee")
    @RequireToken
    @PostMapping("/getZxEqEProjectEmployeeList")
    public ResponseEntity getZxEqEProjectEmployeeList(@RequestBody(required = false) ZxEqEProjectEmployee zxEqEProjectEmployee) {
        return zxEqEProjectEmployeeService.getZxEqEProjectEmployeeListByCondition(zxEqEProjectEmployee);
    }

    @ApiOperation(value="查询详情项目设备管理-机械设备人员", notes="查询详情项目设备管理-机械设备人员")
    @ApiImplicitParam(name = "zxEqEProjectEmployee", value = "项目设备管理-机械设备人员entity", dataType = "ZxEqEProjectEmployee")
    @RequireToken
    @PostMapping("/getZxEqEProjectEmployeeDetails")
    public ResponseEntity getZxEqEProjectEmployeeDetails(@RequestBody(required = false) ZxEqEProjectEmployee zxEqEProjectEmployee) {
        return zxEqEProjectEmployeeService.getZxEqEProjectEmployeeDetails(zxEqEProjectEmployee);
    }

    @ApiOperation(value="新增项目设备管理-机械设备人员", notes="新增项目设备管理-机械设备人员")
    @ApiImplicitParam(name = "zxEqEProjectEmployee", value = "项目设备管理-机械设备人员entity", dataType = "ZxEqEProjectEmployee")
    @RequireToken
    @PostMapping("/addZxEqEProjectEmployee")
    public ResponseEntity addZxEqEProjectEmployee(@RequestBody(required = false) ZxEqEProjectEmployee zxEqEProjectEmployee) {
        return zxEqEProjectEmployeeService.saveZxEqEProjectEmployee(zxEqEProjectEmployee);
    }

    @ApiOperation(value="更新项目设备管理-机械设备人员", notes="更新项目设备管理-机械设备人员")
    @ApiImplicitParam(name = "zxEqEProjectEmployee", value = "项目设备管理-机械设备人员entity", dataType = "ZxEqEProjectEmployee")
    @RequireToken
    @PostMapping("/updateZxEqEProjectEmployee")
    public ResponseEntity updateZxEqEProjectEmployee(@RequestBody(required = false) ZxEqEProjectEmployee zxEqEProjectEmployee) {
        return zxEqEProjectEmployeeService.updateZxEqEProjectEmployee(zxEqEProjectEmployee);
    }

    @ApiOperation(value="删除项目设备管理-机械设备人员", notes="删除项目设备管理-机械设备人员")
    @ApiImplicitParam(name = "zxEqEProjectEmployeeList", value = "项目设备管理-机械设备人员List", dataType = "List<ZxEqEProjectEmployee>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEProjectEmployee")
    public ResponseEntity batchDeleteUpdateZxEqEProjectEmployee(@RequestBody(required = false) List<ZxEqEProjectEmployee> zxEqEProjectEmployeeList) {
        return zxEqEProjectEmployeeService.batchDeleteUpdateZxEqEProjectEmployee(zxEqEProjectEmployeeList);
    }

}
