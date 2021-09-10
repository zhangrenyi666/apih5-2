package com.apih5.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import com.apih5.framework.annotation.DuplicateCommit;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxUserIndexLibrary;
import com.apih5.service.ZjXmJxUserIndexLibraryService;

@RestController
public class ZjXmJxUserIndexLibraryController {

	@Autowired(required = true)
	private ZjXmJxUserIndexLibraryService zjXmJxUserIndexLibraryService;

	@ApiOperation(value = "查询人员指标库", notes = "查询人员指标库")
	@ApiImplicitParam(name = "zjXmJxUserIndexLibrary", value = "人员指标库entity", dataType = "ZjXmJxUserIndexLibrary")
	@RequireToken
	@PostMapping("/getZjXmJxUserIndexLibraryList")
	public ResponseEntity getZjXmJxUserIndexLibraryList(
			@RequestBody(required = false) ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		return zjXmJxUserIndexLibraryService.getZjXmJxUserIndexLibraryListByCondition(zjXmJxUserIndexLibrary);
	}

	@ApiOperation(value = "查询详情人员指标库", notes = "查询详情人员指标库")
	@ApiImplicitParam(name = "zjXmJxUserIndexLibrary", value = "人员指标库entity", dataType = "ZjXmJxUserIndexLibrary")
	@RequireToken
	@PostMapping("/getZjXmJxUserIndexLibraryDetails")
	public ResponseEntity getZjXmJxUserIndexLibraryDetails(
			@RequestBody(required = false) ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		return zjXmJxUserIndexLibraryService.getZjXmJxUserIndexLibraryDetails(zjXmJxUserIndexLibrary);
	}

	@ApiOperation(value = "新增人员指标库", notes = "新增人员指标库")
	@ApiImplicitParam(name = "zjXmJxUserIndexLibrary", value = "人员指标库entity", dataType = "ZjXmJxUserIndexLibrary")
	@RequireToken
	@PostMapping("/addZjXmJxUserIndexLibrary")
	public ResponseEntity addZjXmJxUserIndexLibrary(
			@RequestBody(required = false) ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		return zjXmJxUserIndexLibraryService.saveZjXmJxUserIndexLibrary(zjXmJxUserIndexLibrary);
	}

	@ApiOperation(value = "更新人员指标库", notes = "更新人员指标库")
	@ApiImplicitParam(name = "zjXmJxUserIndexLibrary", value = "人员指标库entity", dataType = "ZjXmJxUserIndexLibrary")
	@RequireToken
	@PostMapping("/updateZjXmJxUserIndexLibrary")
	public ResponseEntity updateZjXmJxUserIndexLibrary(
			@RequestBody(required = false) ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		return zjXmJxUserIndexLibraryService.updateZjXmJxUserIndexLibrary(zjXmJxUserIndexLibrary);
	}

	@ApiOperation(value = "删除人员指标库", notes = "删除人员指标库")
	@ApiImplicitParam(name = "zjXmJxUserIndexLibraryList", value = "人员指标库List", dataType = "List<ZjXmJxUserIndexLibrary>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxUserIndexLibrary")
	public ResponseEntity batchDeleteUpdateZjXmJxUserIndexLibrary(
			@RequestBody(required = false) List<ZjXmJxUserIndexLibrary> zjXmJxUserIndexLibraryList) {
		return zjXmJxUserIndexLibraryService.batchDeleteUpdateZjXmJxUserIndexLibrary(zjXmJxUserIndexLibraryList);
	}

//+++++++++++++++++++++++++++++++业务接口start+++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "[废弃]一键获取(根据人员所在部门id获取指标库)", notes = "[废弃]一键获取(根据人员所在部门id获取指标库)")
	@ApiImplicitParam(name = "zjXmJxUserIndexLibrary", value = "人员指标库entity", dataType = "ZjXmJxUserIndexLibrary")
	@RequireToken
	@PostMapping("/oneClickPullZjXmJxUserIndexLibrary")
	public ResponseEntity oneClickPullZjXmJxUserIndexLibrary(
			@RequestBody(required = false) ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		return zjXmJxUserIndexLibraryService.oneClickPullZjXmJxUserIndexLibrary(zjXmJxUserIndexLibrary);
	}

	@ApiOperation(value = "批量提交人员指标库", notes = "批量提交人员指标库")
	@ApiImplicitParam(name = "zjXmJxUserIndexLibraryList", value = "人员指标库List", dataType = "List<ZjXmJxUserIndexLibrary>")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/batchSubmitZjXmJxUserIndexLibrary")
	public ResponseEntity batchSubmitZjXmJxUserIndexLibrary(
			@RequestBody(required = false) ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		return zjXmJxUserIndexLibraryService.batchSubmitZjXmJxUserIndexLibrary(zjXmJxUserIndexLibrary);
	}
//+++++++++++++++++++++++++++++++业务接口end+++++++++++++++++++++++++++++++++++++++++++++

}
