package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysWoaSmallModule;

public interface SysWoaSmallModuleService {

    public ResponseEntity getSysWoaSmallModuleListByCondition(SysWoaSmallModule sysWoaSmallModule);

    public ResponseEntity saveSysWoaSmallModule(SysWoaSmallModule sysWoaSmallModule);

    public ResponseEntity updateSysWoaSmallModule(SysWoaSmallModule sysWoaSmallModule);

    public ResponseEntity batchDeleteUpdateSysWoaSmallModule(List<SysWoaSmallModule> sysWoaSmallModuleList);

}

