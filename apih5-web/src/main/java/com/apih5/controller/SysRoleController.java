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
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.SysRole;
import com.apih5.service.SysRoleService;

@RestController
public class SysRoleController {

    @Autowired(required = true)
    private SysRoleService sysRoleService;

    @ApiOperation(value="查询角色", notes="查询角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/getSysRoleList")
    public ResponseEntity getSysRoleList(@RequestBody(required = false) SysRole sysRole) {
        return sysRoleService.getSysRoleListByCondition(sysRole);
    }

    @ApiOperation(value="新增角色", notes="新增角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/addSysRole")
    public ResponseEntity addSysRole(@RequestBody(required = false) SysRole sysRole) {
        return sysRoleService.saveSysRole(sysRole);
    }

    @ApiOperation(value="更新角色", notes="更新角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/updateSysRole")
    public ResponseEntity updateSysRole(@RequestBody(required = false) SysRole sysRole) {
        return sysRoleService.updateSysRole(sysRole);
    }

    @ApiOperation(value="删除角色", notes="删除角色")
    @ApiImplicitParam(name = "sysRoleList", value = "角色List", dataType = "List<SysRole>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysRole")
    public ResponseEntity batchDeleteUpdateSysRole(@RequestBody(required = false) List<SysRole> sysRoleList) {
        return sysRoleService.batchDeleteUpdateSysRole(sysRoleList);
    }
    
    //--------------
    /**
     * 获取当前用户登录的菜单节点下的信息（树形结构）
     * 
     * @param sysMenu
     * @return
     */
    @ApiOperation(value="查询菜单", notes="查询菜单")
    @ApiImplicitParam(name = "sysMenu", value = "部门entity", dataType = "SysMenu")
    @RequireToken
    @PostMapping("/getSysRoleAllTree")
    public ResponseEntity getSysRoleAllTree(@RequestBody(required = false) SysRole sysRole) {
    	return sysRoleService.getSysRoleAllTree(sysRole);
    }

}
