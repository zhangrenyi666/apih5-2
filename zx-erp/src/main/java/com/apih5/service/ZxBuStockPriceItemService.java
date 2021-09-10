package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuStockPriceItem;
import com.apih5.mybatis.pojo.ZxBuWorksToStockPrice;

public interface ZxBuStockPriceItemService {

    public ResponseEntity getZxBuStockPriceItemListByCondition(ZxBuStockPriceItem zxBuStockPriceItem);

    public ResponseEntity getZxBuStockPriceItemDetail(ZxBuStockPriceItem zxBuStockPriceItem);

    public ResponseEntity saveZxBuStockPriceItem(ZxBuStockPriceItem zxBuStockPriceItem);

    public ResponseEntity updateZxBuStockPriceItem(ZxBuStockPriceItem zxBuStockPriceItem);

    public ResponseEntity batchDeleteUpdateZxBuStockPriceItem(List<ZxBuStockPriceItem> zxBuStockPriceItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getBaseZxBuStockPriceItemList(ZxBuStockPriceItem zxBuStockPriceItem);

    public ResponseEntity getZxBuStockPriceItemResAll(ZxBuStockPriceItem zxBuStockPriceItem);

}
