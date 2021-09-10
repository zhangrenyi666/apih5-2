package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipFactItem;

public interface ZxEqEquipFactItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipFactItem record);

    int insertSelective(ZxEqEquipFactItem record);

    ZxEqEquipFactItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipFactItem record);

    int updateByPrimaryKey(ZxEqEquipFactItem record);

    List<ZxEqEquipFactItem> selectByZxEqEquipFactItemList(ZxEqEquipFactItem record);

    int batchDeleteUpdateZxEqEquipFactItem(List<ZxEqEquipFactItem> recordList, ZxEqEquipFactItem record);

}

