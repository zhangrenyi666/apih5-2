package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightDetails;

public interface ZjXmJxQuarterlyWeightDetailsService {

	public ResponseEntity getZjXmJxQuarterlyWeightDetailsListByCondition(
			ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails);

	public ResponseEntity getZjXmJxQuarterlyWeightDetailsDetail(
			ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails);

	public ResponseEntity saveZjXmJxQuarterlyWeightDetails(ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails);

	public ResponseEntity updateZjXmJxQuarterlyWeightDetails(ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails);

	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyWeightDetails(
			List<ZjXmJxQuarterlyWeightDetails> zjXmJxQuarterlyWeightDetailsList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
	public ResponseEntity batchConfirmZjXmJxQuarterlyWeightDetails(
			List<ZjXmJxQuarterlyWeightDetails> zjXmJxQuarterlyWeightDetailsList);

	public ResponseEntity checkZjXmJxQuarterlyWeightDetailsConfirmStatus(
			ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails);
}
