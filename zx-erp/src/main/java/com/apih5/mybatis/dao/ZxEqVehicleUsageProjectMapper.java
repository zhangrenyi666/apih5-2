package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqVehicleUsageProject;

public interface ZxEqVehicleUsageProjectMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqVehicleUsageProject record);

    int insertSelective(ZxEqVehicleUsageProject record);

    ZxEqVehicleUsageProject selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqVehicleUsageProject record);

    int updateByPrimaryKey(ZxEqVehicleUsageProject record);

    List<ZxEqVehicleUsageProject> selectByZxEqVehicleUsageProjectList(ZxEqVehicleUsageProject record);

    int batchDeleteUpdateZxEqVehicleUsageProject(List<ZxEqVehicleUsageProject> recordList, ZxEqVehicleUsageProject record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqVehicleUsageProject> selectZxEqVehicleUsageProject(ZxEqVehicleUsageProject record);
}
