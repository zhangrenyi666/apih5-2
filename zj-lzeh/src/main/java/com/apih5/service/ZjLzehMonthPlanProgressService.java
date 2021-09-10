package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehMonthPlanProgress;

public interface ZjLzehMonthPlanProgressService {

    public ResponseEntity getZjLzehMonthPlanProgressListByCondition(ZjLzehMonthPlanProgress zjLzehMonthPlanProgress);

    public ResponseEntity getZjLzehMonthPlanProgressDetail(ZjLzehMonthPlanProgress zjLzehMonthPlanProgress);

    public ResponseEntity saveZjLzehMonthPlanProgress(ZjLzehMonthPlanProgress zjLzehMonthPlanProgress);

    public ResponseEntity updateZjLzehMonthPlanProgress(ZjLzehMonthPlanProgress zjLzehMonthPlanProgress);

    public ResponseEntity batchDeleteUpdateZjLzehMonthPlanProgress(List<ZjLzehMonthPlanProgress> zjLzehMonthPlanProgressList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
