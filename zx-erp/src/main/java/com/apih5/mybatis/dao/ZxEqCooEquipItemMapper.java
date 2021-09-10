package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqCooEquipItem;

public interface ZxEqCooEquipItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqCooEquipItem record);

    int insertSelective(ZxEqCooEquipItem record);

    ZxEqCooEquipItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqCooEquipItem record);

    int updateByPrimaryKey(ZxEqCooEquipItem record);

    List<ZxEqCooEquipItem> selectByZxEqCooEquipItemList(ZxEqCooEquipItem record);

    int batchDeleteUpdateZxEqCooEquipItem(List<ZxEqCooEquipItem> recordList, ZxEqCooEquipItem record);

}

