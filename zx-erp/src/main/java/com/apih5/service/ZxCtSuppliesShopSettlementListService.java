package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseCampChangeIncrease;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementList;

public interface ZxCtSuppliesShopSettlementListService {

    public ResponseEntity getZxCtSuppliesShopSettlementListListByCondition(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);

    public ResponseEntity getZxCtSuppliesShopSettlementListDetail(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);

    public ResponseEntity saveZxCtSuppliesShopSettlementList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);

    public ResponseEntity updateZxCtSuppliesShopSettlementList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopSettlementList(List<ZxCtSuppliesShopSettlementList> zxCtSuppliesShopSettlementListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesShopSettlementListListByOrgId(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
    public ResponseEntity addZxCtSuppliesShopSettlementListByOrgId(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
    public ResponseEntity updateZxCtSuppliesShopSettlementListByOrgId(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
    public ResponseEntity getZxCtSuppliesShopSettlementListFlowDetail(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
    public ResponseEntity saveZxCtSuppliesShopSettlementListFlow(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
    public ResponseEntity updateZxCtSuppliesShopSettlementListFlow(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
    public ResponseEntity getZxCtSuppliesShopCampChangeIncreaseList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
    public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesShopSettlementReportList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
    
    public List<ZxCtSuppliesLeaseCampChangeIncrease> getZxCtSuppliesShopSettlementSummaryReportList(ZxCtSuppliesShopSettlementList zxCtSuppliesShopSettlementList);
}
