package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzQualityManagementOrgan;

public interface ZjTzQualityManagementOrganService {

    public ResponseEntity getZjTzQualityManagementOrganListByCondition(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan);

    public ResponseEntity getZjTzQualityManagementOrganDetails(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan);

    public ResponseEntity saveZjTzQualityManagementOrgan(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan);
    
    public ResponseEntity saveZjTzQualityManagementOrganAddFile(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan);

    public ResponseEntity updateZjTzQualityManagementOrgan(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan);
    
    public ResponseEntity updateZjTzQualityManagementOrganAddFile(ZjTzQualityManagementOrgan zjTzQualityManagementOrgan);

    public ResponseEntity batchDeleteUpdateZjTzQualityManagementOrgan(List<ZjTzQualityManagementOrgan> zjTzQualityManagementOrganList);

}

