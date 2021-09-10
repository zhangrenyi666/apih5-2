package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopList;

public interface ZxCtSuppliesContrReplenishShopListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrReplenishShopList record);

    int insertSelective(ZxCtSuppliesContrReplenishShopList record);

    ZxCtSuppliesContrReplenishShopList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrReplenishShopList record);

    int updateByPrimaryKey(ZxCtSuppliesContrReplenishShopList record);

    List<ZxCtSuppliesContrReplenishShopList> selectByZxCtSuppliesContrReplenishShopListList(ZxCtSuppliesContrReplenishShopList record);

    int batchDeleteUpdateZxCtSuppliesContrReplenishShopList(List<ZxCtSuppliesContrReplenishShopList> recordList, ZxCtSuppliesContrReplenishShopList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
