package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettlement;

public interface ZxCtSuppliesLeaseResSettlementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesLeaseResSettlement record);

    int insertSelective(ZxCtSuppliesLeaseResSettlement record);

    ZxCtSuppliesLeaseResSettlement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesLeaseResSettlement record);

    int updateByPrimaryKey(ZxCtSuppliesLeaseResSettlement record);

    List<ZxCtSuppliesLeaseResSettlement> selectByZxCtSuppliesLeaseResSettlementList(ZxCtSuppliesLeaseResSettlement record);

    int batchDeleteUpdateZxCtSuppliesLeaseResSettlement(List<ZxCtSuppliesLeaseResSettlement> recordList, ZxCtSuppliesLeaseResSettlement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
