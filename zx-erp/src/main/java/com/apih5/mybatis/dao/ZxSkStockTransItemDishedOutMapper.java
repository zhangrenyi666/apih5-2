package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransItemDishedOut;

public interface ZxSkStockTransItemDishedOutMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransItemDishedOut record);

    int insertSelective(ZxSkStockTransItemDishedOut record);

    ZxSkStockTransItemDishedOut selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransItemDishedOut record);

    int updateByPrimaryKey(ZxSkStockTransItemDishedOut record);

    List<ZxSkStockTransItemDishedOut> selectByZxSkStockTransItemDishedOutList(ZxSkStockTransItemDishedOut record);

    int batchDeleteUpdateZxSkStockTransItemDishedOut(List<ZxSkStockTransItemDishedOut> recordList, ZxSkStockTransItemDishedOut record);

}

