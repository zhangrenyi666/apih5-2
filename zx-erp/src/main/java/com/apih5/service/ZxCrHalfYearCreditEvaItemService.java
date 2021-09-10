package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrColCategory;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEvaItem;

public interface ZxCrHalfYearCreditEvaItemService {

    public ResponseEntity getZxCrHalfYearCreditEvaItemListByCondition(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem);

    public ResponseEntity getZxCrHalfYearCreditEvaItemDetail(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem);

    public ResponseEntity saveZxCrHalfYearCreditEvaItem(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem);

    public ResponseEntity updateZxCrHalfYearCreditEvaItem(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem);

    public ResponseEntity batchDeleteUpdateZxCrHalfYearCreditEvaItem(List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxCrColCategoryTree(ZxCrColCategory zxCrColCategory);
    
    public ResponseEntity getZxCrHalfYearCreditEvaItemInit(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem);
    
    public List<ZxCrHalfYearCreditEvaItem> getZxCrHalfYearCreditEvaItem(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem);
    
    public ResponseEntity batchDeleteUpdateAll();
}
