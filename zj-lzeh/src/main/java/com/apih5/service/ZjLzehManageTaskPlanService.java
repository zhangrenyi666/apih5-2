package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlan;

public interface ZjLzehManageTaskPlanService {

    public ResponseEntity getZjLzehManageTaskPlanListByCondition(ZjLzehManageTaskPlan zjLzehManageTaskPlan);

    public ResponseEntity getZjLzehManageTaskPlanDetail(ZjLzehManageTaskPlan zjLzehManageTaskPlan);

    public ResponseEntity saveZjLzehManageTaskPlan(ZjLzehManageTaskPlan zjLzehManageTaskPlan);

    public ResponseEntity updateZjLzehManageTaskPlan(ZjLzehManageTaskPlan zjLzehManageTaskPlan);

    public ResponseEntity batchDeleteUpdateZjLzehManageTaskPlan(List<ZjLzehManageTaskPlan> zjLzehManageTaskPlanList);

	public ResponseEntity getZjLzehTaskNum(ZjLzehManageTaskPlan zjLzehManageTaskPlan);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
