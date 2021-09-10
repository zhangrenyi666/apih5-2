package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysWebBg;

public interface SysWebBgService {

    public ResponseEntity getSysWebBgListByCondition(SysWebBg sysWebBg);

    public ResponseEntity getSysWebBgDetails(SysWebBg sysWebBg);

    public ResponseEntity saveSysWebBg(SysWebBg sysWebBg);

    public ResponseEntity updateSysWebBg(SysWebBg sysWebBg);

    public ResponseEntity batchDeleteUpdateSysWebBg(List<SysWebBg> sysWebBgList);

    public ResponseEntity getSysWebBg(SysWebBg sysWebBg);

}

