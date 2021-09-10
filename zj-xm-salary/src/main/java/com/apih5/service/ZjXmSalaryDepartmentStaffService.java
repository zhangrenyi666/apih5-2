package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartmentStaff;

public interface ZjXmSalaryDepartmentStaffService {

    public ResponseEntity getZjXmSalaryDepartmentStaffListByCondition(ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff);

    public ResponseEntity getZjXmSalaryDepartmentStaffDetails(ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff);

    public ResponseEntity saveZjXmSalaryDepartmentStaff(ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff);

    public ResponseEntity updateZjXmSalaryDepartmentStaff(ZjXmSalaryDepartmentStaff zjXmSalaryDepartmentStaff);

    public ResponseEntity batchDeleteUpdateZjXmSalaryDepartmentStaff(List<ZjXmSalaryDepartmentStaff> zjXmSalaryDepartmentStaffList);

}

