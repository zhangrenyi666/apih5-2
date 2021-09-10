package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyShopList;

public interface ZxCtSuppliesContrApplyShopListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrApplyShopList record);

    int insertSelective(ZxCtSuppliesContrApplyShopList record);

    ZxCtSuppliesContrApplyShopList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrApplyShopList record);

    int updateByPrimaryKey(ZxCtSuppliesContrApplyShopList record);

    List<ZxCtSuppliesContrApplyShopList> selectByZxCtSuppliesContrApplyShopListList(ZxCtSuppliesContrApplyShopList record);

    int batchDeleteUpdateZxCtSuppliesContrApplyShopList(List<ZxCtSuppliesContrApplyShopList> recordList, ZxCtSuppliesContrApplyShopList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxCtSuppliesContrApplyShopList> selectSuppliesContrApplyShopListByCondition(ZxCtSuppliesContrApplyShopList record);
}
