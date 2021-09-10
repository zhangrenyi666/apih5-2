package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeasePaySettlementItem;

public interface ZxCtSuppliesLeasePaySettlementItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesLeasePaySettlementItem record);

    int insertSelective(ZxCtSuppliesLeasePaySettlementItem record);

    ZxCtSuppliesLeasePaySettlementItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesLeasePaySettlementItem record);

    int updateByPrimaryKey(ZxCtSuppliesLeasePaySettlementItem record);

    List<ZxCtSuppliesLeasePaySettlementItem> selectByZxCtSuppliesLeasePaySettlementItemList(ZxCtSuppliesLeasePaySettlementItem record);

    int batchDeleteUpdateZxCtSuppliesLeasePaySettlementItem(List<ZxCtSuppliesLeasePaySettlementItem> recordList, ZxCtSuppliesLeasePaySettlementItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
