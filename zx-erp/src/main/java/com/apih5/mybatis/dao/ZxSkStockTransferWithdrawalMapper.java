package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransferWithdrawal;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockTransferWithdrawalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransferWithdrawal record);

    int insertSelective(ZxSkStockTransferWithdrawal record);

    ZxSkStockTransferWithdrawal selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransferWithdrawal record);

    int updateByPrimaryKey(ZxSkStockTransferWithdrawal record);

    List<ZxSkStockTransferWithdrawal> selectByZxSkStockTransferWithdrawalList(ZxSkStockTransferWithdrawal record);

    int batchDeleteUpdateZxSkStockTransferWithdrawal(List<ZxSkStockTransferWithdrawal> recordList, ZxSkStockTransferWithdrawal record);

    int checkZxSkStockTransferWithdrawal(ZxSkStockTransferWithdrawal zxSkStockTransferWithdrawal);

    int getZxSkStockTransferWithdrawalCount(@Param("date") String date,@Param("orgID") String orgID);
}

