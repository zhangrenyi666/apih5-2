package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxMonthlySummaryFlow;
import com.apih5.service.ZjXmJxMonthlySummaryFlowService;

@RestController
public class ZjXmJxMonthlySummaryFlowController {

	@Autowired(required = true)
	private ZjXmJxMonthlySummaryFlowService zjXmJxMonthlySummaryFlowService;

	@ApiOperation(value = "查询月度考核评分汇总审核", notes = "查询月度考核评分汇总审核")
	@ApiImplicitParam(name = "zjXmJxMonthlySummaryFlow", value = "月度考核评分汇总审核entity", dataType = "ZjXmJxMonthlySummaryFlow")
	@RequireToken
	@PostMapping("/getZjXmJxMonthlySummaryFlowList")
	public ResponseEntity getZjXmJxMonthlySummaryFlowList(
			@RequestBody(required = false) ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow) {
		return zjXmJxMonthlySummaryFlowService.getZjXmJxMonthlySummaryFlowListByCondition(zjXmJxMonthlySummaryFlow);
	}

	@ApiOperation(value = "查询详情月度考核评分汇总审核", notes = "查询详情月度考核评分汇总审核")
	@ApiImplicitParam(name = "zjXmJxMonthlySummaryFlow", value = "月度考核评分汇总审核entity", dataType = "ZjXmJxMonthlySummaryFlow")
	@RequireToken
	@PostMapping("/getZjXmJxMonthlySummaryFlowDetails")
	public ResponseEntity getZjXmJxMonthlySummaryFlowDetails(
			@RequestBody(required = false) ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow) {
		return zjXmJxMonthlySummaryFlowService.getZjXmJxMonthlySummaryFlowDetails(zjXmJxMonthlySummaryFlow);
	}

	@ApiOperation(value = "新增月度考核评分汇总审核", notes = "新增月度考核评分汇总审核")
	@ApiImplicitParam(name = "zjXmJxMonthlySummaryFlow", value = "月度考核评分汇总审核entity", dataType = "ZjXmJxMonthlySummaryFlow")
	@RequireToken
	@PostMapping("/addZjXmJxMonthlySummaryFlow")
	public ResponseEntity addZjXmJxMonthlySummaryFlow(
			@RequestBody(required = false) ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow) {
		return zjXmJxMonthlySummaryFlowService.saveZjXmJxMonthlySummaryFlow(zjXmJxMonthlySummaryFlow);
	}

	@ApiOperation(value = "更新月度考核评分汇总审核", notes = "更新月度考核评分汇总审核")
	@ApiImplicitParam(name = "zjXmJxMonthlySummaryFlow", value = "月度考核评分汇总审核entity", dataType = "ZjXmJxMonthlySummaryFlow")
	@RequireToken
	@PostMapping("/updateZjXmJxMonthlySummaryFlow")
	public ResponseEntity updateZjXmJxMonthlySummaryFlow(
			@RequestBody(required = false) ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow) {
		return zjXmJxMonthlySummaryFlowService.updateZjXmJxMonthlySummaryFlow(zjXmJxMonthlySummaryFlow);
	}

	@ApiOperation(value = "删除月度考核评分汇总审核", notes = "删除月度考核评分汇总审核")
	@ApiImplicitParam(name = "zjXmJxMonthlySummaryFlowList", value = "月度考核评分汇总审核List", dataType = "List<ZjXmJxMonthlySummaryFlow>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxMonthlySummaryFlow")
	public ResponseEntity batchDeleteUpdateZjXmJxMonthlySummaryFlow(
			@RequestBody(required = false) List<ZjXmJxMonthlySummaryFlow> zjXmJxMonthlySummaryFlowList) {
		return zjXmJxMonthlySummaryFlowService.batchDeleteUpdateZjXmJxMonthlySummaryFlow(zjXmJxMonthlySummaryFlowList);
	}

}