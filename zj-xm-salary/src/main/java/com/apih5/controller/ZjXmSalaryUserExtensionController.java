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
import com.apih5.mybatis.pojo.ZjXmSalaryUserExtension;
import com.apih5.service.ZjXmSalaryUserExtensionService;

@RestController
public class ZjXmSalaryUserExtensionController {

	@Autowired(required = true)
	private ZjXmSalaryUserExtensionService zjXmSalaryUserExtensionService;

	@ApiOperation(value = "查询User扩展", notes = "查询User扩展")
	@ApiImplicitParam(name = "zjXmSalaryUserExtension", value = "User扩展entity", dataType = "ZjXmSalaryUserExtension")
	@RequireToken
	@PostMapping("/getZjXmSalaryUserExtensionList")
	public ResponseEntity getZjXmSalaryUserExtensionList(
			@RequestBody(required = false) ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		return zjXmSalaryUserExtensionService.getZjXmSalaryUserExtensionListByCondition(zjXmSalaryUserExtension);
	}

	@ApiOperation(value = "查询详情User扩展", notes = "查询详情User扩展")
	@ApiImplicitParam(name = "zjXmSalaryUserExtension", value = "User扩展entity", dataType = "ZjXmSalaryUserExtension")
	@RequireToken
	@PostMapping("/getZjXmSalaryUserExtensionDetails")
	public ResponseEntity getZjXmSalaryUserExtensionDetails(
			@RequestBody(required = false) ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		return zjXmSalaryUserExtensionService.getZjXmSalaryUserExtensionDetails(zjXmSalaryUserExtension);
	}

	@ApiOperation(value = "新增User扩展", notes = "新增User扩展")
	@ApiImplicitParam(name = "zjXmSalaryUserExtension", value = "User扩展entity", dataType = "ZjXmSalaryUserExtension")
	@RequireToken
	@PostMapping("/addZjXmSalaryUserExtension")
	public ResponseEntity addZjXmSalaryUserExtension(
			@RequestBody(required = false) ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		return zjXmSalaryUserExtensionService.saveZjXmSalaryUserExtension(zjXmSalaryUserExtension);
	}

	@ApiOperation(value = "更新User扩展", notes = "更新User扩展")
	@ApiImplicitParam(name = "zjXmSalaryUserExtension", value = "User扩展entity", dataType = "ZjXmSalaryUserExtension")
	@RequireToken
	@PostMapping("/updateZjXmSalaryUserExtension")
	public ResponseEntity updateZjXmSalaryUserExtension(
			@RequestBody(required = false) ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		return zjXmSalaryUserExtensionService.updateZjXmSalaryUserExtension(zjXmSalaryUserExtension);
	}

	@ApiOperation(value = "删除User扩展", notes = "删除User扩展")
	@ApiImplicitParam(name = "zjXmSalaryUserExtensionList", value = "User扩展List", dataType = "List<ZjXmSalaryUserExtension>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryUserExtension")
	public ResponseEntity batchDeleteUpdateZjXmSalaryUserExtension(
			@RequestBody(required = false) List<ZjXmSalaryUserExtension> zjXmSalaryUserExtensionList) {
		return zjXmSalaryUserExtensionService.batchDeleteUpdateZjXmSalaryUserExtension(zjXmSalaryUserExtensionList);
	}

	// ==================================业务start==============================================
	@ApiOperation(value = "查询User扩展列表", notes = "查询User扩展列表")
	@ApiImplicitParam(name = "zjXmSalaryUserExtension", value = "User扩展entity", dataType = "ZjXmSalaryUserExtension")
	@RequireToken
	@PostMapping("/pcGetZjXmSalaryUserExtensionList")
	public ResponseEntity pcGetZjXmSalaryUserExtensionList(
			@RequestBody(required = false) ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		return zjXmSalaryUserExtensionService.pcGetZjXmSalaryUserExtensionList(zjXmSalaryUserExtension);
	}

	@ApiOperation(value = "查询详情User扩展", notes = "查询详情User扩展")
	@ApiImplicitParam(name = "zjXmSalaryUserExtension", value = "User扩展entity", dataType = "ZjXmSalaryUserExtension")
	@RequireToken
	@PostMapping("/pcGetZjXmSalaryUserExtensionDetails")
	public ResponseEntity pcGetZjXmSalaryUserExtensionDetails(
			@RequestBody(required = false) ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		return zjXmSalaryUserExtensionService.pcGetZjXmSalaryUserExtensionDetails(zjXmSalaryUserExtension);
	}

	@ApiOperation(value = "生成账号", notes = "生成账号")
	@ApiImplicitParam(name = "zjXmSalaryUserExtension", value = "User扩展entity", dataType = "ZjXmSalaryUserExtension")
	@RequireToken
	@PostMapping("/generateZjXmSalaryUserExtensionAccount")
	public ResponseEntity generateZjXmSalaryUserExtensionAccount(
			@RequestBody(required = false) ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		return zjXmSalaryUserExtensionService.generateZjXmSalaryUserExtensionAccount(zjXmSalaryUserExtension);
	}

	@ApiOperation(value = "根据部门获取人员扩展列表", notes = "根据部门获取人员扩展列表")
	@ApiImplicitParam(name = "zjXmSalaryUserExtension", value = "User扩展entity", dataType = "ZjXmSalaryUserExtension")
	@RequireToken
	@PostMapping("/getZjXmSalaryUserExtensionListByDept")
	public ResponseEntity getZjXmSalaryUserExtensionListByDept(
			@RequestBody(required = false) ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		return zjXmSalaryUserExtensionService.getZjXmSalaryUserExtensionListByDept(zjXmSalaryUserExtension);
	}
	// ==================================业务end================================================
}
