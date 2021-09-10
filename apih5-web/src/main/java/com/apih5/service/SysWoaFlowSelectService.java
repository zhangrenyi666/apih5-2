package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysWoaFlowSelect;

public interface SysWoaFlowSelectService {

    public ResponseEntity getSysWoaFlowSelectListByCondition(SysWoaFlowSelect sysWoaFlowSelect);

    public ResponseEntity saveSysWoaFlowSelect(SysWoaFlowSelect sysWoaFlowSelect);

    public ResponseEntity updateSysWoaFlowSelect(SysWoaFlowSelect sysWoaFlowSelect);

    public ResponseEntity batchDeleteUpdateSysWoaFlowSelect(List<SysWoaFlowSelect> sysWoaFlowSelectList);

    public ResponseEntity getSysWoaFlowSelectDetailByCondition(SysWoaFlowSelect sysWoaFlowSelect);
}

