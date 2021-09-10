package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaFsPaySettlementDetail;

public interface ZxSaFsPaySettlementDetailService {

    public ResponseEntity getZxSaFsPaySettlementDetailListByCondition(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail);

    public ResponseEntity getZxSaFsPaySettlementDetailDetail(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail);

    public ResponseEntity saveZxSaFsPaySettlementDetail(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception;

    public ResponseEntity updateZxSaFsPaySettlementDetail(ZxSaFsPaySettlementDetail zxSaFsPaySettlementDetail) throws Exception;

    public ResponseEntity batchDeleteUpdateZxSaFsPaySettlementDetail(List<ZxSaFsPaySettlementDetail> zxSaFsPaySettlementDetailList)throws Exception;

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
