package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtOtherManageAmtRate;

public interface ZxCtOtherManageAmtRateService {

    public ResponseEntity getZxCtOtherManageAmtRateListByCondition(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate);

    public ResponseEntity getZxCtOtherManageAmtRateDetail(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate);

    public ResponseEntity saveZxCtOtherManageAmtRate(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate);

    public ResponseEntity updateZxCtOtherManageAmtRate(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate);

    public ResponseEntity batchDeleteUpdateZxCtOtherManageAmtRate(List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxCtOtherManageAmtRateByContractId(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate);

}
