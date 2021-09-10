package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtOtherWorks;

public interface ZxCtOtherWorksMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtOtherWorks record);

    int insertSelective(ZxCtOtherWorks record);

    ZxCtOtherWorks selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtOtherWorks record);

    int updateByPrimaryKey(ZxCtOtherWorks record);

    List<ZxCtOtherWorks> selectByZxCtOtherWorksList(ZxCtOtherWorks record);

    int batchDeleteUpdateZxCtOtherWorks(List<ZxCtOtherWorks> recordList, ZxCtOtherWorks record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
