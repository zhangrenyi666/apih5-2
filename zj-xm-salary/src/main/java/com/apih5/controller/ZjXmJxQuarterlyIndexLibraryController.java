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
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyIndexLibrary;
import com.apih5.service.ZjXmJxQuarterlyIndexLibraryService;

@RestController
public class ZjXmJxQuarterlyIndexLibraryController {

	@Autowired(required = true)
	private ZjXmJxQuarterlyIndexLibraryService zjXmJxQuarterlyIndexLibraryService;

	@ApiOperation(value = "查询季度考核指标库", notes = "查询季度考核指标库")
	@ApiImplicitParam(name = "zjXmJxQuarterlyIndexLibrary", value = "季度考核指标库entity", dataType = "ZjXmJxQuarterlyIndexLibrary")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyIndexLibraryList")
	public ResponseEntity getZjXmJxQuarterlyIndexLibraryList(
			@RequestBody(required = false) ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary) {
		return zjXmJxQuarterlyIndexLibraryService
				.getZjXmJxQuarterlyIndexLibraryListByCondition(zjXmJxQuarterlyIndexLibrary);
	}

	@ApiOperation(value = "查询详情季度考核指标库", notes = "查询详情季度考核指标库")
	@ApiImplicitParam(name = "zjXmJxQuarterlyIndexLibrary", value = "季度考核指标库entity", dataType = "ZjXmJxQuarterlyIndexLibrary")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyIndexLibraryDetail")
	public ResponseEntity getZjXmJxQuarterlyIndexLibraryDetail(
			@RequestBody(required = false) ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary) {
		return zjXmJxQuarterlyIndexLibraryService.getZjXmJxQuarterlyIndexLibraryDetail(zjXmJxQuarterlyIndexLibrary);
	}

	@ApiOperation(value = "新增季度考核指标库", notes = "新增季度考核指标库")
	@ApiImplicitParam(name = "zjXmJxQuarterlyIndexLibrary", value = "季度考核指标库entity", dataType = "ZjXmJxQuarterlyIndexLibrary")
	@RequireToken
	@PostMapping("/addZjXmJxQuarterlyIndexLibrary")
	public ResponseEntity addZjXmJxQuarterlyIndexLibrary(
			@RequestBody(required = false) ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary) {
		return zjXmJxQuarterlyIndexLibraryService.saveZjXmJxQuarterlyIndexLibrary(zjXmJxQuarterlyIndexLibrary);
	}

	@ApiOperation(value = "更新季度考核指标库", notes = "更新季度考核指标库")
	@ApiImplicitParam(name = "zjXmJxQuarterlyIndexLibrary", value = "季度考核指标库entity", dataType = "ZjXmJxQuarterlyIndexLibrary")
	@RequireToken
	@PostMapping("/updateZjXmJxQuarterlyIndexLibrary")
	public ResponseEntity updateZjXmJxQuarterlyIndexLibrary(
			@RequestBody(required = false) ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary) {
		return zjXmJxQuarterlyIndexLibraryService.updateZjXmJxQuarterlyIndexLibrary(zjXmJxQuarterlyIndexLibrary);
	}

	@ApiOperation(value = "删除季度考核指标库", notes = "删除季度考核指标库")
	@ApiImplicitParam(name = "zjXmJxQuarterlyIndexLibraryList", value = "季度考核指标库List", dataType = "List<ZjXmJxQuarterlyIndexLibrary>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxQuarterlyIndexLibrary")
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyIndexLibrary(
			@RequestBody(required = false) List<ZjXmJxQuarterlyIndexLibrary> zjXmJxQuarterlyIndexLibraryList) {
		return zjXmJxQuarterlyIndexLibraryService
				.batchDeleteUpdateZjXmJxQuarterlyIndexLibrary(zjXmJxQuarterlyIndexLibraryList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
