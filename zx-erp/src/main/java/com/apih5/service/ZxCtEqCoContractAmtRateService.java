package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtEqCoContractAmtRate;

public interface ZxCtEqCoContractAmtRateService {

    public ResponseEntity getZxCtEqCoContractAmtRateListByCondition(ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate);

    public ResponseEntity getZxCtEqCoContractAmtRateDetail(ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate);

    public ResponseEntity saveZxCtEqCoContractAmtRate(ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate);

    public ResponseEntity updateZxCtEqCoContractAmtRate(ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate);

    public ResponseEntity batchDeleteUpdateZxCtEqCoContractAmtRate(List<ZxCtEqCoContractAmtRate> zxCtEqCoContractAmtRateList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
