package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAuditItem;

public interface ZxSaEquipSettleAuditItemService {

    public ResponseEntity getZxSaEquipSettleAuditItemListByCondition(ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem);

    public ResponseEntity getZxSaEquipSettleAuditItemDetail(ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem);

    public ResponseEntity saveZxSaEquipSettleAuditItem(ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem);

    public ResponseEntity updateZxSaEquipSettleAuditItem(ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem);

    public ResponseEntity batchDeleteUpdateZxSaEquipSettleAuditItem(List<ZxSaEquipSettleAuditItem> zxSaEquipSettleAuditItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
