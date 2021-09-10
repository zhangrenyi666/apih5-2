package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyLibraryDetails;

public interface ZjXmJxQuarterlyLibraryDetailsService {

	public ResponseEntity getZjXmJxQuarterlyLibraryDetailsListByCondition(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails);

	public ResponseEntity getZjXmJxQuarterlyLibraryDetailsDetail(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails);

	public ResponseEntity saveZjXmJxQuarterlyLibraryDetails(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails);

	public ResponseEntity updateZjXmJxQuarterlyLibraryDetails(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails);

	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyLibraryDetails(
			List<ZjXmJxQuarterlyLibraryDetails> zjXmJxQuarterlyLibraryDetailsList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity batchConfirmZjXmJxQuarterlyLibraryDetails(
			List<ZjXmJxQuarterlyLibraryDetails> zjXmJxQuarterlyLibraryDetailsList);

	public ResponseEntity checkZjXmJxQuarterlyLibraryDetailsConfirmStatus(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails);
}
