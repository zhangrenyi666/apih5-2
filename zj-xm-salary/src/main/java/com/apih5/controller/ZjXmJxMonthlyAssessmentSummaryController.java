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
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessmentSummary;
import com.apih5.service.ZjXmJxMonthlyAssessmentSummaryService;

@RestController
public class ZjXmJxMonthlyAssessmentSummaryController {

	@Autowired(required = true)
	private ZjXmJxMonthlyAssessmentSummaryService zjXmJxMonthlyAssessmentSummaryService;

	@ApiOperation(value = "查询项目员工月度考核评分汇总", notes = "查询项目员工月度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	@RequireToken
	@PostMapping("/getZjXmJxMonthlyAssessmentSummaryList")
	public ResponseEntity getZjXmJxMonthlyAssessmentSummaryList(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		return zjXmJxMonthlyAssessmentSummaryService
				.getZjXmJxMonthlyAssessmentSummaryListByCondition(zjXmJxMonthlyAssessmentSummary);
	}

	@ApiOperation(value = "查询详情项目员工月度考核评分汇总", notes = "查询详情项目员工月度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	@RequireToken
	@PostMapping("/getZjXmJxMonthlyAssessmentSummaryDetails")
	public ResponseEntity getZjXmJxMonthlyAssessmentSummaryDetails(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		return zjXmJxMonthlyAssessmentSummaryService
				.getZjXmJxMonthlyAssessmentSummaryDetails(zjXmJxMonthlyAssessmentSummary);
	}

	@ApiOperation(value = "新增项目员工月度考核评分汇总", notes = "新增项目员工月度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	@RequireToken
	@PostMapping("/addZjXmJxMonthlyAssessmentSummary")
	public ResponseEntity addZjXmJxMonthlyAssessmentSummary(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		return zjXmJxMonthlyAssessmentSummaryService.saveZjXmJxMonthlyAssessmentSummary(zjXmJxMonthlyAssessmentSummary);
	}

	@ApiOperation(value = "更新项目员工月度考核评分汇总", notes = "更新项目员工月度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	@RequireToken
	@PostMapping("/updateZjXmJxMonthlyAssessmentSummary")
	public ResponseEntity updateZjXmJxMonthlyAssessmentSummary(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		return zjXmJxMonthlyAssessmentSummaryService
				.updateZjXmJxMonthlyAssessmentSummary(zjXmJxMonthlyAssessmentSummary);
	}

	@ApiOperation(value = "删除项目员工月度考核评分汇总", notes = "删除项目员工月度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummaryList", value = "项目员工月度考核评分汇总List", dataType = "List<ZjXmJxMonthlyAssessmentSummary>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxMonthlyAssessmentSummary")
	public ResponseEntity batchDeleteUpdateZjXmJxMonthlyAssessmentSummary(
			@RequestBody(required = false) List<ZjXmJxMonthlyAssessmentSummary> zjXmJxMonthlyAssessmentSummaryList) {
		return zjXmJxMonthlyAssessmentSummaryService
				.batchDeleteUpdateZjXmJxMonthlyAssessmentSummary(zjXmJxMonthlyAssessmentSummaryList);
	}

	// =============================业务接口start=============================================
	@ApiOperation(value = "定时统计项目员工月度考核评分汇总", notes = "定时统计项目员工月度考核评分汇总")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	@PostMapping("/timingToCountZjXmJxMonthlyAssessmentSummary")
	public ResponseEntity timingToCountZjXmJxMonthlyAssessmentSummary(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary)
			throws Exception {
		return zjXmJxMonthlyAssessmentSummaryService
				.timingToCountZjXmJxMonthlyAssessmentSummary(zjXmJxMonthlyAssessmentSummary);
	}

	@ApiOperation(value = "导出月度考核汇总排名页面[返回URL]", notes = "导出月度考核汇总排名页面")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	// @RequireToken
	@PostMapping("/exportRankZjXmJxMonthlyAssessmentSummary2")
	public ResponseEntity exportRankZjXmJxMonthlyAssessmentSummary2(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		return zjXmJxMonthlyAssessmentSummaryService
				.exportRankZjXmJxMonthlyAssessmentSummary2(zjXmJxMonthlyAssessmentSummary);
	}

	@ApiOperation(value = "导出月度考核汇总末位人员页面[返回URL]", notes = "导出月度考核汇总末位人员页面")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	// @RequireToken
	@PostMapping("/exportLastZjXmJxMonthlyAssessmentSummary2")
	public ResponseEntity exportLastZjXmJxMonthlyAssessmentSummary2(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		return zjXmJxMonthlyAssessmentSummaryService
				.exportLastZjXmJxMonthlyAssessmentSummary2(zjXmJxMonthlyAssessmentSummary);
	}

	@ApiOperation(value = "导出月度考核汇总排名页面[客户端流文件]", notes = "导出月度考核汇总排名页面")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	// @RequireToken
	@PostMapping("/exportRankZjXmJxMonthlyAssessmentSummary")
	public void exportRankZjXmJxMonthlyAssessmentSummary(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary,
			HttpServletResponse response) {
		zjXmJxMonthlyAssessmentSummaryService.exportRankZjXmJxMonthlyAssessmentSummary(zjXmJxMonthlyAssessmentSummary,
				response);
	}

	@ApiOperation(value = "导出月度考核汇总末位人员页面[客户端流文件]", notes = "导出月度考核汇总末位人员页面")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentSummary", value = "项目员工月度考核评分汇总entity", dataType = "ZjXmJxMonthlyAssessmentSummary")
	// @RequireToken
	@PostMapping("/exportLastZjXmJxMonthlyAssessmentSummary")
	public void exportLastZjXmJxMonthlyAssessmentSummary(
			@RequestBody(required = false) ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary,
			HttpServletResponse response) {
		zjXmJxMonthlyAssessmentSummaryService.exportLastZjXmJxMonthlyAssessmentSummary(zjXmJxMonthlyAssessmentSummary,
				response);
	}
	// =============================业务接口end++==============================================
}