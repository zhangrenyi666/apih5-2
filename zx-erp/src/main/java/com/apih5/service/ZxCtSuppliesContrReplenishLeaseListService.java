package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseList;

public interface ZxCtSuppliesContrReplenishLeaseListService {

    public ResponseEntity getZxCtSuppliesContrReplenishLeaseListListByCondition(ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList);

    public ResponseEntity getZxCtSuppliesContrReplenishLeaseListDetail(ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList);

    public ResponseEntity saveZxCtSuppliesContrReplenishLeaseList(ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList);

    public ResponseEntity updateZxCtSuppliesContrReplenishLeaseList(ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList(List<ZxCtSuppliesContrReplenishLeaseList> zxCtSuppliesContrReplenishLeaseListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
