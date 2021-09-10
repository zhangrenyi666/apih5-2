package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransItemTransferOrder;

public interface ZxSkStockTransItemTransferOrderMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransItemTransferOrder record);

    int insertSelective(ZxSkStockTransItemTransferOrder record);

    ZxSkStockTransItemTransferOrder selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransItemTransferOrder record);

    int updateByPrimaryKey(ZxSkStockTransItemTransferOrder record);

    List<ZxSkStockTransItemTransferOrder> selectByZxSkStockTransItemTransferOrderList(ZxSkStockTransItemTransferOrder record);

    int batchDeleteUpdateZxSkStockTransItemTransferOrder(List<ZxSkStockTransItemTransferOrder> recordList, ZxSkStockTransItemTransferOrder record);

}

