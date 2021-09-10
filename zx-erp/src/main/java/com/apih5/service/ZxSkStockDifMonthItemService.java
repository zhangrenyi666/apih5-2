package com.apih5.service;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockDifMonthItem;

public interface ZxSkStockDifMonthItemService {

    public ResponseEntity getZxSkStockDifMonthItemListByCondition(ZxSkStockDifMonthItem zxSkStockDifMonthItem);

    public ResponseEntity getZxSkStockDifMonthItemDetail(ZxSkStockDifMonthItem zxSkStockDifMonthItem);

    public ResponseEntity saveZxSkStockDifMonthItem(ZxSkStockDifMonthItem zxSkStockDifMonthItem);

    public ResponseEntity updateZxSkStockDifMonthItem(ZxSkStockDifMonthItem zxSkStockDifMonthItem);

    public ResponseEntity batchDeleteUpdateZxSkStockDifMonthItem(List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public List<ZxSkStockDifMonthItem> getStockDifMonthForm();

    public List<ZxSkStockDifMonthItem> getStockDifJiDuForm();
    
    public List<ZxSkStockDifMonthItem> getStockDifMonthMaterialCategory();
    
    public ResponseEntity getReceivingDynamicItem(ZxSkStockDifMonthItem zxSkStockDifMonthItem);
    
    public List<ZxSkStockDifMonthItem> getReceivingDynamic(ZxSkStockDifMonthItem zxSkStockDifMonthItem);
    
    public List<ZxSkStockDifMonthItem> getResMoveMonthMP();
    
    public ResponseEntity getResMoveMonthMPItem();

    public ResponseEntity getStockDifMonthFormList(Date period );
    public ResponseEntity getStockDifJiDuFormList(Date period);

}
