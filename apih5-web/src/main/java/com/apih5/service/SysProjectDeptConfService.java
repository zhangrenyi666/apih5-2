package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysProjectDeptConf;

public interface SysProjectDeptConfService {

    public ResponseEntity getSysProjectDeptConfListByCondition(SysProjectDeptConf sysProjectDeptConf);

    public ResponseEntity getSysProjectDeptConfDetail(SysProjectDeptConf sysProjectDeptConf);

    public ResponseEntity saveSysProjectDeptConf(SysProjectDeptConf sysProjectDeptConf);

    public ResponseEntity updateSysProjectDeptConf(SysProjectDeptConf sysProjectDeptConf);

    public ResponseEntity batchDeleteUpdateSysProjectDeptConf(List<SysProjectDeptConf> sysProjectDeptConfList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
