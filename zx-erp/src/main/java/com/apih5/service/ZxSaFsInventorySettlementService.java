package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlement;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;

public interface ZxSaFsInventorySettlementService {

    public ResponseEntity getZxSaFsInventorySettlementListByCondition(ZxSaFsInventorySettlement zxSaFsInventorySettlement);

    public ResponseEntity getZxSaFsInventorySettlementDetail(ZxSaFsInventorySettlement zxSaFsInventorySettlement);

    public ResponseEntity saveZxSaFsInventorySettlement(ZxSaFsInventorySettlement zxSaFsInventorySettlement);

    public ResponseEntity updateZxSaFsInventorySettlement(ZxSaFsInventorySettlement zxSaFsInventorySettlement);

    public ResponseEntity batchDeleteUpdateZxSaFsInventorySettlement(List<ZxSaFsInventorySettlement> zxSaFsInventorySettlementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 查询清单和清单明细列表
     * @author
     * @param zxSaFsInventorySettlement
     * */
    public ResponseEntity getZxSaFsInventoryAndInventoryDetail(ZxSaFsInventorySettlement zxSaFsInventorySettlement);

    /**
     * 查询清单
     * @author
     * @param zxSaFsSettlement
     * */
    public ResponseEntity getZxSaFsInventory(ZxSaFsSettlement zxSaFsSettlement);
}
