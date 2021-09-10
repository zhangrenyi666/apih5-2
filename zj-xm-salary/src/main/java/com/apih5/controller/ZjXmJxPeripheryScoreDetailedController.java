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
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailed;
import com.apih5.service.ZjXmJxPeripheryScoreDetailedService;

@RestController
public class ZjXmJxPeripheryScoreDetailedController {

	@Autowired(required = true)
	private ZjXmJxPeripheryScoreDetailedService zjXmJxPeripheryScoreDetailedService;

	@ApiOperation(value = "查询周边考核人员得分明细", notes = "查询周边考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxPeripheryScoreDetailedList")
	public ResponseEntity getZjXmJxPeripheryScoreDetailedList(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		return zjXmJxPeripheryScoreDetailedService
				.getZjXmJxPeripheryScoreDetailedListByCondition(zjXmJxPeripheryScoreDetailed);
	}

	@ApiOperation(value = "查询详情周边考核人员得分明细", notes = "查询详情周边考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxPeripheryScoreDetailedDetails")
	public ResponseEntity getZjXmJxPeripheryScoreDetailedDetails(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		return zjXmJxPeripheryScoreDetailedService.getZjXmJxPeripheryScoreDetailedDetails(zjXmJxPeripheryScoreDetailed);
	}

	@ApiOperation(value = "新增周边考核人员得分明细", notes = "新增周边考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	@RequireToken
	@PostMapping("/addZjXmJxPeripheryScoreDetailed")
	public ResponseEntity addZjXmJxPeripheryScoreDetailed(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		return zjXmJxPeripheryScoreDetailedService.saveZjXmJxPeripheryScoreDetailed(zjXmJxPeripheryScoreDetailed);
	}

	@ApiOperation(value = "更新周边考核人员得分明细", notes = "更新周边考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	@RequireToken
	@PostMapping("/updateZjXmJxPeripheryScoreDetailed")
	public ResponseEntity updateZjXmJxPeripheryScoreDetailed(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		return zjXmJxPeripheryScoreDetailedService.updateZjXmJxPeripheryScoreDetailed(zjXmJxPeripheryScoreDetailed);
	}

	@ApiOperation(value = "删除周边考核人员得分明细", notes = "删除周边考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailedList", value = "周边考核人员得分明细List", dataType = "List<ZjXmJxPeripheryScoreDetailed>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxPeripheryScoreDetailed")
	public ResponseEntity batchDeleteUpdateZjXmJxPeripheryScoreDetailed(
			@RequestBody(required = false) List<ZjXmJxPeripheryScoreDetailed> zjXmJxPeripheryScoreDetailedList) {
		return zjXmJxPeripheryScoreDetailedService
				.batchDeleteUpdateZjXmJxPeripheryScoreDetailed(zjXmJxPeripheryScoreDetailedList);
	}

	// ++++++++++++++++++++++++++++业务接口start+++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "根据月度考核id和打分者key获取被打分者列表", notes = "根据月度考核id和打分者key获取被打分者列表")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxPeripheryScoreDetailedListByReviewer")
	public ResponseEntity getZjXmJxPeripheryScoreDetailedListByReviewer(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		return zjXmJxPeripheryScoreDetailedService
				.getZjXmJxPeripheryScoreDetailedListByReviewer(zjXmJxPeripheryScoreDetailed);
	}

	@ApiOperation(value = "打分者暂存或者提交周边考核", notes = "打分者暂存或者提交周边考核")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/tempOrSubmitZjXmJxPeripheryScoreDetailedList")
	public ResponseEntity tempOrSubmitZjXmJxPeripheryScoreDetailedList(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) throws Exception {
		return zjXmJxPeripheryScoreDetailedService
				.tempOrSubmitZjXmJxPeripheryScoreDetailedList(zjXmJxPeripheryScoreDetailed);
	}

	// ++++++++++++++++++++++++++++业务接口end+++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++导出接口start+++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "根据项目/月度考核周边业绩得分明细表动态表头", notes = "周边业绩得分明细表")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	// @RequireToken
	@PostMapping("/getZjXmJxPeripheryScoreDetailedPeripheryExcelColumn")
	public ResponseEntity getZjXmJxPeripheryScoreDetailedPeripheryExcelColumn(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		return zjXmJxPeripheryScoreDetailedService
				.getZjXmJxPeripheryScoreDetailedPeripheryExcelColumn(zjXmJxPeripheryScoreDetailed);
	}

	@ApiOperation(value = "根据项目/月度考核周边业绩得分明细表", notes = "周边业绩得分明细表")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	// @RequireToken
	@PostMapping("/getZjXmJxPeripheryScoreDetailedPeripheryExcel")
	public ResponseEntity getZjXmJxPeripheryScoreDetailedPeripheryExcel(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		return zjXmJxPeripheryScoreDetailedService
				.getZjXmJxPeripheryScoreDetailedPeripheryExcel(zjXmJxPeripheryScoreDetailed);
	}

	@ApiOperation(value = "导出周边业绩得分明细表[客户端流文件]", notes = "导出周边业绩得分明细表[客户端流文件]")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	// @RequireToken
	@PostMapping("/exportZjXmJxPeripheryScoreDetailedPeripheryExcel")
	public void exportZjXmJxPeripheryScoreDetailedPeripheryExcel(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed,
			HttpServletResponse response) {
		zjXmJxPeripheryScoreDetailedService
				.exportZjXmJxPeripheryScoreDetailedPeripheryExcel(zjXmJxPeripheryScoreDetailed, response);
	}
	// ++++++++++++++++++++++++++++导出接口end+++++++++++++++++++++++++++++++++++++++++++++++++

	@ApiOperation(value = "重新算某一个月度考核的已出分的所有周边考核分数", notes = "重新算某一个月度考核的已出分的所有周边考核分数")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreDetailed", value = "周边考核人员得分明细entity", dataType = "ZjXmJxPeripheryScoreDetailed")
	@PostMapping("/recalculateZjXmJxPeripheryScoreDetailed")
	public ResponseEntity recalculateZjXmJxPeripheryScoreDetailed(
			@RequestBody(required = false) ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		return zjXmJxPeripheryScoreDetailedService
				.recalculateZjXmJxPeripheryScoreDetailed(zjXmJxPeripheryScoreDetailed);
	}
}
