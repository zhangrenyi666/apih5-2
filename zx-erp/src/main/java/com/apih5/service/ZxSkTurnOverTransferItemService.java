package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnOverTransferItem;

public interface ZxSkTurnOverTransferItemService {

    public ResponseEntity getZxSkTurnOverTransferItemListByCondition(ZxSkTurnOverTransferItem zxSkTurnOverTransferItem);

    public ResponseEntity getZxSkTurnOverTransferItemDetail(ZxSkTurnOverTransferItem zxSkTurnOverTransferItem);

    public ResponseEntity saveZxSkTurnOverTransferItem(ZxSkTurnOverTransferItem zxSkTurnOverTransferItem);

    public ResponseEntity updateZxSkTurnOverTransferItem(ZxSkTurnOverTransferItem zxSkTurnOverTransferItem);

    public ResponseEntity batchDeleteUpdateZxSkTurnOverTransferItem(List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
