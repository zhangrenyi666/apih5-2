package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSuperiorUnitQualityDuty;

public interface ZjTzSuperiorUnitQualityDutyService {

    public ResponseEntity getZjTzSuperiorUnitQualityDutyListByCondition(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty);

    public ResponseEntity getZjTzSuperiorUnitQualityDutyDetails(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty);

    public ResponseEntity saveZjTzSuperiorUnitQualityDuty(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty);
    
    public ResponseEntity saveZjTzSuperiorUnitQualityDutyAddFile(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty);

    public ResponseEntity updateZjTzSuperiorUnitQualityDuty(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty);
    
    public ResponseEntity updateZjTzSuperiorUnitQualityDutyAddFile(ZjTzSuperiorUnitQualityDuty zjTzSuperiorUnitQualityDuty);

    public ResponseEntity batchDeleteUpdateZjTzSuperiorUnitQualityDuty(List<ZjTzSuperiorUnitQualityDuty> zjTzSuperiorUnitQualityDutyList);

}

