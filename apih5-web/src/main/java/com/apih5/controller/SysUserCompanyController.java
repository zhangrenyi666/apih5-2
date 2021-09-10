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
import com.apih5.framework.api.sysdb.entity.SysUserCompany;
import com.apih5.framework.api.sysdb.service.SysUserCompanyService;

@RestController
public class SysUserCompanyController {

    @Autowired(required = true)
    private SysUserCompanyService sysUserCompanyService;

    @ApiOperation(value="查询用户公司关系", notes="查询用户公司关系")
    @ApiImplicitParam(name = "sysUserCompany", value = "用户公司关系entity", dataType = "SysUserCompany")
    @RequireToken
    @PostMapping("/getSysUserCompanyList")
    public ResponseEntity getSysUserCompanyList(@RequestBody(required = false) SysUserCompany sysUserCompany) {
        return sysUserCompanyService.getSysUserCompanyListByCondition(sysUserCompany);
    }

    @ApiOperation(value="新增用户公司关系", notes="新增用户公司关系")
    @ApiImplicitParam(name = "sysUserCompany", value = "用户公司关系entity", dataType = "SysUserCompany")
    @RequireToken
    @PostMapping("/addSysUserCompany")
    public ResponseEntity addSysUserCompany(@RequestBody(required = false) SysUserCompany sysUserCompany) {
        return sysUserCompanyService.saveSysUserCompany(sysUserCompany);
    }

    @ApiOperation(value="更新用户公司关系", notes="更新用户公司关系")
    @ApiImplicitParam(name = "sysUserCompany", value = "用户公司关系entity", dataType = "SysUserCompany")
    @RequireToken
    @PostMapping("/updateSysUserCompany")
    public ResponseEntity updateSysUserCompany(@RequestBody(required = false) SysUserCompany sysUserCompany) {
        return sysUserCompanyService.updateSysUserCompany(sysUserCompany);
    }

    @ApiOperation(value="删除用户公司关系", notes="删除用户公司关系")
    @ApiImplicitParam(name = "sysUserCompanyList", value = "用户公司关系List", dataType = "List<SysUserCompany>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysUserCompany")
    public ResponseEntity batchDeleteUpdateSysUserCompany(@RequestBody(required = false) List<SysUserCompany> sysUserCompanyList) {
        return sysUserCompanyService.batchDeleteUpdateSysUserCompany(sysUserCompanyList);
    }

}
