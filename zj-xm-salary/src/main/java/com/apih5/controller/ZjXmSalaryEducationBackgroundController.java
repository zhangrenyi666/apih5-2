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
import com.apih5.mybatis.pojo.ZjXmSalaryEducationBackground;
import com.apih5.service.ZjXmSalaryEducationBackgroundService;

@RestController
public class ZjXmSalaryEducationBackgroundController {

	@Autowired(required = true)
	private ZjXmSalaryEducationBackgroundService zjXmSalaryEducationBackgroundService;

	@ApiOperation(value = "查询学历情况", notes = "查询学历情况")
	@ApiImplicitParam(name = "zjXmSalaryEducationBackground", value = "学历情况entity", dataType = "ZjXmSalaryEducationBackground")
	@RequireToken
	@PostMapping("/getZjXmSalaryEducationBackgroundList")
	public ResponseEntity getZjXmSalaryEducationBackgroundList(
			@RequestBody(required = false) ZjXmSalaryEducationBackground zjXmSalaryEducationBackground) {
		return zjXmSalaryEducationBackgroundService
				.getZjXmSalaryEducationBackgroundListByCondition(zjXmSalaryEducationBackground);
	}

	@ApiOperation(value = "查询详情学历情况", notes = "查询详情学历情况")
	@ApiImplicitParam(name = "zjXmSalaryEducationBackground", value = "学历情况entity", dataType = "ZjXmSalaryEducationBackground")
	@RequireToken
	@PostMapping("/getZjXmSalaryEducationBackgroundDetails")
	public ResponseEntity getZjXmSalaryEducationBackgroundDetails(
			@RequestBody(required = false) ZjXmSalaryEducationBackground zjXmSalaryEducationBackground) {
		return zjXmSalaryEducationBackgroundService
				.getZjXmSalaryEducationBackgroundDetails(zjXmSalaryEducationBackground);
	}

	@ApiOperation(value = "新增学历情况", notes = "新增学历情况")
	@ApiImplicitParam(name = "zjXmSalaryEducationBackground", value = "学历情况entity", dataType = "ZjXmSalaryEducationBackground")
	@RequireToken
	@PostMapping("/addZjXmSalaryEducationBackground")
	public ResponseEntity addZjXmSalaryEducationBackground(
			@RequestBody(required = false) ZjXmSalaryEducationBackground zjXmSalaryEducationBackground) {
		return zjXmSalaryEducationBackgroundService.saveZjXmSalaryEducationBackground(zjXmSalaryEducationBackground);
	}

	@ApiOperation(value = "更新学历情况", notes = "更新学历情况")
	@ApiImplicitParam(name = "zjXmSalaryEducationBackground", value = "学历情况entity", dataType = "ZjXmSalaryEducationBackground")
	@RequireToken
	@PostMapping("/updateZjXmSalaryEducationBackground")
	public ResponseEntity updateZjXmSalaryEducationBackground(
			@RequestBody(required = false) ZjXmSalaryEducationBackground zjXmSalaryEducationBackground) {
		return zjXmSalaryEducationBackgroundService.updateZjXmSalaryEducationBackground(zjXmSalaryEducationBackground);
	}

	@ApiOperation(value = "删除学历情况", notes = "删除学历情况")
	@ApiImplicitParam(name = "zjXmSalaryEducationBackgroundList", value = "学历情况List", dataType = "List<ZjXmSalaryEducationBackground>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryEducationBackground")
	public ResponseEntity batchDeleteUpdateZjXmSalaryEducationBackground(
			@RequestBody(required = false) List<ZjXmSalaryEducationBackground> zjXmSalaryEducationBackgroundList) {
		return zjXmSalaryEducationBackgroundService
				.batchDeleteUpdateZjXmSalaryEducationBackground(zjXmSalaryEducationBackgroundList);
	}

}
