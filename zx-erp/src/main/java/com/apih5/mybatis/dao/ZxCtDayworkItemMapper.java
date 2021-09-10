package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtDayworkItem;

public interface ZxCtDayworkItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtDayworkItem record);

    int insertSelective(ZxCtDayworkItem record);

    ZxCtDayworkItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtDayworkItem record);

    int updateByPrimaryKey(ZxCtDayworkItem record);

    List<ZxCtDayworkItem> selectByZxCtDayworkItemList(ZxCtDayworkItem record);

    int batchDeleteUpdateZxCtDayworkItem(List<ZxCtDayworkItem> recordList, ZxCtDayworkItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int deleteAllZxCtDayworkItem(ZxCtDayworkItem zxCtDayworkItem);
}
