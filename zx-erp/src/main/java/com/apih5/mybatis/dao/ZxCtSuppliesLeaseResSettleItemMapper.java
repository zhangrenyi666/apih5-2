package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseResSettleItem;

public interface ZxCtSuppliesLeaseResSettleItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesLeaseResSettleItem record);

    int insertSelective(ZxCtSuppliesLeaseResSettleItem record);

    ZxCtSuppliesLeaseResSettleItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesLeaseResSettleItem record);

    int updateByPrimaryKey(ZxCtSuppliesLeaseResSettleItem record);

    List<ZxCtSuppliesLeaseResSettleItem> selectByZxCtSuppliesLeaseResSettleItemList(ZxCtSuppliesLeaseResSettleItem record);

    int batchDeleteUpdateZxCtSuppliesLeaseResSettleItem(List<ZxCtSuppliesLeaseResSettleItem> recordList, ZxCtSuppliesLeaseResSettleItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
