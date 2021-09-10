package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessmentDept;

public interface ZjXmJxQuarterlyAssessmentDeptService {

	public ResponseEntity getZjXmJxQuarterlyAssessmentDeptListByCondition(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept);

	public ResponseEntity getZjXmJxQuarterlyAssessmentDeptDetail(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept);

	public ResponseEntity saveZjXmJxQuarterlyAssessmentDept(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept);

	public ResponseEntity updateZjXmJxQuarterlyAssessmentDept(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept);

	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessmentDept(
			List<ZjXmJxQuarterlyAssessmentDept> zjXmJxQuarterlyAssessmentDeptList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity addZjXmJxQuarterlyAssessmentDeptToWeight(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept);

	public ResponseEntity updateZjXmJxQuarterlyAssessmentDeptToWeight(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept);

	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessmentDeptToWeight(
			List<ZjXmJxQuarterlyAssessmentDept> zjXmJxQuarterlyAssessmentDeptList);
}
