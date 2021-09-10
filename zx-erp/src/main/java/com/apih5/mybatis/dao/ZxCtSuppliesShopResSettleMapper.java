package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopResSettle;

public interface ZxCtSuppliesShopResSettleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesShopResSettle record);

    int insertSelective(ZxCtSuppliesShopResSettle record);

    ZxCtSuppliesShopResSettle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesShopResSettle record);

    int updateByPrimaryKey(ZxCtSuppliesShopResSettle record);

    List<ZxCtSuppliesShopResSettle> selectByZxCtSuppliesShopResSettleList(ZxCtSuppliesShopResSettle record);

    int batchDeleteUpdateZxCtSuppliesShopResSettle(List<ZxCtSuppliesShopResSettle> recordList, ZxCtSuppliesShopResSettle record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
