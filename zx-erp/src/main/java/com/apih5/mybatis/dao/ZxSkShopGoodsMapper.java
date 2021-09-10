package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkShopGoods;

public interface ZxSkShopGoodsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkShopGoods record);

    int insertSelective(ZxSkShopGoods record);

    ZxSkShopGoods selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkShopGoods record);

    int updateByPrimaryKey(ZxSkShopGoods record);

    List<ZxSkShopGoods> selectByZxSkShopGoodsList(ZxSkShopGoods record);

    int batchDeleteUpdateZxSkShopGoods(List<ZxSkShopGoods> recordList, ZxSkShopGoods record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
