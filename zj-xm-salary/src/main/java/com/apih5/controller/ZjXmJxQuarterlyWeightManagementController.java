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
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightManagement;
import com.apih5.service.ZjXmJxQuarterlyWeightManagementService;

@RestController
public class ZjXmJxQuarterlyWeightManagementController {

	@Autowired(required = true)
	private ZjXmJxQuarterlyWeightManagementService zjXmJxQuarterlyWeightManagementService;

	@ApiOperation(value = "查询季度考核权重", notes = "查询季度考核权重")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightManagement", value = "季度考核权重entity", dataType = "ZjXmJxQuarterlyWeightManagement")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyWeightManagementList")
	public ResponseEntity getZjXmJxQuarterlyWeightManagementList(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		return zjXmJxQuarterlyWeightManagementService
				.getZjXmJxQuarterlyWeightManagementListByCondition(zjXmJxQuarterlyWeightManagement);
	}

	@ApiOperation(value = "查询详情季度考核权重", notes = "查询详情季度考核权重")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightManagement", value = "季度考核权重entity", dataType = "ZjXmJxQuarterlyWeightManagement")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyWeightManagementDetail")
	public ResponseEntity getZjXmJxQuarterlyWeightManagementDetail(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		return zjXmJxQuarterlyWeightManagementService
				.getZjXmJxQuarterlyWeightManagementDetail(zjXmJxQuarterlyWeightManagement);
	}

	@ApiOperation(value = "新增季度考核权重", notes = "新增季度考核权重")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightManagement", value = "季度考核权重entity", dataType = "ZjXmJxQuarterlyWeightManagement")
	@RequireToken
	@PostMapping("/addZjXmJxQuarterlyWeightManagement")
	public ResponseEntity addZjXmJxQuarterlyWeightManagement(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		return zjXmJxQuarterlyWeightManagementService
				.saveZjXmJxQuarterlyWeightManagement(zjXmJxQuarterlyWeightManagement);
	}

	@ApiOperation(value = "更新季度考核权重", notes = "更新季度考核权重")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightManagement", value = "季度考核权重entity", dataType = "ZjXmJxQuarterlyWeightManagement")
	@RequireToken
	@PostMapping("/updateZjXmJxQuarterlyWeightManagement")
	public ResponseEntity updateZjXmJxQuarterlyWeightManagement(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		return zjXmJxQuarterlyWeightManagementService
				.updateZjXmJxQuarterlyWeightManagement(zjXmJxQuarterlyWeightManagement);
	}

	@ApiOperation(value = "删除季度考核权重", notes = "删除季度考核权重")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightManagementList", value = "季度考核权重List", dataType = "List<ZjXmJxQuarterlyWeightManagement>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxQuarterlyWeightManagement")
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyWeightManagement(
			@RequestBody(required = false) List<ZjXmJxQuarterlyWeightManagement> zjXmJxQuarterlyWeightManagementList) {
		return zjXmJxQuarterlyWeightManagementService
				.batchDeleteUpdateZjXmJxQuarterlyWeightManagement(zjXmJxQuarterlyWeightManagementList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "更新季度考核权重[按行保存没有空值]", notes = "更新季度考核权重[按行保存没有空值]")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightManagement", value = "季度考核权重entity", dataType = "ZjXmJxQuarterlyWeightManagement")
	@RequireToken
	@PostMapping("/singleUpdateZjXmJxQuarterlyWeightManagement")
	public ResponseEntity singleUpdateZjXmJxQuarterlyWeightManagement(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightManagement zjXmJxQuarterlyWeightManagement) {
		return zjXmJxQuarterlyWeightManagementService
				.singleUpdateZjXmJxQuarterlyWeightManagement(zjXmJxQuarterlyWeightManagement);
	}
}
