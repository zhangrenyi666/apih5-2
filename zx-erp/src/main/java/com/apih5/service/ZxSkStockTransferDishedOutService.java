package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferDishedOut;

public interface ZxSkStockTransferDishedOutService {

    public ResponseEntity getZxSkStockTransferDishedOutListByCondition(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut);

    public ResponseEntity getZxSkStockTransferDishedOutDetails(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut);

    public ResponseEntity saveZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut);

    public ResponseEntity updateZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut);

    public ResponseEntity batchDeleteUpdateZxSkStockTransferDishedOut(List<ZxSkStockTransferDishedOut> zxSkStockTransferDishedOutList);

    public ResponseEntity getZxSkStockTransferDishedOutNo(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut);

    public ResponseEntity checkZxSkStockTransferDishedOut(ZxSkStockTransferDishedOut zxSkStockTransferDishedOut);

}

