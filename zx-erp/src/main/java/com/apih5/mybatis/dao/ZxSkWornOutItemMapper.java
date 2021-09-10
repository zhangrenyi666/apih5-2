package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkWornOutItem;

public interface ZxSkWornOutItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkWornOutItem record);

    int insertSelective(ZxSkWornOutItem record);

    ZxSkWornOutItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkWornOutItem record);

    int updateByPrimaryKey(ZxSkWornOutItem record);

    List<ZxSkWornOutItem> selectByZxSkWornOutItemList(ZxSkWornOutItem record);

    int batchDeleteUpdateZxSkWornOutItem(List<ZxSkWornOutItem> recordList, ZxSkWornOutItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
