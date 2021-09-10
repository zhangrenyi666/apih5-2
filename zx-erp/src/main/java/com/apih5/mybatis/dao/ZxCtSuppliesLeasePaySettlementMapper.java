package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlement;

public interface ZxCtSuppliesLeasePaySettlementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesLeasePaySettlement record);

    int insertSelective(ZxCtSuppliesLeasePaySettlement record);

    ZxCtSuppliesLeasePaySettlement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesLeasePaySettlement record);

    int updateByPrimaryKey(ZxCtSuppliesLeasePaySettlement record);

    List<ZxCtSuppliesLeasePaySettlement> selectByZxCtSuppliesLeasePaySettlementList(ZxCtSuppliesLeasePaySettlement record);

    int batchDeleteUpdateZxCtSuppliesLeasePaySettlement(List<ZxCtSuppliesLeasePaySettlement> recordList, ZxCtSuppliesLeasePaySettlement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
