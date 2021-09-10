package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryOfficeSalary;

public interface ZjXmSalaryOfficeSalaryService {

    public ResponseEntity getZjXmSalaryOfficeSalaryListByCondition(ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary);

    public ResponseEntity getZjXmSalaryOfficeSalaryDetails(ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary);

    public ResponseEntity saveZjXmSalaryOfficeSalary(ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary);

    public ResponseEntity updateZjXmSalaryOfficeSalary(ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary);

    public ResponseEntity batchDeleteUpdateZjXmSalaryOfficeSalary(List<ZjXmSalaryOfficeSalary> zjXmSalaryOfficeSalaryList);

}

