package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSysItem;

public interface ZxGcsgCtPriceSysItemService {

    public ResponseEntity getZxGcsgCtPriceSysItemListByCondition(ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem);

    public ResponseEntity getZxGcsgCtPriceSysItemDetail(ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem);

    public ResponseEntity saveZxGcsgCtPriceSysItem(ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem);

    public ResponseEntity updateZxGcsgCtPriceSysItem(ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem);

    public ResponseEntity batchDeleteUpdateZxGcsgCtPriceSysItem(List<ZxGcsgCtPriceSysItem> zxGcsgCtPriceSysItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
