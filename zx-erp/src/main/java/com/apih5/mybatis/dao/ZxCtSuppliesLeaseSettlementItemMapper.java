package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementItem;

public interface ZxCtSuppliesLeaseSettlementItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesLeaseSettlementItem record);

    int insertSelective(ZxCtSuppliesLeaseSettlementItem record);

    ZxCtSuppliesLeaseSettlementItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesLeaseSettlementItem record);

    int updateByPrimaryKey(ZxCtSuppliesLeaseSettlementItem record);

    List<ZxCtSuppliesLeaseSettlementItem> selectByZxCtSuppliesLeaseSettlementItemList(ZxCtSuppliesLeaseSettlementItem record);

    int batchDeleteUpdateZxCtSuppliesLeaseSettlementItem(List<ZxCtSuppliesLeaseSettlementItem> recordList, ZxCtSuppliesLeaseSettlementItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
