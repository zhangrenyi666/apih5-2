package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferTransferOrder;

public interface ZxSkStockTransferTransferOrderService {

    public ResponseEntity getZxSkStockTransferTransferOrderListByCondition(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    public ResponseEntity getZxSkStockTransferTransferOrderDetails(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    public ResponseEntity saveZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    public ResponseEntity updateZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    public ResponseEntity batchDeleteUpdateZxSkStockTransferTransferOrder(List<ZxSkStockTransferTransferOrder> zxSkStockTransferTransferOrderList);

    public ResponseEntity checkZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    public ResponseEntity checkZxSkStockTransferTransferOrderIn(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    public ResponseEntity checkZxSkStockTransferTransferOrderOut(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    public ResponseEntity getZxSkStockTransferTransferOrderNo(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    public ResponseEntity getZxSkStockTransferOrderReceiveOrg(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

}

