package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;

public interface ZxSkStockTransItemSalesReturnMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransItemSalesReturn record);

    int insertSelective(ZxSkStockTransItemSalesReturn record);

    ZxSkStockTransItemSalesReturn selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransItemSalesReturn record);

    int updateByPrimaryKey(ZxSkStockTransItemSalesReturn record);

    List<ZxSkStockTransItemSalesReturn> selectByZxSkStockTransItemSalesReturnList(ZxSkStockTransItemSalesReturn record);

    int batchDeleteUpdateZxSkStockTransItemSalesReturn(List<ZxSkStockTransItemSalesReturn> recordList, ZxSkStockTransItemSalesReturn record);

    List<ZxSkStockTransItemSalesReturn> selectByZxSkStockTransItemSalesReturnListMinNumber(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn);

    void updateSettlementStatusByPrimaryKey(String id);

}

