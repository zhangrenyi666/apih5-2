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
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailed;
import com.apih5.service.ZjXmJxTaskScoreDetailedService;

@RestController
public class ZjXmJxTaskScoreDetailedController {

	@Autowired(required = true)
	private ZjXmJxTaskScoreDetailedService zjXmJxTaskScoreDetailedService;

	@ApiOperation(value = "查询任务考核人员得分明细", notes = "查询任务考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxTaskScoreDetailedList")
	public ResponseEntity getZjXmJxTaskScoreDetailedList(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.getZjXmJxTaskScoreDetailedListByCondition(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "查询详情任务考核人员得分明细", notes = "查询详情任务考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxTaskScoreDetailedDetails")
	public ResponseEntity getZjXmJxTaskScoreDetailedDetails(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.getZjXmJxTaskScoreDetailedDetails(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "新增任务考核人员得分明细", notes = "新增任务考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@PostMapping("/addZjXmJxTaskScoreDetailed")
	public ResponseEntity addZjXmJxTaskScoreDetailed(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.saveZjXmJxTaskScoreDetailed(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "更新任务考核人员得分明细", notes = "更新任务考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@PostMapping("/updateZjXmJxTaskScoreDetailed")
	public ResponseEntity updateZjXmJxTaskScoreDetailed(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.updateZjXmJxTaskScoreDetailed(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "删除任务考核人员得分明细", notes = "删除任务考核人员得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailedList", value = "任务考核人员得分明细List", dataType = "List<ZjXmJxTaskScoreDetailed>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxTaskScoreDetailed")
	public ResponseEntity batchDeleteUpdateZjXmJxTaskScoreDetailed(
			@RequestBody(required = false) List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList) {
		return zjXmJxTaskScoreDetailedService.batchDeleteUpdateZjXmJxTaskScoreDetailed(zjXmJxTaskScoreDetailedList);
	}

	// +++++++++++++++++++++++++业务接口start+++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "根据得分表主键获取被考核人得分明细", notes = "根据得分表主键获取被考核人得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxTaskScoreDetailedListByAuditee")
	public ResponseEntity getZjXmJxTaskScoreDetailedListByAuditee(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.getZjXmJxTaskScoreDetailedListByAuditee(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "被考核者批量申诉得分明细(暂时废弃)", notes = "被考核者批量申诉得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@PostMapping("/batchAppealZjXmJxTaskScoreDetailed")
	public ResponseEntity batchAppealZjXmJxTaskScoreDetailed(
			@RequestBody(required = false) List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList) {
		return zjXmJxTaskScoreDetailedService.batchAppealZjXmJxTaskScoreDetailed(zjXmJxTaskScoreDetailedList);
	}

	@ApiOperation(value = "被考核者逐条申诉得分明细", notes = "被考核者逐条申诉得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/appealZjXmJxTaskScoreDetailedByAuditee")
	public ResponseEntity appealZjXmJxTaskScoreDetailedByAuditee(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.appealZjXmJxTaskScoreDetailedByAuditee(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "被考核者暂存或确认任务考核分数", notes = "被考核者确认任务考核分数")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/tempOrConfirmZjXmJxTaskScoreDetailedByAuditee")
	public ResponseEntity tempOrConfirmZjXmJxTaskScoreDetailedByAuditee(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.tempOrConfirmZjXmJxTaskScoreDetailedByAuditee(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "根据HR所在项目获取审核管理列表", notes = "根据HR所在项目获取审核管理列表")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxTaskScoreDetailedListByHR")
	public ResponseEntity getZjXmJxTaskScoreDetailedListByHR(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.getZjXmJxTaskScoreDetailedListByHR(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "HR驳回或者确认该得分明细申诉", notes = "HR驳回或者确认该得分明细申诉")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/rejectOrConfirmZjXmJxTaskScoreDetailedByHR")
	public ResponseEntity rejectOrConfirmZjXmJxTaskScoreDetailedByHR(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.rejectOrConfirmZjXmJxTaskScoreDetailedByHR(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "领导批量驳回或者提交该得分明细", notes = "领导批量驳回或者提交该得分明细")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/rejectOrSubmitZjXmJxTaskScoreDetailedByLeader")
	public ResponseEntity rejectOrSubmitZjXmJxTaskScoreDetailedByLeader(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.rejectOrSubmitZjXmJxTaskScoreDetailedByLeader(zjXmJxTaskScoreDetailed);
	}

	// +++++++++++++++++++++++++业务接口end+++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++++导出接口start+++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "根据项目/月份/月度考核获取任务业绩得分明细表", notes = "任务业绩得分明细表")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	// @RequireToken
	@PostMapping("/getZjXmJxTaskScoreDetailedTaskExcel")
	public ResponseEntity getZjXmJxTaskScoreDetailedTaskExcel(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		return zjXmJxTaskScoreDetailedService.getZjXmJxTaskScoreDetailedTaskExcel(zjXmJxTaskScoreDetailed);
	}

	@ApiOperation(value = "导出任务业绩得分明细表[客户端流文件]", notes = "导出任务业绩得分明细表[客户端流文件]")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailed", value = "任务考核人员得分明细entity", dataType = "ZjXmJxTaskScoreDetailed")
	// @RequireToken
	@PostMapping("/exportZjXmJxTaskScoreDetailedTaskExcel")
	public void exportZjXmJxTaskScoreDetailedTaskExcel(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed,
			HttpServletResponse response) {
		zjXmJxTaskScoreDetailedService.exportZjXmJxTaskScoreDetailedTaskExcel(zjXmJxTaskScoreDetailed, response);
	}
	// +++++++++++++++++++++++++导出接口end+++++++++++++++++++++++++++++++++++++++++++
}
