package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryOfficeSalaryDetails;

public interface ZjXmSalaryOfficeSalaryDetailsService {

    public ResponseEntity getZjXmSalaryOfficeSalaryDetailsListByCondition(ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails);

    public ResponseEntity getZjXmSalaryOfficeSalaryDetailsDetails(ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails);

    public ResponseEntity saveZjXmSalaryOfficeSalaryDetails(ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails);

    public ResponseEntity updateZjXmSalaryOfficeSalaryDetails(ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails);

    public ResponseEntity batchDeleteUpdateZjXmSalaryOfficeSalaryDetails(List<ZjXmSalaryOfficeSalaryDetails> zjXmSalaryOfficeSalaryDetailsList);

}

