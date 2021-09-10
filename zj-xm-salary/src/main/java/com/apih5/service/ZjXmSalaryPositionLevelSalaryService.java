package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryPositionLevelSalary;

public interface ZjXmSalaryPositionLevelSalaryService {

	public ResponseEntity getZjXmSalaryPositionLevelSalaryListByCondition(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary);

	public ResponseEntity getZjXmSalaryPositionLevelSalaryDetails(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary);

	public ResponseEntity saveZjXmSalaryPositionLevelSalary(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary);

	public ResponseEntity updateZjXmSalaryPositionLevelSalary(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary);

	public ResponseEntity batchDeleteUpdateZjXmSalaryPositionLevelSalary(
			List<ZjXmSalaryPositionLevelSalary> zjXmSalaryPositionLevelSalaryList);

	public ResponseEntity getZjXmSalaryPositionLevelSalarySelect(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary);
}
