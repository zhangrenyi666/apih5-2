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
import com.apih5.mybatis.pojo.ZxCtValuationSZRules;
import com.apih5.service.ZxCtValuationSZRulesService;

@RestController
public class ZxCtValuationSZRulesController {

	@Autowired(required = true)
	private ZxCtValuationSZRulesService zxCtValuationSZRulesService;

	@ApiOperation(value = "查询市政计价规则库", notes = "查询市政计价规则库")
	@ApiImplicitParam(name = "zxCtValuationSZRules", value = "市政计价规则库entity", dataType = "ZxCtValuationSZRules")
	@RequireToken
	@PostMapping("/getZxCtValuationSZRulesList")
	public ResponseEntity getZxCtValuationSZRulesList(
			@RequestBody(required = false) ZxCtValuationSZRules zxCtValuationSZRules) {
		return zxCtValuationSZRulesService.getZxCtValuationSZRulesListByCondition(zxCtValuationSZRules);
	}

	@ApiOperation(value = "查询详情市政计价规则库", notes = "查询详情市政计价规则库")
	@ApiImplicitParam(name = "zxCtValuationSZRules", value = "市政计价规则库entity", dataType = "ZxCtValuationSZRules")
	@RequireToken
	@PostMapping("/getZxCtValuationSZRulesDetails")
	public ResponseEntity getZxCtValuationSZRulesDetails(
			@RequestBody(required = false) ZxCtValuationSZRules zxCtValuationSZRules) {
		return zxCtValuationSZRulesService.getZxCtValuationSZRulesDetails(zxCtValuationSZRules);
	}

	@ApiOperation(value = "新增市政计价规则库", notes = "新增市政计价规则库")
	@ApiImplicitParam(name = "zxCtValuationSZRules", value = "市政计价规则库entity", dataType = "ZxCtValuationSZRules")
	@RequireToken
	@PostMapping("/addZxCtValuationSZRules")
	public ResponseEntity addZxCtValuationSZRules(
			@RequestBody(required = false) ZxCtValuationSZRules zxCtValuationSZRules) {
		return zxCtValuationSZRulesService.saveZxCtValuationSZRules(zxCtValuationSZRules);
	}

	@ApiOperation(value = "更新市政计价规则库", notes = "更新市政计价规则库")
	@ApiImplicitParam(name = "zxCtValuationSZRules", value = "市政计价规则库entity", dataType = "ZxCtValuationSZRules")
	@RequireToken
	@PostMapping("/updateZxCtValuationSZRules")
	public ResponseEntity updateZxCtValuationSZRules(
			@RequestBody(required = false) ZxCtValuationSZRules zxCtValuationSZRules) {
		return zxCtValuationSZRulesService.updateZxCtValuationSZRules(zxCtValuationSZRules);
	}

	@ApiOperation(value = "删除市政计价规则库", notes = "删除市政计价规则库")
	@ApiImplicitParam(name = "zxCtValuationSZRulesList", value = "市政计价规则库List", dataType = "List<ZxCtValuationSZRules>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxCtValuationSZRules")
	public ResponseEntity batchDeleteUpdateZxCtValuationSZRules(
			@RequestBody(required = false) List<ZxCtValuationSZRules> zxCtValuationSZRulesList) {
		return zxCtValuationSZRulesService.batchDeleteUpdateZxCtValuationSZRules(zxCtValuationSZRulesList);
	}

	// +++++++++++++++++++++++++++++分包合同引用+++++++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "分包合同评审/管理/补充协议中获取计价规则列表", notes = "分包合同评审/管理/补充协议中获取计价规则列表")
	@ApiImplicitParam(name = "zxCtValuationSZRules", value = "市政计价规则库entity", dataType = "ZxCtValuationSZRules")
	@RequireToken
	@PostMapping("/gcsgGetZxCtValuationSZRulesList")
	public ResponseEntity gcsgGetZxCtValuationSZRulesList(
			@RequestBody(required = false) ZxCtValuationSZRules zxCtValuationSZRules) {
		return zxCtValuationSZRulesService.gcsgGetZxCtValuationSZRulesList(zxCtValuationSZRules);
	}
	// +++++++++++++++++++++++++++++分包合同引用+++++++++++++++++++++++++++++++++++++++++++++++++++

}
