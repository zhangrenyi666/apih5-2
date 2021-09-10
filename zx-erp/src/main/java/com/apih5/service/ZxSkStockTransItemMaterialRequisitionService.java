package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemMaterialRequisition;

public interface ZxSkStockTransItemMaterialRequisitionService {

    public ResponseEntity getZxSkStockTransItemMaterialRequisitionListByCondition(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition);

    public ResponseEntity getZxSkStockTransItemMaterialRequisitionDetails(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition);

    public ResponseEntity saveZxSkStockTransItemMaterialRequisition(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition);

    public ResponseEntity updateZxSkStockTransItemMaterialRequisition(ZxSkStockTransItemMaterialRequisition zxSkStockTransItemMaterialRequisition);

    public ResponseEntity batchDeleteUpdateZxSkStockTransItemMaterialRequisition(List<ZxSkStockTransItemMaterialRequisition> zxSkStockTransItemMaterialRequisitionList);

}
