package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettlement;

public interface ZxCtSuppliesLeaseResSettlementService {

    public ResponseEntity getZxCtSuppliesLeaseResSettlementListByCondition(ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement);

    public ResponseEntity getZxCtSuppliesLeaseResSettlementDetail(ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement);

    public ResponseEntity saveZxCtSuppliesLeaseResSettlement(ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement);

    public ResponseEntity updateZxCtSuppliesLeaseResSettlement(ZxCtSuppliesLeaseResSettlement zxCtSuppliesLeaseResSettlement);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseResSettlement(List<ZxCtSuppliesLeaseResSettlement> zxCtSuppliesLeaseResSettlementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
