package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkLimitPriceItem;

public interface ZxSkLimitPriceItemService {

    public ResponseEntity getZxSkLimitPriceItemListByCondition(ZxSkLimitPriceItem zxSkLimitPriceItem);

    public ResponseEntity getZxSkLimitPriceItemDetails(ZxSkLimitPriceItem zxSkLimitPriceItem);

    public ResponseEntity saveZxSkLimitPriceItem(ZxSkLimitPriceItem zxSkLimitPriceItem);

    public ResponseEntity updateZxSkLimitPriceItem(ZxSkLimitPriceItem zxSkLimitPriceItem);

    public ResponseEntity batchDeleteUpdateZxSkLimitPriceItem(List<ZxSkLimitPriceItem> zxSkLimitPriceItemList);

}

