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
import com.apih5.mybatis.pojo.ZjXmJxWeightManagement;
import com.apih5.service.ZjXmJxWeightManagementService;

@RestController
public class ZjXmJxWeightManagementController {

	@Autowired(required = true)
	private ZjXmJxWeightManagementService zjXmJxWeightManagementService;

	@ApiOperation(value = "查询绩效考核权重管理", notes = "查询绩效考核权重管理")
	@ApiImplicitParam(name = "zjXmJxWeightManagement", value = "绩效考核权重管理entity", dataType = "ZjXmJxWeightManagement")
	@RequireToken
	@PostMapping("/getZjXmJxWeightManagementList")
	public ResponseEntity getZjXmJxWeightManagementList(
			@RequestBody(required = false) ZjXmJxWeightManagement zjXmJxWeightManagement) {
		return zjXmJxWeightManagementService.getZjXmJxWeightManagementListByCondition(zjXmJxWeightManagement);
	}

	@ApiOperation(value = "查询详情绩效考核权重管理", notes = "查询详情绩效考核权重管理")
	@ApiImplicitParam(name = "zjXmJxWeightManagement", value = "绩效考核权重管理entity", dataType = "ZjXmJxWeightManagement")
	@RequireToken
	@PostMapping("/getZjXmJxWeightManagementDetails")
	public ResponseEntity getZjXmJxWeightManagementDetails(
			@RequestBody(required = false) ZjXmJxWeightManagement zjXmJxWeightManagement) {
		return zjXmJxWeightManagementService.getZjXmJxWeightManagementDetails(zjXmJxWeightManagement);
	}

	@ApiOperation(value = "新增绩效考核权重管理", notes = "新增绩效考核权重管理")
	@ApiImplicitParam(name = "zjXmJxWeightManagement", value = "绩效考核权重管理entity", dataType = "ZjXmJxWeightManagement")
	@RequireToken
	@PostMapping("/addZjXmJxWeightManagement")
	public ResponseEntity addZjXmJxWeightManagement(
			@RequestBody(required = false) ZjXmJxWeightManagement zjXmJxWeightManagement) {
		return zjXmJxWeightManagementService.saveZjXmJxWeightManagement(zjXmJxWeightManagement);
	}

	@ApiOperation(value = "更新绩效考核权重管理", notes = "更新绩效考核权重管理")
	@ApiImplicitParam(name = "zjXmJxWeightManagement", value = "绩效考核权重管理entity", dataType = "ZjXmJxWeightManagement")
	@RequireToken
	@PostMapping("/updateZjXmJxWeightManagement")
	public ResponseEntity updateZjXmJxWeightManagement(
			@RequestBody(required = false) ZjXmJxWeightManagement zjXmJxWeightManagement) {
		return zjXmJxWeightManagementService.updateZjXmJxWeightManagement(zjXmJxWeightManagement);
	}

	@ApiOperation(value = "删除绩效考核权重管理", notes = "删除绩效考核权重管理")
	@ApiImplicitParam(name = "zjXmJxWeightManagementList", value = "绩效考核权重管理List", dataType = "List<ZjXmJxWeightManagement>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxWeightManagement")
	public ResponseEntity batchDeleteUpdateZjXmJxWeightManagement(
			@RequestBody(required = false) List<ZjXmJxWeightManagement> zjXmJxWeightManagementList) {
		return zjXmJxWeightManagementService.batchDeleteUpdateZjXmJxWeightManagement(zjXmJxWeightManagementList);
	}

}
