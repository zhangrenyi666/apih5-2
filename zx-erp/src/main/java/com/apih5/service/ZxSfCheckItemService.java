package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfCheckItem;

public interface ZxSfCheckItemService {

    public ResponseEntity getZxSfCheckItemListByCondition(ZxSfCheckItem zxSfCheckItem);

    public ResponseEntity getZxSfCheckItemDetail(ZxSfCheckItem zxSfCheckItem);

    public ResponseEntity saveZxSfCheckItem(ZxSfCheckItem zxSfCheckItem);

    public ResponseEntity updateZxSfCheckItem(ZxSfCheckItem zxSfCheckItem);

    public ResponseEntity batchDeleteUpdateZxSfCheckItem(List<ZxSfCheckItem> zxSfCheckItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
