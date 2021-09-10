package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockDifMonth;

public interface ZxSkStockDifMonthMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockDifMonth record);

    int insertSelective(ZxSkStockDifMonth record);

    ZxSkStockDifMonth selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockDifMonth record);

    int updateByPrimaryKey(ZxSkStockDifMonth record);

    List<ZxSkStockDifMonth> selectByZxSkStockDifMonthList(ZxSkStockDifMonth record);

    int batchDeleteUpdateZxSkStockDifMonth(List<ZxSkStockDifMonth> recordList, ZxSkStockDifMonth record);


    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int checkZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth1);
}
