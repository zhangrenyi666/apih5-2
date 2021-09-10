package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;

public interface ZxCtSuppliesContractService {

    public ResponseEntity getZxCtSuppliesContractListByCondition(ZxCtSuppliesContract zxCtSuppliesContract);

    public ResponseEntity getZxCtSuppliesContractDetail(ZxCtSuppliesContract zxCtSuppliesContract);

    public ResponseEntity saveZxCtSuppliesContract(ZxCtSuppliesContract zxCtSuppliesContract);

    public ResponseEntity updateZxCtSuppliesContract(ZxCtSuppliesContract zxCtSuppliesContract);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContract(List<ZxCtSuppliesContract> zxCtSuppliesContractList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesContractListByOrgId(ZxCtSuppliesContract zxCtSuppliesContract);
    
    public ResponseEntity getZxCtSuppliesContractListByFirstId(ZxCtSuppliesContract zxCtSuppliesContract);
    
    public ResponseEntity getZxCtSuppliesContractListAddAgreementNum(ZxCtSuppliesContract zxCtSuppliesContract);
    
    public ResponseEntity updateZxCtSuppliesContractByContId(ZxCtSuppliesContract zxCtSuppliesContract);
}
