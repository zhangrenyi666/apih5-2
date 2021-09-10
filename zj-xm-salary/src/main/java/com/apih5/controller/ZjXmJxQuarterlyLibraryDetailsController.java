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
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyLibraryDetails;
import com.apih5.service.ZjXmJxQuarterlyLibraryDetailsService;

@RestController
public class ZjXmJxQuarterlyLibraryDetailsController {

	@Autowired(required = true)
	private ZjXmJxQuarterlyLibraryDetailsService zjXmJxQuarterlyLibraryDetailsService;

	@ApiOperation(value = "查询季度考核指标库详情", notes = "查询季度考核指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyLibraryDetails", value = "季度考核指标库详情entity", dataType = "ZjXmJxQuarterlyLibraryDetails")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyLibraryDetailsList")
	public ResponseEntity getZjXmJxQuarterlyLibraryDetailsList(
			@RequestBody(required = false) ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		return zjXmJxQuarterlyLibraryDetailsService
				.getZjXmJxQuarterlyLibraryDetailsListByCondition(zjXmJxQuarterlyLibraryDetails);
	}

	@ApiOperation(value = "查询详情季度考核指标库详情", notes = "查询详情季度考核指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyLibraryDetails", value = "季度考核指标库详情entity", dataType = "ZjXmJxQuarterlyLibraryDetails")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyLibraryDetailsDetail")
	public ResponseEntity getZjXmJxQuarterlyLibraryDetailsDetail(
			@RequestBody(required = false) ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		return zjXmJxQuarterlyLibraryDetailsService
				.getZjXmJxQuarterlyLibraryDetailsDetail(zjXmJxQuarterlyLibraryDetails);
	}

	@ApiOperation(value = "新增季度考核指标库详情", notes = "新增季度考核指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyLibraryDetails", value = "季度考核指标库详情entity", dataType = "ZjXmJxQuarterlyLibraryDetails")
	@RequireToken
	@PostMapping("/addZjXmJxQuarterlyLibraryDetails")
	public ResponseEntity addZjXmJxQuarterlyLibraryDetails(
			@RequestBody(required = false) ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		return zjXmJxQuarterlyLibraryDetailsService.saveZjXmJxQuarterlyLibraryDetails(zjXmJxQuarterlyLibraryDetails);
	}

	@ApiOperation(value = "更新季度考核指标库详情", notes = "更新季度考核指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyLibraryDetails", value = "季度考核指标库详情entity", dataType = "ZjXmJxQuarterlyLibraryDetails")
	@RequireToken
	@PostMapping("/updateZjXmJxQuarterlyLibraryDetails")
	public ResponseEntity updateZjXmJxQuarterlyLibraryDetails(
			@RequestBody(required = false) ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		return zjXmJxQuarterlyLibraryDetailsService.updateZjXmJxQuarterlyLibraryDetails(zjXmJxQuarterlyLibraryDetails);
	}

	@ApiOperation(value = "删除季度考核指标库详情", notes = "删除季度考核指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyLibraryDetailsList", value = "季度考核指标库详情List", dataType = "List<ZjXmJxQuarterlyLibraryDetails>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxQuarterlyLibraryDetails")
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyLibraryDetails(
			@RequestBody(required = false) List<ZjXmJxQuarterlyLibraryDetails> zjXmJxQuarterlyLibraryDetailsList) {
		return zjXmJxQuarterlyLibraryDetailsService
				.batchDeleteUpdateZjXmJxQuarterlyLibraryDetails(zjXmJxQuarterlyLibraryDetailsList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "批量确认季度考核指标库详情(生成季度考核项目)", notes = "批量确认季度考核指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyLibraryDetailsList", value = "季度考核指标库详情List", dataType = "List<ZjXmJxQuarterlyLibraryDetails>")
	@RequireToken
	@PostMapping("/batchConfirmZjXmJxQuarterlyLibraryDetails")
	public ResponseEntity batchConfirmZjXmJxQuarterlyLibraryDetails(
			@RequestBody(required = false) List<ZjXmJxQuarterlyLibraryDetails> zjXmJxQuarterlyLibraryDetailsList) {
		return zjXmJxQuarterlyLibraryDetailsService
				.batchConfirmZjXmJxQuarterlyLibraryDetails(zjXmJxQuarterlyLibraryDetailsList);
	}

	@ApiOperation(value = "判断是否显示确认按钮", notes = "判断是否显示确认按钮")
	@ApiImplicitParam(name = "zjXmJxQuarterlyLibraryDetails", value = "季度考核指标库详情entity", dataType = "ZjXmJxQuarterlyLibraryDetails")
	@RequireToken
	@PostMapping("/checkZjXmJxQuarterlyLibraryDetailsConfirmStatus")
	public ResponseEntity checkZjXmJxQuarterlyLibraryDetailsConfirmStatus(
			@RequestBody(required = false) ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		return zjXmJxQuarterlyLibraryDetailsService
				.checkZjXmJxQuarterlyLibraryDetailsConfirmStatus(zjXmJxQuarterlyLibraryDetails);
	}
}
