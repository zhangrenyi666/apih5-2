package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryUserDepartment;

public interface ZjXmSalaryUserDepartmentService {

    public ResponseEntity getZjXmSalaryUserDepartmentListByCondition(ZjXmSalaryUserDepartment zjXmSalaryUserDepartment);

    public ResponseEntity getZjXmSalaryUserDepartmentDetails(ZjXmSalaryUserDepartment zjXmSalaryUserDepartment);

    public ResponseEntity saveZjXmSalaryUserDepartment(ZjXmSalaryUserDepartment zjXmSalaryUserDepartment);

    public ResponseEntity updateZjXmSalaryUserDepartment(ZjXmSalaryUserDepartment zjXmSalaryUserDepartment);

    public ResponseEntity batchDeleteUpdateZjXmSalaryUserDepartment(List<ZjXmSalaryUserDepartment> zjXmSalaryUserDepartmentList);

}

