package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStock;

public interface ZxSkStockService {

    public ResponseEntity getZxSkStockListByCondition(ZxSkStock zxSkStock);

    public ResponseEntity getZxSkStockDetails(ZxSkStock zxSkStock);

    public ResponseEntity saveZxSkStock(ZxSkStock zxSkStock);

    public ResponseEntity updateZxSkStock(ZxSkStock zxSkStock);

    public ResponseEntity batchDeleteUpdateZxSkStock(List<ZxSkStock> zxSkStockList);

    public ResponseEntity getZxSkStockDataList(ZxSkStock zxSkStock);

    public ResponseEntity getZxSkStockResCategoryDataList(ZxSkStock zxSkStock);

    public ResponseEntity addZxSkStock(List<ZxSkStock> zxSkStockList);

    public ResponseEntity reduceZxSkStock(List<ZxSkStock> zxSkStockList);

    public ResponseEntity reduceZxSkStockPriceChange(List<ZxSkStock> zxSkStockList);

    public ResponseEntity addZxSkStockNumTotalChange(List<ZxSkStock> zxSkStockList);

    public ResponseEntity reduceZxSkStockNumTotalChange(List<ZxSkStock> zxSkStockList);

    public ResponseEntity changeZxSkStockTotal(List<ZxSkStock> zxSkStockList);

}

