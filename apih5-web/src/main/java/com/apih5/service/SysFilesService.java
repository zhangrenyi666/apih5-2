package com.apih5.service;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.SysFiles;
import com.apih5.framework.entity.ResponseEntity;

public interface SysFilesService {

    public ResponseEntity getSysFilesListByCondition(SysFiles sysFiles);

    public ResponseEntity saveSysFiles(SysFiles sysFiles);

    public ResponseEntity updateSysFiles(SysFiles sysFiles);

    public ResponseEntity batchDeleteUpdateSysFiles(List<SysFiles> sysFilesList);

}

