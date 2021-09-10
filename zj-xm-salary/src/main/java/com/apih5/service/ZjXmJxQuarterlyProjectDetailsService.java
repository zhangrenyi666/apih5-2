package com.apih5.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyProjectDetails;

import cn.hutool.json.JSONArray;

public interface ZjXmJxQuarterlyProjectDetailsService {

	public ResponseEntity getZjXmJxQuarterlyProjectDetailsListByCondition(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity getZjXmJxQuarterlyProjectDetailsDetail(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity saveZjXmJxQuarterlyProjectDetails(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity updateZjXmJxQuarterlyProjectDetails(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyProjectDetails(
			List<ZjXmJxQuarterlyProjectDetails> zjXmJxQuarterlyProjectDetailsList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity batchConfirmZjXmJxQuarterlyProjectDetails(
			List<ZjXmJxQuarterlyProjectDetails> zjXmJxQuarterlyProjectDetailsList);

	public ResponseEntity getZjXmJxQuarterlyProjectDetailsByPersonLiable(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity submitZjXmJxQuarterlyProjectDetailsByPersonLiable(List<Map<String, Object>> mapList);

	public ResponseEntity getZjXmJxQuarterlyProjectDetailsDeptColumn(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity countZjXmJxQuarterlyProjectDetailsByDept(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity getZjXmJxQuarterlyProjectDetailsWeightColumn(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity countZjXmJxQuarterlyProjectDetailsByWeight(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity checkZjXmJxQuarterlyProjectDetailsConfirmStatus(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public ResponseEntity checkZjXmJxQuarterlyProjectDetailsActualScore(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails);

	public void exportZjXmJxQuarterlyProjectDetailsDeptExcel(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails, HttpServletResponse response);

	public void exportZjXmJxQuarterlyProjectDetailsWeightExcel(
			ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails, HttpServletResponse response);
}
