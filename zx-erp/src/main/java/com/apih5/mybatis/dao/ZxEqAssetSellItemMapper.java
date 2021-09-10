package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqAssetSellItem;

public interface ZxEqAssetSellItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqAssetSellItem record);

    int insertSelective(ZxEqAssetSellItem record);

    ZxEqAssetSellItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqAssetSellItem record);

    int updateByPrimaryKey(ZxEqAssetSellItem record);

    List<ZxEqAssetSellItem> selectByZxEqAssetSellItemList(ZxEqAssetSellItem record);

    int batchDeleteUpdateZxEqAssetSellItem(List<ZxEqAssetSellItem> recordList, ZxEqAssetSellItem record);

	List<ZxEqAssetSellItem> ureportZxEqAssetSellItemList(ZxEqAssetSellItem zxEqAssetSellItem);

}

