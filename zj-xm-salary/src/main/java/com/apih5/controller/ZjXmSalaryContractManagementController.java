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
import com.apih5.mybatis.pojo.ZjXmSalaryContractManagement;
import com.apih5.service.ZjXmSalaryContractManagementService;

@RestController
public class ZjXmSalaryContractManagementController {

	@Autowired(required = true)
	private ZjXmSalaryContractManagementService zjXmSalaryContractManagementService;

	@ApiOperation(value = "查询合同管理", notes = "查询合同管理")
	@ApiImplicitParam(name = "zjXmSalaryContractManagement", value = "合同管理entity", dataType = "ZjXmSalaryContractManagement")
	@RequireToken
	@PostMapping("/getZjXmSalaryContractManagementList")
	public ResponseEntity getZjXmSalaryContractManagementList(
			@RequestBody(required = false) ZjXmSalaryContractManagement zjXmSalaryContractManagement) {
		return zjXmSalaryContractManagementService
				.getZjXmSalaryContractManagementListByCondition(zjXmSalaryContractManagement);
	}

	@ApiOperation(value = "查询详情合同管理", notes = "查询详情合同管理")
	@ApiImplicitParam(name = "zjXmSalaryContractManagement", value = "合同管理entity", dataType = "ZjXmSalaryContractManagement")
	@RequireToken
	@PostMapping("/getZjXmSalaryContractManagementDetails")
	public ResponseEntity getZjXmSalaryContractManagementDetails(
			@RequestBody(required = false) ZjXmSalaryContractManagement zjXmSalaryContractManagement) {
		return zjXmSalaryContractManagementService.getZjXmSalaryContractManagementDetails(zjXmSalaryContractManagement);
	}

	@ApiOperation(value = "新增合同管理", notes = "新增合同管理")
	@ApiImplicitParam(name = "zjXmSalaryContractManagement", value = "合同管理entity", dataType = "ZjXmSalaryContractManagement")
	@RequireToken
	@PostMapping("/addZjXmSalaryContractManagement")
	public ResponseEntity addZjXmSalaryContractManagement(
			@RequestBody(required = false) ZjXmSalaryContractManagement zjXmSalaryContractManagement) {
		return zjXmSalaryContractManagementService.saveZjXmSalaryContractManagement(zjXmSalaryContractManagement);
	}

	@ApiOperation(value = "更新合同管理", notes = "更新合同管理")
	@ApiImplicitParam(name = "zjXmSalaryContractManagement", value = "合同管理entity", dataType = "ZjXmSalaryContractManagement")
	@RequireToken
	@PostMapping("/updateZjXmSalaryContractManagement")
	public ResponseEntity updateZjXmSalaryContractManagement(
			@RequestBody(required = false) ZjXmSalaryContractManagement zjXmSalaryContractManagement) {
		return zjXmSalaryContractManagementService.updateZjXmSalaryContractManagement(zjXmSalaryContractManagement);
	}

	@ApiOperation(value = "删除合同管理", notes = "删除合同管理")
	@ApiImplicitParam(name = "zjXmSalaryContractManagementList", value = "合同管理List", dataType = "List<ZjXmSalaryContractManagement>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryContractManagement")
	public ResponseEntity batchDeleteUpdateZjXmSalaryContractManagement(
			@RequestBody(required = false) List<ZjXmSalaryContractManagement> zjXmSalaryContractManagementList) {
		return zjXmSalaryContractManagementService
				.batchDeleteUpdateZjXmSalaryContractManagement(zjXmSalaryContractManagementList);
	}

}
