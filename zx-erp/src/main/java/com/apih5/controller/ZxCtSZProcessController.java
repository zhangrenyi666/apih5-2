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
import com.apih5.mybatis.pojo.ZxCtContrProcess;
import com.apih5.mybatis.pojo.ZxCtSZProcess;
import com.apih5.service.ZxCtSZProcessService;

@RestController
public class ZxCtSZProcessController {

	@Autowired(required = true)
	private ZxCtSZProcessService zxCtSZProcessService;

	@ApiOperation(value = "查询市政标准工序", notes = "查询市政标准工序")
	@ApiImplicitParam(name = "zxCtSZProcess", value = "市政标准工序entity", dataType = "ZxCtSZProcess")
	@RequireToken
	@PostMapping("/getZxCtSZProcessList")
	public ResponseEntity getZxCtSZProcessList(@RequestBody(required = false) ZxCtSZProcess zxCtSZProcess) {
		return zxCtSZProcessService.getZxCtSZProcessListByCondition(zxCtSZProcess);
	}

	@ApiOperation(value = "查询详情市政标准工序", notes = "查询详情市政标准工序")
	@ApiImplicitParam(name = "zxCtSZProcess", value = "市政标准工序entity", dataType = "ZxCtSZProcess")
	@RequireToken
	@PostMapping("/getZxCtSZProcessDetails")
	public ResponseEntity getZxCtSZProcessDetails(@RequestBody(required = false) ZxCtSZProcess zxCtSZProcess) {
		return zxCtSZProcessService.getZxCtSZProcessDetails(zxCtSZProcess);
	}

	@ApiOperation(value = "新增市政标准工序", notes = "新增市政标准工序")
	@ApiImplicitParam(name = "zxCtSZProcess", value = "市政标准工序entity", dataType = "ZxCtSZProcess")
	@RequireToken
	@PostMapping("/addZxCtSZProcess")
	public ResponseEntity addZxCtSZProcess(@RequestBody(required = false) ZxCtSZProcess zxCtSZProcess) {
		return zxCtSZProcessService.saveZxCtSZProcess(zxCtSZProcess);
	}

	@ApiOperation(value = "更新市政标准工序", notes = "更新市政标准工序")
	@ApiImplicitParam(name = "zxCtSZProcess", value = "市政标准工序entity", dataType = "ZxCtSZProcess")
	@RequireToken
	@PostMapping("/updateZxCtSZProcess")
	public ResponseEntity updateZxCtSZProcess(@RequestBody(required = false) ZxCtSZProcess zxCtSZProcess) {
		return zxCtSZProcessService.updateZxCtSZProcess(zxCtSZProcess);
	}

	@ApiOperation(value = "删除市政标准工序", notes = "删除市政标准工序")
	@ApiImplicitParam(name = "zxCtSZProcessList", value = "市政标准工序List", dataType = "List<ZxCtSZProcess>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxCtSZProcess")
	public ResponseEntity batchDeleteUpdateZxCtSZProcess(
			@RequestBody(required = false) List<ZxCtSZProcess> zxCtSZProcessList) {
		return zxCtSZProcessService.batchDeleteUpdateZxCtSZProcess(zxCtSZProcessList);
	}

	@ApiOperation(value = "查询市政标准工序左边树形", notes = "查询市政标准工序左边树形")
	@ApiImplicitParam(name = "zxCtSZProcess", value = "市政标准工序entity", dataType = "ZxCtSZProcess")
	@RequireToken
	@PostMapping("/getZxCtSZProcessTree")
	public ResponseEntity getZxCtSZProcessTree(@RequestBody(required = false) ZxCtSZProcess zxCtSZProcess) {
		return zxCtSZProcessService.getZxCtSZProcessTree(zxCtSZProcess);
	}

	@ApiOperation(value = "查询市政标准工序右边树形", notes = "查询市政标准工序右边树形")
	@ApiImplicitParam(name = "zxCtSZProcess", value = "市政标准工序entity", dataType = "ZxCtSZProcess")
	@RequireToken
	@PostMapping("/getZxCtSZProcessItemList")
	public ResponseEntity getZxCtSZProcessItemList(@RequestBody(required = false) ZxCtSZProcess zxCtSZProcess) {
		return zxCtSZProcessService.getZxCtSZProcessItemList(zxCtSZProcess);
	}

	// +++++++++++++++++++++++++++++分包合同引用+++++++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "分包合同评审/管理/补充协议中获取标准工序", notes = "分包合同评审/管理/补充协议中获取标准工序")
	@ApiImplicitParam(name = "zxCtSZProcess", value = "市政标准工序entity", dataType = "ZxCtSZProcess")
	@RequireToken
	@PostMapping("/gcsgGetZxCtSZProcessList")
	public ResponseEntity gcsgGetZxCtSZProcessList(@RequestBody(required = false) ZxCtSZProcess zxCtSZProcess) {
		return zxCtSZProcessService.gcsgGetZxCtSZProcessList(zxCtSZProcess);
	}

	@ApiOperation(value = "分包合同评审/管理/补充协议中标准工序库二级下拉", notes = "分包合同评审/管理/补充协议中标准工序库二级下拉")
	@ApiImplicitParam(name = "zxCtSZProcess", value = "市政标准工序entity", dataType = "ZxCtSZProcess")
	// @RequireToken
	@PostMapping("/gcsgGetZxCtSZProcessSelect")
	public ResponseEntity gcsgGetZxCtSZProcessSelect(@RequestBody(required = false) ZxCtSZProcess zxCtSZProcess) {
		return zxCtSZProcessService.gcsgGetZxCtSZProcessSelect(zxCtSZProcess);
	}
	// +++++++++++++++++++++++++++++分包合同引用+++++++++++++++++++++++++++++++++++++++++++++++++++

}
