package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuBudgetDetails;

public interface ZxBuBudgetDetailsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuBudgetDetails record);

    int insertSelective(ZxBuBudgetDetails record);

    ZxBuBudgetDetails selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuBudgetDetails record);

    int updateByPrimaryKey(ZxBuBudgetDetails record);

    List<ZxBuBudgetDetails> selectByZxBuBudgetDetailsList(ZxBuBudgetDetails record);

    int batchDeleteUpdateZxBuBudgetDetails(List<ZxBuBudgetDetails> recordList, ZxBuBudgetDetails record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
