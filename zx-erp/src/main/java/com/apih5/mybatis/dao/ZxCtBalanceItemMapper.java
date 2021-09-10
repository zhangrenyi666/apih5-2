package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxCtBalanceItem;

public interface ZxCtBalanceItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtBalanceItem record);

    int insertSelective(ZxCtBalanceItem record);

    ZxCtBalanceItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtBalanceItem record);

    int updateByPrimaryKey(ZxCtBalanceItem record);

    List<ZxCtBalanceItem> selectByZxCtBalanceItemList(ZxCtBalanceItem record);

    int batchDeleteUpdateZxCtBalanceItem(List<ZxCtBalanceItem> recordList, ZxCtBalanceItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxCtBalanceItem> getNowWorksBalance(ZxCtBalanceItem record);
}
