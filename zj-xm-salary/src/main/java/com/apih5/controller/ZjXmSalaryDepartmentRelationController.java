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
import com.apih5.mybatis.pojo.ZjXmSalaryDepartmentRelation;
import com.apih5.service.ZjXmSalaryDepartmentRelationService;

@RestController
public class ZjXmSalaryDepartmentRelationController {

	@Autowired(required = true)
	private ZjXmSalaryDepartmentRelationService zjXmSalaryDepartmentRelationService;

	@ApiOperation(value = "查询业务项目与系统部门关系", notes = "查询业务项目与系统部门关系")
	@ApiImplicitParam(name = "zjXmSalaryDepartmentRelation", value = "业务项目与系统部门关系entity", dataType = "ZjXmSalaryDepartmentRelation")
	@RequireToken
	@PostMapping("/getZjXmSalaryDepartmentRelationList")
	public ResponseEntity getZjXmSalaryDepartmentRelationList(
			@RequestBody(required = false) ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation) {
		return zjXmSalaryDepartmentRelationService
				.getZjXmSalaryDepartmentRelationListByCondition(zjXmSalaryDepartmentRelation);
	}

	@ApiOperation(value = "查询详情业务项目与系统部门关系", notes = "查询详情业务项目与系统部门关系")
	@ApiImplicitParam(name = "zjXmSalaryDepartmentRelation", value = "业务项目与系统部门关系entity", dataType = "ZjXmSalaryDepartmentRelation")
	@RequireToken
	@PostMapping("/getZjXmSalaryDepartmentRelationDetails")
	public ResponseEntity getZjXmSalaryDepartmentRelationDetails(
			@RequestBody(required = false) ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation) {
		return zjXmSalaryDepartmentRelationService.getZjXmSalaryDepartmentRelationDetails(zjXmSalaryDepartmentRelation);
	}

	@ApiOperation(value = "新增业务项目与系统部门关系", notes = "新增业务项目与系统部门关系")
	@ApiImplicitParam(name = "zjXmSalaryDepartmentRelation", value = "业务项目与系统部门关系entity", dataType = "ZjXmSalaryDepartmentRelation")
	@RequireToken
	@PostMapping("/addZjXmSalaryDepartmentRelation")
	public ResponseEntity addZjXmSalaryDepartmentRelation(
			@RequestBody(required = false) ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation) {
		return zjXmSalaryDepartmentRelationService.saveZjXmSalaryDepartmentRelation(zjXmSalaryDepartmentRelation);
	}

	@ApiOperation(value = "更新业务项目与系统部门关系", notes = "更新业务项目与系统部门关系")
	@ApiImplicitParam(name = "zjXmSalaryDepartmentRelation", value = "业务项目与系统部门关系entity", dataType = "ZjXmSalaryDepartmentRelation")
	@RequireToken
	@PostMapping("/updateZjXmSalaryDepartmentRelation")
	public ResponseEntity updateZjXmSalaryDepartmentRelation(
			@RequestBody(required = false) ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation) {
		return zjXmSalaryDepartmentRelationService.updateZjXmSalaryDepartmentRelation(zjXmSalaryDepartmentRelation);
	}

	@ApiOperation(value = "删除业务项目与系统部门关系", notes = "删除业务项目与系统部门关系")
	@ApiImplicitParam(name = "zjXmSalaryDepartmentRelationList", value = "业务项目与系统部门关系List", dataType = "List<ZjXmSalaryDepartmentRelation>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryDepartmentRelation")
	public ResponseEntity batchDeleteUpdateZjXmSalaryDepartmentRelation(
			@RequestBody(required = false) List<ZjXmSalaryDepartmentRelation> zjXmSalaryDepartmentRelationList) {
		return zjXmSalaryDepartmentRelationService
				.batchDeleteUpdateZjXmSalaryDepartmentRelation(zjXmSalaryDepartmentRelationList);
	}

}
