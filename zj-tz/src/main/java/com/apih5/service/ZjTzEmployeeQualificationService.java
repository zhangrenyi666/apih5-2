package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeeQualification;

public interface ZjTzEmployeeQualificationService {

    public ResponseEntity getZjTzEmployeeQualificationListByCondition(ZjTzEmployeeQualification zjTzEmployeeQualification);

    public ResponseEntity getZjTzEmployeeQualificationDetails(ZjTzEmployeeQualification zjTzEmployeeQualification);

    public ResponseEntity saveZjTzEmployeeQualification(ZjTzEmployeeQualification zjTzEmployeeQualification);

    public ResponseEntity updateZjTzEmployeeQualification(ZjTzEmployeeQualification zjTzEmployeeQualification);

    public ResponseEntity batchDeleteUpdateZjTzEmployeeQualification(List<ZjTzEmployeeQualification> zjTzEmployeeQualificationList);

}

