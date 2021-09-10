package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkPurchase;

public interface ZxSkPurchaseService {

    public ResponseEntity getZxSkPurchaseListByCondition(ZxSkPurchase zxSkPurchase);

    public ResponseEntity getZxSkPurchaseDetail(ZxSkPurchase zxSkPurchase);

    public ResponseEntity saveZxSkPurchase(ZxSkPurchase zxSkPurchase);

    public ResponseEntity updateZxSkPurchase(ZxSkPurchase zxSkPurchase);

    public ResponseEntity batchDeleteUpdateZxSkPurchase(List<ZxSkPurchase> zxSkPurchaseList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkPurchaseNo(ZxSkPurchase zxSkPurchase);
}
