package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSafetyManagementOrgan;

public interface ZjTzSafetyManagementOrganService {

    public ResponseEntity getZjTzSafetyManagementOrganListByCondition(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan);

    public ResponseEntity getZjTzSafetyManagementOrganDetails(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan);

    public ResponseEntity saveZjTzSafetyManagementOrgan(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan);
    
    public ResponseEntity saveZjTzSafetyManagementOrganAddFile(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan);

    public ResponseEntity updateZjTzSafetyManagementOrgan(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan);
    
    public ResponseEntity updateZjTzSafetyManagementOrganAddFile(ZjTzSafetyManagementOrgan zjTzSafetyManagementOrgan);

    public ResponseEntity batchDeleteUpdateZjTzSafetyManagementOrgan(List<ZjTzSafetyManagementOrgan> zjTzSafetyManagementOrganList);

}

