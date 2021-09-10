package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrShopList;

public interface ZxCtSuppliesContrShopListService {

    public ResponseEntity getZxCtSuppliesContrShopListListByCondition(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList);

    public ResponseEntity getZxCtSuppliesContrShopListDetail(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList);

    public ResponseEntity saveZxCtSuppliesContrShopList(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList);

    public ResponseEntity updateZxCtSuppliesContrShopList(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrShopList(List<ZxCtSuppliesContrShopList> zxCtSuppliesContrShopListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtSuppliesContrShopListListByContID(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList);
    
    public ResponseEntity getZxCtSuppliesContrShopListByWorkID(ZxCtSuppliesContrShopList zxCtSuppliesContrShopList);
}
