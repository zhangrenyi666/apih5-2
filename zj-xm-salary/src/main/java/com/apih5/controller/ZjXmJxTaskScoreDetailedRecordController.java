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
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailedRecord;
import com.apih5.service.ZjXmJxTaskScoreDetailedRecordService;

@RestController
public class ZjXmJxTaskScoreDetailedRecordController {

	@Autowired(required = true)
	private ZjXmJxTaskScoreDetailedRecordService zjXmJxTaskScoreDetailedRecordService;

	@ApiOperation(value = "查询任务考核人员得分明细审核记录", notes = "查询任务考核人员得分明细审核记录")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailedRecord", value = "任务考核人员得分明细审核记录entity", dataType = "ZjXmJxTaskScoreDetailedRecord")
	@RequireToken
	@PostMapping("/getZjXmJxTaskScoreDetailedRecordList")
	public ResponseEntity getZjXmJxTaskScoreDetailedRecordList(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord) {
		return zjXmJxTaskScoreDetailedRecordService
				.getZjXmJxTaskScoreDetailedRecordListByCondition(zjXmJxTaskScoreDetailedRecord);
	}

	@ApiOperation(value = "查询详情任务考核人员得分明细审核记录", notes = "查询详情任务考核人员得分明细审核记录")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailedRecord", value = "任务考核人员得分明细审核记录entity", dataType = "ZjXmJxTaskScoreDetailedRecord")
	@RequireToken
	@PostMapping("/getZjXmJxTaskScoreDetailedRecordDetails")
	public ResponseEntity getZjXmJxTaskScoreDetailedRecordDetails(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord) {
		return zjXmJxTaskScoreDetailedRecordService
				.getZjXmJxTaskScoreDetailedRecordDetails(zjXmJxTaskScoreDetailedRecord);
	}

	@ApiOperation(value = "新增任务考核人员得分明细审核记录", notes = "新增任务考核人员得分明细审核记录")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailedRecord", value = "任务考核人员得分明细审核记录entity", dataType = "ZjXmJxTaskScoreDetailedRecord")
	@RequireToken
	@PostMapping("/addZjXmJxTaskScoreDetailedRecord")
	public ResponseEntity addZjXmJxTaskScoreDetailedRecord(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord) {
		return zjXmJxTaskScoreDetailedRecordService.saveZjXmJxTaskScoreDetailedRecord(zjXmJxTaskScoreDetailedRecord);
	}

	@ApiOperation(value = "更新任务考核人员得分明细审核记录", notes = "更新任务考核人员得分明细审核记录")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailedRecord", value = "任务考核人员得分明细审核记录entity", dataType = "ZjXmJxTaskScoreDetailedRecord")
	@RequireToken
	@PostMapping("/updateZjXmJxTaskScoreDetailedRecord")
	public ResponseEntity updateZjXmJxTaskScoreDetailedRecord(
			@RequestBody(required = false) ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord) {
		return zjXmJxTaskScoreDetailedRecordService.updateZjXmJxTaskScoreDetailedRecord(zjXmJxTaskScoreDetailedRecord);
	}

	@ApiOperation(value = "删除任务考核人员得分明细审核记录", notes = "删除任务考核人员得分明细审核记录")
	@ApiImplicitParam(name = "zjXmJxTaskScoreDetailedRecordList", value = "任务考核人员得分明细审核记录List", dataType = "List<ZjXmJxTaskScoreDetailedRecord>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxTaskScoreDetailedRecord")
	public ResponseEntity batchDeleteUpdateZjXmJxTaskScoreDetailedRecord(
			@RequestBody(required = false) List<ZjXmJxTaskScoreDetailedRecord> zjXmJxTaskScoreDetailedRecordList) {
		return zjXmJxTaskScoreDetailedRecordService
				.batchDeleteUpdateZjXmJxTaskScoreDetailedRecord(zjXmJxTaskScoreDetailedRecordList);
	}

}
