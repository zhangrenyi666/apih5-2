package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnResAmoItem;

public interface ZxSkTurnResAmoItemService {

    public ResponseEntity getZxSkTurnResAmoItemListByCondition(ZxSkTurnResAmoItem zxSkTurnResAmoItem);

    public ResponseEntity getZxSkTurnResAmoItemDetail(ZxSkTurnResAmoItem zxSkTurnResAmoItem);

    public ResponseEntity saveZxSkTurnResAmoItem(ZxSkTurnResAmoItem zxSkTurnResAmoItem);

    public ResponseEntity updateZxSkTurnResAmoItem(ZxSkTurnResAmoItem zxSkTurnResAmoItem);

    public ResponseEntity batchDeleteUpdateZxSkTurnResAmoItem(List<ZxSkTurnResAmoItem> zxSkTurnResAmoItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkTurnResAmoItem> ureportZxSkTurnResAmoItem(ZxSkTurnResAmoItem zxSkTurnResAmoItem);

    public ResponseEntity ureportZxSkTurnResAmo(ZxSkTurnResAmoItem zxSkTurnResAmoItem);
}
