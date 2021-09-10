package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlementItem;

public interface ZxCtSuppliesShopPaySettlementItemService {

    public ResponseEntity getZxCtSuppliesShopPaySettlementItemListByCondition(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem);

    public ResponseEntity getZxCtSuppliesShopPaySettlementItemDetail(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem);

    public ResponseEntity saveZxCtSuppliesShopPaySettlementItem(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem);

    public ResponseEntity updateZxCtSuppliesShopPaySettlementItem(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlementItem(List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesShopPaySettlementItemListByContID(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem);
    
    public ResponseEntity saveZxCtSuppliesShopPaySettlementItemByPayId(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem);
    
    public ResponseEntity updateZxCtSuppliesShopPaySettlementItemByPayId(ZxCtSuppliesShopPaySettlementItem zxCtSuppliesShopPaySettlementItem);
    
    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlementItemByPayId(List<ZxCtSuppliesShopPaySettlementItem> zxCtSuppliesShopPaySettlementItemList);
}
