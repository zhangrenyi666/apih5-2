package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuStockPrice;

public interface ZxBuStockPriceService {

    public ResponseEntity getZxBuStockPriceListByCondition(ZxBuStockPrice zxBuStockPrice);

    public ResponseEntity getZxBuStockPriceDetail(ZxBuStockPrice zxBuStockPrice);

    public ResponseEntity saveZxBuStockPrice(ZxBuStockPrice zxBuStockPrice);

    public ResponseEntity updateZxBuStockPrice(ZxBuStockPrice zxBuStockPrice);

    public ResponseEntity batchDeleteUpdateZxBuStockPrice(List<ZxBuStockPrice> zxBuStockPriceList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity importZxBuStockPrice(ZxBuStockPrice zxBuStockPrice);
}
