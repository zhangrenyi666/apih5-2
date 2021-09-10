package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysRoleMenu;

public interface SysRoleMenuService {

    public ResponseEntity getSysRoleMenuListByCondition(SysRoleMenu sysRoleMenu);

    public ResponseEntity saveSysRoleMenu(SysRoleMenu sysRoleMenu);

    public ResponseEntity updateSysRoleMenu(SysRoleMenu sysRoleMenu);

    public ResponseEntity batchDeleteUpdateSysRoleMenu(List<SysRoleMenu> sysRoleMenuList);
    
    // --扩展
    public ResponseEntity getSysRoleMenuListByRole(SysRoleMenu sysRoleMenu);

}

