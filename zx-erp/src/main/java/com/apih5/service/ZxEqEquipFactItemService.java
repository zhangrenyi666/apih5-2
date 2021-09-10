package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqEquipFactItem;

public interface ZxEqEquipFactItemService {

    public ResponseEntity getZxEqEquipFactItemListByCondition(ZxEqEquipFactItem zxEqEquipFactItem);

    public ResponseEntity getZxEqEquipFactItemDetails(ZxEqEquipFactItem zxEqEquipFactItem);

    public ResponseEntity saveZxEqEquipFactItem(ZxEqEquipFactItem zxEqEquipFactItem);

    public ResponseEntity updateZxEqEquipFactItem(ZxEqEquipFactItem zxEqEquipFactItem);

    public ResponseEntity batchDeleteUpdateZxEqEquipFactItem(List<ZxEqEquipFactItem> zxEqEquipFactItemList);

}

