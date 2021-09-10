package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzGeneralContractSecurityDuty;

public interface ZjTzGeneralContractSecurityDutyService {

    public ResponseEntity getZjTzGeneralContractSecurityDutyListByCondition(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty);

    public ResponseEntity getZjTzGeneralContractSecurityDutyDetails(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty);

    public ResponseEntity saveZjTzGeneralContractSecurityDuty(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty);
    
    public ResponseEntity saveZjTzGeneralContractSecurityDutyAddFile(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty);

    public ResponseEntity updateZjTzGeneralContractSecurityDuty(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty);
    
    public ResponseEntity updateZjTzGeneralContractSecurityDutyAddFile(ZjTzGeneralContractSecurityDuty zjTzGeneralContractSecurityDuty);

    public ResponseEntity batchDeleteUpdateZjTzGeneralContractSecurityDuty(List<ZjTzGeneralContractSecurityDuty> zjTzGeneralContractSecurityDutyList);

}

