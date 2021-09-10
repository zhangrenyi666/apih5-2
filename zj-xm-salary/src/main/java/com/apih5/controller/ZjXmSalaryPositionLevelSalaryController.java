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
import com.apih5.mybatis.pojo.ZjXmSalaryPositionLevelSalary;
import com.apih5.service.ZjXmSalaryPositionLevelSalaryService;

@RestController
public class ZjXmSalaryPositionLevelSalaryController {

	@Autowired(required = true)
	private ZjXmSalaryPositionLevelSalaryService zjXmSalaryPositionLevelSalaryService;

	@ApiOperation(value = "查询岗级及岗薪", notes = "查询岗级及岗薪")
	@ApiImplicitParam(name = "zjXmSalaryPositionLevelSalary", value = "岗级及岗薪entity", dataType = "ZjXmSalaryPositionLevelSalary")
	@RequireToken
	@PostMapping("/getZjXmSalaryPositionLevelSalaryList")
	public ResponseEntity getZjXmSalaryPositionLevelSalaryList(
			@RequestBody(required = false) ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		return zjXmSalaryPositionLevelSalaryService
				.getZjXmSalaryPositionLevelSalaryListByCondition(zjXmSalaryPositionLevelSalary);
	}

	@ApiOperation(value = "查询详情岗级及岗薪", notes = "查询详情岗级及岗薪")
	@ApiImplicitParam(name = "zjXmSalaryPositionLevelSalary", value = "岗级及岗薪entity", dataType = "ZjXmSalaryPositionLevelSalary")
	@RequireToken
	@PostMapping("/getZjXmSalaryPositionLevelSalaryDetails")
	public ResponseEntity getZjXmSalaryPositionLevelSalaryDetails(
			@RequestBody(required = false) ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		return zjXmSalaryPositionLevelSalaryService
				.getZjXmSalaryPositionLevelSalaryDetails(zjXmSalaryPositionLevelSalary);
	}

	@ApiOperation(value = "新增岗级及岗薪", notes = "新增岗级及岗薪")
	@ApiImplicitParam(name = "zjXmSalaryPositionLevelSalary", value = "岗级及岗薪entity", dataType = "ZjXmSalaryPositionLevelSalary")
	@RequireToken
	@PostMapping("/addZjXmSalaryPositionLevelSalary")
	public ResponseEntity addZjXmSalaryPositionLevelSalary(
			@RequestBody(required = false) ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		return zjXmSalaryPositionLevelSalaryService.saveZjXmSalaryPositionLevelSalary(zjXmSalaryPositionLevelSalary);
	}

	@ApiOperation(value = "更新岗级及岗薪", notes = "更新岗级及岗薪")
	@ApiImplicitParam(name = "zjXmSalaryPositionLevelSalary", value = "岗级及岗薪entity", dataType = "ZjXmSalaryPositionLevelSalary")
	@RequireToken
	@PostMapping("/updateZjXmSalaryPositionLevelSalary")
	public ResponseEntity updateZjXmSalaryPositionLevelSalary(
			@RequestBody(required = false) ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		return zjXmSalaryPositionLevelSalaryService.updateZjXmSalaryPositionLevelSalary(zjXmSalaryPositionLevelSalary);
	}

	@ApiOperation(value = "删除岗级及岗薪", notes = "删除岗级及岗薪")
	@ApiImplicitParam(name = "zjXmSalaryPositionLevelSalaryList", value = "岗级及岗薪List", dataType = "List<ZjXmSalaryPositionLevelSalary>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryPositionLevelSalary")
	public ResponseEntity batchDeleteUpdateZjXmSalaryPositionLevelSalary(
			@RequestBody(required = false) List<ZjXmSalaryPositionLevelSalary> zjXmSalaryPositionLevelSalaryList) {
		return zjXmSalaryPositionLevelSalaryService
				.batchDeleteUpdateZjXmSalaryPositionLevelSalary(zjXmSalaryPositionLevelSalaryList);
	}

	// +++++++++++++++++++++++++++++业务接口start++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "获取岗级及岗薪二级下拉", notes = "获取岗级及岗薪二级下拉")
	@ApiImplicitParam(name = "zjXmSalaryPositionLevelSalary", value = "岗级及岗薪entity", dataType = "ZjXmSalaryPositionLevelSalary")
	@RequireToken
	@PostMapping("/getZjXmSalaryPositionLevelSalarySelect")
	public ResponseEntity getZjXmSalaryPositionLevelSalarySelect(
			@RequestBody(required = false) ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		return zjXmSalaryPositionLevelSalaryService
				.getZjXmSalaryPositionLevelSalarySelect(zjXmSalaryPositionLevelSalary);
	}
	// +++++++++++++++++++++++++++++业务接口end++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}
