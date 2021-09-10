package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryCertificateManagement;

public interface ZjXmSalaryCertificateManagementService {

    public ResponseEntity getZjXmSalaryCertificateManagementListByCondition(ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement);

    public ResponseEntity getZjXmSalaryCertificateManagementDetails(ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement);

    public ResponseEntity saveZjXmSalaryCertificateManagement(ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement);

    public ResponseEntity updateZjXmSalaryCertificateManagement(ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement);

    public ResponseEntity batchDeleteUpdateZjXmSalaryCertificateManagement(List<ZjXmSalaryCertificateManagement> zjXmSalaryCertificateManagementList);

}

