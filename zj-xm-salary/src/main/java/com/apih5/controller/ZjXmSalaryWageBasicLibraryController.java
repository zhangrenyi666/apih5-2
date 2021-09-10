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
import com.apih5.mybatis.pojo.ZjXmSalaryWageBasicLibrary;
import com.apih5.service.ZjXmSalaryWageBasicLibraryService;

@RestController
public class ZjXmSalaryWageBasicLibraryController {

	@Autowired(required = true)
	private ZjXmSalaryWageBasicLibraryService zjXmSalaryWageBasicLibraryService;

	@ApiOperation(value = "查询工资基础库", notes = "查询工资基础库")
	@ApiImplicitParam(name = "zjXmSalaryWageBasicLibrary", value = "工资基础库entity", dataType = "ZjXmSalaryWageBasicLibrary")
	@RequireToken
	@PostMapping("/getZjXmSalaryWageBasicLibraryList")
	public ResponseEntity getZjXmSalaryWageBasicLibraryList(
			@RequestBody(required = false) ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary) {
		return zjXmSalaryWageBasicLibraryService
				.getZjXmSalaryWageBasicLibraryListByCondition(zjXmSalaryWageBasicLibrary);
	}

	@ApiOperation(value = "查询详情工资基础库", notes = "查询详情工资基础库")
	@ApiImplicitParam(name = "zjXmSalaryWageBasicLibrary", value = "工资基础库entity", dataType = "ZjXmSalaryWageBasicLibrary")
	@RequireToken
	@PostMapping("/getZjXmSalaryWageBasicLibraryDetails")
	public ResponseEntity getZjXmSalaryWageBasicLibraryDetails(
			@RequestBody(required = false) ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary) {
		return zjXmSalaryWageBasicLibraryService.getZjXmSalaryWageBasicLibraryDetails(zjXmSalaryWageBasicLibrary);
	}

	@ApiOperation(value = "新增工资基础库", notes = "新增工资基础库")
	@ApiImplicitParam(name = "zjXmSalaryWageBasicLibrary", value = "工资基础库entity", dataType = "ZjXmSalaryWageBasicLibrary")
	@RequireToken
	@PostMapping("/addZjXmSalaryWageBasicLibrary")
	public ResponseEntity addZjXmSalaryWageBasicLibrary(
			@RequestBody(required = false) ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary) {
		return zjXmSalaryWageBasicLibraryService.saveZjXmSalaryWageBasicLibrary(zjXmSalaryWageBasicLibrary);
	}

	@ApiOperation(value = "更新工资基础库", notes = "更新工资基础库")
	@ApiImplicitParam(name = "zjXmSalaryWageBasicLibrary", value = "工资基础库entity", dataType = "ZjXmSalaryWageBasicLibrary")
	@RequireToken
	@PostMapping("/updateZjXmSalaryWageBasicLibrary")
	public ResponseEntity updateZjXmSalaryWageBasicLibrary(
			@RequestBody(required = false) ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary) {
		return zjXmSalaryWageBasicLibraryService.updateZjXmSalaryWageBasicLibrary(zjXmSalaryWageBasicLibrary);
	}

	@ApiOperation(value = "删除工资基础库", notes = "删除工资基础库")
	@ApiImplicitParam(name = "zjXmSalaryWageBasicLibraryList", value = "工资基础库List", dataType = "List<ZjXmSalaryWageBasicLibrary>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmSalaryWageBasicLibrary")
	public ResponseEntity batchDeleteUpdateZjXmSalaryWageBasicLibrary(
			@RequestBody(required = false) List<ZjXmSalaryWageBasicLibrary> zjXmSalaryWageBasicLibraryList) {
		return zjXmSalaryWageBasicLibraryService
				.batchDeleteUpdateZjXmSalaryWageBasicLibrary(zjXmSalaryWageBasicLibraryList);
	}

}