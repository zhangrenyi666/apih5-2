package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzSecurityManagementSystem;

public interface ZjTzSecurityManagementSystemService {

    public ResponseEntity getZjTzSecurityManagementSystemListByCondition(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem);

    public ResponseEntity getZjTzSecurityManagementSystemDetails(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem);

    public ResponseEntity saveZjTzSecurityManagementSystem(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem);
    
    public ResponseEntity saveZjTzSecurityManagementSystemAddFile(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem);

    public ResponseEntity updateZjTzSecurityManagementSystem(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem);
    
    public ResponseEntity updateZjTzSecurityManagementSystemAddFile(ZjTzSecurityManagementSystem zjTzSecurityManagementSystem);

    public ResponseEntity batchDeleteUpdateZjTzSecurityManagementSystem(List<ZjTzSecurityManagementSystem> zjTzSecurityManagementSystemList);

}

