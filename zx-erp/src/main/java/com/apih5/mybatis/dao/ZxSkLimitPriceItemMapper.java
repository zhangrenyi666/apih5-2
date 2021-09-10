package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkLimitPriceItem;

public interface ZxSkLimitPriceItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkLimitPriceItem record);

    int insertSelective(ZxSkLimitPriceItem record);

    ZxSkLimitPriceItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkLimitPriceItem record);

    int updateByPrimaryKey(ZxSkLimitPriceItem record);

    List<ZxSkLimitPriceItem> selectByZxSkLimitPriceItemList(ZxSkLimitPriceItem record);

    int batchDeleteUpdateZxSkLimitPriceItem(List<ZxSkLimitPriceItem> recordList, ZxSkLimitPriceItem record);

}

