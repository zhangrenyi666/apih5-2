package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemDishedOut;

public interface ZxSkStockTransItemDishedOutService {

    public ResponseEntity getZxSkStockTransItemDishedOutListByCondition(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut);

    public ResponseEntity getZxSkStockTransItemDishedOutDetails(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut);

    public ResponseEntity saveZxSkStockTransItemDishedOut(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut);

    public ResponseEntity updateZxSkStockTransItemDishedOut(ZxSkStockTransItemDishedOut zxSkStockTransItemDishedOut);

    public ResponseEntity batchDeleteUpdateZxSkStockTransItemDishedOut(List<ZxSkStockTransItemDishedOut> zxSkStockTransItemDishedOutList);

}

