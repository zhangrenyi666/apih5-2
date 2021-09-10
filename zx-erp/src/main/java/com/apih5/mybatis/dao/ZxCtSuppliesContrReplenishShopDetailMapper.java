package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopDetail;

public interface ZxCtSuppliesContrReplenishShopDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrReplenishShopDetail record);

    int insertSelective(ZxCtSuppliesContrReplenishShopDetail record);

    ZxCtSuppliesContrReplenishShopDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrReplenishShopDetail record);

    int updateByPrimaryKey(ZxCtSuppliesContrReplenishShopDetail record);

    List<ZxCtSuppliesContrReplenishShopDetail> selectByZxCtSuppliesContrReplenishShopDetailList(ZxCtSuppliesContrReplenishShopDetail record);

    int batchDeleteUpdateZxCtSuppliesContrReplenishShopDetail(List<ZxCtSuppliesContrReplenishShopDetail> recordList, ZxCtSuppliesContrReplenishShopDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
