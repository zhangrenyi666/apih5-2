package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkLimitPriceAdjust;
import org.apache.ibatis.annotations.Param;

public interface ZxSkLimitPriceAdjustMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkLimitPriceAdjust record);

    int insertSelective(ZxSkLimitPriceAdjust record);

    ZxSkLimitPriceAdjust selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkLimitPriceAdjust record);

    int updateByPrimaryKey(ZxSkLimitPriceAdjust record);

    List<ZxSkLimitPriceAdjust> selectByZxSkLimitPriceAdjustList(ZxSkLimitPriceAdjust record);

    int batchDeleteUpdateZxSkLimitPriceAdjust(List<ZxSkLimitPriceAdjust> recordList, ZxSkLimitPriceAdjust record);




}

