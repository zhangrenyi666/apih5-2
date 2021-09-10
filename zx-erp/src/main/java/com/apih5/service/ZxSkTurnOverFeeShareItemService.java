package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShareItem;

public interface ZxSkTurnOverFeeShareItemService {

    public ResponseEntity getZxSkTurnOverFeeShareItemListByCondition(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem);

    public ResponseEntity getZxSkTurnOverFeeShareItemDetail(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem);

    public ResponseEntity saveZxSkTurnOverFeeShareItem(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem);

    public ResponseEntity updateZxSkTurnOverFeeShareItem(ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem);

    public ResponseEntity batchDeleteUpdateZxSkTurnOverFeeShareItem(List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
