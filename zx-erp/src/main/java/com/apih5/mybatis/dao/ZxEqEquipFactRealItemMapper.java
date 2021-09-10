package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipFactRealItem;

public interface ZxEqEquipFactRealItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipFactRealItem record);

    int insertSelective(ZxEqEquipFactRealItem record);

    ZxEqEquipFactRealItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipFactRealItem record);

    int updateByPrimaryKey(ZxEqEquipFactRealItem record);

    List<ZxEqEquipFactRealItem> selectByZxEqEquipFactRealItemList(ZxEqEquipFactRealItem record);

    int batchDeleteUpdateZxEqEquipFactRealItem(List<ZxEqEquipFactRealItem> recordList, ZxEqEquipFactRealItem record);

}

