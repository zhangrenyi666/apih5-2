package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrJzItem;

public interface ZxCtContrJzItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrJzItem record);

    int insertSelective(ZxCtContrJzItem record);

    ZxCtContrJzItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrJzItem record);

    int updateByPrimaryKey(ZxCtContrJzItem record);

    List<ZxCtContrJzItem> selectByZxCtContrJzItemList(ZxCtContrJzItem record);

    int batchDeleteUpdateZxCtContrJzItem(List<ZxCtContrJzItem> recordList, ZxCtContrJzItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
