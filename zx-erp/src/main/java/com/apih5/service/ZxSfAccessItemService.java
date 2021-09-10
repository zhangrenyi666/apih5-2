package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfAccessItem;

public interface ZxSfAccessItemService {

    public ResponseEntity getZxSfAccessItemListByCondition(ZxSfAccessItem zxSfAccessItem);

    public ResponseEntity getZxSfAccessItemDetail(ZxSfAccessItem zxSfAccessItem);

    public ResponseEntity saveZxSfAccessItem(ZxSfAccessItem zxSfAccessItem);

    public ResponseEntity updateZxSfAccessItem(ZxSfAccessItem zxSfAccessItem);

    public ResponseEntity batchDeleteUpdateZxSfAccessItem(List<ZxSfAccessItem> zxSfAccessItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
