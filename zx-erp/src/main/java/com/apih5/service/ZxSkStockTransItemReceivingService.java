package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemReceiving;

public interface ZxSkStockTransItemReceivingService {

    public ResponseEntity getZxSkStockTransItemReceivingListByCondition(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving);

    public ResponseEntity getZxSkStockTransItemReceivingDetails(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving);

    public ResponseEntity saveZxSkStockTransItemReceiving(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving);

    public ResponseEntity updateZxSkStockTransItemReceiving(ZxSkStockTransItemReceiving zxSkStockTransItemReceiving);

    public ResponseEntity batchDeleteUpdateZxSkStockTransItemReceiving(List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList);

}

