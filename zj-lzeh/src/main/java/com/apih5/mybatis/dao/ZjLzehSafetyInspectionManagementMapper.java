package com.apih5.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.apih5.mybatis.pojo.ZjLzehSafetyInspectionManagement;
@Mapper
public interface ZjLzehSafetyInspectionManagementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehSafetyInspectionManagement record);

    int insertSelective(ZjLzehSafetyInspectionManagement record);

    ZjLzehSafetyInspectionManagement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehSafetyInspectionManagement record);

    int updateByPrimaryKey(ZjLzehSafetyInspectionManagement record);

    List<ZjLzehSafetyInspectionManagement> selectByZjLzehSafetyInspectionManagementList(ZjLzehSafetyInspectionManagement record);

    int batchDeleteUpdateZjLzehSafetyInspectionManagement(List<ZjLzehSafetyInspectionManagement> recordList, ZjLzehSafetyInspectionManagement record);
    
    List<ZjLzehSafetyInspectionManagement> selectZjLzehSafetyInspectionManagementBySumDangerType(ZjLzehSafetyInspectionManagement record);
    
    List<ZjLzehSafetyInspectionManagement> selectZjLzehSafetyInspectionManagementBySumDangerLevel(ZjLzehSafetyInspectionManagement record);
    
    List<ZjLzehSafetyInspectionManagement> selectZjLzehSafetyInspectionManagementBySumTroubleLevel(ZjLzehSafetyInspectionManagement record);

}

