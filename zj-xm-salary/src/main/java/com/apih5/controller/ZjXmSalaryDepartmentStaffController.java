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
import com.apih5.mybatis.pojo.ZjXmSalaryDepartmentStaff;
import com.apih5.service.ZjXmSalaryDepartmentStaffService;

@RestController
public class ZjXmSalaryDepartmentStaffController {

    @Autowired(required = true)
    private ZjXmSalaryDepartmentStaffService zjXmSalaryDepartmentStaffService;

    @ApiOperation(value="查询项目人员配备标准明细", notes="查询项目人员配备标准明细")
    @ApiImplicitParam(name = "zjXmSalaryDepartmentStaff", value = "项目人员配备标准明细entity", dataType = "ZjXmSalaryDepartmentStaff")
    @RequireToken
    @PostMapping("/getZjXmSalaryDepartmentStaffList")
    public ResponseEntity getZjXmSalaryDepartmentStaffList(@RequestBody(required = false) ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff) {
        return zjXmSalaryDepartmentStaffService.getZjXmSalaryDepartmentStaffListByCondition(zjXmSalaryDepartmentStaff);
    }

    @ApiOperation(value="查询详情项目人员配备标准明细", notes="查询详情项目人员配备标准明细")
    @ApiImplicitParam(name = "zjXmSalaryDepartmentStaff", value = "项目人员配备标准明细entity", dataType = "ZjXmSalaryDepartmentStaff")
    @RequireToken
    @PostMapping("/getZjXmSalaryDepartmentStaffDetails")
    public ResponseEntity getZjXmSalaryDepartmentStaffDetails(@RequestBody(required = false) ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff) {
        return zjXmSalaryDepartmentStaffService.getZjXmSalaryDepartmentStaffDetails(zjXmSalaryDepartmentStaff);
    }

    @ApiOperation(value="新增项目人员配备标准明细", notes="新增项目人员配备标准明细")
    @ApiImplicitParam(name = "zjXmSalaryDepartmentStaff", value = "项目人员配备标准明细entity", dataType = "ZjXmSalaryDepartmentStaff")
    @RequireToken
    @PostMapping("/addZjXmSalaryDepartmentStaff")
    public ResponseEntity addZjXmSalaryDepartmentStaff(@RequestBody(required = false) ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff) {
        return zjXmSalaryDepartmentStaffService.saveZjXmSalaryDepartmentStaff(zjXmSalaryDepartmentStaff);
    }

    @ApiOperation(value="更新项目人员配备标准明细", notes="更新项目人员配备标准明细")
    @ApiImplicitParam(name = "zjXmSalaryDepartmentStaff", value = "项目人员配备标准明细entity", dataType = "ZjXmSalaryDepartmentStaff")
    @RequireToken
    @PostMapping("/updateZjXmSalaryDepartmentStaff")
    public ResponseEntity updateZjXmSalaryDepartmentStaff(@RequestBody(required = false) ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff) {
        return zjXmSalaryDepartmentStaffService.updateZjXmSalaryDepartmentStaff(zjXmSalaryDepartmentStaff);
    }

    @ApiOperation(value="删除项目人员配备标准明细", notes="删除项目人员配备标准明细")
    @ApiImplicitParam(name = "zjXmSalaryDepartmentStaffList", value = "项目人员配备标准明细List", dataType = "List<ZjXmSalaryDepartmentStaff>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmSalaryDepartmentStaff")
    public ResponseEntity batchDeleteUpdateZjXmSalaryDepartmentStaff(@RequestBody(required = false) List<ZjXmSalaryDepartmentStaff> zjXmSalaryDepartmentStaffList) {
        return zjXmSalaryDepartmentStaffService.batchDeleteUpdateZjXmSalaryDepartmentStaff(zjXmSalaryDepartmentStaffList);
    }

}
