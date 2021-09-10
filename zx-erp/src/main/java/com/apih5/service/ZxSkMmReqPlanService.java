package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkMmReqPlan;

public interface ZxSkMmReqPlanService {

    public ResponseEntity getZxSkMmReqPlanListByCondition(ZxSkMmReqPlan zxSkMmReqPlan);

    public ResponseEntity getZxSkMmReqPlanDetails(ZxSkMmReqPlan zxSkMmReqPlan);

    public ResponseEntity saveZxSkMmReqPlan(ZxSkMmReqPlan zxSkMmReqPlan);

    public ResponseEntity updateZxSkMmReqPlan(ZxSkMmReqPlan zxSkMmReqPlan);

    public ResponseEntity batchDeleteUpdateZxSkMmReqPlan(List<ZxSkMmReqPlan> zxSkMmReqPlanList);


}

