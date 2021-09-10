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
import com.apih5.mybatis.pojo.ZjXmSalaryCertificateManagement;
import com.apih5.service.ZjXmSalaryCertificateManagementService;

@RestController
public class ZjXmSalaryCertificateManagementController {

	@Autowired(required = true)
	private ZjXmSalaryCertificateManagementService zjXmSalaryCertificateManagementService;

	@ApiOperation(value = "查询证书管理", notes = "查询证书管理")
	@ApiImplicitParam(name = "zjXmSalaryCertificateManagement", value = "证书管理entity", dataType = "ZjXmSalaryCertificateManagement")
	@RequireToken
	@PostMapping("/getZjXmSalaryCertificateManagementList")
	public ResponseEntity getZjXmSalaryCertificateManagementList(
			@RequestBody(required = false) ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement) {
		return zjXmSalaryCertificateManagementService
				.getZjXmSalaryCertificateManagementListByCondition(zjXmSalaryCertificateManagement);
	}

	@ApiOperation(value = "查询详情证书管理", notes = "查询详情证书管理")
	@ApiImplicitParam(name = "zjXmSalaryCertificateManagement", value = "证书管理entity", dataType = "ZjXmSalaryCertificateManagement")
	@RequireToken
	@PostMapping("/getZjXmSalaryCertificateManagementDetails")
	public ResponseEntity getZjXmSalaryCertificateManagementDetails(
			@RequestBody(required = false) ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement) {
		return zjXmSalaryCertificateManagementService
				.getZjXmSalaryCertificateManagementDetails(zjXmSalaryCertificateManagement);
	}

	@ApiOperation(value = "新增证书管理", notes = "新增证书管理")
	@ApiImplicitParam(name = "zjXmSalaryCertificateManagement", value = "证书管理entity", dataType = "ZjXmSalaryCertificateManagement")
	@RequireToken
	@PostMapping("/addZjXmSalaryCertificateManagement")
	public ResponseEntity addZjXmSalaryCertificateManagement(
			@RequestBody(required = false) ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement) {
		return zjXmSalaryCertificateManagementService
				.saveZjXmSalaryCertificateManagement(zjXmSalaryCertificateManagement);
	}

	@ApiOperation(value = "更新证书管理", notes = "更新证书管理")
	@ApiImplicitParam(name = "zjXmSalaryCertificateManagement", value = "证书管理entity", dataType = "ZjXmSalaryCertificateManagement")
	@RequireToken
	@PostMapping("/updateZjXmSalaryCertificateManagement")
	public ResponseEntity updateZjXmSalaryCertificateManagement(
			@RequestBody(required = false) ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement) {
		return zjXmSalaryCertificateManagementService
				.updateZjXmSalaryCertificateManagement(zjXmSalaryCertificateManagement);
	}

	@ApiOperation(value = "删除证书管理", notes = "删除证书管理")
	@ApiImplicitParam(name = "zjXmSalaryCertificateManagementList", value = "证书管理List", dataType = "List<ZjXmSalaryCertificateManagement>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryCertificateManagement")
	public ResponseEntity batchDeleteUpdateZjXmSalaryCertificateManagement(
			@RequestBody(required = false) List<ZjXmSalaryCertificateManagement> zjXmSalaryCertificateManagementList) {
		return zjXmSalaryCertificateManagementService
				.batchDeleteUpdateZjXmSalaryCertificateManagement(zjXmSalaryCertificateManagementList);
	}

}
