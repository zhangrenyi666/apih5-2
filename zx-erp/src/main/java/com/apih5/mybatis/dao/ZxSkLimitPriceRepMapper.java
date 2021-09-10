package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkLimitPriceRep;

public interface ZxSkLimitPriceRepMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkLimitPriceRep record);

    int insertSelective(ZxSkLimitPriceRep record);

    ZxSkLimitPriceRep selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkLimitPriceRep record);

    int updateByPrimaryKey(ZxSkLimitPriceRep record);

    List<ZxSkLimitPriceRep> selectByZxSkLimitPriceRepList(ZxSkLimitPriceRep record);

    int batchDeleteUpdateZxSkLimitPriceRep(List<ZxSkLimitPriceRep> recordList, ZxSkLimitPriceRep record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkLimitPriceRep> selectZxSkLimitPriceRep(ZxSkLimitPriceRep record);
}
