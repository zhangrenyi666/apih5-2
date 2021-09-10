package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockTransferInitialReceiptMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferInitialReceipt record);

    int insertSelective(ZxSkStockTransferInitialReceipt record);

    ZxSkStockTransferInitialReceipt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferInitialReceipt record);

    int updateByPrimaryKey(ZxSkStockTransferInitialReceipt record);

    List<ZxSkStockTransferInitialReceipt> selectByZxSkStockTransferInitialReceiptList(ZxSkStockTransferInitialReceipt record);

    int batchDeleteUpdateZxSkStockTransferInitialReceipt(List<ZxSkStockTransferInitialReceipt> recordList, ZxSkStockTransferInitialReceipt record);

    int checkZxSkStockTransferInitialReceipt(ZxSkStockTransferInitialReceipt zxSkStockTransferInitialReceipt);

    int getZxSkStockTransferInitialReceiptCount(@Param("date") String date, @Param("orgID") String orgID);






}

