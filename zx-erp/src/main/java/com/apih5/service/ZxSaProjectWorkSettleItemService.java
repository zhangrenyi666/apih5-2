package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettleItem;

public interface ZxSaProjectWorkSettleItemService {

    public ResponseEntity getZxSaProjectWorkSettleItemListByCondition(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

    public ResponseEntity getZxSaProjectWorkSettleItemDetail(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

    public ResponseEntity saveZxSaProjectWorkSettleItem(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

    public ResponseEntity updateZxSaProjectWorkSettleItem(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

    public ResponseEntity batchDeleteUpdateZxSaProjectWorkSettleItem(List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity deleteAllZxSaProjectWorkSettleItem(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

	public ResponseEntity getZxSaProjectSettleAuditYgzInfo(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

	public List<ZxSaProjectWorkSettleItem> getZxSaProjectSettleAuditYgzList(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

	public ResponseEntity exportZxSaProjectSettleAuditYgzInfo(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

	public ResponseEntity exportZxSaProjectSettleAuditGcjsjsb(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

	public ResponseEntity exportZxSaProjectSettleAuditGcjsjsbDyb(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);

	public ResponseEntity exportZxSaProjectSettleAuditGcjsjsdYgz(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem);
}
