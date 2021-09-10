package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfExamItem;

public interface ZxSfExamItemService {

    public ResponseEntity getZxSfExamItemListByCondition(ZxSfExamItem zxSfExamItem);

    public ResponseEntity getZxSfExamItemDetail(ZxSfExamItem zxSfExamItem);

    public ResponseEntity saveZxSfExamItem(ZxSfExamItem zxSfExamItem);

    public ResponseEntity updateZxSfExamItem(ZxSfExamItem zxSfExamItem);

    public ResponseEntity batchDeleteUpdateZxSfExamItem(List<ZxSfExamItem> zxSfExamItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxSfExamItemInit(ZxSfExamItem zxSfExamItem);
}
