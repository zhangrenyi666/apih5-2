package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrCustomerInfo;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluation;

public interface ZxCrProjectEvaluationService {

    public ResponseEntity getZxCrProjectEvaluationListByCondition(ZxCrProjectEvaluation zxCrProjectEvaluation);

    public ResponseEntity getZxCrProjectEvaluationDetail(ZxCrProjectEvaluation zxCrProjectEvaluation);

    public ResponseEntity saveZxCrProjectEvaluation(ZxCrProjectEvaluation zxCrProjectEvaluation);

    public ResponseEntity updateZxCrProjectEvaluation(ZxCrProjectEvaluation zxCrProjectEvaluation);

    public ResponseEntity batchDeleteUpdateZxCrProjectEvaluation(List<ZxCrProjectEvaluation> zxCrProjectEvaluationList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxCrCustomerInfoList();
    
    public ResponseEntity updateAuditStatusOne(ZxCrProjectEvaluation zxCrProjectEvaluation);
    
    public ResponseEntity updateAuditStatusOut(ZxCrProjectEvaluation zxCrProjectEvaluation);

    public ResponseEntity getZxCrProjectEvaluationListByCatName();
    
    public ResponseEntity getZxCrProjectEvaluationListByResName(ZxCrProjectEvaluation zxCrProjectEvaluation);
    
}
