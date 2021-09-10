package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTtReqPlan;

public interface ZxSkTtReqPlanService {

    public ResponseEntity getZxSkTtReqPlanListByCondition(ZxSkTtReqPlan zxSkTtReqPlan);

    public ResponseEntity getZxSkTtReqPlanDetails(ZxSkTtReqPlan zxSkTtReqPlan);

    public ResponseEntity saveZxSkTtReqPlan(ZxSkTtReqPlan zxSkTtReqPlan);

    public ResponseEntity updateZxSkTtReqPlan(ZxSkTtReqPlan zxSkTtReqPlan);

    public ResponseEntity batchDeleteUpdateZxSkTtReqPlan(List<ZxSkTtReqPlan> zxSkTtReqPlanList);

    public ResponseEntity checkZxSkTtReqPlanList(ZxSkTtReqPlan zxSkTtReqPlan);

    public ResponseEntity updateZxSkTtReqPlanCheckOver(ZxSkTtReqPlan zxSkTtReqPlan);
}

