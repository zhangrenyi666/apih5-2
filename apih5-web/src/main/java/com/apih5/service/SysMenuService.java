package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TreeNodeEntity;
import com.apih5.mybatis.pojo.SysMenu;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public interface SysMenuService {

	public ResponseEntity getSysMenuListByCondition(SysMenu sysMenu);

	public ResponseEntity saveSysMenu(SysMenu sysMenu);

	public ResponseEntity updateSysMenu(SysMenu sysMenu);

	public ResponseEntity updateSysMenuDetails(SysMenu sysMenu);

	public ResponseEntity batchDeleteUpdateSysMenu(List<SysMenu> sysMenuList);

	/**
	 * 获取菜单节点，并形成树形结构
	 * 
	 * @return 树形结构
	 */
	public ResponseEntity getSysMenuAllTree(SysMenu sysMenu);
	
	/**
	 * 获取菜单节点，并形成树形结构
	 * 
	 * @return 树形结构
	 */
	public ResponseEntity getSysMenuBtn(SysMenu sysMenu);

	/**
	 * 根据用户权限获取菜单节点，并形成树形结构
	 * 
	 * @return 树形结构
	 */
	public JSONObject getSysMenuAllTreeByRole(String userKey);

	public ResponseEntity getSysMenuByCondition(SysMenu sysMenu);

	public ResponseEntity moveUpdateSysMenu(SysMenu sysMenu) throws Exception;

	public ResponseEntity tempMenuPath();
}
