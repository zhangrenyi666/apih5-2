package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferRptNew;

public interface ZxSkStockTransferRptNewService {

    public ResponseEntity getZxSkStockTransferRptNewListByCondition(ZxSkStockTransferRptNew zxSkStockTransferRptNew);

    public ResponseEntity getZxSkStockTransferRptNewDetail(ZxSkStockTransferRptNew zxSkStockTransferRptNew);

    public ResponseEntity saveZxSkStockTransferRptNew(ZxSkStockTransferRptNew zxSkStockTransferRptNew);

    public ResponseEntity updateZxSkStockTransferRptNew(ZxSkStockTransferRptNew zxSkStockTransferRptNew);

    public ResponseEntity batchDeleteUpdateZxSkStockTransferRptNew(List<ZxSkStockTransferRptNew> zxSkStockTransferRptNewList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkStockTransferRptNew> ureportZxSkStockTransferRptNew(ZxSkStockTransferRptNew zxSkStockTransferRptNew);
    
    public ResponseEntity ureportZxSkStockTransferRptNewIdle(ZxSkStockTransferRptNew zxSkStockTransferRptNew);
}
