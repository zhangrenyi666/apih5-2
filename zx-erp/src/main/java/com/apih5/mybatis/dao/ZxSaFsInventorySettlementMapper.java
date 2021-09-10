package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlement;
import org.apache.ibatis.annotations.Param;

public interface ZxSaFsInventorySettlementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaFsInventorySettlement record);

    int insertSelective(ZxSaFsInventorySettlement record);

    ZxSaFsInventorySettlement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaFsInventorySettlement record);

    int updateByPrimaryKey(ZxSaFsInventorySettlement record);

    List<ZxSaFsInventorySettlement> selectByZxSaFsInventorySettlementList(ZxSaFsInventorySettlement record);

    int batchDeleteUpdateZxSaFsInventorySettlement(List<ZxSaFsInventorySettlement> recordList, ZxSaFsInventorySettlement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    ZxSaFsInventorySettlement selectBysettlementKey(@Param(value = "settlementId") String settlementId);

    ZxSaFsInventorySettlement countTotalAmt (@Param(value = "record") ZxSaFsInventorySettlement record);
}
