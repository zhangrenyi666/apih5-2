package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuWorksToStockPrice;

public interface ZxBuWorksToStockPriceService {

    public ResponseEntity getZxBuWorksToStockPriceListByCondition(ZxBuWorksToStockPrice zxBuWorksToStockPrice);

    public ResponseEntity getZxBuWorksToStockPriceDetail(ZxBuWorksToStockPrice zxBuWorksToStockPrice);

    public ResponseEntity saveZxBuWorksToStockPrice(ZxBuWorksToStockPrice zxBuWorksToStockPrice);

    public ResponseEntity updateZxBuWorksToStockPrice(ZxBuWorksToStockPrice zxBuWorksToStockPrice);

    public ResponseEntity batchDeleteUpdateZxBuWorksToStockPrice(List<ZxBuWorksToStockPrice> zxBuWorksToStockPriceList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity relevanceZxBuWorksToStockPrice(ZxBuWorksToStockPrice zxBuWorksToStockPrice);

    public ResponseEntity removeRelevanceZxBuWorksToStockPrice(ZxBuWorksToStockPrice zxBuWorksToStockPrice);

}
