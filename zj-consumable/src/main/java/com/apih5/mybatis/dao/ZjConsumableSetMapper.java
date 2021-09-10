package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjConsumableSet;

public interface ZjConsumableSetMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjConsumableSet record);

    int insertSelective(ZjConsumableSet record);

    ZjConsumableSet selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjConsumableSet record);

    int updateByPrimaryKey(ZjConsumableSet record);

    List<ZjConsumableSet> selectByZjConsumableSetList(ZjConsumableSet record);

    int batchDeleteUpdateZjConsumableSet(List<ZjConsumableSet> recordList, ZjConsumableSet record);

}

