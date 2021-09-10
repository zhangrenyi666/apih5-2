package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkMakeItem;

public interface ZxSkMakeItemService {

    public ResponseEntity getZxSkMakeItemListByCondition(ZxSkMakeItem zxSkMakeItem);

    public ResponseEntity getZxSkMakeItemDetail(ZxSkMakeItem zxSkMakeItem);

    public ResponseEntity saveZxSkMakeItem(ZxSkMakeItem zxSkMakeItem);

    public ResponseEntity updateZxSkMakeItem(ZxSkMakeItem zxSkMakeItem);

    public ResponseEntity batchDeleteUpdateZxSkMakeItem(List<ZxSkMakeItem> zxSkMakeItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
