package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkMonthPurItem;

public interface ZxSkMonthPurItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkMonthPurItem record);

    int insertSelective(ZxSkMonthPurItem record);

    ZxSkMonthPurItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkMonthPurItem record);

    int updateByPrimaryKey(ZxSkMonthPurItem record);

    List<ZxSkMonthPurItem> selectByZxSkMonthPurItemList(ZxSkMonthPurItem record);

    int batchDeleteUpdateZxSkMonthPurItem(List<ZxSkMonthPurItem> recordList, ZxSkMonthPurItem record);

}

