package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquipIntegratedQuery;

public interface ZxEqEquipIntegratedQueryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquipIntegratedQuery record);

    int insertSelective(ZxEqEquipIntegratedQuery record);

    ZxEqEquipIntegratedQuery selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquipIntegratedQuery record);

    int updateByPrimaryKey(ZxEqEquipIntegratedQuery record);

    List<ZxEqEquipIntegratedQuery> selectByZxEqEquipIntegratedQueryList(ZxEqEquipIntegratedQuery record);

    int batchDeleteUpdateZxEqEquipIntegratedQuery(List<ZxEqEquipIntegratedQuery> recordList, ZxEqEquipIntegratedQuery record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqEquipIntegratedQuery> selectZxEqEquipIntegratedQuery(ZxEqEquipIntegratedQuery record);
}
