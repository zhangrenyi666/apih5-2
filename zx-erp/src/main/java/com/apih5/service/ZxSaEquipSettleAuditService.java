package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAudit;

public interface ZxSaEquipSettleAuditService {

    public ResponseEntity getZxSaEquipSettleAuditListByCondition(ZxSaEquipSettleAudit zxSaEquipSettleAudit);

    public ResponseEntity getZxSaEquipSettleAuditDetail(ZxSaEquipSettleAudit zxSaEquipSettleAudit);

    public ResponseEntity saveZxSaEquipSettleAudit(ZxSaEquipSettleAudit zxSaEquipSettleAudit);

    public ResponseEntity updateZxSaEquipSettleAudit(ZxSaEquipSettleAudit zxSaEquipSettleAudit);

    public ResponseEntity batchDeleteUpdateZxSaEquipSettleAudit(List<ZxSaEquipSettleAudit> zxSaEquipSettleAuditList);

	public ResponseEntity generateZxSaEquipSettleAuditAutoNum(ZxSaEquipSettleAudit zxSaEquipSettleAudit);

	public ResponseEntity updateZxSaEquipSettleAuditForFlow(ZxSaEquipSettleAudit zxSaEquipSettleAudit);

	public ResponseEntity taxZxSaEquipSettleAudit(ZxSaEquipSettleAudit zxSaEquipSettleAudit);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
