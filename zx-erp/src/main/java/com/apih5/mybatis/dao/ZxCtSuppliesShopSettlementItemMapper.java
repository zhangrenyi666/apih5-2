package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementItem;

public interface ZxCtSuppliesShopSettlementItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesShopSettlementItem record);

    int insertSelective(ZxCtSuppliesShopSettlementItem record);

    ZxCtSuppliesShopSettlementItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesShopSettlementItem record);

    int updateByPrimaryKey(ZxCtSuppliesShopSettlementItem record);

    List<ZxCtSuppliesShopSettlementItem> selectByZxCtSuppliesShopSettlementItemList(ZxCtSuppliesShopSettlementItem record);

    int batchDeleteUpdateZxCtSuppliesShopSettlementItem(List<ZxCtSuppliesShopSettlementItem> recordList, ZxCtSuppliesShopSettlementItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
