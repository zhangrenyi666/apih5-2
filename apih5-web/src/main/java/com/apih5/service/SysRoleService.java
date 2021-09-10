package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.SysRole;

public interface SysRoleService {

    public ResponseEntity getSysRoleListByCondition(SysRole sysRole);

    public ResponseEntity saveSysRole(SysRole sysRole);

    public ResponseEntity updateSysRole(SysRole sysRole);

    public ResponseEntity batchDeleteUpdateSysRole(List<SysRole> sysRoleList);

	/**
	 * 获取权限节点，并形成树形结构
	 * 
	 * @return 树形结构
	 */
	public ResponseEntity getSysRoleAllTree(SysRole sysRole);
}

