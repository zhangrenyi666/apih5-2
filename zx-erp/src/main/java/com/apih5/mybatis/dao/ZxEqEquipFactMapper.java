package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipFact;

public interface ZxEqEquipFactMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipFact record);

    int insertSelective(ZxEqEquipFact record);

    ZxEqEquipFact selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipFact record);

    int updateByPrimaryKey(ZxEqEquipFact record);

    List<ZxEqEquipFact> selectByZxEqEquipFactList(ZxEqEquipFact record);

    int batchDeleteUpdateZxEqEquipFact(List<ZxEqEquipFact> recordList, ZxEqEquipFact record);

	List<ZxEqEquipFact> getZxEqEquipFactListForCopy(ZxEqEquipFact zxEqEquipFact);

}

