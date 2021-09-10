package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuStockPrice;
import com.apih5.mybatis.pojo.ZxBuStockPriceItem;

public interface ZxBuStockPriceMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuStockPrice record);

    int insertSelective(ZxBuStockPrice record);

    ZxBuStockPrice selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuStockPrice record);

    int updateByPrimaryKey(ZxBuStockPrice record);

    List<ZxBuStockPrice> selectByZxBuStockPriceList(ZxBuStockPrice record);

    int batchDeleteUpdateZxBuStockPrice(List<ZxBuStockPrice> recordList, ZxBuStockPrice record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓


}
