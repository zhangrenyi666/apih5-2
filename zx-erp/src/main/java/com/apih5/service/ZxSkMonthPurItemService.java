package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkMonthPurItem;

public interface ZxSkMonthPurItemService {

    public ResponseEntity getZxSkMonthPurItemListByCondition(ZxSkMonthPurItem zxSkMonthPurItem);

    public ResponseEntity getZxSkMonthPurItemDetails(ZxSkMonthPurItem zxSkMonthPurItem);

    public ResponseEntity saveZxSkMonthPurItem(ZxSkMonthPurItem zxSkMonthPurItem);

    public ResponseEntity updateZxSkMonthPurItem(ZxSkMonthPurItem zxSkMonthPurItem);

    public ResponseEntity batchDeleteUpdateZxSkMonthPurItem(List<ZxSkMonthPurItem> zxSkMonthPurItemList);

}

