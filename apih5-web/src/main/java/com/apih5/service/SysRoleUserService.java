package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysRoleUser;

public interface SysRoleUserService {

    public ResponseEntity getSysRoleUserListByCondition(SysRoleUser sysRoleUser);

    public ResponseEntity saveSysRoleUser(SysRoleUser sysRoleUser);

    public ResponseEntity updateSysRoleUser(SysRoleUser sysRoleUser);

    public ResponseEntity batchDeleteUpdateSysRoleUser(List<SysRoleUser> sysRoleUserList);

//    public ResponseEntity getSysRoleUserTree(SysRoleUser sysRoleUser);
}

