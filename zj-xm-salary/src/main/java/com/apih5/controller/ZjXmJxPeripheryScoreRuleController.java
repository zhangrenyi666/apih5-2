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
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreRule;
import com.apih5.service.ZjXmJxPeripheryScoreRuleService;

@RestController
public class ZjXmJxPeripheryScoreRuleController {

	@Autowired(required = true)
	private ZjXmJxPeripheryScoreRuleService zjXmJxPeripheryScoreRuleService;

	@ApiOperation(value = "查询周边考核打分规则", notes = "查询周边考核打分规则")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreRule", value = "周边考核打分规则entity", dataType = "ZjXmJxPeripheryScoreRule")
	@RequireToken
	@PostMapping("/getZjXmJxPeripheryScoreRuleList")
	public ResponseEntity getZjXmJxPeripheryScoreRuleList(
			@RequestBody(required = false) ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		return zjXmJxPeripheryScoreRuleService.getZjXmJxPeripheryScoreRuleListByCondition(zjXmJxPeripheryScoreRule);
	}

	@ApiOperation(value = "查询详情周边考核打分规则", notes = "查询详情周边考核打分规则")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreRule", value = "周边考核打分规则entity", dataType = "ZjXmJxPeripheryScoreRule")
	@RequireToken
	@PostMapping("/getZjXmJxPeripheryScoreRuleDetails")
	public ResponseEntity getZjXmJxPeripheryScoreRuleDetails(
			@RequestBody(required = false) ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		return zjXmJxPeripheryScoreRuleService.getZjXmJxPeripheryScoreRuleDetails(zjXmJxPeripheryScoreRule);
	}

	@ApiOperation(value = "新增周边考核打分规则", notes = "新增周边考核打分规则")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreRule", value = "周边考核打分规则entity", dataType = "ZjXmJxPeripheryScoreRule")
	@RequireToken
	@PostMapping("/addZjXmJxPeripheryScoreRule")
	public ResponseEntity addZjXmJxPeripheryScoreRule(
			@RequestBody(required = false) ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		return zjXmJxPeripheryScoreRuleService.saveZjXmJxPeripheryScoreRule(zjXmJxPeripheryScoreRule);
	}

	@ApiOperation(value = "更新周边考核打分规则", notes = "更新周边考核打分规则")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreRule", value = "周边考核打分规则entity", dataType = "ZjXmJxPeripheryScoreRule")
	@RequireToken
	@PostMapping("/updateZjXmJxPeripheryScoreRule")
	public ResponseEntity updateZjXmJxPeripheryScoreRule(
			@RequestBody(required = false) ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		return zjXmJxPeripheryScoreRuleService.updateZjXmJxPeripheryScoreRule(zjXmJxPeripheryScoreRule);
	}

	@ApiOperation(value = "删除周边考核打分规则", notes = "删除周边考核打分规则")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreRuleList", value = "周边考核打分规则List", dataType = "List<ZjXmJxPeripheryScoreRule>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxPeripheryScoreRule")
	public ResponseEntity batchDeleteUpdateZjXmJxPeripheryScoreRule(
			@RequestBody(required = false) List<ZjXmJxPeripheryScoreRule> zjXmJxPeripheryScoreRuleList) {
		return zjXmJxPeripheryScoreRuleService.batchDeleteUpdateZjXmJxPeripheryScoreRule(zjXmJxPeripheryScoreRuleList);
	}

	// +++++++++++++++++++++++++++++业务接口start+++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "获取生效中的周边考核打分规则(一条)", notes = "获取生效中的周边考核打分规则(一条)")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreRule", value = "周边考核打分规则entity", dataType = "ZjXmJxPeripheryScoreRule")
	@RequireToken
	@PostMapping("/getValidZjXmJxPeripheryScoreRule")
	public ResponseEntity getValidZjXmJxPeripheryScoreRule(
			@RequestBody(required = false) ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		return zjXmJxPeripheryScoreRuleService.getValidZjXmJxPeripheryScoreRule(zjXmJxPeripheryScoreRule);
	}

	@ApiOperation(value = "设置周边考核打分规则", notes = "新增周边考核打分规则")
	@ApiImplicitParam(name = "zjXmJxPeripheryScoreRule", value = "周边考核打分规则entity", dataType = "ZjXmJxPeripheryScoreRule")
	@RequireToken
	@PostMapping("/setUpZjXmJxPeripheryScoreRule")
	public ResponseEntity setUpZjXmJxPeripheryScoreRule(
			@RequestBody(required = false) ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		return zjXmJxPeripheryScoreRuleService.setUpZjXmJxPeripheryScoreRule(zjXmJxPeripheryScoreRule);
	}
	// +++++++++++++++++++++++++++++业务接口end+++++++++++++++++++++++++++++++++++++++++++++++++++++++++

}
