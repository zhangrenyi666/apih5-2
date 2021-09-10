package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessment;

public interface ZjXmJxQuarterlyAssessmentService {

	public ResponseEntity getZjXmJxQuarterlyAssessmentListByCondition(
			ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment);

	public ResponseEntity getZjXmJxQuarterlyAssessmentDetail(ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment);

	public ResponseEntity saveZjXmJxQuarterlyAssessment(ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment);

	public ResponseEntity updateZjXmJxQuarterlyAssessment(ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment);

	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessment(
			List<ZjXmJxQuarterlyAssessment> zjXmJxQuarterlyAssessmentList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity cascadeAddZjXmJxQuarterlyAssessment(ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment);
}
