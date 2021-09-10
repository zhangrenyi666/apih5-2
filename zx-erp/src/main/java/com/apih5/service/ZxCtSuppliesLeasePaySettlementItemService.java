package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlementItem;

public interface ZxCtSuppliesLeasePaySettlementItemService {

    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemListByCondition(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem);

    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemDetail(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem);

    public ResponseEntity saveZxCtSuppliesLeasePaySettlementItem(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem);

    public ResponseEntity updateZxCtSuppliesLeasePaySettlementItem(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem(List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesLeasePaySettlementItemListByContID(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem);

    public ResponseEntity saveZxCtSuppliesLeasePaySettlementItemByPayId(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem);

    public ResponseEntity updateZxCtSuppliesLeasePaySettlementItemByPayId(ZxCtSuppliesLeasePaySettlementItem zxCtSuppliesLeasePaySettlementItem);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlementItemByPayId(List<ZxCtSuppliesLeasePaySettlementItem> zxCtSuppliesLeasePaySettlementItemList);
}
