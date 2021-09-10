package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransItemReceiving;
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;

public interface ZxSkStockTransItemReceivingMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransItemReceiving record);

    int insertSelective(ZxSkStockTransItemReceiving record);

    ZxSkStockTransItemReceiving selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransItemReceiving record);

    int updateByPrimaryKey(ZxSkStockTransItemReceiving record);

    List<ZxSkStockTransItemReceiving> selectByZxSkStockTransItemReceivingList(ZxSkStockTransItemReceiving record);

    int batchDeleteUpdateZxSkStockTransItemReceiving(List<ZxSkStockTransItemReceiving> recordList, ZxSkStockTransItemReceiving record);

    int updateZxSkStockTransItemReceivingMapperIsOutNumber(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn);

    void updateSettlementStatusByPrimaryKey(String id);

}

