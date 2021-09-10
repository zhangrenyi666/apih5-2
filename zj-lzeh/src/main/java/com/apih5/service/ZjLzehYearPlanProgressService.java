package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehYearPlanProgress;

public interface ZjLzehYearPlanProgressService {

    public ResponseEntity getZjLzehYearPlanProgressListByCondition(ZjLzehYearPlanProgress zjLzehYearPlanProgress);

    public ResponseEntity getZjLzehYearPlanProgressDetail(ZjLzehYearPlanProgress zjLzehYearPlanProgress);

    public ResponseEntity saveZjLzehYearPlanProgress(ZjLzehYearPlanProgress zjLzehYearPlanProgress);

    public ResponseEntity updateZjLzehYearPlanProgress(ZjLzehYearPlanProgress zjLzehYearPlanProgress);

    public ResponseEntity batchDeleteUpdateZjLzehYearPlanProgress(List<ZjLzehYearPlanProgress> zjLzehYearPlanProgressList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
