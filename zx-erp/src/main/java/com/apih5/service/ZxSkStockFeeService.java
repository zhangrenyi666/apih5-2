package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockFee;

public interface ZxSkStockFeeService {

    public ResponseEntity getZxSkStockFeeListByCondition(ZxSkStockFee zxSkStockFee);

    public ResponseEntity getZxSkStockFeeDetail(ZxSkStockFee zxSkStockFee);

    public ResponseEntity saveZxSkStockFee(ZxSkStockFee zxSkStockFee);

    public ResponseEntity updateZxSkStockFee(ZxSkStockFee zxSkStockFee);

    public ResponseEntity batchDeleteUpdateZxSkStockFee(List<ZxSkStockFee> zxSkStockFeeList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


}
