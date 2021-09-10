package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysWoaAddFlow;

public interface SysWoaAddFlowService {

    public ResponseEntity getSysWoaAddFlowListByCondition(SysWoaAddFlow sysWoaAddFlow);

    public ResponseEntity saveSysWoaAddFlow(SysWoaAddFlow sysWoaAddFlow);

    public ResponseEntity updateSysWoaAddFlow(SysWoaAddFlow sysWoaAddFlow);

    public ResponseEntity batchDeleteUpdateSysWoaAddFlow(List<SysWoaAddFlow> sysWoaAddFlowList);

    public ResponseEntity getSysWoaAddFlowListByRole(SysWoaAddFlow sysWoaAddFlow);
}

