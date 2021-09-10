package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAuditItem;

public interface ZxSaProjectSettleAuditItemService {

    public ResponseEntity getZxSaProjectSettleAuditItemListByCondition(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

    public ResponseEntity getZxSaProjectSettleAuditItemDetail(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

    public ResponseEntity saveZxSaProjectSettleAuditItem(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

    public ResponseEntity updateZxSaProjectSettleAuditItem(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

    public ResponseEntity batchDeleteUpdateZxSaProjectSettleAuditItem(List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity deleteAllZxSaProjectSettleAuditItem(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

	public ResponseEntity getZxSaProjectSettleAuditItemByContractId(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

	public List<ZxSaProjectSettleAuditItem> getZxSaProjectSettleAuditItemListNoToken(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

	public ResponseEntity exportZxSaProjectSettleAuditItemList(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

	public List<ZxSaProjectSettleAuditItem> getZxSaProjectSettleAuditAllItemList(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

	public ResponseEntity exportZxSaProjectSettleAuditCountersignature(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);
}
