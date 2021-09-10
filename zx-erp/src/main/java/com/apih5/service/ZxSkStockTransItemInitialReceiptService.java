package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemInitialReceipt;

public interface ZxSkStockTransItemInitialReceiptService {

    public ResponseEntity getZxSkStockTransItemInitialReceiptListByCondition(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt);

    public ResponseEntity getZxSkStockTransItemInitialReceiptDetails(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt);

    public ResponseEntity saveZxSkStockTransItemInitialReceipt(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt);

    public ResponseEntity updateZxSkStockTransItemInitialReceipt(ZxSkStockTransItemInitialReceipt zxSkStockTransItemInitialReceipt);

    public ResponseEntity batchDeleteUpdateZxSkStockTransItemInitialReceipt(List<ZxSkStockTransItemInitialReceipt> zxSkStockTransItemInitialReceiptList);

}

