package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlementDetail;

public interface ZxSaFsInventorySettlementDetailService {

    public ResponseEntity getZxSaFsInventorySettlementDetailListByCondition(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail);

    public ResponseEntity getZxSaFsInventorySettlementDetailDetail(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail);

    public ResponseEntity saveZxSaFsInventorySettlementDetail(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail);

    public ResponseEntity updateZxSaFsInventorySettlementDetail(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail)  throws Exception;

    public ResponseEntity batchDeleteUpdateZxSaFsInventorySettlementDetail(List<ZxSaFsInventorySettlementDetail> zxSaFsInventorySettlementDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 查询营改增一览
     * @suncg
     *
     * */
    public ResponseEntity  getYgzYiLan(ZxSaFsInventorySettlementDetail zxSaFsInventorySettlementDetail);

    /**
     * 导出营改增一览
     * @suncg
     *
     * */
    public List<ZxSaFsInventorySettlementDetail>  exportYgzYiLan(ZxSaFsInventorySettlementDetail zxSaFsPaySettlementId);
}
