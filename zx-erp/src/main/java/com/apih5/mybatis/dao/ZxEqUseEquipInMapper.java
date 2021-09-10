package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqUseEquipIn;

public interface ZxEqUseEquipInMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqUseEquipIn record);

    int insertSelective(ZxEqUseEquipIn record);

    ZxEqUseEquipIn selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqUseEquipIn record);

    int updateByPrimaryKey(ZxEqUseEquipIn record);

    List<ZxEqUseEquipIn> selectByZxEqUseEquipInList(ZxEqUseEquipIn record);

    int batchDeleteUpdateZxEqUseEquipIn(List<ZxEqUseEquipIn> recordList, ZxEqUseEquipIn record);

}

