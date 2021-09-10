package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAudit;

public interface ZxSaProjectSettleAuditService {

    public ResponseEntity getZxSaProjectSettleAuditListByCondition(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

    public ResponseEntity getZxSaProjectSettleAuditDetail(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

    public ResponseEntity saveZxSaProjectSettleAudit(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

    public ResponseEntity updateZxSaProjectSettleAudit(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

    public ResponseEntity batchDeleteUpdateZxSaProjectSettleAudit(List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxSaProjectSettleAuditProjectList(ZxSaProjectSettleAudit zxSaProjectSettleAudit);
    
    public ResponseEntity getZxSaProjectSettleAuditContractNoList(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

	public ResponseEntity updateZxSaProjectSettleAuditOnCommitFlow(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

	public ZxSaProjectSettleAudit getZxSaProjectSettleAuditDetailNoToken(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

	public ResponseEntity addZxSaProjectSettleAuditOnCommitFlow(ZxSaProjectSettleAudit zxSaProjectSettleAudit);
}
