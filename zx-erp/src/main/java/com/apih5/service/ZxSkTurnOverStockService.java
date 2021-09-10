package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnOverStock;

public interface ZxSkTurnOverStockService {

    public ResponseEntity getZxSkTurnOverStockListByCondition(ZxSkTurnOverStock zxSkTurnOverStock);

    public ResponseEntity getZxSkTurnOverStockDetail(ZxSkTurnOverStock zxSkTurnOverStock);

    public ResponseEntity saveZxSkTurnOverStock(ZxSkTurnOverStock zxSkTurnOverStock);

    public ResponseEntity updateZxSkTurnOverStock(ZxSkTurnOverStock zxSkTurnOverStock);

    public ResponseEntity batchDeleteUpdateZxSkTurnOverStock(List<ZxSkTurnOverStock> zxSkTurnOverStockList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity reduceZxSkStock(List<ZxSkTurnOverStock> zxSkTurnOverStockList);

    public ResponseEntity addZxSkStock(List<ZxSkTurnOverStock> zxSkTurnOverStockList);

    public ResponseEntity returnZxSkStock(List<ZxSkTurnOverStock> zxSkTurnOverStockList);
}
