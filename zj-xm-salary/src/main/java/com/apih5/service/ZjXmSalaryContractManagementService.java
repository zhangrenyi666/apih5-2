package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryContractManagement;

public interface ZjXmSalaryContractManagementService {

    public ResponseEntity getZjXmSalaryContractManagementListByCondition(ZjXmSalaryContractManagement zjXmSalaryContractManagement);

    public ResponseEntity getZjXmSalaryContractManagementDetails(ZjXmSalaryContractManagement zjXmSalaryContractManagement);

    public ResponseEntity saveZjXmSalaryContractManagement(ZjXmSalaryContractManagement zjXmSalaryContractManagement);

    public ResponseEntity updateZjXmSalaryContractManagement(ZjXmSalaryContractManagement zjXmSalaryContractManagement);

    public ResponseEntity batchDeleteUpdateZjXmSalaryContractManagement(List<ZjXmSalaryContractManagement> zjXmSalaryContractManagementList);

}

