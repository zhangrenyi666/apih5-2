package com.apih5.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxAnnualAssessmentSummary;
import com.apih5.service.ZjXmJxAnnualAssessmentSummaryService;

@RestController
public class ZjXmJxAnnualAssessmentSummaryController {

	@Autowired(required = true)
	private ZjXmJxAnnualAssessmentSummaryService zjXmJxAnnualAssessmentSummaryService;

	@ApiOperation(value = "查询项目员工年度考核评分汇总", notes = "查询项目员工年度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxAnnualAssessmentSummary", value = "项目员工年度考核评分汇总entity", dataType = "ZjXmJxAnnualAssessmentSummary")
	@RequireToken
	@PostMapping("/getZjXmJxAnnualAssessmentSummaryList")
	public ResponseEntity getZjXmJxAnnualAssessmentSummaryList(
			@RequestBody(required = false) ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		return zjXmJxAnnualAssessmentSummaryService
				.getZjXmJxAnnualAssessmentSummaryListByCondition(zjXmJxAnnualAssessmentSummary);
	}

	@ApiOperation(value = "查询详情项目员工年度考核评分汇总", notes = "查询详情项目员工年度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxAnnualAssessmentSummary", value = "项目员工年度考核评分汇总entity", dataType = "ZjXmJxAnnualAssessmentSummary")
	@RequireToken
	@PostMapping("/getZjXmJxAnnualAssessmentSummaryDetails")
	public ResponseEntity getZjXmJxAnnualAssessmentSummaryDetails(
			@RequestBody(required = false) ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		return zjXmJxAnnualAssessmentSummaryService
				.getZjXmJxAnnualAssessmentSummaryDetails(zjXmJxAnnualAssessmentSummary);
	}

	@ApiOperation(value = "新增项目员工年度考核评分汇总", notes = "新增项目员工年度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxAnnualAssessmentSummary", value = "项目员工年度考核评分汇总entity", dataType = "ZjXmJxAnnualAssessmentSummary")
	@RequireToken
	@PostMapping("/addZjXmJxAnnualAssessmentSummary")
	public ResponseEntity addZjXmJxAnnualAssessmentSummary(
			@RequestBody(required = false) ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		return zjXmJxAnnualAssessmentSummaryService.saveZjXmJxAnnualAssessmentSummary(zjXmJxAnnualAssessmentSummary);
	}

	@ApiOperation(value = "更新项目员工年度考核评分汇总", notes = "更新项目员工年度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxAnnualAssessmentSummary", value = "项目员工年度考核评分汇总entity", dataType = "ZjXmJxAnnualAssessmentSummary")
	@RequireToken
	@PostMapping("/updateZjXmJxAnnualAssessmentSummary")
	public ResponseEntity updateZjXmJxAnnualAssessmentSummary(
			@RequestBody(required = false) ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		return zjXmJxAnnualAssessmentSummaryService.updateZjXmJxAnnualAssessmentSummary(zjXmJxAnnualAssessmentSummary);
	}

	@ApiOperation(value = "删除项目员工年度考核评分汇总", notes = "删除项目员工年度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxAnnualAssessmentSummaryList", value = "项目员工年度考核评分汇总List", dataType = "List<ZjXmJxAnnualAssessmentSummary>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxAnnualAssessmentSummary")
	public ResponseEntity batchDeleteUpdateZjXmJxAnnualAssessmentSummary(
			@RequestBody(required = false) List<ZjXmJxAnnualAssessmentSummary> zjXmJxAnnualAssessmentSummaryList) {
		return zjXmJxAnnualAssessmentSummaryService
				.batchDeleteUpdateZjXmJxAnnualAssessmentSummary(zjXmJxAnnualAssessmentSummaryList);
	}

	// =============================业务接口start=============================================
	@ApiOperation(value = "定时统计项目员工年度考核评分汇总", notes = "定时统计项目员工年度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxAnnualAssessmentSummary", value = "项目员工年度考核评分汇总entity", dataType = "ZjXmJxAnnualAssessmentSummary")
	// @RequireToken
	@PostMapping("/timingToCountZjXmJxAnnualAssessmentSummary")
	public ResponseEntity timingToCountZjXmJxAnnualAssessmentSummary(
			@RequestBody(required = false) ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary)
			throws Exception {
		return zjXmJxAnnualAssessmentSummaryService
				.timingToCountZjXmJxAnnualAssessmentSummary(zjXmJxAnnualAssessmentSummary);
	}

	@ApiOperation(value = "导出年度考核汇总排名页面[客户端流文件]", notes = "导出年度考核汇总排名页面[客户端流文件]")
	@ApiImplicitParam(name = "zjXmJxAnnualAssessmentSummary", value = "项目员工年度考核评分汇总entity", dataType = "ZjXmJxAnnualAssessmentSummary")
	// @RequireToken
	@PostMapping("/exportRankZjXmJxAnnualAssessmentSummary")
	public void exportRankZjXmJxAnnualAssessmentSummary(
			@RequestBody(required = false) ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary,
			HttpServletResponse response) {
		zjXmJxAnnualAssessmentSummaryService.exportRankZjXmJxAnnualAssessmentSummary(zjXmJxAnnualAssessmentSummary,
				response);
	}
	// =============================业务接口end===============================================
}