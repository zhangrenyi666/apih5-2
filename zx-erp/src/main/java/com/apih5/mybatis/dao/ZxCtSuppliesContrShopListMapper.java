package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrShopList;

public interface ZxCtSuppliesContrShopListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrShopList record);

    int insertSelective(ZxCtSuppliesContrShopList record);

    ZxCtSuppliesContrShopList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrShopList record);

    int updateByPrimaryKey(ZxCtSuppliesContrShopList record);

    List<ZxCtSuppliesContrShopList> selectByZxCtSuppliesContrShopListList(ZxCtSuppliesContrShopList record);

    int batchDeleteUpdateZxCtSuppliesContrShopList(List<ZxCtSuppliesContrShopList> recordList, ZxCtSuppliesContrShopList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxCtSuppliesContrShopList> selectByZxCtSuppliesContrShopListByContId(ZxCtSuppliesContrShopList record);
}
