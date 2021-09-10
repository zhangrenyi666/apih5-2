package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContractChangeDetail;

public interface ZxCtSuppliesContractChangeDetailService {

    public ResponseEntity getZxCtSuppliesContractChangeDetailListByCondition(ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail);

    public ResponseEntity getZxCtSuppliesContractChangeDetailDetail(ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail);

    public ResponseEntity saveZxCtSuppliesContractChangeDetail(ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail);

    public ResponseEntity updateZxCtSuppliesContractChangeDetail(ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContractChangeDetail(List<ZxCtSuppliesContractChangeDetail> zxCtSuppliesContractChangeDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
