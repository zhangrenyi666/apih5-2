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
import com.apih5.mybatis.pojo.ZjXmSalaryDepartment;
import com.apih5.service.ZjXmSalaryDepartmentService;

@RestController
public class ZjXmSalaryDepartmentController {

	@Autowired(required = true)
	private ZjXmSalaryDepartmentService zjXmSalaryDepartmentService;

	@ApiOperation(value = "查询所属项目部门", notes = "查询所属项目部门")
	@ApiImplicitParam(name = "zjXmSalaryDepartment", value = "所属项目部门entity", dataType = "ZjXmSalaryDepartment")
	@RequireToken
	@PostMapping("/getZjXmSalaryDepartmentList")
	public ResponseEntity getZjXmSalaryDepartmentList(
			@RequestBody(required = false) ZjXmSalaryDepartment zjXmSalaryDepartment) {
		return zjXmSalaryDepartmentService.getZjXmSalaryDepartmentListByCondition(zjXmSalaryDepartment);
	}

	@ApiOperation(value = "查询详情所属项目部门", notes = "查询详情所属项目部门")
	@ApiImplicitParam(name = "zjXmSalaryDepartment", value = "所属项目部门entity", dataType = "ZjXmSalaryDepartment")
	@RequireToken
	@PostMapping("/getZjXmSalaryDepartmentDetails")
	public ResponseEntity getZjXmSalaryDepartmentDetails(
			@RequestBody(required = false) ZjXmSalaryDepartment zjXmSalaryDepartment) {
		return zjXmSalaryDepartmentService.getZjXmSalaryDepartmentDetails(zjXmSalaryDepartment);
	}

	@ApiOperation(value = "新增所属项目部门", notes = "新增所属项目部门")
	@ApiImplicitParam(name = "zjXmSalaryDepartment", value = "所属项目部门entity", dataType = "ZjXmSalaryDepartment")
	@RequireToken
	@PostMapping("/addZjXmSalaryDepartment")
	public ResponseEntity addZjXmSalaryDepartment(
			@RequestBody(required = false) ZjXmSalaryDepartment zjXmSalaryDepartment) {
		return zjXmSalaryDepartmentService.saveZjXmSalaryDepartment(zjXmSalaryDepartment);
	}

	@ApiOperation(value = "更新所属项目部门", notes = "更新所属项目部门")
	@ApiImplicitParam(name = "zjXmSalaryDepartment", value = "所属项目部门entity", dataType = "ZjXmSalaryDepartment")
	@RequireToken
	@PostMapping("/updateZjXmSalaryDepartment")
	public ResponseEntity updateZjXmSalaryDepartment(
			@RequestBody(required = false) ZjXmSalaryDepartment zjXmSalaryDepartment) {
		return zjXmSalaryDepartmentService.updateZjXmSalaryDepartment(zjXmSalaryDepartment);
	}

	@ApiOperation(value = "删除所属项目部门", notes = "删除所属项目部门")
	@ApiImplicitParam(name = "zjXmSalaryDepartmentList", value = "所属项目部门List", dataType = "List<ZjXmSalaryDepartment>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryDepartment")
	public ResponseEntity batchDeleteUpdateZjXmSalaryDepartment(
			@RequestBody(required = false) List<ZjXmSalaryDepartment> zjXmSalaryDepartmentList) {
		return zjXmSalaryDepartmentService.batchDeleteUpdateZjXmSalaryDepartment(zjXmSalaryDepartmentList);
	}

	// ++++++++++++++++++++++++++++++业务接口start++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "更新所属项目", notes = "更新所属项目")
	@ApiImplicitParam(name = "zjXmSalaryDepartment", value = "所属项目部门entity", dataType = "ZjXmSalaryDepartment")
	@RequireToken
	@PostMapping("/updateZjXmSalaryDepartmentPrj")
	public ResponseEntity updateZjXmSalaryDepartmentPrj(
			@RequestBody(required = false) ZjXmSalaryDepartment zjXmSalaryDepartment) {
		return zjXmSalaryDepartmentService.updateZjXmSalaryDepartmentPrj(zjXmSalaryDepartment);
	}

	@ApiOperation(value = "更新所属部门", notes = "更新所属部门")
	@ApiImplicitParam(name = "zjXmSalaryDepartment", value = "所属项目部门entity", dataType = "ZjXmSalaryDepartment")
	@RequireToken
	@PostMapping("/updateZjXmSalaryDepartmentDept")
	public ResponseEntity updateZjXmSalaryDepartmentDept(
			@RequestBody(required = false) ZjXmSalaryDepartment zjXmSalaryDepartment) {
		return zjXmSalaryDepartmentService.updateZjXmSalaryDepartmentDept(zjXmSalaryDepartment);
	}
	// ++++++++++++++++++++++++++++++业务接口end++++++++++++++++++++++++++++++++++++++++++++++++
}
