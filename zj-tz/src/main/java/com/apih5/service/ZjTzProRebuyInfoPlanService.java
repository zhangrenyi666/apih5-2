package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProRebuyInfoPlan;

public interface ZjTzProRebuyInfoPlanService {

    public ResponseEntity getZjTzProRebuyInfoPlanListByCondition(ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan);

    public ResponseEntity getZjTzProRebuyInfoPlanDetails(ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan);

    public ResponseEntity saveZjTzProRebuyInfoPlan(ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan);

    public ResponseEntity updateZjTzProRebuyInfoPlan(ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan);

    public ResponseEntity batchDeleteUpdateZjTzProRebuyInfoPlan(List<ZjTzProRebuyInfoPlan> zjTzProRebuyInfoPlanList);

}

