package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysWoaRole;

public interface SysWoaRoleService {

    public ResponseEntity getSysWoaRoleListByCondition(SysWoaRole sysWoaRole);

    public ResponseEntity saveSysWoaRole(SysWoaRole sysWoaRole);

    public ResponseEntity updateSysWoaRole(SysWoaRole sysWoaRole);

    public ResponseEntity batchDeleteUpdateSysWoaRole(List<SysWoaRole> sysWoaRoleList);

}

