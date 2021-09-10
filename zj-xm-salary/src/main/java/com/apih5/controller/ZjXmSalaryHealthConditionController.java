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
import com.apih5.mybatis.pojo.ZjXmSalaryHealthCondition;
import com.apih5.service.ZjXmSalaryHealthConditionService;

@RestController
public class ZjXmSalaryHealthConditionController {

	@Autowired(required = true)
	private ZjXmSalaryHealthConditionService zjXmSalaryHealthConditionService;

	@ApiOperation(value = "查询健康情况", notes = "查询健康情况")
	@ApiImplicitParam(name = "zjXmSalaryHealthCondition", value = "健康情况entity", dataType = "ZjXmSalaryHealthCondition")
	@RequireToken
	@PostMapping("/getZjXmSalaryHealthConditionList")
	public ResponseEntity getZjXmSalaryHealthConditionList(
			@RequestBody(required = false) ZjXmSalaryHealthCondition zjXmSalaryHealthCondition) {
		return zjXmSalaryHealthConditionService.getZjXmSalaryHealthConditionListByCondition(zjXmSalaryHealthCondition);
	}

	@ApiOperation(value = "查询详情健康情况", notes = "查询详情健康情况")
	@ApiImplicitParam(name = "zjXmSalaryHealthCondition", value = "健康情况entity", dataType = "ZjXmSalaryHealthCondition")
	@RequireToken
	@PostMapping("/getZjXmSalaryHealthConditionDetails")
	public ResponseEntity getZjXmSalaryHealthConditionDetails(
			@RequestBody(required = false) ZjXmSalaryHealthCondition zjXmSalaryHealthCondition) {
		return zjXmSalaryHealthConditionService.getZjXmSalaryHealthConditionDetails(zjXmSalaryHealthCondition);
	}

	@ApiOperation(value = "新增健康情况", notes = "新增健康情况")
	@ApiImplicitParam(name = "zjXmSalaryHealthCondition", value = "健康情况entity", dataType = "ZjXmSalaryHealthCondition")
	@RequireToken
	@PostMapping("/addZjXmSalaryHealthCondition")
	public ResponseEntity addZjXmSalaryHealthCondition(
			@RequestBody(required = false) ZjXmSalaryHealthCondition zjXmSalaryHealthCondition) {
		return zjXmSalaryHealthConditionService.saveZjXmSalaryHealthCondition(zjXmSalaryHealthCondition);
	}

	@ApiOperation(value = "更新健康情况", notes = "更新健康情况")
	@ApiImplicitParam(name = "zjXmSalaryHealthCondition", value = "健康情况entity", dataType = "ZjXmSalaryHealthCondition")
	@RequireToken
	@PostMapping("/updateZjXmSalaryHealthCondition")
	public ResponseEntity updateZjXmSalaryHealthCondition(
			@RequestBody(required = false) ZjXmSalaryHealthCondition zjXmSalaryHealthCondition) {
		return zjXmSalaryHealthConditionService.updateZjXmSalaryHealthCondition(zjXmSalaryHealthCondition);
	}

	@ApiOperation(value = "删除健康情况", notes = "删除健康情况")
	@ApiImplicitParam(name = "zjXmSalaryHealthConditionList", value = "健康情况List", dataType = "List<ZjXmSalaryHealthCondition>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryHealthCondition")
	public ResponseEntity batchDeleteUpdateZjXmSalaryHealthCondition(
			@RequestBody(required = false) List<ZjXmSalaryHealthCondition> zjXmSalaryHealthConditionList) {
		return zjXmSalaryHealthConditionService
				.batchDeleteUpdateZjXmSalaryHealthCondition(zjXmSalaryHealthConditionList);
	}

}
