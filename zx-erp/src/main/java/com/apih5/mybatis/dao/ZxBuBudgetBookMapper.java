package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuBudgetBook;

public interface ZxBuBudgetBookMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuBudgetBook record);

    int insertSelective(ZxBuBudgetBook record);

    ZxBuBudgetBook selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuBudgetBook record);

    int updateByPrimaryKey(ZxBuBudgetBook record);

    List<ZxBuBudgetBook> selectByZxBuBudgetBookList(ZxBuBudgetBook record);

    int batchDeleteUpdateZxBuBudgetBook(List<ZxBuBudgetBook> recordList, ZxBuBudgetBook record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
