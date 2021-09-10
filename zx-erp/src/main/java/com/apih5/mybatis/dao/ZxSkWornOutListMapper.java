package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkWornOutList;

public interface ZxSkWornOutListMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkWornOutList record);

    int insertSelective(ZxSkWornOutList record);

    ZxSkWornOutList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkWornOutList record);

    int updateByPrimaryKey(ZxSkWornOutList record);

    List<ZxSkWornOutList> selectByZxSkWornOutListList(ZxSkWornOutList record);

    int batchDeleteUpdateZxSkWornOutList(List<ZxSkWornOutList> recordList, ZxSkWornOutList record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkWornOutList> selectZxSkWornOutList(ZxSkWornOutList record);
}
