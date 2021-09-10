package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferSalesReturn;

public interface ZxSkStockTransferSalesReturnService {

    public ResponseEntity getZxSkStockTransferSalesReturnListByCondition(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    public ResponseEntity getZxSkStockTransferSalesReturnDetails(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    public ResponseEntity saveZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    public ResponseEntity updateZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    public ResponseEntity batchDeleteUpdateZxSkStockTransferSalesReturn(List<ZxSkStockTransferSalesReturn> zxSkStockTransferSalesReturnList);

    public ResponseEntity checkZxSkStockTransferSalesReturn(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    public ResponseEntity getZxSkStockTransferSalesReturnNo(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    public ResponseEntity getZxSkStockTransferSalesReturnOutOrgName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    public ResponseEntity getZxSkStockTransferSalesReturnResourceName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);

    public ResponseEntity getZxSkStockTransferSalesReturnResName(ZxSkStockTransferSalesReturn zxSkStockTransferSalesReturn);
}

