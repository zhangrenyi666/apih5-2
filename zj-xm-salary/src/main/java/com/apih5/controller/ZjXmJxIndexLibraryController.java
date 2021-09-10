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
import com.apih5.mybatis.pojo.ZjXmJxIndexLibrary;
import com.apih5.service.ZjXmJxIndexLibraryService;

@RestController
public class ZjXmJxIndexLibraryController {

	@Autowired(required = true)
	private ZjXmJxIndexLibraryService zjXmJxIndexLibraryService;

	@ApiOperation(value = "查询指标库", notes = "查询指标库")
	@ApiImplicitParam(name = "zjXmJxIndexLibrary", value = "指标库entity", dataType = "ZjXmJxIndexLibrary")
	@RequireToken
	@PostMapping("/getZjXmJxIndexLibraryList")
	public ResponseEntity getZjXmJxIndexLibraryList(
			@RequestBody(required = false) ZjXmJxIndexLibrary zjXmJxIndexLibrary) {
		return zjXmJxIndexLibraryService.getZjXmJxIndexLibraryListByCondition(zjXmJxIndexLibrary);
	}

	@ApiOperation(value = "查询详情指标库", notes = "查询详情指标库")
	@ApiImplicitParam(name = "zjXmJxIndexLibrary", value = "指标库entity", dataType = "ZjXmJxIndexLibrary")
	@RequireToken
	@PostMapping("/getZjXmJxIndexLibraryDetails")
	public ResponseEntity getZjXmJxIndexLibraryDetails(
			@RequestBody(required = false) ZjXmJxIndexLibrary zjXmJxIndexLibrary) {
		return zjXmJxIndexLibraryService.getZjXmJxIndexLibraryDetails(zjXmJxIndexLibrary);
	}

	@ApiOperation(value = "新增指标库", notes = "新增指标库")
	@ApiImplicitParam(name = "zjXmJxIndexLibrary", value = "指标库entity", dataType = "ZjXmJxIndexLibrary")
	@RequireToken
	@PostMapping("/addZjXmJxIndexLibrary")
	public ResponseEntity addZjXmJxIndexLibrary(@RequestBody(required = false) ZjXmJxIndexLibrary zjXmJxIndexLibrary) {
		return zjXmJxIndexLibraryService.saveZjXmJxIndexLibrary(zjXmJxIndexLibrary);
	}

	@ApiOperation(value = "更新指标库", notes = "更新指标库")
	@ApiImplicitParam(name = "zjXmJxIndexLibrary", value = "指标库entity", dataType = "ZjXmJxIndexLibrary")
	@RequireToken
	@PostMapping("/updateZjXmJxIndexLibrary")
	public ResponseEntity updateZjXmJxIndexLibrary(
			@RequestBody(required = false) ZjXmJxIndexLibrary zjXmJxIndexLibrary) {
		return zjXmJxIndexLibraryService.updateZjXmJxIndexLibrary(zjXmJxIndexLibrary);
	}

	@ApiOperation(value = "删除指标库", notes = "删除指标库")
	@ApiImplicitParam(name = "zjXmJxIndexLibraryList", value = "指标库List", dataType = "List<ZjXmJxIndexLibrary>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxIndexLibrary")
	public ResponseEntity batchDeleteUpdateZjXmJxIndexLibrary(
			@RequestBody(required = false) List<ZjXmJxIndexLibrary> zjXmJxIndexLibraryList) {
		return zjXmJxIndexLibraryService.batchDeleteUpdateZjXmJxIndexLibrary(zjXmJxIndexLibraryList);
	}

}
