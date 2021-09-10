package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrJYearCreditEvaItem;

public interface ZxCrJYearCreditEvaItemService {

    public ResponseEntity getZxCrJYearCreditEvaItemListByCondition(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem);

    public ResponseEntity getZxCrJYearCreditEvaItemDetail(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem);

    public ResponseEntity saveZxCrJYearCreditEvaItem(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem);

    public ResponseEntity updateZxCrJYearCreditEvaItem(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem);

    public ResponseEntity batchDeleteUpdateZxCrJYearCreditEvaItem(List<ZxCrJYearCreditEvaItem> zxCrJYearCreditEvaItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxCrJYearCreditEvaItemInit(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem);
    
    public ResponseEntity updateZxCrJYearCreditEvaItemAll(List<ZxCrJYearCreditEvaItem> zxCrJYearCreditEvaItem);

}
