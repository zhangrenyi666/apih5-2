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
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.api.sysdb.service.SysUserDepartmentService;

@RestController
public class SysUserDepartmentController {

	@Autowired(required = true)
	private ResponseEntity repEntity;
    @Autowired(required = true)
    private SysUserDepartmentService sysUserDepartmentService;

    @ApiOperation(value="查询用户部门关系", notes="查询用户部门关系")
    @ApiImplicitParam(name = "sysUserDepartment", value = "用户部门关系entity", dataType = "SysUserDepartment")
//    @RequireToken
    @PostMapping("/getSysUserDepartmentList")
    public ResponseEntity getSysUserDepartmentList(@RequestBody(required = false) SysUserDepartment sysUserDepartment) {
        return sysUserDepartmentService.getSysUserDepartmentListByCondition(sysUserDepartment);
    }

    @ApiOperation(value="新增用户部门关系", notes="新增用户部门关系")
    @ApiImplicitParam(name = "sysUserDepartment", value = "用户部门关系entity", dataType = "SysUserDepartment")
    @RequireToken
    @PostMapping("/addSysUserDepartment")
    public ResponseEntity addSysUserDepartment(@RequestBody(required = false) SysUserDepartment sysUserDepartment) {
        return sysUserDepartmentService.saveSysUserDepartment(sysUserDepartment);
    }

    @ApiOperation(value="更新用户部门关系", notes="更新用户部门关系")
    @ApiImplicitParam(name = "sysUserDepartment", value = "用户部门关系entity", dataType = "SysUserDepartment")
    @RequireToken
    @PostMapping("/updateSysUserDepartment")
    public ResponseEntity updateSysUserDepartment(@RequestBody(required = false) SysUserDepartment sysUserDepartment) {
        return sysUserDepartmentService.updateSysUserDepartment(sysUserDepartment);
    }

    @ApiOperation(value="删除用户部门关系", notes="删除用户部门关系")
    @ApiImplicitParam(name = "sysUserDepartmentList", value = "用户部门关系List", dataType = "List<SysUserDepartment>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysUserDepartment")
    public ResponseEntity batchDeleteUpdateSysUserDepartment(@RequestBody(required = false) List<SysUserDepartment> sysUserDepartmentList) {
        return sysUserDepartmentService.batchDeleteUpdateSysUserDepartment(sysUserDepartmentList);
    }
    
    @ApiOperation(value="删除用户部门关系", notes="删除用户部门关系")
    @ApiImplicitParam(name = "sysUserDepartmentList", value = "用户部门关系List", dataType = "List<SysUserDepartment>")
    @RequireToken
    @PostMapping("/syncAddSysUserDepartment")
    public ResponseEntity syncAddSysUserDepartment(@RequestBody(required = false) SysUserDepartment sysUserDepartment) {
    	int flag = sysUserDepartmentService.addSysUserDepartmentCommon(sysUserDepartment);
    	return repEntity.ok(flag);
    }

}
