package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopList;

public interface ZxCtSuppliesContrReplenishShopListService {

    public ResponseEntity getZxCtSuppliesContrReplenishShopListListByCondition(ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList);

    public ResponseEntity getZxCtSuppliesContrReplenishShopListDetail(ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList);

    public ResponseEntity saveZxCtSuppliesContrReplenishShopList(ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList);

    public ResponseEntity updateZxCtSuppliesContrReplenishShopList(ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishShopList(List<ZxCtSuppliesContrReplenishShopList> zxCtSuppliesContrReplenishShopListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
