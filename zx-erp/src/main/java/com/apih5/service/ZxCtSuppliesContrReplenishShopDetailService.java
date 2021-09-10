package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopDetail;

public interface ZxCtSuppliesContrReplenishShopDetailService {

    public ResponseEntity getZxCtSuppliesContrReplenishShopDetailListByCondition(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail);

    public ResponseEntity getZxCtSuppliesContrReplenishShopDetailDetail(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail);

    public ResponseEntity saveZxCtSuppliesContrReplenishShopDetail(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail);

    public ResponseEntity updateZxCtSuppliesContrReplenishShopDetail(ZxCtSuppliesContrReplenishShopDetail zxCtSuppliesContrReplenishShopDetail);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishShopDetail(List<ZxCtSuppliesContrReplenishShopDetail> zxCtSuppliesContrReplenishShopDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
