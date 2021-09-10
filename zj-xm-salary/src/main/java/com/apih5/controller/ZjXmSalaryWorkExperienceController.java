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
import com.apih5.mybatis.pojo.ZjXmSalaryWorkExperience;
import com.apih5.service.ZjXmSalaryWorkExperienceService;

@RestController
public class ZjXmSalaryWorkExperienceController {

	@Autowired(required = true)
	private ZjXmSalaryWorkExperienceService zjXmSalaryWorkExperienceService;

	@ApiOperation(value = "查询工作履历", notes = "查询工作履历")
	@ApiImplicitParam(name = "zjXmSalaryWorkExperience", value = "工作履历entity", dataType = "ZjXmSalaryWorkExperience")
	@RequireToken
	@PostMapping("/getZjXmSalaryWorkExperienceList")
	public ResponseEntity getZjXmSalaryWorkExperienceList(
			@RequestBody(required = false) ZjXmSalaryWorkExperience zjXmSalaryWorkExperience) {
		return zjXmSalaryWorkExperienceService.getZjXmSalaryWorkExperienceListByCondition(zjXmSalaryWorkExperience);
	}

	@ApiOperation(value = "查询详情工作履历", notes = "查询详情工作履历")
	@ApiImplicitParam(name = "zjXmSalaryWorkExperience", value = "工作履历entity", dataType = "ZjXmSalaryWorkExperience")
	@RequireToken
	@PostMapping("/getZjXmSalaryWorkExperienceDetails")
	public ResponseEntity getZjXmSalaryWorkExperienceDetails(
			@RequestBody(required = false) ZjXmSalaryWorkExperience zjXmSalaryWorkExperience) {
		return zjXmSalaryWorkExperienceService.getZjXmSalaryWorkExperienceDetails(zjXmSalaryWorkExperience);
	}

	@ApiOperation(value = "新增工作履历", notes = "新增工作履历")
	@ApiImplicitParam(name = "zjXmSalaryWorkExperience", value = "工作履历entity", dataType = "ZjXmSalaryWorkExperience")
	@RequireToken
	@PostMapping("/addZjXmSalaryWorkExperience")
	public ResponseEntity addZjXmSalaryWorkExperience(
			@RequestBody(required = false) ZjXmSalaryWorkExperience zjXmSalaryWorkExperience) {
		return zjXmSalaryWorkExperienceService.saveZjXmSalaryWorkExperience(zjXmSalaryWorkExperience);
	}

	@ApiOperation(value = "更新工作履历", notes = "更新工作履历")
	@ApiImplicitParam(name = "zjXmSalaryWorkExperience", value = "工作履历entity", dataType = "ZjXmSalaryWorkExperience")
	@RequireToken
	@PostMapping("/updateZjXmSalaryWorkExperience")
	public ResponseEntity updateZjXmSalaryWorkExperience(
			@RequestBody(required = false) ZjXmSalaryWorkExperience zjXmSalaryWorkExperience) {
		return zjXmSalaryWorkExperienceService.updateZjXmSalaryWorkExperience(zjXmSalaryWorkExperience);
	}

	@ApiOperation(value = "删除工作履历", notes = "删除工作履历")
	@ApiImplicitParam(name = "zjXmSalaryWorkExperienceList", value = "工作履历List", dataType = "List<ZjXmSalaryWorkExperience>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryWorkExperience")
	public ResponseEntity batchDeleteUpdateZjXmSalaryWorkExperience(
			@RequestBody(required = false) List<ZjXmSalaryWorkExperience> zjXmSalaryWorkExperienceList) {
		return zjXmSalaryWorkExperienceService.batchDeleteUpdateZjXmSalaryWorkExperience(zjXmSalaryWorkExperienceList);
	}

}
