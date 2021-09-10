package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkShopGoodsItem;

public interface ZxSkShopGoodsItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkShopGoodsItem record);

    int insertSelective(ZxSkShopGoodsItem record);

    ZxSkShopGoodsItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkShopGoodsItem record);

    int updateByPrimaryKey(ZxSkShopGoodsItem record);

    List<ZxSkShopGoodsItem> selectByZxSkShopGoodsItemList(ZxSkShopGoodsItem record);

    int batchDeleteUpdateZxSkShopGoodsItem(List<ZxSkShopGoodsItem> recordList, ZxSkShopGoodsItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
