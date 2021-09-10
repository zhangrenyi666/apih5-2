package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSafetyEmergencyPlan;

public interface ZjTzSafetyEmergencyPlanService {

    public ResponseEntity getZjTzSafetyEmergencyPlanListByCondition(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan);

    public ResponseEntity getZjTzSafetyEmergencyPlanDetails(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan);

    public ResponseEntity saveZjTzSafetyEmergencyPlan(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan);
    
    public ResponseEntity saveZjTzSafetyEmergencyPlanAddFile(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan);

    public ResponseEntity updateZjTzSafetyEmergencyPlan(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan);
    
    public ResponseEntity updateZjTzSafetyEmergencyPlanAddFile(ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan);

    public ResponseEntity batchDeleteUpdateZjTzSafetyEmergencyPlan(List<ZjTzSafetyEmergencyPlan> zjTzSafetyEmergencyPlanList);

}

