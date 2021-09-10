package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransferTransferOrder;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockTransferTransferOrderMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferTransferOrder record);

    int insertSelective(ZxSkStockTransferTransferOrder record);

    ZxSkStockTransferTransferOrder selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferTransferOrder record);

    int updateByPrimaryKey(ZxSkStockTransferTransferOrder record);

    List<ZxSkStockTransferTransferOrder> selectByZxSkStockTransferTransferOrderList(ZxSkStockTransferTransferOrder record);

    int batchDeleteUpdateZxSkStockTransferTransferOrder(List<ZxSkStockTransferTransferOrder> recordList, ZxSkStockTransferTransferOrder record);

    int checkZxSkStockTransferTransferOrder(ZxSkStockTransferTransferOrder zxSkStockTransferTransferOrder);

    int getZxSkStockTransferTransferOrderCount(@Param("date") String date,@Param("orgID") String orgID);
}

