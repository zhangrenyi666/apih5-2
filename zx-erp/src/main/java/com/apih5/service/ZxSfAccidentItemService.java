package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfAccidentItem;

public interface ZxSfAccidentItemService {

    public ResponseEntity getZxSfAccidentItemListByCondition(ZxSfAccidentItem zxSfAccidentItem);

    public ResponseEntity getZxSfAccidentItemDetail(ZxSfAccidentItem zxSfAccidentItem);

    public ResponseEntity saveZxSfAccidentItem(ZxSfAccidentItem zxSfAccidentItem);

    public ResponseEntity updateZxSfAccidentItem(ZxSfAccidentItem zxSfAccidentItem);

    public ResponseEntity batchDeleteUpdateZxSfAccidentItem(List<ZxSfAccidentItem> zxSfAccidentItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getUReportFormList(ZxSfAccidentItem zxSfAccidentItem);

    public List<ZxSfAccidentItem> uReportForm(ZxSfAccidentItem zxSfAccidentItem);

    public ResponseEntity getUReportFormComList(ZxSfAccidentItem zxSfAccidentItem);

    public List<ZxSfAccidentItem> uReportFormCom(ZxSfAccidentItem zxSfAccidentItem);




}
