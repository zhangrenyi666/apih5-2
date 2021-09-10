package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfSpecialEmpItem;

public interface ZxSfSpecialEmpItemService {

    public ResponseEntity getZxSfSpecialEmpItemListByCondition(ZxSfSpecialEmpItem zxSfSpecialEmpItem);

    public ResponseEntity getZxSfSpecialEmpItemDetail(ZxSfSpecialEmpItem zxSfSpecialEmpItem);

    public ResponseEntity saveZxSfSpecialEmpItem(ZxSfSpecialEmpItem zxSfSpecialEmpItem);

    public ResponseEntity updateZxSfSpecialEmpItem(ZxSfSpecialEmpItem zxSfSpecialEmpItem);

    public ResponseEntity batchDeleteUpdateZxSfSpecialEmpItem(List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getFormList(ZxSfSpecialEmpItem zxSfSpecialEmpItem);

    public List<ZxSfSpecialEmpItem> UreportForm(ZxSfSpecialEmpItem zxSfSpecialEmpItem);

    public ResponseEntity getFormListCom(ZxSfSpecialEmpItem zxSfSpecialEmpItem);

    public List<ZxSfSpecialEmpItem> UreportFormCom(ZxSfSpecialEmpItem zxSfSpecialEmpItem);
}
