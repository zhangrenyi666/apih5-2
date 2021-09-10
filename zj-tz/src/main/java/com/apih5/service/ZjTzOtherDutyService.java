package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzOtherDuty;

public interface ZjTzOtherDutyService {

    public ResponseEntity getZjTzOtherDutyListByCondition(ZjTzOtherDuty zjTzOtherDuty);

    public ResponseEntity getZjTzOtherDutyDetails(ZjTzOtherDuty zjTzOtherDuty);

    public ResponseEntity saveZjTzOtherDuty(ZjTzOtherDuty zjTzOtherDuty);
    
    public ResponseEntity saveZjTzOtherDutyAddFile(ZjTzOtherDuty zjTzOtherDuty);

    public ResponseEntity updateZjTzOtherDuty(ZjTzOtherDuty zjTzOtherDuty);
    
    public ResponseEntity updateZjTzOtherDutyAddFile(ZjTzOtherDuty zjTzOtherDuty);

    public ResponseEntity batchDeleteUpdateZjTzOtherDuty(List<ZjTzOtherDuty> zjTzOtherDutyList);

}

