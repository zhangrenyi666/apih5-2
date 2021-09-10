package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfSWAccidentItem;

public interface ZxSfSWAccidentItemService {

    public ResponseEntity getZxSfSWAccidentItemListByCondition(ZxSfSWAccidentItem zxSfSWAccidentItem);

    public ResponseEntity getZxSfSWAccidentItemDetail(ZxSfSWAccidentItem zxSfSWAccidentItem);

    public ResponseEntity saveZxSfSWAccidentItem(ZxSfSWAccidentItem zxSfSWAccidentItem);

    public ResponseEntity updateZxSfSWAccidentItem(ZxSfSWAccidentItem zxSfSWAccidentItem);

    public ResponseEntity batchDeleteUpdateZxSfSWAccidentItem(List<ZxSfSWAccidentItem> zxSfSWAccidentItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getUReportFormList(ZxSfSWAccidentItem zxSfSWAccidentItem);

    public List<ZxSfSWAccidentItem> UReportForm(ZxSfSWAccidentItem zxSfSWAccidentItem);

    public ResponseEntity getUReportFormComList(ZxSfSWAccidentItem zxSfSWAccidentItem);

    public List<ZxSfSWAccidentItem> UReportFormCom(ZxSfSWAccidentItem zxSfSWAccidentItem);

}
