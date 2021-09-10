package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysWoaHintInformation;

public interface SysWoaHintInformationService {

    public ResponseEntity getSysWoaHintInformationListByCondition(SysWoaHintInformation sysWoaHintInformation);

    public ResponseEntity saveSysWoaHintInformation(SysWoaHintInformation sysWoaHintInformation);

    public ResponseEntity updateSysWoaHintInformation(SysWoaHintInformation sysWoaHintInformation);

    public ResponseEntity batchDeleteUpdateSysWoaHintInformation(List<SysWoaHintInformation> sysWoaHintInformationList);

}

