package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseCampChangeIncrease;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementList;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;

public interface ZxCtSuppliesLeaseSettlementListService {

    public ResponseEntity getZxCtSuppliesLeaseSettlementListListByCondition(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);

    public ResponseEntity getZxCtSuppliesLeaseSettlementListDetail(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);

    public ResponseEntity saveZxCtSuppliesLeaseSettlementList(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);

    public ResponseEntity updateZxCtSuppliesLeaseSettlementList(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesLeaseSettlementList(List<ZxCtSuppliesLeaseSettlementList> zxCtSuppliesLeaseSettlementListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesLeaseSettlementListListByOrgId(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);
    
    public ResponseEntity updateZxCtSuppliesLeaseSettlementListByOrgId(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);
    
    public ResponseEntity saveZxCtSuppliesLeaseSettlementListByOrgId(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);
    
    public ResponseEntity getZxCtSuppliesLeaseSettlementListFlowDetail(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);
    
    public ResponseEntity updateZxCtSuppliesLeaseSettlementListFlow(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);
    
    public ResponseEntity saveZxCtSuppliesLeaseSettlementListFlow(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);
    
    public ResponseEntity getZxCtSuppliesLeaseCampChangeIncrease(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);
    
    public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesLeaseSettlementReportList(ZxCtSuppliesLeaseSettlementList zxCtSuppliesLeaseSettlementList);
    
}
