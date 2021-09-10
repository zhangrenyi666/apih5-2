package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferRpt;

public interface ZxSkStockTransferRptService {

    public ResponseEntity getZxSkStockTransferRptListByCondition(ZxSkStockTransferRpt zxSkStockTransferRpt);

    public ResponseEntity getZxSkStockTransferRptDetail(ZxSkStockTransferRpt zxSkStockTransferRpt);

    public ResponseEntity saveZxSkStockTransferRpt(ZxSkStockTransferRpt zxSkStockTransferRpt);

    public ResponseEntity updateZxSkStockTransferRpt(ZxSkStockTransferRpt zxSkStockTransferRpt);

    public ResponseEntity batchDeleteUpdateZxSkStockTransferRpt(List<ZxSkStockTransferRpt> zxSkStockTransferRptList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkStockTransferRpt> ureportZxSkStockTransferRpt(ZxSkStockTransferRpt zxSkStockTransferRpt);
    
    public ResponseEntity ureportZxSkStockTransferRptIdle(ZxSkStockTransferRpt zxSkStockTransferRpt);
}
