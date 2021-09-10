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
import com.apih5.mybatis.pojo.ZjXmJxSystemAutoScoreDetailed;
import com.apih5.service.ZjXmJxSystemAutoScoreDetailedService;

@RestController
public class ZjXmJxSystemAutoScoreDetailedController {

	@Autowired(required = true)
	private ZjXmJxSystemAutoScoreDetailedService zjXmJxSystemAutoScoreDetailedService;

	@ApiOperation(value = "查询系统自动扣分明细", notes = "查询系统自动扣分明细")
	@ApiImplicitParam(name = "zjXmJxSystemAutoScoreDetailed", value = "系统自动扣分明细entity", dataType = "ZjXmJxSystemAutoScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxSystemAutoScoreDetailedList")
	public ResponseEntity getZjXmJxSystemAutoScoreDetailedList(
			@RequestBody(required = false) ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed) {
		return zjXmJxSystemAutoScoreDetailedService
				.getZjXmJxSystemAutoScoreDetailedListByCondition(zjXmJxSystemAutoScoreDetailed);
	}

	@ApiOperation(value = "查询详情系统自动扣分明细", notes = "查询详情系统自动扣分明细")
	@ApiImplicitParam(name = "zjXmJxSystemAutoScoreDetailed", value = "系统自动扣分明细entity", dataType = "ZjXmJxSystemAutoScoreDetailed")
	@RequireToken
	@PostMapping("/getZjXmJxSystemAutoScoreDetailedDetails")
	public ResponseEntity getZjXmJxSystemAutoScoreDetailedDetails(
			@RequestBody(required = false) ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed) {
		return zjXmJxSystemAutoScoreDetailedService
				.getZjXmJxSystemAutoScoreDetailedDetails(zjXmJxSystemAutoScoreDetailed);
	}

	@ApiOperation(value = "新增系统自动扣分明细", notes = "新增系统自动扣分明细")
	@ApiImplicitParam(name = "zjXmJxSystemAutoScoreDetailed", value = "系统自动扣分明细entity", dataType = "ZjXmJxSystemAutoScoreDetailed")
	@RequireToken
	@PostMapping("/addZjXmJxSystemAutoScoreDetailed")
	public ResponseEntity addZjXmJxSystemAutoScoreDetailed(
			@RequestBody(required = false) ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed) {
		return zjXmJxSystemAutoScoreDetailedService.saveZjXmJxSystemAutoScoreDetailed(zjXmJxSystemAutoScoreDetailed);
	}

	@ApiOperation(value = "更新系统自动扣分明细", notes = "更新系统自动扣分明细")
	@ApiImplicitParam(name = "zjXmJxSystemAutoScoreDetailed", value = "系统自动扣分明细entity", dataType = "ZjXmJxSystemAutoScoreDetailed")
	@RequireToken
	@PostMapping("/updateZjXmJxSystemAutoScoreDetailed")
	public ResponseEntity updateZjXmJxSystemAutoScoreDetailed(
			@RequestBody(required = false) ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed) {
		return zjXmJxSystemAutoScoreDetailedService.updateZjXmJxSystemAutoScoreDetailed(zjXmJxSystemAutoScoreDetailed);
	}

	@ApiOperation(value = "删除系统自动扣分明细", notes = "删除系统自动扣分明细")
	@ApiImplicitParam(name = "zjXmJxSystemAutoScoreDetailedList", value = "系统自动扣分明细List", dataType = "List<ZjXmJxSystemAutoScoreDetailed>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxSystemAutoScoreDetailed")
	public ResponseEntity batchDeleteUpdateZjXmJxSystemAutoScoreDetailed(
			@RequestBody(required = false) List<ZjXmJxSystemAutoScoreDetailed> zjXmJxSystemAutoScoreDetailedList) {
		return zjXmJxSystemAutoScoreDetailedService
				.batchDeleteUpdateZjXmJxSystemAutoScoreDetailed(zjXmJxSystemAutoScoreDetailedList);
	}

}
