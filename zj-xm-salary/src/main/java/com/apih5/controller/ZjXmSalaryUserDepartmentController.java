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
import com.apih5.mybatis.pojo.ZjXmSalaryUserDepartment;
import com.apih5.service.ZjXmSalaryUserDepartmentService;

@RestController
public class ZjXmSalaryUserDepartmentController {

	@Autowired(required = true)
	private ZjXmSalaryUserDepartmentService zjXmSalaryUserDepartmentService;

	@ApiOperation(value = "查询部门与人员扩展关系", notes = "查询部门与人员扩展关系")
	@ApiImplicitParam(name = "zjXmSalaryUserDepartment", value = "部门与人员扩展关系entity", dataType = "ZjXmSalaryUserDepartment")
	@RequireToken
	@PostMapping("/getZjXmSalaryUserDepartmentList")
	public ResponseEntity getZjXmSalaryUserDepartmentList(
			@RequestBody(required = false) ZjXmSalaryUserDepartment zjXmSalaryUserDepartment) {
		return zjXmSalaryUserDepartmentService.getZjXmSalaryUserDepartmentListByCondition(zjXmSalaryUserDepartment);
	}

	@ApiOperation(value = "查询详情部门与人员扩展关系", notes = "查询详情部门与人员扩展关系")
	@ApiImplicitParam(name = "zjXmSalaryUserDepartment", value = "部门与人员扩展关系entity", dataType = "ZjXmSalaryUserDepartment")
	@RequireToken
	@PostMapping("/getZjXmSalaryUserDepartmentDetails")
	public ResponseEntity getZjXmSalaryUserDepartmentDetails(
			@RequestBody(required = false) ZjXmSalaryUserDepartment zjXmSalaryUserDepartment) {
		return zjXmSalaryUserDepartmentService.getZjXmSalaryUserDepartmentDetails(zjXmSalaryUserDepartment);
	}

	@ApiOperation(value = "新增部门与人员扩展关系", notes = "新增部门与人员扩展关系")
	@ApiImplicitParam(name = "zjXmSalaryUserDepartment", value = "部门与人员扩展关系entity", dataType = "ZjXmSalaryUserDepartment")
	@RequireToken
	@PostMapping("/addZjXmSalaryUserDepartment")
	public ResponseEntity addZjXmSalaryUserDepartment(
			@RequestBody(required = false) ZjXmSalaryUserDepartment zjXmSalaryUserDepartment) {
		return zjXmSalaryUserDepartmentService.saveZjXmSalaryUserDepartment(zjXmSalaryUserDepartment);
	}

	@ApiOperation(value = "更新部门与人员扩展关系", notes = "更新部门与人员扩展关系")
	@ApiImplicitParam(name = "zjXmSalaryUserDepartment", value = "部门与人员扩展关系entity", dataType = "ZjXmSalaryUserDepartment")
	@RequireToken
	@PostMapping("/updateZjXmSalaryUserDepartment")
	public ResponseEntity updateZjXmSalaryUserDepartment(
			@RequestBody(required = false) ZjXmSalaryUserDepartment zjXmSalaryUserDepartment) {
		return zjXmSalaryUserDepartmentService.updateZjXmSalaryUserDepartment(zjXmSalaryUserDepartment);
	}

	@ApiOperation(value = "删除部门与人员扩展关系", notes = "删除部门与人员扩展关系")
	@ApiImplicitParam(name = "zjXmSalaryUserDepartmentList", value = "部门与人员扩展关系List", dataType = "List<ZjXmSalaryUserDepartment>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryUserDepartment")
	public ResponseEntity batchDeleteUpdateZjXmSalaryUserDepartment(
			@RequestBody(required = false) List<ZjXmSalaryUserDepartment> zjXmSalaryUserDepartmentList) {
		return zjXmSalaryUserDepartmentService.batchDeleteUpdateZjXmSalaryUserDepartment(zjXmSalaryUserDepartmentList);
	}

}
