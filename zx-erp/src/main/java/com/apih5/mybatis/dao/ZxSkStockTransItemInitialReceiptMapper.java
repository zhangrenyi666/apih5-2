package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransItemInitialReceipt;
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;

public interface ZxSkStockTransItemInitialReceiptMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransItemInitialReceipt record);

    int insertSelective(ZxSkStockTransItemInitialReceipt record);

    ZxSkStockTransItemInitialReceipt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransItemInitialReceipt record);

    int updateByPrimaryKey(ZxSkStockTransItemInitialReceipt record);

    List<ZxSkStockTransItemInitialReceipt> selectByZxSkStockTransItemInitialReceiptList(ZxSkStockTransItemInitialReceipt record);

    int batchDeleteUpdateZxSkStockTransItemInitialReceipt(List<ZxSkStockTransItemInitialReceipt> recordList, ZxSkStockTransItemInitialReceipt record);

    int updateZxSkStockTransItemReceivingMapperIsOutNumber(ZxSkStockTransItemSalesReturn zxSkStockTransItemSalesReturn);

}

