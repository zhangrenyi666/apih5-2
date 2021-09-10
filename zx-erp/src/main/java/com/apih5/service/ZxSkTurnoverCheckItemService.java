package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverCheckItem;

public interface ZxSkTurnoverCheckItemService {

    public ResponseEntity getZxSkTurnoverCheckItemListByCondition(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem);

    public ResponseEntity getZxSkTurnoverCheckItemDetail(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem);

    public ResponseEntity saveZxSkTurnoverCheckItem(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem);

    public ResponseEntity updateZxSkTurnoverCheckItem(ZxSkTurnoverCheckItem zxSkTurnoverCheckItem);

    public ResponseEntity batchDeleteUpdateZxSkTurnoverCheckItem(List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
