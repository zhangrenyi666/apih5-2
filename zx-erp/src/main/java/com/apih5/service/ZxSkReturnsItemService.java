package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkReturnsItem;

public interface ZxSkReturnsItemService {

    public ResponseEntity getZxSkReturnsItemListByCondition(ZxSkReturnsItem zxSkReturnsItem);

    public ResponseEntity getZxSkReturnsItemDetail(ZxSkReturnsItem zxSkReturnsItem);

    public ResponseEntity saveZxSkReturnsItem(ZxSkReturnsItem zxSkReturnsItem);

    public ResponseEntity updateZxSkReturnsItem(ZxSkReturnsItem zxSkReturnsItem);

    public ResponseEntity batchDeleteUpdateZxSkReturnsItem(List<ZxSkReturnsItem> zxSkReturnsItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
