package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuProjectTypeItem;

public interface ZxBuProjectTypeItemService {

    public ResponseEntity getZxBuProjectTypeItemListByCondition(ZxBuProjectTypeItem zxBuProjectTypeItem);

    public ResponseEntity getZxBuProjectTypeItemDetail(ZxBuProjectTypeItem zxBuProjectTypeItem);

    public ResponseEntity saveZxBuProjectTypeItem(ZxBuProjectTypeItem zxBuProjectTypeItem);

    public ResponseEntity updateZxBuProjectTypeItem(ZxBuProjectTypeItem zxBuProjectTypeItem);

    public ResponseEntity batchDeleteUpdateZxBuProjectTypeItem(List<ZxBuProjectTypeItem> zxBuProjectTypeItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
