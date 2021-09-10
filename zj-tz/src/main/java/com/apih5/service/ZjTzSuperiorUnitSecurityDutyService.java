package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitSecurityDuty;

public interface ZjTzSuperiorUnitSecurityDutyService {

    public ResponseEntity getZjTzSuperiorUnitSecurityDutyListByCondition(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty);

    public ResponseEntity getZjTzSuperiorUnitSecurityDutyDetails(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty);

    public ResponseEntity saveZjTzSuperiorUnitSecurityDuty(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty);
    
    public ResponseEntity saveZjTzSuperiorUnitSecurityDutyAddFile(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty);

    public ResponseEntity updateZjTzSuperiorUnitSecurityDuty(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty);
    
    public ResponseEntity updateZjTzSuperiorUnitSecurityDutyAddFile(ZjTzSuperiorUnitSecurityDuty zjTzSuperiorUnitSecurityDuty);

    public ResponseEntity batchDeleteUpdateZjTzSuperiorUnitSecurityDuty(List<ZjTzSuperiorUnitSecurityDuty> zjTzSuperiorUnitSecurityDutyList);

}

