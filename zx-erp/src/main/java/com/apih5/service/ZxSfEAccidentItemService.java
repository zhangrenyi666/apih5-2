package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfEAccidentItem;

public interface ZxSfEAccidentItemService {

    public ResponseEntity getZxSfEAccidentItemListByCondition(ZxSfEAccidentItem zxSfEAccidentItem);

    public ResponseEntity getZxSfEAccidentItemDetail(ZxSfEAccidentItem zxSfEAccidentItem);

    public ResponseEntity saveZxSfEAccidentItem(ZxSfEAccidentItem zxSfEAccidentItem);

    public ResponseEntity updateZxSfEAccidentItem(ZxSfEAccidentItem zxSfEAccidentItem);

    public ResponseEntity batchDeleteUpdateZxSfEAccidentItem(List<ZxSfEAccidentItem> zxSfEAccidentItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getUReportFormYearList(ZxSfEAccidentItem zxSfAccidentItem);

    public List<ZxSfEAccidentItem> uReportFormYear(ZxSfEAccidentItem zxSfAccidentItem);

    public ResponseEntity getUReportFormYearListCom(ZxSfEAccidentItem zxSfAccidentItem);

    public List<ZxSfEAccidentItem> uReportFormYearCom(ZxSfEAccidentItem zxSfAccidentItem);
}
