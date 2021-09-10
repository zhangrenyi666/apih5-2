package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementItem;

public interface ZxCtSuppliesShopSettlementItemService {

    public ResponseEntity getZxCtSuppliesShopSettlementItemListByCondition(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem);

    public ResponseEntity getZxCtSuppliesShopSettlementItemDetail(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem);

    public ResponseEntity saveZxCtSuppliesShopSettlementItem(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem);

    public ResponseEntity updateZxCtSuppliesShopSettlementItem(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopSettlementItem(List<ZxCtSuppliesShopSettlementItem> zxCtSuppliesShopSettlementItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesShopSettlementItemListByConID(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem);
    
    public ResponseEntity updateZxCtSuppliesShopSettlementItemByConID(ZxCtSuppliesShopSettlementItem zxCtSuppliesShopSettlementItem);
}
