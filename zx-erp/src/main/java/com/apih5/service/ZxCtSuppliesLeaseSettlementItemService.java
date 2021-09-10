package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementItem;

public interface ZxCtSuppliesLeaseSettlementItemService {

    public ResponseEntity getZxCtSuppliesLeaseSettlementItemListByCondition(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem);

    public ResponseEntity getZxCtSuppliesLeaseSettlementItemDetail(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem);

    public ResponseEntity saveZxCtSuppliesLeaseSettlementItem(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem);

    public ResponseEntity updateZxCtSuppliesLeaseSettlementItem(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseSettlementItem(List<ZxCtSuppliesLeaseSettlementItem> zxCtSuppliesLeaseSettlementItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesLeaseSettlementItemListByConID(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem);
    
    public ResponseEntity updateZxCtSuppliesLeaseSettlementItemByConID(ZxCtSuppliesLeaseSettlementItem zxCtSuppliesLeaseSettlementItem);
}
