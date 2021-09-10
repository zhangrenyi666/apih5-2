package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtBalanceItem;

public interface ZxCtBalanceItemService {

    public ResponseEntity getZxCtBalanceItemListByCondition(ZxCtBalanceItem zxCtBalanceItem);

    public ResponseEntity getZxCtBalanceItemDetail(ZxCtBalanceItem zxCtBalanceItem);

    public ResponseEntity saveZxCtBalanceItem(ZxCtBalanceItem zxCtBalanceItem);

    public ResponseEntity updateZxCtBalanceItem(ZxCtBalanceItem zxCtBalanceItem);

    public ResponseEntity batchDeleteUpdateZxCtBalanceItem(List<ZxCtBalanceItem> zxCtBalanceItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity updateZxCtBalanceItemById(ZxCtBalanceItem zxCtBalanceItem);
}
