package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxPuMMReqPlan;

public interface ZxPuMMReqPlanService {

    public ResponseEntity getZxPuMMReqPlanListByCondition(ZxPuMMReqPlan zxPuMMReqPlan);

    public ResponseEntity getZxPuMMReqPlanDetail(ZxPuMMReqPlan zxPuMMReqPlan);

    public ResponseEntity saveZxPuMMReqPlan(ZxPuMMReqPlan zxPuMMReqPlan);

    public ResponseEntity updateZxPuMMReqPlan(ZxPuMMReqPlan zxPuMMReqPlan);

    public ResponseEntity batchDeleteUpdateZxPuMMReqPlan(List<ZxPuMMReqPlan> zxPuMMReqPlanList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxPuMMReqPlan> ureportZxPuMMReqPlan(ZxPuMMReqPlan zxPuMMReqPlan);
    
    public ResponseEntity ureportZxPuMMReqPlanIdle(ZxPuMMReqPlan zxPuMMReqPlan);
}
