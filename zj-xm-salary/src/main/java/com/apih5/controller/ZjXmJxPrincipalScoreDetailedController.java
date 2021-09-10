package com.apih5.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import com.apih5.framework.annotation.DuplicateCommit;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxAnnualAssessmentSummary;
import com.apih5.mybatis.pojo.ZjXmJxPrincipalScoreDetailed;
import com.apih5.service.ZjXmJxPrincipalScoreDetailedService;

@RestController
public class ZjXmJxPrincipalScoreDetailedController {

	@Autowired(required = true)
	private ZjXmJxPrincipalScoreDetailedService zjXmJxPrincipalScoreDetailedService;

	@ApiOperation(value = "查询正职评分人员得分明细", notes = "查询正职评分人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailed", value = "正职评分人员得分明细entity", dataType = "ZjXmJxPrincipalScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxPrincipalScoreDetailedList")
	public ResponseEntity getZjXmJxPrincipalScoreDetailedList(
			@RequestBody(required = false) ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		return zjXmJxPrincipalScoreDetailedService
				.getZjXmJxPrincipalScoreDetailedListByCondition(zjXmJxPrincipalScoreDetailed);
	}

	@ApiOperation(value = "查询详情正职评分人员得分明细", notes = "查询详情正职评分人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailed", value = "正职评分人员得分明细entity", dataType = "ZjXmJxPrincipalScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxPrincipalScoreDetailedDetails")
	public ResponseEntity getZjXmJxPrincipalScoreDetailedDetails(
			@RequestBody(required = false) ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		return zjXmJxPrincipalScoreDetailedService.getZjXmJxPrincipalScoreDetailedDetails(zjXmJxPrincipalScoreDetailed);
	}

	@ApiOperation(value = "新增正职评分人员得分明细", notes = "新增正职评分人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailed", value = "正职评分人员得分明细entity", dataType = "ZjXmJxPrincipalScoreDetailed")
	@RequireToken
	@PostMapping("/addZjXmJxPrincipalScoreDetailed")
	public ResponseEntity addZjXmJxPrincipalScoreDetailed(
			@RequestBody(required = false) ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		return zjXmJxPrincipalScoreDetailedService.saveZjXmJxPrincipalScoreDetailed(zjXmJxPrincipalScoreDetailed);
	}

	@ApiOperation(value = "更新正职评分人员得分明细", notes = "更新正职评分人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailed", value = "正职评分人员得分明细entity", dataType = "ZjXmJxPrincipalScoreDetailed")
	@RequireToken
	@PostMapping("/updateZjXmJxPrincipalScoreDetailed")
	public ResponseEntity updateZjXmJxPrincipalScoreDetailed(
			@RequestBody(required = false) ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		return zjXmJxPrincipalScoreDetailedService.updateZjXmJxPrincipalScoreDetailed(zjXmJxPrincipalScoreDetailed);
	}

	@ApiOperation(value = "删除正职评分人员得分明细", notes = "删除正职评分人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailedList", value = "正职评分人员得分明细List", dataType = "List<ZjXmJxPrincipalScoreDetailed>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxPrincipalScoreDetailed")
	public ResponseEntity batchDeleteUpdateZjXmJxPrincipalScoreDetailed(
			@RequestBody(required = false) List<ZjXmJxPrincipalScoreDetailed> zjXmJxPrincipalScoreDetailedList) {
		return zjXmJxPrincipalScoreDetailedService
				.batchDeleteUpdateZjXmJxPrincipalScoreDetailed(zjXmJxPrincipalScoreDetailedList);
	}

	// ++++++++++++++++++++++++++++++++业务接口start++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "根据月度考核id和打分者key获取被打分者列表", notes = "根据月度考核id和打分者key获取被打分者列表")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailed", value = "正职评分人员得分明细entity", dataType = "ZjXmJxPrincipalScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxPrincipalScoreDetailedListByReviewer")
	public ResponseEntity getZjXmJxPrincipalScoreDetailedListByReviewer(
			@RequestBody(required = false) ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		return zjXmJxPrincipalScoreDetailedService
				.getZjXmJxPrincipalScoreDetailedListByReviewer(zjXmJxPrincipalScoreDetailed);
	}

	@ApiOperation(value = "打分者暂存或者提交正职评分", notes = "打分者暂存或者提交正职评分")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailed", value = "正职评分人员得分明细entity", dataType = "ZjXmJxPrincipalScoreDetailed")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/tempOrSubmitZjXmJxPrincipalScoreDetailedList")
	public ResponseEntity tempOrSubmitZjXmJxPrincipalScoreDetailedList(
			@RequestBody(required = false) ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		return zjXmJxPrincipalScoreDetailedService
				.tempOrSubmitZjXmJxPrincipalScoreDetailedList(zjXmJxPrincipalScoreDetailed);
	}

	// ++++++++++++++++++++++++++++++++业务接口end++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++导出接口start++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "根据项目/月度考核/月份获取正职得分明细表", notes = "正职得分明细表")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailed", value = "正职评分人员得分明细entity", dataType = "ZjXmJxPrincipalScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxPrincipalScoreDetailedPrincipalExcel")
	public ResponseEntity getZjXmJxPrincipalScoreDetailedPrincipalExcel(
			@RequestBody(required = false) ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		return zjXmJxPrincipalScoreDetailedService
				.getZjXmJxPrincipalScoreDetailedPrincipalExcel(zjXmJxPrincipalScoreDetailed);
	}

	@ApiOperation(value = "导出正职得分明细表[客户端流文件]", notes = "导出正职得分明细表[客户端流文件]")
	@ApiImplicitParam(name = "zjXmJxPrincipalScoreDetailed", value = "正职评分人员得分明细entity", dataType = "ZjXmJxPrincipalScoreDetailed")
	// @RequireToken
	@PostMapping("/exportZjXmJxPrincipalScoreDetailedPrincipalExcel")
	public void exportZjXmJxPrincipalScoreDetailedPrincipalExcel(
			@RequestBody(required = false) ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed,
			HttpServletResponse response) {
		zjXmJxPrincipalScoreDetailedService
				.exportZjXmJxPrincipalScoreDetailedPrincipalExcel(zjXmJxPrincipalScoreDetailed, response);
	}
	// ++++++++++++++++++++++++++++++++导出接口end++++++++++++++++++++++++++++++++++++++++++++++++
}
