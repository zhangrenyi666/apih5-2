package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSafetyEmergencyPlan;

public interface ZjTzSafetyEmergencyPlanMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSafetyEmergencyPlan record);

    int insertSelective(ZjTzSafetyEmergencyPlan record);

    ZjTzSafetyEmergencyPlan selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSafetyEmergencyPlan record);

    int updateByPrimaryKey(ZjTzSafetyEmergencyPlan record);

    List<ZjTzSafetyEmergencyPlan> selectByZjTzSafetyEmergencyPlanList(ZjTzSafetyEmergencyPlan record);

    int batchDeleteUpdateZjTzSafetyEmergencyPlan(List<ZjTzSafetyEmergencyPlan> recordList, ZjTzSafetyEmergencyPlan record);

}

