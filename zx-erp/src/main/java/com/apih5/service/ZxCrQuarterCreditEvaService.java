package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrQuarterCreditEva;

public interface ZxCrQuarterCreditEvaService {

    public ResponseEntity getZxCrQuarterCreditEvaListByCondition(ZxCrQuarterCreditEva zxCrQuarterCreditEva);

    public ResponseEntity getZxCrQuarterCreditEvaDetail(ZxCrQuarterCreditEva zxCrQuarterCreditEva);

    public ResponseEntity saveZxCrQuarterCreditEva(ZxCrQuarterCreditEva zxCrQuarterCreditEva);

    public ResponseEntity updateZxCrQuarterCreditEva(ZxCrQuarterCreditEva zxCrQuarterCreditEva);

    public ResponseEntity batchDeleteUpdateZxCrQuarterCreditEva(List<ZxCrQuarterCreditEva> zxCrQuarterCreditEvaList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
