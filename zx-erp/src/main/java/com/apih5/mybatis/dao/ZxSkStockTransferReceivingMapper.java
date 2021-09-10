package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransferReceiving;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockTransferReceivingMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferReceiving record);

    int insertSelective(ZxSkStockTransferReceiving record);

    ZxSkStockTransferReceiving selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferReceiving record);

    int updateByPrimaryKey(ZxSkStockTransferReceiving record);

    List<ZxSkStockTransferReceiving> selectByZxSkStockTransferReceivingList(ZxSkStockTransferReceiving record);

    int batchDeleteUpdateZxSkStockTransferReceiving(List<ZxSkStockTransferReceiving> recordList, ZxSkStockTransferReceiving record);

    int checkZxSkStockTransferReceiving(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    int getZxSkStockTransferReceivingCount(@Param("date") String date,@Param("orgID") String orgID);

    List<ZxSkStockTransferReceiving> selectByZxSkStockTransferReceivingListByPrimaryNo(ZxSkStockTransferReceiving record);

    List<ZxSkStockTransferReceiving> getZxSkStockTransferReceivingByContractNoList(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    List<ZxSkStockTransferReceiving> selectOrderAll(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

}

