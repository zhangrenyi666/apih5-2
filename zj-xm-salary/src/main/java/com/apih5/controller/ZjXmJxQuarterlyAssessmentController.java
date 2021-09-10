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
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessment;
import com.apih5.service.ZjXmJxQuarterlyAssessmentService;

@RestController
public class ZjXmJxQuarterlyAssessmentController {

	@Autowired(required = true)
	private ZjXmJxQuarterlyAssessmentService zjXmJxQuarterlyAssessmentService;

	@ApiOperation(value = "查询项目季度考核", notes = "查询项目季度考核")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessment", value = "项目季度考核entity", dataType = "ZjXmJxQuarterlyAssessment")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyAssessmentList")
	public ResponseEntity getZjXmJxQuarterlyAssessmentList(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		return zjXmJxQuarterlyAssessmentService.getZjXmJxQuarterlyAssessmentListByCondition(zjXmJxQuarterlyAssessment);
	}

	@ApiOperation(value = "查询详情项目季度考核", notes = "查询详情项目季度考核")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessment", value = "项目季度考核entity", dataType = "ZjXmJxQuarterlyAssessment")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyAssessmentDetail")
	public ResponseEntity getZjXmJxQuarterlyAssessmentDetail(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		return zjXmJxQuarterlyAssessmentService.getZjXmJxQuarterlyAssessmentDetail(zjXmJxQuarterlyAssessment);
	}

	@ApiOperation(value = "新增项目季度考核", notes = "新增项目季度考核")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessment", value = "项目季度考核entity", dataType = "ZjXmJxQuarterlyAssessment")
	@RequireToken
	@PostMapping("/addZjXmJxQuarterlyAssessment")
	public ResponseEntity addZjXmJxQuarterlyAssessment(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		return zjXmJxQuarterlyAssessmentService.saveZjXmJxQuarterlyAssessment(zjXmJxQuarterlyAssessment);
	}

	@ApiOperation(value = "更新项目季度考核", notes = "更新项目季度考核")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessment", value = "项目季度考核entity", dataType = "ZjXmJxQuarterlyAssessment")
	@RequireToken
	@PostMapping("/updateZjXmJxQuarterlyAssessment")
	public ResponseEntity updateZjXmJxQuarterlyAssessment(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		return zjXmJxQuarterlyAssessmentService.updateZjXmJxQuarterlyAssessment(zjXmJxQuarterlyAssessment);
	}

	@ApiOperation(value = "删除项目季度考核", notes = "删除项目季度考核")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentList", value = "项目季度考核List", dataType = "List<ZjXmJxQuarterlyAssessment>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxQuarterlyAssessment")
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessment(
			@RequestBody(required = false) List<ZjXmJxQuarterlyAssessment> zjXmJxQuarterlyAssessmentList) {
		return zjXmJxQuarterlyAssessmentService
				.batchDeleteUpdateZjXmJxQuarterlyAssessment(zjXmJxQuarterlyAssessmentList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "新增项目季度考核[创建指标库/权重/项目指标库等内容]", notes = "新增项目季度考核")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessment", value = "项目季度考核entity", dataType = "ZjXmJxQuarterlyAssessment")
	@RequireToken
	@PostMapping("/cascadeAddZjXmJxQuarterlyAssessment")
	public ResponseEntity cascadeAddZjXmJxQuarterlyAssessment(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		return zjXmJxQuarterlyAssessmentService.cascadeAddZjXmJxQuarterlyAssessment(zjXmJxQuarterlyAssessment);
	}

}
