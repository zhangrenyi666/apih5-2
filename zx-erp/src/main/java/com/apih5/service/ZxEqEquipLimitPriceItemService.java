package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipLimitPriceItem;

public interface ZxEqEquipLimitPriceItemService {

    public ResponseEntity getZxEqEquipLimitPriceItemListByCondition(ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem);

    public ResponseEntity getZxEqEquipLimitPriceItemDetails(ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem);

    public ResponseEntity saveZxEqEquipLimitPriceItem(ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem);

    public ResponseEntity updateZxEqEquipLimitPriceItem(ZxEqEquipLimitPriceItem zxEqEquipLimitPriceItem);

    public ResponseEntity batchDeleteUpdateZxEqEquipLimitPriceItem(List<ZxEqEquipLimitPriceItem> zxEqEquipLimitPriceItemList);

    public List<ZxEqEquipLimitPriceItem> getLimitPriceForm( );
}

