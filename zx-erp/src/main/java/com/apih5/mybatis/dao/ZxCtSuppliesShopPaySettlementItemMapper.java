package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlementItem;

public interface ZxCtSuppliesShopPaySettlementItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesShopPaySettlementItem record);

    int insertSelective(ZxCtSuppliesShopPaySettlementItem record);

    ZxCtSuppliesShopPaySettlementItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesShopPaySettlementItem record);

    int updateByPrimaryKey(ZxCtSuppliesShopPaySettlementItem record);

    List<ZxCtSuppliesShopPaySettlementItem> selectByZxCtSuppliesShopPaySettlementItemList(ZxCtSuppliesShopPaySettlementItem record);

    int batchDeleteUpdateZxCtSuppliesShopPaySettlementItem(List<ZxCtSuppliesShopPaySettlementItem> recordList, ZxCtSuppliesShopPaySettlementItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
