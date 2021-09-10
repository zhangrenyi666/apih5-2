package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnOverScrapItem;

public interface ZxSkTurnOverScrapItemService {

    public ResponseEntity getZxSkTurnOverScrapItemListByCondition(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem);

    public ResponseEntity getZxSkTurnOverScrapItemDetail(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem);

    public ResponseEntity saveZxSkTurnOverScrapItem(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem);

    public ResponseEntity updateZxSkTurnOverScrapItem(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem);

    public ResponseEntity batchDeleteUpdateZxSkTurnOverScrapItem(List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
