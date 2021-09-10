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
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlter;
import com.apih5.service.ZxGcsgCcCoAlterService;

@RestController
public class ZxGcsgCcCoAlterController {

	@Autowired(required = true)
	private ZxGcsgCcCoAlterService zxGcsgCcCoAlterService;

	@ApiOperation(value = "查询工程施工类合同补充协议清单表", notes = "查询工程施工类合同补充协议清单表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlter", value = "工程施工类合同补充协议清单表entity", dataType = "ZxGcsgCcCoAlter")
	@RequireToken
	@PostMapping("/getZxGcsgCcCoAlterList")
	public ResponseEntity getZxGcsgCcCoAlterList(@RequestBody(required = false) ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		return zxGcsgCcCoAlterService.getZxGcsgCcCoAlterListByCondition(zxGcsgCcCoAlter);
	}

	@ApiOperation(value = "查询详情工程施工类合同补充协议清单表", notes = "查询详情工程施工类合同补充协议清单表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlter", value = "工程施工类合同补充协议清单表entity", dataType = "ZxGcsgCcCoAlter")
	@RequireToken
	@PostMapping("/getZxGcsgCcCoAlterDetail")
	public ResponseEntity getZxGcsgCcCoAlterDetail(@RequestBody(required = false) ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		return zxGcsgCcCoAlterService.getZxGcsgCcCoAlterDetail(zxGcsgCcCoAlter);
	}

	@ApiOperation(value = "新增工程施工类合同补充协议清单表", notes = "新增工程施工类合同补充协议清单表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlter", value = "工程施工类合同补充协议清单表entity", dataType = "ZxGcsgCcCoAlter")
	@RequireToken
	@PostMapping("/addZxGcsgCcCoAlter")
	public ResponseEntity addZxGcsgCcCoAlter(@RequestBody(required = false) ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		return zxGcsgCcCoAlterService.saveZxGcsgCcCoAlter(zxGcsgCcCoAlter);
	}

	@ApiOperation(value = "更新工程施工类合同补充协议清单表", notes = "更新工程施工类合同补充协议清单表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlter", value = "工程施工类合同补充协议清单表entity", dataType = "ZxGcsgCcCoAlter")
	@RequireToken
	@PostMapping("/updateZxGcsgCcCoAlter")
	public ResponseEntity updateZxGcsgCcCoAlter(@RequestBody(required = false) ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		return zxGcsgCcCoAlterService.updateZxGcsgCcCoAlter(zxGcsgCcCoAlter);
	}

	@ApiOperation(value = "删除工程施工类合同补充协议清单表", notes = "删除工程施工类合同补充协议清单表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterList", value = "工程施工类合同补充协议清单表List", dataType = "List<ZxGcsgCcCoAlter>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxGcsgCcCoAlter")
	public ResponseEntity batchDeleteUpdateZxGcsgCcCoAlter(
			@RequestBody(required = false) List<ZxGcsgCcCoAlter> zxGcsgCcCoAlterList) {
		return zxGcsgCcCoAlterService.batchDeleteUpdateZxGcsgCcCoAlter(zxGcsgCcCoAlterList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "工程类合同补充协议获取清单详情及明细列表", notes = "工程类合同补充协议获取清单详情及明细列表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlter", value = "工程施工类合同补充协议清单表entity", dataType = "ZxGcsgCcCoAlter")
	@RequireToken
	@PostMapping("/getZxGcsgCcCoAlterDetailsWorkList")
	public ResponseEntity getZxGcsgCcCoAlterDetailsWorkList(
			@RequestBody(required = false) ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		return zxGcsgCcCoAlterService.getZxGcsgCcCoAlterDetailsWorkList(zxGcsgCcCoAlter);
	}

	@ApiOperation(value = "工程类合同补充协议保存清单详情及明细列表(最新版)", notes = "工程类合同补充协议获取清单详情及明细列表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlter", value = "工程施工类合同补充协议清单表entity", dataType = "ZxGcsgCcCoAlter")
	@RequireToken
	@PostMapping("/saveZxGcsgCcCoAlterDetailsWorkList")
	public ResponseEntity saveZxGcsgCcCoAlterDetailsWorkList(
			@RequestBody(required = false) ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		return zxGcsgCcCoAlterService.saveZxGcsgCcCoAlterDetailsWorkList(zxGcsgCcCoAlter);
	}

	@ApiOperation(value = "工程类合同补充协议保存清单详情及明细列表(第一版备份)", notes = "工程类合同补充协议获取清单详情及明细列表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlter", value = "工程施工类合同补充协议清单表entity", dataType = "ZxGcsgCcCoAlter")
	@RequireToken
	@PostMapping("/saveZxGcsgCcCoAlterDetailsWorkList20210121")
	public ResponseEntity saveZxGcsgCcCoAlterDetailsWorkList20210121(
			@RequestBody(required = false) ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		return zxGcsgCcCoAlterService.saveZxGcsgCcCoAlterDetailsWorkList20210121(zxGcsgCcCoAlter);
	}

}
