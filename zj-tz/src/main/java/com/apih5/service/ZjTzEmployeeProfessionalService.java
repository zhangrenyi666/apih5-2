package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzEmployeeProfessional;

public interface ZjTzEmployeeProfessionalService {

    public ResponseEntity getZjTzEmployeeProfessionalListByCondition(ZjTzEmployeeProfessional zjTzEmployeeProfessional);

    public ResponseEntity getZjTzEmployeeProfessionalDetails(ZjTzEmployeeProfessional zjTzEmployeeProfessional);

    public ResponseEntity saveZjTzEmployeeProfessional(ZjTzEmployeeProfessional zjTzEmployeeProfessional);

    public ResponseEntity updateZjTzEmployeeProfessional(ZjTzEmployeeProfessional zjTzEmployeeProfessional);

    public ResponseEntity batchDeleteUpdateZjTzEmployeeProfessional(List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionalList);

}

