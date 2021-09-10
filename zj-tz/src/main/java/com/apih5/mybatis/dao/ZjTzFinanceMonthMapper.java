package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzFinanceMonth;

public interface ZjTzFinanceMonthMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzFinanceMonth record);

    int insertSelective(ZjTzFinanceMonth record);

    ZjTzFinanceMonth selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzFinanceMonth record);

    int updateByPrimaryKey(ZjTzFinanceMonth record);

    List<ZjTzFinanceMonth> selectByZjTzFinanceMonthList(ZjTzFinanceMonth record);

    int batchDeleteUpdateZjTzFinanceMonth(List<ZjTzFinanceMonth> recordList, ZjTzFinanceMonth record);

	int batchRecallZjTzFinanceMonth(List<ZjTzFinanceMonth> zjTzFinanceMonthList, ZjTzFinanceMonth zjTzRules);

}

