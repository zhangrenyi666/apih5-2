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
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxPrincipalScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailed;
import com.apih5.service.ZjXmJxAssessmentUserScoreService;

@RestController
public class ZjXmJxAssessmentUserScoreController {

	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreService zjXmJxAssessmentUserScoreService;

	@ApiOperation(value = "查询项目月度考核人员得分", notes = "查询项目月度考核人员得分")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreList")
	public ResponseEntity getZjXmJxAssessmentUserScoreList(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService.getZjXmJxAssessmentUserScoreListByCondition(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "查询详情项目月度考核人员得分", notes = "查询详情项目月度考核人员得分")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreDetails")
	public ResponseEntity getZjXmJxAssessmentUserScoreDetails(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService.getZjXmJxAssessmentUserScoreDetails(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "新增项目月度考核人员得分", notes = "新增项目月度考核人员得分")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/addZjXmJxAssessmentUserScore")
	public ResponseEntity addZjXmJxAssessmentUserScore(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService.saveZjXmJxAssessmentUserScore(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "更新项目月度考核人员得分", notes = "更新项目月度考核人员得分")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/updateZjXmJxAssessmentUserScore")
	public ResponseEntity updateZjXmJxAssessmentUserScore(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService.updateZjXmJxAssessmentUserScore(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "删除项目月度考核人员得分", notes = "删除项目月度考核人员得分")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScoreList", value = "项目月度考核人员得分List", dataType = "List<ZjXmJxAssessmentUserScore>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxAssessmentUserScore")
	public ResponseEntity batchDeleteUpdateZjXmJxAssessmentUserScore(
			@RequestBody(required = false) List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList) {
		return zjXmJxAssessmentUserScoreService
				.batchDeleteUpdateZjXmJxAssessmentUserScore(zjXmJxAssessmentUserScoreList);
	}

	// +++++++++++++++++++++++++业务接口start+++++++++++++++++++++++++++++++++++++++++++++++

	@ApiOperation(value = "根据被打分者和任务考核类型获取考核得分列表", notes = "根据登陆者和任务考核类型获取考核得分列表")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreListByTaskAuditee")
	public ResponseEntity getZjXmJxAssessmentUserScoreListByTaskAuditee(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService
				.getZjXmJxAssessmentUserScoreListByTaskAuditee(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "根据评分者获取月度考核列表(待评分列表)", notes = "根据评分者获取月度考核列表")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreListByReviewer")
	public ResponseEntity getZjXmJxAssessmentUserScoreListByReviewer(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService.getZjXmJxAssessmentUserScoreListByReviewer(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "根据评分者和任务考核类型获取考核得分列表", notes = "根据评分者和任务考核类型获取考核得分列表")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreListByTaskReviewer")
	public ResponseEntity getZjXmJxAssessmentUserScoreListByTaskReviewer(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService
				.getZjXmJxAssessmentUserScoreListByTaskReviewer(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "(暂时废弃)根据月度考核id和打分者key和考核类型获取被打分者集合", notes = "根据月度考核id和打分者key和考核类型获取被打分者集合")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreListByPrincipalReviewer")
	public ResponseEntity getZjXmJxAssessmentUserScoreListByPrincipalReviewer(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService
				.getZjXmJxAssessmentUserScoreListByPrincipalReviewer(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "根据月度考核id和打分者key和综合评分类型获取被打分者列表", notes = "根据月度考核id和打分者key和考核类型获取被打分者集合")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreListByCompositeReviewer")
	public ResponseEntity getZjXmJxAssessmentUserScoreListByCompositeReviewer(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService
				.getZjXmJxAssessmentUserScoreListByCompositeReviewer(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "打分者(书记)批量暂存/提交综合评分", notes = "打分者(书记)批量暂存/提交综合评分")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScoreList", value = "项目月度考核人员得分List", dataType = "List<ZjXmJxAssessmentUserScore>")
	@RequireToken
	@PostMapping("/batchSubmitZjXmJxAssessmentUserScoreBySecretary")
	public ResponseEntity batchSubmitZjXmJxAssessmentUserScoreBySecretary(
			@RequestBody(required = false) List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList) {
		return zjXmJxAssessmentUserScoreService
				.batchSubmitZjXmJxAssessmentUserScoreBySecretary(zjXmJxAssessmentUserScoreList);
	}

	@ApiOperation(value = "我的各个月度考核得分列表", notes = "我的各个月度考核得分列表")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreListByAuditee")
	public ResponseEntity getZjXmJxAssessmentUserScoreListByAuditee(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService.getZjXmJxAssessmentUserScoreListByAuditee(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "领导批量撤回多人的任务考核得分明细以及任务得分", notes = "领导批量撤回多人的任务考核得分明细以及任务得分")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScoreList", value = "项目月度考核人员得分List", dataType = "List<ZjXmJxAssessmentUserScore>")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/cancelZjXmJxTaskScoreDetailedByLeader")
	public ResponseEntity cancelZjXmJxTaskScoreDetailedByLeader(
			@RequestBody(required = false) List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList) {
		return zjXmJxAssessmentUserScoreService.cancelZjXmJxTaskScoreDetailedByLeader(zjXmJxAssessmentUserScoreList);
	}

	// +++++++++++++++++++++++++业务接口end+++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++++++++++导出接口start+++++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "根据项目/月度考核获取综合评价扣分明细表", notes = "综合评价扣分明细表")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	@RequireToken
	@PostMapping("/getZjXmJxAssessmentUserScoreCompositeExcel")
	public ResponseEntity getZjXmJxAssessmentUserScoreCompositeExcel(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		return zjXmJxAssessmentUserScoreService.getZjXmJxAssessmentUserScoreCompositeExcel(zjXmJxAssessmentUserScore);
	}

	@ApiOperation(value = "导出综合评价扣分明细表[客户端流文件]", notes = "导出综合评价扣分明细表[客户端流文件]")
	@ApiImplicitParam(name = "zjXmJxAssessmentUserScore", value = "项目月度考核人员得分entity", dataType = "ZjXmJxAssessmentUserScore")
	// @RequireToken
	@PostMapping("/exportZjXmJxAssessmentUserScoreCompositeExcel")
	public void exportZjXmJxAssessmentUserScoreCompositeExcel(
			@RequestBody(required = false) ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore,
			HttpServletResponse response) {
		zjXmJxAssessmentUserScoreService.exportZjXmJxAssessmentUserScoreCompositeExcel(zjXmJxAssessmentUserScore,
				response);
	}
	// +++++++++++++++++++++++++导出接口end+++++++++++++++++++++++++++++++++++++++++++++++++++
}
