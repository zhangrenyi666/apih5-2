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
import com.apih5.mybatis.pojo.ZjXmSalaryProfessionalTechnology;
import com.apih5.service.ZjXmSalaryProfessionalTechnologyService;

@RestController
public class ZjXmSalaryProfessionalTechnologyController {

	@Autowired(required = true)
	private ZjXmSalaryProfessionalTechnologyService zjXmSalaryProfessionalTechnologyService;

	@ApiOperation(value = "查询专业技术", notes = "查询专业技术")
	@ApiImplicitParam(name = "zjXmSalaryProfessionalTechnology", value = "专业技术entity", dataType = "ZjXmSalaryProfessionalTechnology")
	@RequireToken
	@PostMapping("/getZjXmSalaryProfessionalTechnologyList")
	public ResponseEntity getZjXmSalaryProfessionalTechnologyList(
			@RequestBody(required = false) ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology) {
		return zjXmSalaryProfessionalTechnologyService
				.getZjXmSalaryProfessionalTechnologyListByCondition(zjXmSalaryProfessionalTechnology);
	}

	@ApiOperation(value = "查询详情专业技术", notes = "查询详情专业技术")
	@ApiImplicitParam(name = "zjXmSalaryProfessionalTechnology", value = "专业技术entity", dataType = "ZjXmSalaryProfessionalTechnology")
	@RequireToken
	@PostMapping("/getZjXmSalaryProfessionalTechnologyDetails")
	public ResponseEntity getZjXmSalaryProfessionalTechnologyDetails(
			@RequestBody(required = false) ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology) {
		return zjXmSalaryProfessionalTechnologyService
				.getZjXmSalaryProfessionalTechnologyDetails(zjXmSalaryProfessionalTechnology);
	}

	@ApiOperation(value = "新增专业技术", notes = "新增专业技术")
	@ApiImplicitParam(name = "zjXmSalaryProfessionalTechnology", value = "专业技术entity", dataType = "ZjXmSalaryProfessionalTechnology")
	@RequireToken
	@PostMapping("/addZjXmSalaryProfessionalTechnology")
	public ResponseEntity addZjXmSalaryProfessionalTechnology(
			@RequestBody(required = false) ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology) {
		return zjXmSalaryProfessionalTechnologyService
				.saveZjXmSalaryProfessionalTechnology(zjXmSalaryProfessionalTechnology);
	}

	@ApiOperation(value = "更新专业技术", notes = "更新专业技术")
	@ApiImplicitParam(name = "zjXmSalaryProfessionalTechnology", value = "专业技术entity", dataType = "ZjXmSalaryProfessionalTechnology")
	@RequireToken
	@PostMapping("/updateZjXmSalaryProfessionalTechnology")
	public ResponseEntity updateZjXmSalaryProfessionalTechnology(
			@RequestBody(required = false) ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology) {
		return zjXmSalaryProfessionalTechnologyService
				.updateZjXmSalaryProfessionalTechnology(zjXmSalaryProfessionalTechnology);
	}

	@ApiOperation(value = "删除专业技术", notes = "删除专业技术")
	@ApiImplicitParam(name = "zjXmSalaryProfessionalTechnologyList", value = "专业技术List", dataType = "List<ZjXmSalaryProfessionalTechnology>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryProfessionalTechnology")
	public ResponseEntity batchDeleteUpdateZjXmSalaryProfessionalTechnology(
			@RequestBody(required = false) List<ZjXmSalaryProfessionalTechnology> zjXmSalaryProfessionalTechnologyList) {
		return zjXmSalaryProfessionalTechnologyService
				.batchDeleteUpdateZjXmSalaryProfessionalTechnology(zjXmSalaryProfessionalTechnologyList);
	}

}
