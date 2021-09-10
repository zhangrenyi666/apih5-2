package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzGeneralContractQualityDuty;

public interface ZjTzGeneralContractQualityDutyService {

    public ResponseEntity getZjTzGeneralContractQualityDutyListByCondition(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty);

    public ResponseEntity getZjTzGeneralContractQualityDutyDetails(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty);

    public ResponseEntity saveZjTzGeneralContractQualityDuty(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty);
    
    public ResponseEntity saveZjTzGeneralContractQualityDutyAddFile(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty);

    public ResponseEntity updateZjTzGeneralContractQualityDuty(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty);
    
    public ResponseEntity updateZjTzGeneralContractQualityDutyAddFile(ZjTzGeneralContractQualityDuty zjTzGeneralContractQualityDuty);

    public ResponseEntity batchDeleteUpdateZjTzGeneralContractQualityDuty(List<ZjTzGeneralContractQualityDuty> zjTzGeneralContractQualityDutyList);

}

