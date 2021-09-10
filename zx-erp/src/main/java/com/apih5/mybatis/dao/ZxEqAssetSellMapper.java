package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqAssetSell;

public interface ZxEqAssetSellMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqAssetSell record);

    int insertSelective(ZxEqAssetSell record);

    ZxEqAssetSell selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqAssetSell record);

    int updateByPrimaryKey(ZxEqAssetSell record);

    List<ZxEqAssetSell> selectByZxEqAssetSellList(ZxEqAssetSell record);

    int batchDeleteUpdateZxEqAssetSell(List<ZxEqAssetSell> recordList, ZxEqAssetSell record);

}

