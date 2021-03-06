package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemDiskFillingStorage;

public interface ZxSkStockTransItemDiskFillingStorageService {

    public ResponseEntity getZxSkStockTransItemDiskFillingStorageListByCondition(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage);

    public ResponseEntity getZxSkStockTransItemDiskFillingStorageDetails(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage);

    public ResponseEntity saveZxSkStockTransItemDiskFillingStorage(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage);

    public ResponseEntity updateZxSkStockTransItemDiskFillingStorage(ZxSkStockTransItemDiskFillingStorage zxSkStockTransItemDiskFillingStorage);

    public ResponseEntity batchDeleteUpdateZxSkStockTransItemDiskFillingStorage(List<ZxSkStockTransItemDiskFillingStorage> zxSkStockTransItemDiskFillingStorageList);

}

