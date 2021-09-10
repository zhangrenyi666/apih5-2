package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyLeaseList;

public interface ZxCtSuppliesContrApplyLeaseListService {

    public ResponseEntity getZxCtSuppliesContrApplyLeaseListListByCondition(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList);

    public ResponseEntity getZxCtSuppliesContrApplyLeaseListDetail(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList);

    public ResponseEntity saveZxCtSuppliesContrApplyLeaseList(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList);

    public ResponseEntity updateZxCtSuppliesContrApplyLeaseList(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyLeaseList(List<ZxCtSuppliesContrApplyLeaseList> zxCtSuppliesContrApplyLeaseListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity saveZxCtSuppliesContrApplyLeaseListByApplyId(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList);
    
    public ResponseEntity updateZxCtSuppliesContrApplyLeaseListByApplyId(ZxCtSuppliesContrApplyLeaseList zxCtSuppliesContrApplyLeaseList);
}
