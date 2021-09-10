package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqCooEquip;

public interface ZxEqCooEquipMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqCooEquip record);

    int insertSelective(ZxEqCooEquip record);

    ZxEqCooEquip selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqCooEquip record);

    int updateByPrimaryKey(ZxEqCooEquip record);

    List<ZxEqCooEquip> selectByZxEqCooEquipList(ZxEqCooEquip record);

    int batchDeleteUpdateZxEqCooEquip(List<ZxEqCooEquip> recordList, ZxEqCooEquip record);

}

