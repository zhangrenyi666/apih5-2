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
import com.apih5.mybatis.pojo.ZjXmJxUserScoringLeader;
import com.apih5.service.ZjXmJxUserScoringLeaderService;

@RestController
public class ZjXmJxUserScoringLeaderController {

	@Autowired(required = true)
	private ZjXmJxUserScoringLeaderService zjXmJxUserScoringLeaderService;

	@ApiOperation(value = "查询项目人员评分领导", notes = "查询项目人员评分领导")
	@ApiImplicitParam(name = "zjXmJxUserScoringLeader", value = "项目人员评分领导entity", dataType = "ZjXmJxUserScoringLeader")
	@RequireToken
	@PostMapping("/getZjXmJxUserScoringLeaderList")
	public ResponseEntity getZjXmJxUserScoringLeaderList(
			@RequestBody(required = false) ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		return zjXmJxUserScoringLeaderService.getZjXmJxUserScoringLeaderListByCondition(zjXmJxUserScoringLeader);
	}

	@ApiOperation(value = "查询详情项目人员评分领导", notes = "查询详情项目人员评分领导")
	@ApiImplicitParam(name = "zjXmJxUserScoringLeader", value = "项目人员评分领导entity", dataType = "ZjXmJxUserScoringLeader")
	@RequireToken
	@PostMapping("/getZjXmJxUserScoringLeaderDetails")
	public ResponseEntity getZjXmJxUserScoringLeaderDetails(
			@RequestBody(required = false) ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		return zjXmJxUserScoringLeaderService.getZjXmJxUserScoringLeaderDetails(zjXmJxUserScoringLeader);
	}

	@ApiOperation(value = "新增项目人员评分领导", notes = "新增项目人员评分领导")
	@ApiImplicitParam(name = "zjXmJxUserScoringLeader", value = "项目人员评分领导entity", dataType = "ZjXmJxUserScoringLeader")
	@RequireToken
	@PostMapping("/addZjXmJxUserScoringLeader")
	public ResponseEntity addZjXmJxUserScoringLeader(
			@RequestBody(required = false) ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		return zjXmJxUserScoringLeaderService.saveZjXmJxUserScoringLeader(zjXmJxUserScoringLeader);
	}

	@ApiOperation(value = "更新项目人员评分领导", notes = "更新项目人员评分领导")
	@ApiImplicitParam(name = "zjXmJxUserScoringLeader", value = "项目人员评分领导entity", dataType = "ZjXmJxUserScoringLeader")
	@RequireToken
	@PostMapping("/updateZjXmJxUserScoringLeader")
	public ResponseEntity updateZjXmJxUserScoringLeader(
			@RequestBody(required = false) ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		return zjXmJxUserScoringLeaderService.updateZjXmJxUserScoringLeader(zjXmJxUserScoringLeader);
	}

	@ApiOperation(value = "删除项目人员评分领导", notes = "删除项目人员评分领导")
	@ApiImplicitParam(name = "zjXmJxUserScoringLeaderList", value = "项目人员评分领导List", dataType = "List<ZjXmJxUserScoringLeader>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxUserScoringLeader")
	public ResponseEntity batchDeleteUpdateZjXmJxUserScoringLeader(
			@RequestBody(required = false) List<ZjXmJxUserScoringLeader> zjXmJxUserScoringLeaderList) {
		return zjXmJxUserScoringLeaderService.batchDeleteUpdateZjXmJxUserScoringLeader(zjXmJxUserScoringLeaderList);
	}

	// =============================业务接口start==================================================

	@ApiOperation(value = "根据项目/部门设置人员评分领导(先删后增)", notes = "根据项目/部门设置人员评分领导(先删后增)")
	@ApiImplicitParam(name = "zjXmJxUserScoringLeader", value = "项目人员评分领导entity", dataType = "ZjXmJxUserScoringLeader")
	@RequireToken
	@PostMapping("/setUpZjXmJxUserScoringLeader")
	public ResponseEntity setUpZjXmJxUserScoringLeader(
			@RequestBody(required = false) ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		return zjXmJxUserScoringLeaderService.setUpZjXmJxUserScoringLeader(zjXmJxUserScoringLeader);
	}

	@ApiOperation(value = "手动处理旧数据迁移", notes = "手动处理旧数据迁移")
	@ApiImplicitParam(name = "zjXmJxUserScoringLeader", value = "项目人员评分领导entity", dataType = "ZjXmJxUserScoringLeader")
	@PostMapping("/manualAddZjXmJxUserScoringLeader")
	public ResponseEntity manualAddZjXmJxUserScoringLeader(
			@RequestBody(required = false) ZjXmJxUserScoringLeader zjXmJxUserScoringLeader) {
		return zjXmJxUserScoringLeaderService.manualAddZjXmJxUserScoringLeader(zjXmJxUserScoringLeader);
	}

	// =============================业务接口end====================================================
}