package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfJuExamItem;

public interface ZxSfJuExamItemService {

    public ResponseEntity getZxSfJuExamItemListByCondition(ZxSfJuExamItem zxSfJuExamItem);

    public ResponseEntity getZxSfJuExamItemDetail(ZxSfJuExamItem zxSfJuExamItem);

    public ResponseEntity saveZxSfJuExamItem(ZxSfJuExamItem zxSfJuExamItem);

    public ResponseEntity updateZxSfJuExamItem(ZxSfJuExamItem zxSfJuExamItem);

    public ResponseEntity batchDeleteUpdateZxSfJuExamItem(List<ZxSfJuExamItem> zxSfJuExamItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxSfJuExamItemInit(ZxSfJuExamItem zxSfJuExamItem);
}
