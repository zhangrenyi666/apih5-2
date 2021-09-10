package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkWornOutItem;

public interface ZxSkWornOutItemService {

    public ResponseEntity getZxSkWornOutItemListByCondition(ZxSkWornOutItem zxSkWornOutItem);

    public ResponseEntity getZxSkWornOutItemDetail(ZxSkWornOutItem zxSkWornOutItem);

    public ResponseEntity saveZxSkWornOutItem(ZxSkWornOutItem zxSkWornOutItem);

    public ResponseEntity updateZxSkWornOutItem(ZxSkWornOutItem zxSkWornOutItem);

    public ResponseEntity batchDeleteUpdateZxSkWornOutItem(List<ZxSkWornOutItem> zxSkWornOutItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
