package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlement;

public interface ZxCtSuppliesLeasePaySettlementService {

    public ResponseEntity getZxCtSuppliesLeasePaySettlementListByCondition(ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement);

    public ResponseEntity getZxCtSuppliesLeasePaySettlementDetail(ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement);

    public ResponseEntity saveZxCtSuppliesLeasePaySettlement(ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement);

    public ResponseEntity updateZxCtSuppliesLeasePaySettlement(ZxCtSuppliesLeasePaySettlement zxCtSuppliesLeasePaySettlement);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeasePaySettlement(List<ZxCtSuppliesLeasePaySettlement> zxCtSuppliesLeasePaySettlementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
