package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlement;
import com.apih5.mybatis.pojo.ZxSaProjectSet;

public interface ZxCtSuppliesShopPaySettlementService {

    public ResponseEntity getZxCtSuppliesShopPaySettlementListByCondition(ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement);

    public ResponseEntity getZxCtSuppliesShopPaySettlementDetail(ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement);

    public ResponseEntity saveZxCtSuppliesShopPaySettlement(ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement);

    public ResponseEntity updateZxCtSuppliesShopPaySettlement(ZxCtSuppliesShopPaySettlement zxCtSuppliesShopPaySettlement);

    public ResponseEntity batchDeleteUpdateZxCtSuppliesShopPaySettlement(List<ZxCtSuppliesShopPaySettlement> zxCtSuppliesShopPaySettlementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxSaProjectSetListAllListByOrgId(ZxSaProjectSet zxSaProjectSet);
}
