package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopEndorseDetail;

public interface ZxCtSuppliesShopEndorseDetailService {

    public ResponseEntity getZxCtSuppliesShopEndorseDetailListByCondition(ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail);

    public ResponseEntity getZxCtSuppliesShopEndorseDetailDetail(ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail);

    public ResponseEntity saveZxCtSuppliesShopEndorseDetail(ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail);

    public ResponseEntity updateZxCtSuppliesShopEndorseDetail(ZxCtSuppliesShopEndorseDetail zxCtSuppliesShopEndorseDetail);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopEndorseDetail(List<ZxCtSuppliesShopEndorseDetail> zxCtSuppliesShopEndorseDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
