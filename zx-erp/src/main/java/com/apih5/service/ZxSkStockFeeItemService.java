package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockFeeItem;

public interface ZxSkStockFeeItemService {

    public ResponseEntity getZxSkStockFeeItemListByCondition(ZxSkStockFeeItem zxSkStockFeeItem);

    public ResponseEntity getZxSkStockFeeItemDetail(ZxSkStockFeeItem zxSkStockFeeItem);

    public ResponseEntity saveZxSkStockFeeItem(ZxSkStockFeeItem zxSkStockFeeItem);

    public ResponseEntity updateZxSkStockFeeItem(ZxSkStockFeeItem zxSkStockFeeItem);

    public ResponseEntity batchDeleteUpdateZxSkStockFeeItem(List<ZxSkStockFeeItem> zxSkStockFeeItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
