package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkWarehouse;

public interface ZxSkWarehouseService {

    public ResponseEntity getZxSkWarehouseListByCondition(ZxSkWarehouse zxSkWarehouse);

    public ResponseEntity getZxSkWarehouseDetails(ZxSkWarehouse zxSkWarehouse);

    public ResponseEntity saveZxSkWarehouse(ZxSkWarehouse zxSkWarehouse);

    public ResponseEntity updateZxSkWarehouse(ZxSkWarehouse zxSkWarehouse);

    public ResponseEntity batchDeleteUpdateZxSkWarehouse(List<ZxSkWarehouse> zxSkWarehouseList);

    public ResponseEntity getZxSkWarehouseByParentOrgIDList(ZxSkWarehouse zxSkWarehouse);



}

