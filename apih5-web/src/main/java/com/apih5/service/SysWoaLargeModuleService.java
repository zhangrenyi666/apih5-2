package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysWoaLargeModule;

public interface SysWoaLargeModuleService {

    public ResponseEntity getSysWoaLargeModuleListByCondition(SysWoaLargeModule sysWoaLargeModule);

    public ResponseEntity saveSysWoaLargeModule(SysWoaLargeModule sysWoaLargeModule);

    public ResponseEntity updateSysWoaLargeModule(SysWoaLargeModule sysWoaLargeModule);

    public ResponseEntity batchDeleteUpdateSysWoaLargeModule(List<SysWoaLargeModule> sysWoaLargeModuleList);

    public ResponseEntity getSysMobilIndex(SysWoaLargeModule sysWoaLargeModule);

    public ResponseEntity getSysMobilIndexByData(SysWoaLargeModule sysWoaLargeModule);
}

