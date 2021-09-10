package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrProjCreditBadBeha;
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationBad;

public interface ZxCrProjectEvaluationBadService {

    public ResponseEntity getZxCrProjectEvaluationBadListByCondition(ZxCrProjectEvaluationBad zxCrProjectEvaluationBad);

    public ResponseEntity getZxCrProjectEvaluationBadDetail(ZxCrProjectEvaluationBad zxCrProjectEvaluationBad);

    public ResponseEntity saveZxCrProjectEvaluationBad(ZxCrProjectEvaluationBad zxCrProjectEvaluationBad);

    public ResponseEntity updateZxCrProjectEvaluationBad(ZxCrProjectEvaluationBad zxCrProjectEvaluationBad);

    public ResponseEntity batchDeleteUpdateZxCrProjectEvaluationBad(List<ZxCrProjectEvaluationBad> zxCrProjectEvaluationBadList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity getZxCrProjectEvaluationBadDetailOne(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha);
}
