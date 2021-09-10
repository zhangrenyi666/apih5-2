package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqVehicleUsageProject;

public interface ZxEqVehicleUsageProjectService {

    public ResponseEntity getZxEqVehicleUsageProjectListByCondition(ZxEqVehicleUsageProject zxEqVehicleUsageProject);

    public ResponseEntity getZxEqVehicleUsageProjectDetail(ZxEqVehicleUsageProject zxEqVehicleUsageProject);

    public ResponseEntity saveZxEqVehicleUsageProject(ZxEqVehicleUsageProject zxEqVehicleUsageProject);

    public ResponseEntity updateZxEqVehicleUsageProject(ZxEqVehicleUsageProject zxEqVehicleUsageProject);

    public ResponseEntity batchDeleteUpdateZxEqVehicleUsageProject(List<ZxEqVehicleUsageProject> zxEqVehicleUsageProjectList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxEqVehicleUsageProject> ureportZxEqVehicleUsageProject(ZxEqVehicleUsageProject zxEqVehicleUsageProject);
    
    public ResponseEntity ureportZxEqVehicleUsageProjectIdle(ZxEqVehicleUsageProject zxEqVehicleUsageProject);
}
