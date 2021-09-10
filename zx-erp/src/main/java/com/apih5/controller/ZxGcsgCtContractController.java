package com.apih5.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCtContract;
import com.apih5.service.ZxGcsgCtContractService;

@RestController
public class ZxGcsgCtContractController {

	@Autowired(required = true)
	private ZxGcsgCtContractService zxGcsgCtContractService;

	@ApiOperation(value = "查询工程施工类合同管理表", notes = "查询工程施工类合同管理表")
	@ApiImplicitParam(name = "zxGcsgCtContract", value = "工程施工类合同管理表entity", dataType = "ZxGcsgCtContract")
	@RequireToken
	@PostMapping("/getZxGcsgCtContractList")
	public ResponseEntity getZxGcsgCtContractList(@RequestBody(required = false) ZxGcsgCtContract zxGcsgCtContract) {
		return zxGcsgCtContractService.getZxGcsgCtContractListByCondition(zxGcsgCtContract);
	}

	@ApiOperation(value = "查询详情工程施工类合同管理表", notes = "查询详情工程施工类合同管理表")
	@ApiImplicitParam(name = "zxGcsgCtContract", value = "工程施工类合同管理表entity", dataType = "ZxGcsgCtContract")
	@RequireToken
	@PostMapping("/getZxGcsgCtContractDetail")
	public ResponseEntity getZxGcsgCtContractDetail(@RequestBody(required = false) ZxGcsgCtContract zxGcsgCtContract) {
		return zxGcsgCtContractService.getZxGcsgCtContractDetail(zxGcsgCtContract);
	}

	@ApiOperation(value = "新增工程施工类合同管理表", notes = "新增工程施工类合同管理表")
	@ApiImplicitParam(name = "zxGcsgCtContract", value = "工程施工类合同管理表entity", dataType = "ZxGcsgCtContract")
	@RequireToken
	@PostMapping("/addZxGcsgCtContract")
	public ResponseEntity addZxGcsgCtContract(@RequestBody(required = false) ZxGcsgCtContract zxGcsgCtContract) {
		return zxGcsgCtContractService.saveZxGcsgCtContract(zxGcsgCtContract);
	}

	@ApiOperation(value = "更新工程施工类合同管理表", notes = "更新工程施工类合同管理表")
	@ApiImplicitParam(name = "zxGcsgCtContract", value = "工程施工类合同管理表entity", dataType = "ZxGcsgCtContract")
	@RequireToken
	@PostMapping("/updateZxGcsgCtContract")
	public ResponseEntity updateZxGcsgCtContract(@RequestBody(required = false) ZxGcsgCtContract zxGcsgCtContract) {
		return zxGcsgCtContractService.updateZxGcsgCtContract(zxGcsgCtContract);
	}

	@ApiOperation(value = "删除工程施工类合同管理表", notes = "删除工程施工类合同管理表")
	@ApiImplicitParam(name = "zxGcsgCtContractList", value = "工程施工类合同管理表List", dataType = "List<ZxGcsgCtContract>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxGcsgCtContract")
	public ResponseEntity batchDeleteUpdateZxGcsgCtContract(
			@RequestBody(required = false) List<ZxGcsgCtContract> zxGcsgCtContractList) {
		return zxGcsgCtContractService.batchDeleteUpdateZxGcsgCtContract(zxGcsgCtContractList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "查询工程施工类合同管理详情", notes = "查询工程施工类合同管理详情")
	@ApiImplicitParam(name = "zxGcsgCtContract", value = "工程施工类合同管理表entity", dataType = "ZxGcsgCtContract")
	@RequireToken
	@PostMapping("/getZxGcsgCtContractDetails")
	public ResponseEntity getZxGcsgCtContractDetails(@RequestBody(required = false) ZxGcsgCtContract zxGcsgCtContract) {
		return zxGcsgCtContractService.getZxGcsgCtContractDetails(zxGcsgCtContract);
	}

	@ApiOperation(value = "工程施工类合同管理信息保存", notes = "工程施工类合同管理信息保存")
	@ApiImplicitParam(name = "zxGcsgCtContract", value = "工程施工类合同管理表entity", dataType = "ZxGcsgCtContract")
	@RequireToken
	@PostMapping("/saveZxGcsgCtContractInfo")
	public ResponseEntity saveZxGcsgCtContractInfo(@RequestBody(required = false) ZxGcsgCtContract zxGcsgCtContract) {
		return zxGcsgCtContractService.saveZxGcsgCtContractInfo(zxGcsgCtContract);
	}

	@ApiOperation(value = "工程施工类合同管理列表恢复执行", notes = "工程施工类合同管理列表恢复执行")
	@ApiImplicitParam(name = "zxGcsgCtContract", value = "工程施工类合同管理表entity", dataType = "ZxGcsgCtContract")
	@RequireToken
	@PostMapping("/resumeZxGcsgCtContractStatus")
	public ResponseEntity resumeZxGcsgCtContractStatus(
			@RequestBody(required = false) ZxGcsgCtContract zxGcsgCtContract) {
		return zxGcsgCtContractService.resumeZxGcsgCtContractStatus(zxGcsgCtContract);
	}

	@ApiOperation(value = "工程施工类合同管理导出列表页[客户端流文件]", notes = "工程施工类合同管理导出列表页[客户端流文件]")
	@ApiImplicitParam(name = "zxGcsgCtContract", value = "工程施工类合同管理表entity", dataType = "ZxGcsgCtContract")
	@RequireToken
	@PostMapping("/glExportZxGcsgCtContractExcel")
	public void glExportZxGcsgCtContractExcel(@RequestBody(required = false) ZxGcsgCtContract zxGcsgCtContract,
			HttpServletResponse response) {
		zxGcsgCtContractService.glExportZxGcsgCtContractExcel(zxGcsgCtContract, response);
	}

}
