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
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.service.SysMenuService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class SysMenuController {

	@Autowired(required = true)
	private SysMenuService sysMenuService;

	@ApiOperation(value = "查询菜单", notes = "查询菜单")
	@ApiImplicitParam(name = "sysMenu", value = "菜单entity", dataType = "SysMenu")
	@RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
	@RequireToken
	@PostMapping("/getSysMenuList")
	public ResponseEntity getSysMenuList(@RequestBody(required = false) SysMenu sysMenu) {
		return sysMenuService.getSysMenuListByCondition(sysMenu);
	}

	@ApiOperation(value = "新增菜单", notes = "新增菜单")
	@ApiImplicitParam(name = "sysMenu", value = "菜单entity", dataType = "SysMenu")
	@RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
	@RequireToken
	@PostMapping("/addSysMenu")
	public ResponseEntity addSysMenu(@RequestBody(required = false) SysMenu sysMenu) {
		return sysMenuService.saveSysMenu(sysMenu);
	}

	@ApiOperation(value = "更新菜单", notes = "更新菜单")
	@ApiImplicitParam(name = "sysMenu", value = "菜单entity", dataType = "SysMenu")
	@RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
	@RequireToken
	@PostMapping("/updateSysMenu")
	public ResponseEntity updateSysMenu(@RequestBody(required = false) SysMenu sysMenu) {
		return sysMenuService.updateSysMenu(sysMenu);
	}

	@ApiOperation(value = "更新菜单", notes = "更新菜单")
	@ApiImplicitParam(name = "sysMenu", value = "菜单entity", dataType = "SysMenu")
	@RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
	@RequireToken
	@PostMapping("/updateSysMenuDetails")
	public ResponseEntity updateSysMenuDetails(@RequestBody(required = false) SysMenu sysMenu) {
		return sysMenuService.updateSysMenuDetails(sysMenu);
	}

	@ApiOperation(value = "删除菜单", notes = "删除菜单")
	@ApiImplicitParam(name = "sysMenuList", value = "菜单List", dataType = "List<SysMenu>")
	@RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
	@RequireToken
	@PostMapping("/batchDeleteUpdateSysMenu")
	public ResponseEntity batchDeleteUpdateSysMenu(@RequestBody(required = false) List<SysMenu> sysMenuList) {
		return sysMenuService.batchDeleteUpdateSysMenu(sysMenuList);
	}

	// -------------------------
	/**
	 * 获取当前用户登录的菜单节点下的信息（树形结构）
	 * 
	 * @param sysMenu
	 * @return
	 */
	@ApiOperation(value = "查询菜单", notes = "查询菜单")
	@ApiImplicitParam(name = "sysMenu", value = "部门entity", dataType = "SysMenu")
	@RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
	@RequireToken
	@PostMapping("/getSysMenuAllTree")
	public ResponseEntity getSysMenuAllTree(@RequestBody(required = false) SysMenu sysMenu) {
		return sysMenuService.getSysMenuAllTree(sysMenu);
	}
	
	/**
     * 按钮获取，通过前端过来的menuParentId和当前授权信息返回按钮list
     * 
     * @param sysMenu
     * @return
     */
    @ApiOperation(value = "查询菜单按钮", notes = "查询菜单按钮")
    @ApiImplicitParam(name = "sysMenu", value = "部门entity", dataType = "SysMenu")
//    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/getSysMenuBtn")
    public ResponseEntity getSysMenuBtn(@RequestBody(required = false) SysMenu sysMenu) {
        return sysMenuService.getSysMenuBtn(sysMenu);
    }

	@ApiOperation(value = "查询菜单", notes = "查询菜单")
	@ApiImplicitParam(name = "sysMenu", value = "菜单entity", dataType = "SysMenu")
	@RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
	@RequireToken
	@PostMapping("/getSysMenu")
	public ResponseEntity getSysMenu(@RequestBody(required = false) SysMenu sysMenu) {
		return sysMenuService.getSysMenuByCondition(sysMenu);
	}

	@ApiOperation(value = "移动菜单排序", notes = "移动菜单排序")
	@ApiImplicitParam(name = "sysMenu", value = "菜单entity", dataType = "SysMenu")
	@RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
	@RequireToken
	@PostMapping("/moveUpdateSysMenu")
	public ResponseEntity moveUpdateSysMenu(@RequestBody(required = false) SysMenu sysMenu) throws Exception {
		return sysMenuService.moveUpdateSysMenu(sysMenu);
	}
}
