package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverOutItem;

public interface ZxSkTurnoverOutItemService {

    public ResponseEntity getZxSkTurnoverOutItemListByCondition(ZxSkTurnoverOutItem zxSkTurnoverOutItem);

    public ResponseEntity getZxSkTurnoverOutItemDetail(ZxSkTurnoverOutItem zxSkTurnoverOutItem);

    public ResponseEntity saveZxSkTurnoverOutItem(ZxSkTurnoverOutItem zxSkTurnoverOutItem);

    public ResponseEntity updateZxSkTurnoverOutItem(ZxSkTurnoverOutItem zxSkTurnoverOutItem);

    public ResponseEntity batchDeleteUpdateZxSkTurnoverOutItem(List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
