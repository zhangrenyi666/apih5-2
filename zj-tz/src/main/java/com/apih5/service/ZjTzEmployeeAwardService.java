package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeeAward;

public interface ZjTzEmployeeAwardService {

    public ResponseEntity getZjTzEmployeeAwardListByCondition(ZjTzEmployeeAward zjTzEmployeeAward);

    public ResponseEntity getZjTzEmployeeAwardDetails(ZjTzEmployeeAward zjTzEmployeeAward);

    public ResponseEntity saveZjTzEmployeeAward(ZjTzEmployeeAward zjTzEmployeeAward);

    public ResponseEntity updateZjTzEmployeeAward(ZjTzEmployeeAward zjTzEmployeeAward);

    public ResponseEntity batchDeleteUpdateZjTzEmployeeAward(List<ZjTzEmployeeAward> zjTzEmployeeAwardList);

}

