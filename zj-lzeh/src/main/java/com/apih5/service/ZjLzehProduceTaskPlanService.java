package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlan;

public interface ZjLzehProduceTaskPlanService {

    public ResponseEntity getZjLzehProduceTaskPlanListByCondition(ZjLzehProduceTaskPlan zjLzehProduceTaskPlan);

    public ResponseEntity getZjLzehProduceTaskPlanDetail(ZjLzehProduceTaskPlan zjLzehProduceTaskPlan);

    public ResponseEntity saveZjLzehProduceTaskPlan(ZjLzehProduceTaskPlan zjLzehProduceTaskPlan);

    public ResponseEntity updateZjLzehProduceTaskPlan(ZjLzehProduceTaskPlan zjLzehProduceTaskPlan);

    public ResponseEntity batchDeleteUpdateZjLzehProduceTaskPlan(List<ZjLzehProduceTaskPlan> zjLzehProduceTaskPlanList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
