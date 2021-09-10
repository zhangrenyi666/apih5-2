package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzComplianceBase;

public interface ZjTzComplianceBaseService {

    public ResponseEntity getZjTzComplianceBaseListByCondition(ZjTzComplianceBase zjTzComplianceBase);

    public ResponseEntity getZjTzComplianceBaseDetails(ZjTzComplianceBase zjTzComplianceBase);

    public ResponseEntity saveZjTzComplianceBase(ZjTzComplianceBase zjTzComplianceBase);

    public ResponseEntity updateZjTzComplianceBase(ZjTzComplianceBase zjTzComplianceBase);

    public ResponseEntity batchDeleteUpdateZjTzComplianceBase(List<ZjTzComplianceBase> zjTzComplianceBaseList);

}

