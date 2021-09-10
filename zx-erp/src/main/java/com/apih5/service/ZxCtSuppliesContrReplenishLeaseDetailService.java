package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseDetail;

public interface ZxCtSuppliesContrReplenishLeaseDetailService {

    public ResponseEntity getZxCtSuppliesContrReplenishLeaseDetailListByCondition(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail);

    public ResponseEntity getZxCtSuppliesContrReplenishLeaseDetailDetail(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail);

    public ResponseEntity saveZxCtSuppliesContrReplenishLeaseDetail(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail);

    public ResponseEntity updateZxCtSuppliesContrReplenishLeaseDetail(ZxCtSuppliesContrReplenishLeaseDetail zxCtSuppliesContrReplenishLeaseDetail);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishLeaseDetail(List<ZxCtSuppliesContrReplenishLeaseDetail> zxCtSuppliesContrReplenishLeaseDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
