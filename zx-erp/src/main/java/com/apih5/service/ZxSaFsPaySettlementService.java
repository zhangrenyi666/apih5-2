package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaFsPaySettlement;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;

public interface ZxSaFsPaySettlementService {

    public ResponseEntity getZxSaFsPaySettlementListByCondition(ZxSaFsPaySettlement zxSaFsPaySettlement);

    public ResponseEntity getZxSaFsPaySettlementDetail(ZxSaFsPaySettlement zxSaFsPaySettlement);

    public ResponseEntity saveZxSaFsPaySettlement(ZxSaFsPaySettlement zxSaFsPaySettlement);

    public ResponseEntity updateZxSaFsPaySettlement(ZxSaFsPaySettlement zxSaFsPaySettlement);

    public ResponseEntity batchDeleteUpdateZxSaFsPaySettlement(List<ZxSaFsPaySettlement> zxSaFsPaySettlementList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 查询支付项和支付项明细列表
     * @author suncg
     * @param zxSaFsSettlement
     * */
    public ResponseEntity getZxSaFsPaySettlementAndPaySettlementDetail(ZxSaFsSettlement zxSaFsSettlement);

    /**
     * 查询支付项和支付项明细列表
     * @author suncg
     * @param zxSaFsSettlement
     * */
    public ResponseEntity getZxSaFsPaySettlement(ZxSaFsSettlement zxSaFsSettlement);


}
