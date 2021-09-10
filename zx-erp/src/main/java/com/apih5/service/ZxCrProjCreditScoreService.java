package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;

public interface ZxCrProjCreditScoreService {

    public ResponseEntity getZxCrProjCreditScoreListByCondition(ZxCrProjCreditScore zxCrProjCreditScore);

    public ResponseEntity getZxCrProjCreditScoreDetail(ZxCrProjCreditScore zxCrProjCreditScore);

    public ResponseEntity saveZxCrProjCreditScore(ZxCrProjCreditScore zxCrProjCreditScore);

    public ResponseEntity updateZxCrProjCreditScore(ZxCrProjCreditScore zxCrProjCreditScore);

    public ResponseEntity batchDeleteUpdateZxCrProjCreditScore(List<ZxCrProjCreditScore> zxCrProjCreditScoreList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
