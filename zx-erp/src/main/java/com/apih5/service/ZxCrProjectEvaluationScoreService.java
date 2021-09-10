package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationScore;

public interface ZxCrProjectEvaluationScoreService {

    public ResponseEntity getZxCrProjectEvaluationScoreListByCondition(ZxCrProjectEvaluationScore zxCrProjectEvaluationScore);

    public ResponseEntity getZxCrProjectEvaluationScoreDetail(ZxCrProjectEvaluationScore zxCrProjectEvaluationScore);

    public ResponseEntity saveZxCrProjectEvaluationScore(ZxCrProjectEvaluationScore zxCrProjectEvaluationScore);

    public ResponseEntity updateZxCrProjectEvaluationScore(ZxCrProjectEvaluationScore zxCrProjectEvaluationScore);

    public ResponseEntity batchDeleteUpdateZxCrProjectEvaluationScore(List<ZxCrProjectEvaluationScore> zxCrProjectEvaluationScoreList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxCrProjectEvaluationScoreDetailOne(ZxCrProjCreditScore zxCrProjectEvaluationScore);
}
