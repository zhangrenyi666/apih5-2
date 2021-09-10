package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettle;

public interface ZxCtSuppliesShopResSettleService {

    public ResponseEntity getZxCtSuppliesShopResSettleListByCondition(ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle);

    public ResponseEntity getZxCtSuppliesShopResSettleDetail(ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle);

    public ResponseEntity saveZxCtSuppliesShopResSettle(ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle);

    public ResponseEntity updateZxCtSuppliesShopResSettle(ZxCtSuppliesShopResSettle zxCtSuppliesShopResSettle);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopResSettle(List<ZxCtSuppliesShopResSettle> zxCtSuppliesShopResSettleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
