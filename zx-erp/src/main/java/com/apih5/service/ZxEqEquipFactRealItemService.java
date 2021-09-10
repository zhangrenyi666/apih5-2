package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipFactRealItem;

public interface ZxEqEquipFactRealItemService {

    public ResponseEntity getZxEqEquipFactRealItemListByCondition(ZxEqEquipFactRealItem zxEqEquipFactRealItem);

    public ResponseEntity getZxEqEquipFactRealItemDetails(ZxEqEquipFactRealItem zxEqEquipFactRealItem);

    public ResponseEntity saveZxEqEquipFactRealItem(ZxEqEquipFactRealItem zxEqEquipFactRealItem);

    public ResponseEntity updateZxEqEquipFactRealItem(ZxEqEquipFactRealItem zxEqEquipFactRealItem);

    public ResponseEntity batchDeleteUpdateZxEqEquipFactRealItem(List<ZxEqEquipFactRealItem> zxEqEquipFactRealItemList);

}

