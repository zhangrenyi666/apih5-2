package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqOuterEquip;

public interface ZxEqOuterEquipMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqOuterEquip record);

    int insertSelective(ZxEqOuterEquip record);

    ZxEqOuterEquip selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqOuterEquip record);

    int updateByPrimaryKey(ZxEqOuterEquip record);

    List<ZxEqOuterEquip> selectByZxEqOuterEquipList(ZxEqOuterEquip record);

    int batchDeleteUpdateZxEqOuterEquip(List<ZxEqOuterEquip> recordList, ZxEqOuterEquip record);

    List<ZxEqOuterEquip> getZxEqOuterEquipListForCar(ZxEqOuterEquip zxEqOuterEquip);

}

