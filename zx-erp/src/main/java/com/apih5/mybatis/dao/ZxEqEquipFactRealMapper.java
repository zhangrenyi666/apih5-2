package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipFactReal;

public interface ZxEqEquipFactRealMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipFactReal record);

    int insertSelective(ZxEqEquipFactReal record);

    ZxEqEquipFactReal selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipFactReal record);

    int updateByPrimaryKey(ZxEqEquipFactReal record);

    List<ZxEqEquipFactReal> selectByZxEqEquipFactRealList(ZxEqEquipFactReal record);

    int batchDeleteUpdateZxEqEquipFactReal(List<ZxEqEquipFactReal> recordList, ZxEqEquipFactReal record);

}

