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
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightDetails;
import com.apih5.service.ZjXmJxQuarterlyWeightDetailsService;

@RestController
public class ZjXmJxQuarterlyWeightDetailsController {

	@Autowired(required = true)
	private ZjXmJxQuarterlyWeightDetailsService zjXmJxQuarterlyWeightDetailsService;

	@ApiOperation(value = "查询季度考核权重详情", notes = "查询季度考核权重详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightDetails", value = "季度考核权重详情entity", dataType = "ZjXmJxQuarterlyWeightDetails")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyWeightDetailsList")
	public ResponseEntity getZjXmJxQuarterlyWeightDetailsList(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		return zjXmJxQuarterlyWeightDetailsService
				.getZjXmJxQuarterlyWeightDetailsListByCondition(zjXmJxQuarterlyWeightDetails);
	}

	@ApiOperation(value = "查询详情季度考核权重详情", notes = "查询详情季度考核权重详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightDetails", value = "季度考核权重详情entity", dataType = "ZjXmJxQuarterlyWeightDetails")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyWeightDetailsDetail")
	public ResponseEntity getZjXmJxQuarterlyWeightDetailsDetail(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		return zjXmJxQuarterlyWeightDetailsService.getZjXmJxQuarterlyWeightDetailsDetail(zjXmJxQuarterlyWeightDetails);
	}

	@ApiOperation(value = "新增季度考核权重详情", notes = "新增季度考核权重详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightDetails", value = "季度考核权重详情entity", dataType = "ZjXmJxQuarterlyWeightDetails")
	@RequireToken
	@PostMapping("/addZjXmJxQuarterlyWeightDetails")
	public ResponseEntity addZjXmJxQuarterlyWeightDetails(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		return zjXmJxQuarterlyWeightDetailsService.saveZjXmJxQuarterlyWeightDetails(zjXmJxQuarterlyWeightDetails);
	}

	@ApiOperation(value = "更新季度考核权重详情", notes = "更新季度考核权重详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightDetails", value = "季度考核权重详情entity", dataType = "ZjXmJxQuarterlyWeightDetails")
	@RequireToken
	@PostMapping("/updateZjXmJxQuarterlyWeightDetails")
	public ResponseEntity updateZjXmJxQuarterlyWeightDetails(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		return zjXmJxQuarterlyWeightDetailsService.updateZjXmJxQuarterlyWeightDetails(zjXmJxQuarterlyWeightDetails);
	}

	@ApiOperation(value = "删除季度考核权重详情", notes = "删除季度考核权重详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightDetailsList", value = "季度考核权重详情List", dataType = "List<ZjXmJxQuarterlyWeightDetails>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxQuarterlyWeightDetails")
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyWeightDetails(
			@RequestBody(required = false) List<ZjXmJxQuarterlyWeightDetails> zjXmJxQuarterlyWeightDetailsList) {
		return zjXmJxQuarterlyWeightDetailsService
				.batchDeleteUpdateZjXmJxQuarterlyWeightDetails(zjXmJxQuarterlyWeightDetailsList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "批量确认季度考核权重详情", notes = "批量确认季度考核权重详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightDetailsList", value = "季度考核权重详情List", dataType = "List<ZjXmJxQuarterlyWeightDetails>")
	@RequireToken
	@PostMapping("/batchConfirmZjXmJxQuarterlyWeightDetails")
	public ResponseEntity batchConfirmZjXmJxQuarterlyWeightDetails(
			@RequestBody(required = false) List<ZjXmJxQuarterlyWeightDetails> zjXmJxQuarterlyWeightDetailsList) {
		return zjXmJxQuarterlyWeightDetailsService
				.batchConfirmZjXmJxQuarterlyWeightDetails(zjXmJxQuarterlyWeightDetailsList);
	}

	@ApiOperation(value = "判断是否显示确认按钮", notes = "判断是否显示确认按钮")
	@ApiImplicitParam(name = "zjXmJxQuarterlyWeightDetails", value = "季度考核权重详情entity", dataType = "ZjXmJxQuarterlyWeightDetails")
	@RequireToken
	@PostMapping("/checkZjXmJxQuarterlyWeightDetailsConfirmStatus")
	public ResponseEntity checkZjXmJxQuarterlyWeightDetailsConfirmStatus(
			@RequestBody(required = false) ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		return zjXmJxQuarterlyWeightDetailsService
				.checkZjXmJxQuarterlyWeightDetailsConfirmStatus(zjXmJxQuarterlyWeightDetails);
	}
}
