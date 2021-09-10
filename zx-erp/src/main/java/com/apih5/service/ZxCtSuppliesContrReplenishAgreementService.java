package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishAgreement;

public interface ZxCtSuppliesContrReplenishAgreementService {

    public ResponseEntity getZxCtSuppliesContrReplenishAgreementListByCondition(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);

    public ResponseEntity getZxCtSuppliesContrReplenishAgreementDetail(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);

    public ResponseEntity saveZxCtSuppliesContrReplenishAgreement(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);

    public ResponseEntity updateZxCtSuppliesContrReplenishAgreement(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishAgreement(List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity saveZxCtSuppliesContrReplenishAgreementByContrId(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);
    
    public ResponseEntity saveZxCtSuppliesContrReplenishAgreementBycontrAlterID(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);
    
    public ResponseEntity updateZxCtSupContrReplLeaseDetailBycontrAlterID(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);
    
    public ResponseEntity updateZxCtSuppliesContrReplenishAgreementByContrId(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);
    
    public ResponseEntity getZxCtSuppliesContrReplenishAgreementListByContrId(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);
    
    public ResponseEntity getZxCtSuppliesContrReplenishFlowDetail(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);
    
    public ResponseEntity addZxCtSuppliesContrReplenishAgreementFlow(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);
    
    public ResponseEntity updateZxCtSuppliesContrReplenishAgreementFlow(ZxCtSuppliesContrReplenishAgreement zxCtSuppliesContrReplenishAgreement);
    
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishAgreementFlow(List<ZxCtSuppliesContrReplenishAgreement> zxCtSuppliesContrReplenishAgreementList);
}
