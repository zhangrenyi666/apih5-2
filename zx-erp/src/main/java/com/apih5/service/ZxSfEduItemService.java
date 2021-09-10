package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfEduItem;

public interface ZxSfEduItemService {

    public ResponseEntity getZxSfEduItemListByCondition(ZxSfEduItem zxSfEduItem);

    public ResponseEntity getZxSfEduItemDetail(ZxSfEduItem zxSfEduItem);

    public ResponseEntity saveZxSfEduItem(ZxSfEduItem zxSfEduItem);

    public ResponseEntity updateZxSfEduItem(ZxSfEduItem zxSfEduItem);

    public ResponseEntity batchDeleteUpdateZxSfEduItem(List<ZxSfEduItem> zxSfEduItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getUReportFormList(ZxSfEduItem zxSfEduItem);

    public List<ZxSfEduItem> uReportForm(ZxSfEduItem zxSfEduItem);
}
