package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockTransItemWithdrawal;

public interface ZxSkStockTransItemWithdrawalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockTransItemWithdrawal record);

    int insertSelective(ZxSkStockTransItemWithdrawal record);

    ZxSkStockTransItemWithdrawal selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockTransItemWithdrawal record);

    int updateByPrimaryKey(ZxSkStockTransItemWithdrawal record);

    List<ZxSkStockTransItemWithdrawal> selectByZxSkStockTransItemWithdrawalList(ZxSkStockTransItemWithdrawal record);

    int batchDeleteUpdateZxSkStockTransItemWithdrawal(List<ZxSkStockTransItemWithdrawal> recordList, ZxSkStockTransItemWithdrawal record);

}

