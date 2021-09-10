package com.apih5.controller;

import java.util.List;

import com.apih5.mybatis.pojo.ZjTzEmployeeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProjectAndEmployee;
import com.apih5.service.ZjTzProjectAndEmployeeService;

@RestController
public class ZjTzProjectAndEmployeeController {

    @Autowired(required = true)
    private ZjTzProjectAndEmployeeService zjTzProjectAndEmployeeService;

    @ApiOperation(value="查询项目和人员关联", notes="查询项目和人员关联")
    @ApiImplicitParam(name = "zjTzProjectAndEmployee", value = "项目和人员关联entity", dataType = "ZjTzProjectAndEmployee")
    @RequireToken
    @PostMapping("/getZjTzProjectAndEmployeeList")
    public ResponseEntity getZjTzProjectAndEmployeeList(@RequestBody(required = false) ZjTzProjectAndEmployee zjTzProjectAndEmployee) {
        return zjTzProjectAndEmployeeService.getZjTzProjectAndEmployeeListByCondition(zjTzProjectAndEmployee);
    }

    @ApiOperation(value="查询详情项目和人员关联", notes="查询详情项目和人员关联")
    @ApiImplicitParam(name = "zjTzProjectAndEmployee", value = "项目和人员关联entity", dataType = "ZjTzProjectAndEmployee")
    @RequireToken
    @PostMapping("/getZjTzProjectAndEmployeeDetails")
    public ResponseEntity getZjTzProjectAndEmployeeDetails(@RequestBody(required = false) ZjTzProjectAndEmployee zjTzProjectAndEmployee) {
        return zjTzProjectAndEmployeeService.getZjTzProjectAndEmployeeDetails(zjTzProjectAndEmployee);
    }

    @ApiOperation(value="新增项目和人员关联", notes="新增项目和人员关联")
    @ApiImplicitParam(name = "zjTzProjectAndEmployee", value = "项目和人员关联entity", dataType = "ZjTzProjectAndEmployee")
    @RequireToken
    @PostMapping("/addZjTzProjectAndEmployee")
    public ResponseEntity addZjTzProjectAndEmployee(@RequestBody(required = false) ZjTzProjectAndEmployee zjTzProjectAndEmployee) {
        return zjTzProjectAndEmployeeService.saveZjTzProjectAndEmployee(zjTzProjectAndEmployee);
    }

    @ApiOperation(value="更新项目和人员关联", notes="更新项目和人员关联")
    @ApiImplicitParam(name = "zjTzProjectAndEmployee", value = "项目和人员关联entity", dataType = "ZjTzProjectAndEmployee")
    @RequireToken
    @PostMapping("/updateZjTzProjectAndEmployee")
    public ResponseEntity updateZjTzProjectAndEmployee(@RequestBody(required = false) ZjTzProjectAndEmployee zjTzProjectAndEmployee) {
        return zjTzProjectAndEmployeeService.updateZjTzProjectAndEmployee(zjTzProjectAndEmployee);
    }

    @ApiOperation(value="调出人员", notes="调出人员")
    @ApiImplicitParam(name = "zjTzProjectAndEmployeeList", value = "项目和人员关联List", dataType = "List<ZjTzProjectAndEmployee>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProjectAndEmployee")
    public ResponseEntity batchDeleteUpdateZjTzProjectAndEmployee(@RequestBody(required = false) List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList) {
        return zjTzProjectAndEmployeeService.batchDeleteUpdateZjTzProjectAndEmployee(zjTzProjectAndEmployeeList);
    }




}
