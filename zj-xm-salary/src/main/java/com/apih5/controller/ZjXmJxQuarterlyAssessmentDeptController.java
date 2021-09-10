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
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessmentDept;
import com.apih5.service.ZjXmJxQuarterlyAssessmentDeptService;

@RestController
public class ZjXmJxQuarterlyAssessmentDeptController {

	@Autowired(required = true)
	private ZjXmJxQuarterlyAssessmentDeptService zjXmJxQuarterlyAssessmentDeptService;

	@ApiOperation(value = "查询季度考核部门", notes = "查询季度考核部门")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentDept", value = "季度考核部门entity", dataType = "ZjXmJxQuarterlyAssessmentDept")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyAssessmentDeptList")
	public ResponseEntity getZjXmJxQuarterlyAssessmentDeptList(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		return zjXmJxQuarterlyAssessmentDeptService
				.getZjXmJxQuarterlyAssessmentDeptListByCondition(zjXmJxQuarterlyAssessmentDept);
	}

	@ApiOperation(value = "查询详情季度考核部门", notes = "查询详情季度考核部门")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentDept", value = "季度考核部门entity", dataType = "ZjXmJxQuarterlyAssessmentDept")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyAssessmentDeptDetail")
	public ResponseEntity getZjXmJxQuarterlyAssessmentDeptDetail(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		return zjXmJxQuarterlyAssessmentDeptService
				.getZjXmJxQuarterlyAssessmentDeptDetail(zjXmJxQuarterlyAssessmentDept);
	}

	@ApiOperation(value = "新增季度考核部门", notes = "新增季度考核部门")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentDept", value = "季度考核部门entity", dataType = "ZjXmJxQuarterlyAssessmentDept")
	@RequireToken
	@PostMapping("/addZjXmJxQuarterlyAssessmentDept")
	public ResponseEntity addZjXmJxQuarterlyAssessmentDept(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		return zjXmJxQuarterlyAssessmentDeptService.saveZjXmJxQuarterlyAssessmentDept(zjXmJxQuarterlyAssessmentDept);
	}

	@ApiOperation(value = "更新季度考核部门", notes = "更新季度考核部门")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentDept", value = "季度考核部门entity", dataType = "ZjXmJxQuarterlyAssessmentDept")
	@RequireToken
	@PostMapping("/updateZjXmJxQuarterlyAssessmentDept")
	public ResponseEntity updateZjXmJxQuarterlyAssessmentDept(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		return zjXmJxQuarterlyAssessmentDeptService.updateZjXmJxQuarterlyAssessmentDept(zjXmJxQuarterlyAssessmentDept);
	}

	@ApiOperation(value = "删除季度考核部门", notes = "删除季度考核部门")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentDeptList", value = "季度考核部门List", dataType = "List<ZjXmJxQuarterlyAssessmentDept>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxQuarterlyAssessmentDept")
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessmentDept(
			@RequestBody(required = false) List<ZjXmJxQuarterlyAssessmentDept> zjXmJxQuarterlyAssessmentDeptList) {
		return zjXmJxQuarterlyAssessmentDeptService
				.batchDeleteUpdateZjXmJxQuarterlyAssessmentDept(zjXmJxQuarterlyAssessmentDeptList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "新增季度考核部门[生成权重]", notes = "新增季度考核部门[生成权重]")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentDept", value = "季度考核部门entity", dataType = "ZjXmJxQuarterlyAssessmentDept")
	@RequireToken
	@PostMapping("/addZjXmJxQuarterlyAssessmentDeptToWeight")
	public ResponseEntity addZjXmJxQuarterlyAssessmentDeptToWeight(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		return zjXmJxQuarterlyAssessmentDeptService
				.addZjXmJxQuarterlyAssessmentDeptToWeight(zjXmJxQuarterlyAssessmentDept);
	}

	@ApiOperation(value = "更新季度考核部门[更新权重]", notes = "更新季度考核部门[更新权重]")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentDept", value = "季度考核部门entity", dataType = "ZjXmJxQuarterlyAssessmentDept")
	@RequireToken
	@PostMapping("/updateZjXmJxQuarterlyAssessmentDeptToWeight")
	public ResponseEntity updateZjXmJxQuarterlyAssessmentDeptToWeight(
			@RequestBody(required = false) ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		return zjXmJxQuarterlyAssessmentDeptService
				.updateZjXmJxQuarterlyAssessmentDeptToWeight(zjXmJxQuarterlyAssessmentDept);
	}

	@ApiOperation(value = "删除季度考核部门[删除权重]", notes = "删除季度考核部门[删除权重]")
	@ApiImplicitParam(name = "zjXmJxQuarterlyAssessmentDeptList", value = "季度考核部门List", dataType = "List<ZjXmJxQuarterlyAssessmentDept>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxQuarterlyAssessmentDeptToWeight")
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessmentDeptToWeight(
			@RequestBody(required = false) List<ZjXmJxQuarterlyAssessmentDept> zjXmJxQuarterlyAssessmentDeptList) {
		return zjXmJxQuarterlyAssessmentDeptService
				.batchDeleteUpdateZjXmJxQuarterlyAssessmentDeptToWeight(zjXmJxQuarterlyAssessmentDeptList);
	}
}
