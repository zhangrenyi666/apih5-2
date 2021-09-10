package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysFlowUser;

public interface SysFlowUserService {

    public ResponseEntity getSysFlowUserListByCondition(SysFlowUser sysFlowUser);

    public ResponseEntity getSysFlowUserDetail(SysFlowUser sysFlowUser);

    public ResponseEntity saveSysFlowUser(SysFlowUser sysFlowUser);

    public ResponseEntity updateSysFlowUser(SysFlowUser sysFlowUser);

    public ResponseEntity batchDeleteUpdateSysFlowUser(List<SysFlowUser> sysFlowUserList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity saveSysFlowUserByFlow(SysFlowUser sysFlowUser);

    public ResponseEntity deleteUpdateSysFlowUser(SysFlowUser sysFlowUser);
}
