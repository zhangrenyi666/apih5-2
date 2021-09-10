package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemTransferOrder;

public interface ZxSkStockTransItemTransferOrderService {

    public ResponseEntity getZxSkStockTransItemTransferOrderListByCondition(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder);

    public ResponseEntity getZxSkStockTransItemTransferOrderDetails(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder);

    public ResponseEntity saveZxSkStockTransItemTransferOrder(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder);

    public ResponseEntity updateZxSkStockTransItemTransferOrder(ZxSkStockTransItemTransferOrder zxSkStockTransItemTransferOrder);

    public ResponseEntity batchDeleteUpdateZxSkStockTransItemTransferOrder(List<ZxSkStockTransItemTransferOrder> zxSkStockTransItemTransferOrderList);

}

