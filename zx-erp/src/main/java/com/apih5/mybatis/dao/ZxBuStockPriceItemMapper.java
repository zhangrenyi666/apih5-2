package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuStockPriceItem;
import org.apache.ibatis.annotations.Param;

public interface ZxBuStockPriceItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuStockPriceItem record);

    int insertSelective(ZxBuStockPriceItem record);

    ZxBuStockPriceItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuStockPriceItem record);

    int updateByPrimaryKey(ZxBuStockPriceItem record);

    List<ZxBuStockPriceItem> selectByZxBuStockPriceItemList(ZxBuStockPriceItem record);

    int batchDeleteUpdateZxBuStockPriceItem(List<ZxBuStockPriceItem> recordList, ZxBuStockPriceItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int insertAll(@Param("zxBuStockPriceItems") List<ZxBuStockPriceItem> zxBuStockPriceItems);
}
