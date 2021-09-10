package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtWorkToMU;

public interface ZxCtWorkToMUMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtWorkToMU record);

    int insertSelective(ZxCtWorkToMU record);

    ZxCtWorkToMU selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtWorkToMU record);

    int updateByPrimaryKey(ZxCtWorkToMU record);

    List<ZxCtWorkToMU> selectByZxCtWorkToMUList(ZxCtWorkToMU record);

    int batchDeleteUpdateZxCtWorkToMU(List<ZxCtWorkToMU> recordList, ZxCtWorkToMU record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int deleteByWorkBookID(ZxCtWorkToMU zxCtWorkToMU);
}
