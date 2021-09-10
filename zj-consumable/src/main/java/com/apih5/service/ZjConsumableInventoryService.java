package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjConsumableInventory;

public interface ZjConsumableInventoryService {

    public ResponseEntity getZjConsumableInventoryListByCondition(ZjConsumableInventory zjConsumableInventory);

    public ResponseEntity getZjConsumableInventoryDetails(ZjConsumableInventory zjConsumableInventory);

    public ResponseEntity saveZjConsumableInventory(ZjConsumableInventory zjConsumableInventory);

    public ResponseEntity updateZjConsumableInventory(ZjConsumableInventory zjConsumableInventory);

    public ResponseEntity batchDeleteUpdateZjConsumableInventory(List<ZjConsumableInventory> zjConsumableInventoryList);

}

