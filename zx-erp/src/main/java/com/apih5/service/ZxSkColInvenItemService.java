package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkColInvenItem;

public interface ZxSkColInvenItemService {

    public ResponseEntity getZxSkColInvenItemListByCondition(ZxSkColInvenItem zxSkColInvenItem);

    public ResponseEntity getZxSkColInvenItemDetail(ZxSkColInvenItem zxSkColInvenItem);

    public ResponseEntity saveZxSkColInvenItem(ZxSkColInvenItem zxSkColInvenItem);

    public ResponseEntity updateZxSkColInvenItem(ZxSkColInvenItem zxSkColInvenItem);

    public ResponseEntity batchDeleteUpdateZxSkColInvenItem(List<ZxSkColInvenItem> zxSkColInvenItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
