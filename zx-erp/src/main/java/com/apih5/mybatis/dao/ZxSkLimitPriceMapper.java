package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkLimitPrice;

public interface ZxSkLimitPriceMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkLimitPrice record);

    int insertSelective(ZxSkLimitPrice record);

    ZxSkLimitPrice selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkLimitPrice record);

    int updateByPrimaryKey(ZxSkLimitPrice record);

    List<ZxSkLimitPrice> selectByZxSkLimitPriceList(ZxSkLimitPrice record);

    int batchDeleteUpdateZxSkLimitPrice(List<ZxSkLimitPrice> recordList, ZxSkLimitPrice record);

}

