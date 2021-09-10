package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipDepreciationItem;

public interface ZxEqEquipDepreciationItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipDepreciationItem record);

    int insertSelective(ZxEqEquipDepreciationItem record);

    ZxEqEquipDepreciationItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipDepreciationItem record);

    int updateByPrimaryKey(ZxEqEquipDepreciationItem record);

    List<ZxEqEquipDepreciationItem> selectByZxEqEquipDepreciationItemList(ZxEqEquipDepreciationItem record);

    int batchDeleteUpdateZxEqEquipDepreciationItem(List<ZxEqEquipDepreciationItem> recordList, ZxEqEquipDepreciationItem record);

	List<ZxEqEquipDepreciationItem> getZxEqEquipDepreciationItemListForTab(ZxEqEquipDepreciationItem depreciationItem);

}

