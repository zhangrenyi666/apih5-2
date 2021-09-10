package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuWorksToStockPrice;
import com.apih5.mybatis.pojo.ZxCtWorks;

public interface ZxBuWorksToStockPriceMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuWorksToStockPrice record);

    int insertSelective(ZxBuWorksToStockPrice record);

    ZxBuWorksToStockPrice selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuWorksToStockPrice record);

    int updateByPrimaryKey(ZxBuWorksToStockPrice record);

    List<ZxBuWorksToStockPrice> selectByZxBuWorksToStockPriceList(ZxBuWorksToStockPrice record);

    int batchDeleteUpdateZxBuWorksToStockPrice(List<ZxBuWorksToStockPrice> recordList, ZxBuWorksToStockPrice record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    ZxCtWorks getResNoArrResNameArr(String id);


}
