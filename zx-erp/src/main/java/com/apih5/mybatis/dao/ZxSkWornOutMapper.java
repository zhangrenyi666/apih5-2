package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkWornOut;

public interface ZxSkWornOutMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkWornOut record);

    int insertSelective(ZxSkWornOut record);

    ZxSkWornOut selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkWornOut record);

    int updateByPrimaryKey(ZxSkWornOut record);

    List<ZxSkWornOut> selectByZxSkWornOutList(ZxSkWornOut record);

    int batchDeleteUpdateZxSkWornOut(List<ZxSkWornOut> recordList, ZxSkWornOut record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int getZxSkWornOutCount(String orgID);
}
