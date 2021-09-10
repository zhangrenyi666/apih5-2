package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzOtherManagementSystem;

public interface ZjTzOtherManagementSystemService {

    public ResponseEntity getZjTzOtherManagementSystemListByCondition(ZjTzOtherManagementSystem zjTzOtherManagementSystem);

    public ResponseEntity getZjTzOtherManagementSystemDetails(ZjTzOtherManagementSystem zjTzOtherManagementSystem);

    public ResponseEntity saveZjTzOtherManagementSystem(ZjTzOtherManagementSystem zjTzOtherManagementSystem);
    
    public ResponseEntity saveZjTzOtherManagementSystemAddFile(ZjTzOtherManagementSystem zjTzOtherManagementSystem);

    public ResponseEntity updateZjTzOtherManagementSystem(ZjTzOtherManagementSystem zjTzOtherManagementSystem);
    
    public ResponseEntity updateZjTzOtherManagementSystemAddFile(ZjTzOtherManagementSystem zjTzOtherManagementSystem);

    public ResponseEntity batchDeleteUpdateZjTzOtherManagementSystem(List<ZjTzOtherManagementSystem> zjTzOtherManagementSystemList);

}

