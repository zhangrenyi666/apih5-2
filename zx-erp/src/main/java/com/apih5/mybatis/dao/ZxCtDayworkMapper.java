package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtDaywork;

public interface ZxCtDayworkMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtDaywork record);

    int insertSelective(ZxCtDaywork record);

    ZxCtDaywork selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtDaywork record);

    int updateByPrimaryKey(ZxCtDaywork record);

    List<ZxCtDaywork> selectByZxCtDayworkList(ZxCtDaywork record);

    int batchDeleteUpdateZxCtDaywork(List<ZxCtDaywork> recordList, ZxCtDaywork record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
