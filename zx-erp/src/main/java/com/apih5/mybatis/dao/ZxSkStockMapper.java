package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStock;
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;

public interface ZxSkStockMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStock record);

    int insertSelective(ZxSkStock record);

    ZxSkStock selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStock record);

    int updateByPrimaryKey(ZxSkStock record);

    List<ZxSkStock> selectByZxSkStockList(ZxSkStock record);

    int batchDeleteUpdateZxSkStock(List<ZxSkStock> recordList, ZxSkStock record);

    List<ZxSkStock> getZxSkStockResCategoryDataList(ZxSkStock zxSkStock);

    List<ZxSkStock> selectByZxSkStockListNew(ZxSkStock record);


}

