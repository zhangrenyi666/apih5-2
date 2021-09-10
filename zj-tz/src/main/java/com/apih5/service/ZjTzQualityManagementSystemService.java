package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzQualityManagementSystem;

public interface ZjTzQualityManagementSystemService {

    public ResponseEntity getZjTzQualityManagementSystemListByCondition(ZjTzQualityManagementSystem zjTzQualityManagementSystem);

    public ResponseEntity getZjTzQualityManagementSystemDetails(ZjTzQualityManagementSystem zjTzQualityManagementSystem);

    public ResponseEntity saveZjTzQualityManagementSystem(ZjTzQualityManagementSystem zjTzQualityManagementSystem);
    
    public ResponseEntity saveZjTzQualityManagementSystemAddFile(ZjTzQualityManagementSystem zjTzQualityManagementSystem);

    public ResponseEntity updateZjTzQualityManagementSystem(ZjTzQualityManagementSystem zjTzQualityManagementSystem);
    
    public ResponseEntity updateZjTzQualityManagementSystemAddFile(ZjTzQualityManagementSystem zjTzQualityManagementSystem);

    public ResponseEntity batchDeleteUpdateZjTzQualityManagementSystem(List<ZjTzQualityManagementSystem> zjTzQualityManagementSystemList);

}

