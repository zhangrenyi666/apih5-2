package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartment;

public interface ZjXmSalaryDepartmentService {

	public ResponseEntity getZjXmSalaryDepartmentListByCondition(ZjXmSalaryDepartment zjXmSalaryDepartment);

	public ResponseEntity getZjXmSalaryDepartmentDetails(ZjXmSalaryDepartment zjXmSalaryDepartment);

	public ResponseEntity saveZjXmSalaryDepartment(ZjXmSalaryDepartment zjXmSalaryDepartment);

	public ResponseEntity updateZjXmSalaryDepartment(ZjXmSalaryDepartment zjXmSalaryDepartment);

	public ResponseEntity batchDeleteUpdateZjXmSalaryDepartment(List<ZjXmSalaryDepartment> zjXmSalaryDepartmentList);

	public ResponseEntity updateZjXmSalaryDepartmentPrj(ZjXmSalaryDepartment zjXmSalaryDepartment);

	public ResponseEntity updateZjXmSalaryDepartmentDept(ZjXmSalaryDepartment zjXmSalaryDepartment);
}
