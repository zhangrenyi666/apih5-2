package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtDayworkItem;

public interface ZxCtDayworkItemService {

    public ResponseEntity getZxCtDayworkItemListByCondition(ZxCtDayworkItem zxCtDayworkItem);

    public ResponseEntity getZxCtDayworkItemDetail(ZxCtDayworkItem zxCtDayworkItem);

    public ResponseEntity saveZxCtDayworkItem(ZxCtDayworkItem zxCtDayworkItem);

    public ResponseEntity updateZxCtDayworkItem(ZxCtDayworkItem zxCtDayworkItem);

    public ResponseEntity batchDeleteUpdateZxCtDayworkItem(List<ZxCtDayworkItem> zxCtDayworkItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity deleteAllZxCtDayworkItem(ZxCtDayworkItem zxCtDayworkItem);
}
