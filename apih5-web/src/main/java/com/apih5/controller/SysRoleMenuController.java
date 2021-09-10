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
import com.apih5.mybatis.pojo.SysRoleMenu;
import com.apih5.service.SysRoleMenuService;

@RestController
public class SysRoleMenuController {

    @Autowired(required = true)
    private SysRoleMenuService sysRoleMenuService;

    @ApiOperation(value="查询角色菜单关系", notes="查询角色菜单关系")
    @ApiImplicitParam(name = "sysRoleMenu", value = "角色菜单关系entity", dataType = "SysRoleMenu")
    @RequireToken
    @PostMapping("/getSysRoleMenuList")
    public ResponseEntity getSysRoleMenuList(@RequestBody(required = false) SysRoleMenu sysRoleMenu) {
        return sysRoleMenuService.getSysRoleMenuListByCondition(sysRoleMenu);
    }

    @ApiOperation(value="新增角色菜单关系", notes="新增角色菜单关系")
    @ApiImplicitParam(name = "sysRoleMenu", value = "角色菜单关系entity", dataType = "SysRoleMenu")
    @RequireToken
    @PostMapping("/addSysRoleMenu")
    public ResponseEntity addSysRoleMenu(@RequestBody(required = false) SysRoleMenu sysRoleMenu) {
        return sysRoleMenuService.saveSysRoleMenu(sysRoleMenu);
    }

    @ApiOperation(value="更新角色菜单关系", notes="更新角色菜单关系")
    @ApiImplicitParam(name = "sysRoleMenu", value = "角色菜单关系entity", dataType = "SysRoleMenu")
    @RequireToken
    @PostMapping("/updateSysRoleMenu")
    public ResponseEntity updateSysRoleMenu(@RequestBody(required = false) SysRoleMenu sysRoleMenu) {
        return sysRoleMenuService.updateSysRoleMenu(sysRoleMenu);
    }
    
    @ApiOperation(value="删除角色菜单关系", notes="删除角色菜单关系")
    @ApiImplicitParam(name = "sysRoleMenuList", value = "角色菜单关系List", dataType = "List<SysRoleMenu>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysRoleMenu")
    public ResponseEntity batchDeleteUpdateSysRoleMenu(@RequestBody(required = false) List<SysRoleMenu> sysRoleMenuList) {
        return sysRoleMenuService.batchDeleteUpdateSysRoleMenu(sysRoleMenuList);
    }

    // -- 扩展
    @ApiOperation(value="角色管理里面获取匹配的菜单", notes="角色管理里面获取匹配的菜单")
    @ApiImplicitParam(name = "sysRoleMenu", value = "角色菜单关系entity", dataType = "SysRoleMenu")
    @RequireToken
    @PostMapping("/getSysRoleMenuListByRole")
    public ResponseEntity getSysRoleMenuListByRole(@RequestBody(required = false) SysRoleMenu sysRoleMenu) {
        return sysRoleMenuService.getSysRoleMenuListByRole(sysRoleMenu);
    }
}
