package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverInItem;

public interface ZxSkTurnoverInItemService {

    public ResponseEntity getZxSkTurnoverInItemListByCondition(ZxSkTurnoverInItem zxSkTurnoverInItem);

    public ResponseEntity getZxSkTurnoverInItemDetail(ZxSkTurnoverInItem zxSkTurnoverInItem);

    public ResponseEntity saveZxSkTurnoverInItem(ZxSkTurnoverInItem zxSkTurnoverInItem);

    public ResponseEntity updateZxSkTurnoverInItem(ZxSkTurnoverInItem zxSkTurnoverInItem);

    public ResponseEntity batchDeleteUpdateZxSkTurnoverInItem(List<ZxSkTurnoverInItem> zxSkTurnoverInItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
