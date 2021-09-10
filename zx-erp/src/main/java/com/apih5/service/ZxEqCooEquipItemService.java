package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqCooEquipItem;

public interface ZxEqCooEquipItemService {

    public ResponseEntity getZxEqCooEquipItemListByCondition(ZxEqCooEquipItem zxEqCooEquipItem);

    public ResponseEntity getZxEqCooEquipItemDetails(ZxEqCooEquipItem zxEqCooEquipItem);

    public ResponseEntity saveZxEqCooEquipItem(ZxEqCooEquipItem zxEqCooEquipItem);

    public ResponseEntity updateZxEqCooEquipItem(ZxEqCooEquipItem zxEqCooEquipItem);

    public ResponseEntity batchDeleteUpdateZxEqCooEquipItem(List<ZxEqCooEquipItem> zxEqCooEquipItemList);

}

