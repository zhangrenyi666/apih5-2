package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightManagement;

public interface ZjXmJxQuarterlyWeightManagementService {

	public ResponseEntity getZjXmJxQuarterlyWeightManagementListByCondition(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement);

	public ResponseEntity getZjXmJxQuarterlyWeightManagementDetail(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement);

	public ResponseEntity saveZjXmJxQuarterlyWeightManagement(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement);

	public ResponseEntity updateZjXmJxQuarterlyWeightManagement(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement);

	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyWeightManagement(
			List<ZjXmJxQuarterlyWeightManagement> zjXmJxQuarterlyWeightManagementList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity singleUpdateZjXmJxQuarterlyWeightManagement(
			ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement);
}
