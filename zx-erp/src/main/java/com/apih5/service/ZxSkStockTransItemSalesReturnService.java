package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;

public interface ZxSkStockTransItemSalesReturnService {

    public ResponseEntity getZxSkStockTransItemSalesReturnListByCondition(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn);

    public ResponseEntity getZxSkStockTransItemSalesReturnDetails(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn);

    public ResponseEntity saveZxSkStockTransItemSalesReturn(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn);

    public ResponseEntity updateZxSkStockTransItemSalesReturn(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn);

    public ResponseEntity batchDeleteUpdateZxSkStockTransItemSalesReturn(List<ZxSkStockTransItemSalesReturn> zxSkStockTransItemSalesReturnList);

}

