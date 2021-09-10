package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrJYearCreditEva;

public interface ZxCrJYearCreditEvaService {

    public ResponseEntity getZxCrJYearCreditEvaListByCondition(ZxCrJYearCreditEva zxCrJYearCreditEva);

    public ResponseEntity getZxCrJYearCreditEvaDetail(ZxCrJYearCreditEva zxCrJYearCreditEva);

    public ResponseEntity saveZxCrJYearCreditEva(ZxCrJYearCreditEva zxCrJYearCreditEva);

    public ResponseEntity updateZxCrJYearCreditEva(ZxCrJYearCreditEva zxCrJYearCreditEva);

    public ResponseEntity batchDeleteUpdateZxCrJYearCreditEva(List<ZxCrJYearCreditEva> zxCrJYearCreditEvaList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity updateZxCrJYearCreditEvaAuditStatus(ZxCrJYearCreditEva zxCrJYearCreditEva);
}
