package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;

public interface ZxSkStockTransferInitialReceiptService {

    public ResponseEntity getZxSkStockTransferInitialReceiptListByCondition(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt);

    public ResponseEntity getZxSkStockTransferInitialReceiptDetails(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt);

    public ResponseEntity saveZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt);

    public ResponseEntity updateZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt);

    public ResponseEntity batchDeleteUpdateZxSkStockTransferInitialReceipt(List<ZxSkStockTransferInitialReceipt> zxSkStockTransferInitialReceiptList);

    public ResponseEntity getZxSkStockTransferInitialReceiptNo(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt);

    public ResponseEntity checkZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt);



}

