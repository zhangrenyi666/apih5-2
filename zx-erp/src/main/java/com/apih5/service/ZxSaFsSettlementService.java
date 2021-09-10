package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;

import javax.servlet.http.HttpServletResponse;

public interface ZxSaFsSettlementService {

    public ResponseEntity getZxSaFsSettlementListByCondition(ZxSaFsSettlement zxSaFsSettlement);

    public ResponseEntity getZxSaFsSettlementDetail(ZxSaFsSettlement zxSaFsSettlement);

    public ResponseEntity saveZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement)throws Exception;

    public ResponseEntity updateZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement);

    public ResponseEntity batchDeleteUpdateZxSaFsSettlement(List<ZxSaFsSettlement> zxSaFsSettlementList) throws Exception ;

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity exportZxSaFsSettlement(ZxSaFsSettlement zxSaFsSettlement, HttpServletResponse response);

    /**
     * 获取结算单初始化顺序号
     *
     * */
    public ResponseEntity countByContract(ZxSaFsSettlement zxSaFsSettlement);

    /**
     *结算单营改增一览查询
     *
     */
    public ResponseEntity getZxSaFsSettlementReport(ZxSaFsSettlement zxSaFsSettlement);

    public ResponseEntity addZxSaFsSettlementApplyFlow(ZxSaFsSettlement zxSaFsSettlement);


}
