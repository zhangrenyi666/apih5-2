package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResInOutStockAllAmt;

public interface ZxSkResInOutStockAllAmtService {

    public ResponseEntity getZxSkResInOutStockAllAmtListByCondition(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt);

    public ResponseEntity getZxSkResInOutStockAllAmtDetail(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt);

    public ResponseEntity saveZxSkResInOutStockAllAmt(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt);

    public ResponseEntity updateZxSkResInOutStockAllAmt(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt);

    public ResponseEntity batchDeleteUpdateZxSkResInOutStockAllAmt(List<ZxSkResInOutStockAllAmt> zxSkResInOutStockAllAmtList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkResInOutStockAllAmt> ureportZxSkResInOutStockAllAmt(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt);
    
    public ResponseEntity ureportZxSkResInOutStockAllAmtIdle(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt);
}
