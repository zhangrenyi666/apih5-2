package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtAlter;

public interface ZxCtAlterMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtAlter record);

    int insertSelective(ZxCtAlter record);

    ZxCtAlter selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtAlter record);

    int updateByPrimaryKey(ZxCtAlter record);

    List<ZxCtAlter> selectByZxCtAlterList(ZxCtAlter record);

    int batchDeleteUpdateZxCtAlter(List<ZxCtAlter> recordList, ZxCtAlter record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
