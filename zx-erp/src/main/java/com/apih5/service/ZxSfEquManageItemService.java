package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfEquManageItem;

public interface ZxSfEquManageItemService {

    public ResponseEntity getZxSfEquManageItemListByCondition(ZxSfEquManageItem zxSfEquManageItem);

    public ResponseEntity getZxSfEquManageItemDetail(ZxSfEquManageItem zxSfEquManageItem);

    public ResponseEntity saveZxSfEquManageItem(ZxSfEquManageItem zxSfEquManageItem);

    public ResponseEntity updateZxSfEquManageItem(ZxSfEquManageItem zxSfEquManageItem);

    public ResponseEntity batchDeleteUpdateZxSfEquManageItem(List<ZxSfEquManageItem> zxSfEquManageItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getUreportFormList(ZxSfEquManageItem zxSfEquManageItem);

    public List<ZxSfEquManageItem> UreportForm(ZxSfEquManageItem zxSfEquManageItem);
}
