package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrJzBase;

public interface ZxCtContrJzBaseMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrJzBase record);

    int insertSelective(ZxCtContrJzBase record);

    ZxCtContrJzBase selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrJzBase record);

    int updateByPrimaryKey(ZxCtContrJzBase record);

    List<ZxCtContrJzBase> selectByZxCtContrJzBaseList(ZxCtContrJzBase record);

    int batchDeleteUpdateZxCtContrJzBase(List<ZxCtContrJzBase> recordList, ZxCtContrJzBase record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
