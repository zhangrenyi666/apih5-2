package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkLimitPriceAdjustItem;

public interface ZxSkLimitPriceAdjustItemService {

    public ResponseEntity getZxSkLimitPriceAdjustItemListByCondition(ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem);

    public ResponseEntity getZxSkLimitPriceAdjustItemDetails(ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem);

    public ResponseEntity saveZxSkLimitPriceAdjustItem(ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem);

    public ResponseEntity updateZxSkLimitPriceAdjustItem(ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem);

    public ResponseEntity batchDeleteUpdateZxSkLimitPriceAdjustItem(List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItemList);

}

