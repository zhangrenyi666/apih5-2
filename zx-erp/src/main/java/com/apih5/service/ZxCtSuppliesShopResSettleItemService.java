package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettleItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import com.apih5.mybatis.pojo.ZxSkStockTransferReceiving;

public interface ZxCtSuppliesShopResSettleItemService {

    public ResponseEntity getZxCtSuppliesShopResSettleItemListByCondition(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem);

    public ResponseEntity getZxCtSuppliesShopResSettleItemDetail(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem);

    public ResponseEntity saveZxCtSuppliesShopResSettleItem(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem);

    public ResponseEntity updateZxCtSuppliesShopResSettleItem(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopResSettleItem(List<ZxCtSuppliesShopResSettleItem> zxCtSuppliesShopResSettleItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesShopResSettleItemListByContID(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem);
    
    public ResponseEntity getZxCtSupReceivingAndReturnListByResID(ZxSkStockTransferReceiving zxSkStockTransferReceiving);
    
    public ResponseEntity addZxCtSuppliesShopResSettleItemByStockId(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem);
    
    public ResponseEntity addZxCtSuppliesShopResSettleItemList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
//    public ResponseEntity addZxCtSuppliesShopResSettleItemByStockId(List<ZxSkStockTransferInitialReceipt> zxCtSuppliesShopResSettleItemList);
    
    public ResponseEntity updateZxCtSuppliesShopResSettleItemByContID(ZxCtSuppliesShopResSettleItem zxCtSuppliesShopResSettleItem);
}
