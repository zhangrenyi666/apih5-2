package com.apih5.mybatis.dao;

import java.math.BigDecimal;
import java.util.List;
import com.apih5.mybatis.pojo.ZxSaFsPaySettlement;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import org.apache.ibatis.annotations.Param;

public interface ZxSaFsPaySettlementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaFsPaySettlement record);

    int insertSelective(ZxSaFsPaySettlement record);

    ZxSaFsPaySettlement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaFsPaySettlement record);

    int updateByPrimaryKey(ZxSaFsPaySettlement record);

    List<ZxSaFsPaySettlement> selectByZxSaFsPaySettlementList(ZxSaFsPaySettlement record);

    int batchDeleteUpdateZxSaFsPaySettlement(List<ZxSaFsPaySettlement> recordList, ZxSaFsPaySettlement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    /**
     * 通过结算单ID查询支付项数据
     * @author suncg
     * @param settlementId
     * */
    ZxSaFsPaySettlement selectBysettlementKey(@Param(value = "settlementId") String settlementId);

    /**
     * 通过结算单ID查询支付项数据
     * @author suncg
     * @param zxCtFsContractId,period
     * */
    BigDecimal sumThisAmt(@Param(value = "zxCtFsContractId") String zxCtFsContractId , @Param(value = "period") String period);

}
