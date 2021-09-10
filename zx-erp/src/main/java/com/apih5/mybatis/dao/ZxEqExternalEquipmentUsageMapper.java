package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqExternalEquipmentUsage;

public interface ZxEqExternalEquipmentUsageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqExternalEquipmentUsage record);

    int insertSelective(ZxEqExternalEquipmentUsage record);

    ZxEqExternalEquipmentUsage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqExternalEquipmentUsage record);

    int updateByPrimaryKey(ZxEqExternalEquipmentUsage record);

    List<ZxEqExternalEquipmentUsage> selectByZxEqExternalEquipmentUsageList(ZxEqExternalEquipmentUsage record);

    int batchDeleteUpdateZxEqExternalEquipmentUsage(List<ZxEqExternalEquipmentUsage> recordList, ZxEqExternalEquipmentUsage record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqExternalEquipmentUsage> selectZxEqExternalEquipmentUsage(ZxEqExternalEquipmentUsage record);
}
