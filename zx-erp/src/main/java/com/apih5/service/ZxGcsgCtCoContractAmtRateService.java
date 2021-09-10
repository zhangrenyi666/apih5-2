package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCtCoContractAmtRate;

public interface ZxGcsgCtCoContractAmtRateService {

    public ResponseEntity getZxGcsgCtCoContractAmtRateListByCondition(ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate);

    public ResponseEntity getZxGcsgCtCoContractAmtRateDetail(ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate);

    public ResponseEntity saveZxGcsgCtCoContractAmtRate(ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate);

    public ResponseEntity updateZxGcsgCtCoContractAmtRate(ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate);

    public ResponseEntity batchDeleteUpdateZxGcsgCtCoContractAmtRate(List<ZxGcsgCtCoContractAmtRate> zxGcsgCtCoContractAmtRateList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
