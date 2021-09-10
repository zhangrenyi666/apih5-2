package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequirePermissions;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.constant.PGIdConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysRoleUser;
import com.apih5.service.SysRoleUserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class SysRoleUserController {

    @Autowired(required = true)
    private SysRoleUserService sysRoleUserService;

    @ApiOperation(value="查询角色用户关系", notes="查询角色用户关系")
    @ApiImplicitParam(name = "sysRoleUser", value = "角色用户关系entity", dataType = "SysRoleUser")
    @RequirePermissions(value = PGIdConst.SYS_MENU_ROLE_MGMT)
    @RequireToken
    @PostMapping("/getSysRoleUserList")
    public ResponseEntity getSysRoleUserList(@RequestBody(required = false) SysRoleUser sysRoleUser) {
        return sysRoleUserService.getSysRoleUserListByCondition(sysRoleUser);
    }

    @ApiOperation(value="新增角色用户关系", notes="新增角色用户关系")
    @ApiImplicitParam(name = "sysRoleUser", value = "角色用户关系entity", dataType = "SysRoleUser")
    @RequirePermissions(value = PGIdConst.SYS_MENU_ROLE_MGMT)
    @RequireToken
    @PostMapping("/addSysRoleUser")
    public ResponseEntity addSysRoleUser(@RequestBody(required = false) SysRoleUser sysRoleUser) {
        return sysRoleUserService.saveSysRoleUser(sysRoleUser);
    }

    @ApiOperation(value="更新角色用户关系", notes="更新角色用户关系")
    @ApiImplicitParam(name = "sysRoleUser", value = "角色用户关系entity", dataType = "SysRoleUser")
    @RequirePermissions(value = PGIdConst.SYS_MENU_ROLE_MGMT)
    @RequireToken
    @PostMapping("/updateSysRoleUser")
    public ResponseEntity updateSysRoleUser(@RequestBody(required = false) SysRoleUser sysRoleUser) {
        return sysRoleUserService.updateSysRoleUser(sysRoleUser);
    }

    @ApiOperation(value="删除角色用户关系", notes="删除角色用户关系")
    @ApiImplicitParam(name = "sysRoleUserList", value = "角色用户关系List", dataType = "List<SysRoleUser>")
    @RequirePermissions(value = PGIdConst.SYS_MENU_ROLE_MGMT)
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysRoleUser")
    public ResponseEntity batchDeleteUpdateSysRoleUser(@RequestBody(required = false) List<SysRoleUser> sysRoleUserList) {
        return sysRoleUserService.batchDeleteUpdateSysRoleUser(sysRoleUserList);
    }
}
