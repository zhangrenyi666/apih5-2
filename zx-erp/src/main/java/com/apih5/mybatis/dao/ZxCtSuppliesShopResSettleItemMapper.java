package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettleItem;

public interface ZxCtSuppliesShopResSettleItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesShopResSettleItem record);

    int insertSelective(ZxCtSuppliesShopResSettleItem record);

    ZxCtSuppliesShopResSettleItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesShopResSettleItem record);

    int updateByPrimaryKey(ZxCtSuppliesShopResSettleItem record);

    List<ZxCtSuppliesShopResSettleItem> selectByZxCtSuppliesShopResSettleItemList(ZxCtSuppliesShopResSettleItem record);

    int batchDeleteUpdateZxCtSuppliesShopResSettleItem(List<ZxCtSuppliesShopResSettleItem> recordList, ZxCtSuppliesShopResSettleItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
