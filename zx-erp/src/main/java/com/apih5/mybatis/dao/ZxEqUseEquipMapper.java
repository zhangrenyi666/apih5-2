package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqUseEquip;

public interface ZxEqUseEquipMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqUseEquip record);

    int insertSelective(ZxEqUseEquip record);

    ZxEqUseEquip selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqUseEquip record);

    int updateByPrimaryKey(ZxEqUseEquip record);

    List<ZxEqUseEquip> selectByZxEqUseEquipList(ZxEqUseEquip record);

    int batchDeleteUpdateZxEqUseEquip(List<ZxEqUseEquip> recordList, ZxEqUseEquip record);

}

