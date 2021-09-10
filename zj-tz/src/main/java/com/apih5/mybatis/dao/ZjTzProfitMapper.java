package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProfit;

public interface ZjTzProfitMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProfit record);

    int insertSelective(ZjTzProfit record);

    ZjTzProfit selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProfit record);

    int updateByPrimaryKey(ZjTzProfit record);

    List<ZjTzProfit> selectByZjTzProfitList(ZjTzProfit record);

    int batchDeleteUpdateZjTzProfit(List<ZjTzProfit> recordList, ZjTzProfit record);

}

