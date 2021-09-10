package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkLimitPriceAdjustItem;

public interface ZxSkLimitPriceAdjustItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkLimitPriceAdjustItem record);

    int insertSelective(ZxSkLimitPriceAdjustItem record);

    ZxSkLimitPriceAdjustItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkLimitPriceAdjustItem record);

    int updateByPrimaryKey(ZxSkLimitPriceAdjustItem record);

    List<ZxSkLimitPriceAdjustItem> selectByZxSkLimitPriceAdjustItemList(ZxSkLimitPriceAdjustItem record);

    int batchDeleteUpdateZxSkLimitPriceAdjustItem(List<ZxSkLimitPriceAdjustItem> recordList, ZxSkLimitPriceAdjustItem record);

}

