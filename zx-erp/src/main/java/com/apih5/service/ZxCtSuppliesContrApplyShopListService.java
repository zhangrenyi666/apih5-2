package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyShopList;

public interface ZxCtSuppliesContrApplyShopListService {

    public ResponseEntity getZxCtSuppliesContrApplyShopListListByCondition(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList);

    public ResponseEntity getZxCtSuppliesContrApplyShopListDetail(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList);

    public ResponseEntity saveZxCtSuppliesContrApplyShopList(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList);

    public ResponseEntity updateZxCtSuppliesContrApplyShopList(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyShopList(List<ZxCtSuppliesContrApplyShopList> zxCtSuppliesContrApplyShopListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity updateZxCtSuppliesContrApplyShopListByApplyId(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList);
    
    public ResponseEntity saveZxCtSuppliesContrApplyShopListByApplyId(ZxCtSuppliesContrApplyShopList zxCtSuppliesContrApplyShopList);
    
    public ResponseEntity batchDeleteZxCtSuppliesContrApplyShopListByApplyId(List<ZxCtSuppliesContrApplyShopList> zxCtSuppliesContrApplyShopListList);
}
