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
import com.apih5.mybatis.pojo.ZjXmSalaryTrainingSituation;
import com.apih5.service.ZjXmSalaryTrainingSituationService;

@RestController
public class ZjXmSalaryTrainingSituationController {

	@Autowired(required = true)
	private ZjXmSalaryTrainingSituationService zjXmSalaryTrainingSituationService;

	@ApiOperation(value = "查询培训情况", notes = "查询培训情况")
	@ApiImplicitParam(name = "zjXmSalaryTrainingSituation", value = "培训情况entity", dataType = "ZjXmSalaryTrainingSituation")
	@RequireToken
	@PostMapping("/getZjXmSalaryTrainingSituationList")
	public ResponseEntity getZjXmSalaryTrainingSituationList(
			@RequestBody(required = false) ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation) {
		return zjXmSalaryTrainingSituationService
				.getZjXmSalaryTrainingSituationListByCondition(zjXmSalaryTrainingSituation);
	}

	@ApiOperation(value = "查询详情培训情况", notes = "查询详情培训情况")
	@ApiImplicitParam(name = "zjXmSalaryTrainingSituation", value = "培训情况entity", dataType = "ZjXmSalaryTrainingSituation")
	@RequireToken
	@PostMapping("/getZjXmSalaryTrainingSituationDetails")
	public ResponseEntity getZjXmSalaryTrainingSituationDetails(
			@RequestBody(required = false) ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation) {
		return zjXmSalaryTrainingSituationService.getZjXmSalaryTrainingSituationDetails(zjXmSalaryTrainingSituation);
	}

	@ApiOperation(value = "新增培训情况", notes = "新增培训情况")
	@ApiImplicitParam(name = "zjXmSalaryTrainingSituation", value = "培训情况entity", dataType = "ZjXmSalaryTrainingSituation")
	@RequireToken
	@PostMapping("/addZjXmSalaryTrainingSituation")
	public ResponseEntity addZjXmSalaryTrainingSituation(
			@RequestBody(required = false) ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation) {
		return zjXmSalaryTrainingSituationService.saveZjXmSalaryTrainingSituation(zjXmSalaryTrainingSituation);
	}

	@ApiOperation(value = "更新培训情况", notes = "更新培训情况")
	@ApiImplicitParam(name = "zjXmSalaryTrainingSituation", value = "培训情况entity", dataType = "ZjXmSalaryTrainingSituation")
	@RequireToken
	@PostMapping("/updateZjXmSalaryTrainingSituation")
	public ResponseEntity updateZjXmSalaryTrainingSituation(
			@RequestBody(required = false) ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation) {
		return zjXmSalaryTrainingSituationService.updateZjXmSalaryTrainingSituation(zjXmSalaryTrainingSituation);
	}

	@ApiOperation(value = "删除培训情况", notes = "删除培训情况")
	@ApiImplicitParam(name = "zjXmSalaryTrainingSituationList", value = "培训情况List", dataType = "List<ZjXmSalaryTrainingSituation>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryTrainingSituation")
	public ResponseEntity batchDeleteUpdateZjXmSalaryTrainingSituation(
			@RequestBody(required = false) List<ZjXmSalaryTrainingSituation> zjXmSalaryTrainingSituationList) {
		return zjXmSalaryTrainingSituationService
				.batchDeleteUpdateZjXmSalaryTrainingSituation(zjXmSalaryTrainingSituationList);
	}

}
