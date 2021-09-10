package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettleItem;

public interface ZxCtSuppliesLeaseResSettleItemService {

    public ResponseEntity getZxCtSuppliesLeaseResSettleItemListByCondition(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem);

    public ResponseEntity getZxCtSuppliesLeaseResSettleItemDetail(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem);

    public ResponseEntity saveZxCtSuppliesLeaseResSettleItem(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem);

    public ResponseEntity updateZxCtSuppliesLeaseResSettleItem(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseResSettleItem(List<ZxCtSuppliesLeaseResSettleItem> zxCtSuppliesLeaseResSettleItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesLeaseResSettleItemListByConID(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem);
    
    public ResponseEntity updateZxCtSuppliesLeaseResSettleItemByConID(ZxCtSuppliesLeaseResSettleItem zxCtSuppliesLeaseResSettleItem);
}
