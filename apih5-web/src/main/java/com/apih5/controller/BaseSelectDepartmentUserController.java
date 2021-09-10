package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.BaseSelectDepartmentUser;
import com.apih5.framework.api.sysdb.service.BaseSelectDepartmentUserService;
import com.apih5.framework.entity.ResponseEntity;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class BaseSelectDepartmentUserController {

    @Autowired(required = true)
    private BaseSelectDepartmentUserService baseSelectDepartmentUserService;

    @ApiOperation(value="查询业务相关选择部门用户关系", notes="查询业务相关选择部门用户关系")
    @ApiImplicitParam(name = "baseSelectDepartmentUser", value = "业务相关选择部门用户关系entity", dataType = "BaseSelectDepartmentUser")
    @RequireToken
    @PostMapping("/getBaseSelectDepartmentUserList")
    public ResponseEntity getBaseSelectDepartmentUserList(@RequestBody(required = false) BaseSelectDepartmentUser baseSelectDepartmentUser) {
        return baseSelectDepartmentUserService.getBaseSelectDepartmentUserListByCondition(baseSelectDepartmentUser);
    }

    @ApiOperation(value="新增业务相关选择部门用户关系", notes="新增业务相关选择部门用户关系")
    @ApiImplicitParam(name = "baseSelectDepartmentUser", value = "业务相关选择部门用户关系entity", dataType = "BaseSelectDepartmentUser")
    @RequireToken
    @PostMapping("/addBaseSelectDepartmentUser")
    public ResponseEntity addBaseSelectDepartmentUser(@RequestBody(required = false) BaseSelectDepartmentUser baseSelectDepartmentUser) {
        return baseSelectDepartmentUserService.saveBaseSelectDepartmentUser(baseSelectDepartmentUser);
    }

    @ApiOperation(value="更新业务相关选择部门用户关系", notes="更新业务相关选择部门用户关系")
    @ApiImplicitParam(name = "baseSelectDepartmentUser", value = "业务相关选择部门用户关系entity", dataType = "BaseSelectDepartmentUser")
    @RequireToken
    @PostMapping("/updateBaseSelectDepartmentUser")
    public ResponseEntity updateBaseSelectDepartmentUser(@RequestBody(required = false) BaseSelectDepartmentUser baseSelectDepartmentUser) {
        return baseSelectDepartmentUserService.updateBaseSelectDepartmentUser(baseSelectDepartmentUser);
    }

    @ApiOperation(value="删除业务相关选择部门用户关系", notes="删除业务相关选择部门用户关系")
    @ApiImplicitParam(name = "baseSelectDepartmentUserList", value = "业务相关选择部门用户关系List", dataType = "List<BaseSelectDepartmentUser>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseSelectDepartmentUser")
    public ResponseEntity batchDeleteUpdateBaseSelectDepartmentUser(@RequestBody(required = false) List<BaseSelectDepartmentUser> baseSelectDepartmentUserList) {
        return baseSelectDepartmentUserService.batchDeleteUpdateBaseSelectDepartmentUser(baseSelectDepartmentUserList);
    }

}
