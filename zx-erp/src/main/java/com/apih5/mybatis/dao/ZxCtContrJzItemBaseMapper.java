package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrJzItemBase;

public interface ZxCtContrJzItemBaseMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrJzItemBase record);

    int insertSelective(ZxCtContrJzItemBase record);

    ZxCtContrJzItemBase selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrJzItemBase record);

    int updateByPrimaryKey(ZxCtContrJzItemBase record);

    List<ZxCtContrJzItemBase> selectByZxCtContrJzItemBaseList(ZxCtContrJzItemBase record);

    int batchDeleteUpdateZxCtContrJzItemBase(List<ZxCtContrJzItemBase> recordList, ZxCtContrJzItemBase record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
