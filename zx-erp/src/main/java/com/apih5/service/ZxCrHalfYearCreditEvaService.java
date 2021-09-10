package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEva;

public interface ZxCrHalfYearCreditEvaService {

    public ResponseEntity getZxCrHalfYearCreditEvaListByCondition(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva);

    public ResponseEntity getZxCrHalfYearCreditEvaDetail(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva);

    public ResponseEntity saveZxCrHalfYearCreditEva(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva);

    public ResponseEntity updateZxCrHalfYearCreditEva(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva);

    public ResponseEntity batchDeleteUpdateZxCrHalfYearCreditEva(List<ZxCrHalfYearCreditEva> zxCrHalfYearCreditEvaList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity updateauditStatus(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva);
}
