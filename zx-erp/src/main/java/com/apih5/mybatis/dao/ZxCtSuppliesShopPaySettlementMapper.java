package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopPaySettlement;

public interface ZxCtSuppliesShopPaySettlementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesShopPaySettlement record);

    int insertSelective(ZxCtSuppliesShopPaySettlement record);

    ZxCtSuppliesShopPaySettlement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesShopPaySettlement record);

    int updateByPrimaryKey(ZxCtSuppliesShopPaySettlement record);

    List<ZxCtSuppliesShopPaySettlement> selectByZxCtSuppliesShopPaySettlementList(ZxCtSuppliesShopPaySettlement record);

    int batchDeleteUpdateZxCtSuppliesShopPaySettlement(List<ZxCtSuppliesShopPaySettlement> recordList, ZxCtSuppliesShopPaySettlement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
