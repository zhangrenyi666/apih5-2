package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeeEducation;

public interface ZjTzEmployeeEducationService {

    public ResponseEntity getZjTzEmployeeEducationListByCondition(ZjTzEmployeeEducation zjTzEmployeeEducation);

    public ResponseEntity getZjTzEmployeeEducationDetails(ZjTzEmployeeEducation zjTzEmployeeEducation);

    public ResponseEntity saveZjTzEmployeeEducation(ZjTzEmployeeEducation zjTzEmployeeEducation);

    public ResponseEntity updateZjTzEmployeeEducation(ZjTzEmployeeEducation zjTzEmployeeEducation);

    public ResponseEntity batchDeleteUpdateZjTzEmployeeEducation(List<ZjTzEmployeeEducation> zjTzEmployeeEducationList);

    public ZjTzEmployeeEducation printZjTzEmployeeEducation(ZjTzEmployeeEducation zjTzEmployeeEducation);
}

