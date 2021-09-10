package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipDepreciation;

public interface ZxEqEquipDepreciationMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipDepreciation record);

    int insertSelective(ZxEqEquipDepreciation record);

    ZxEqEquipDepreciation selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipDepreciation record);

    int updateByPrimaryKey(ZxEqEquipDepreciation record);

    List<ZxEqEquipDepreciation> selectByZxEqEquipDepreciationList(ZxEqEquipDepreciation record);

    int batchDeleteUpdateZxEqEquipDepreciation(List<ZxEqEquipDepreciation> recordList, ZxEqEquipDepreciation record);

	List<ZxEqEquipDepreciation> getZxEqEquipDepreciationTotalList(ZxEqEquipDepreciation checkDepreciation);

	List<ZxEqEquipDepreciation> getZxEqEquipDepreciationTotalNotEquipList(ZxEqEquipDepreciation checkDepreciation);

}

