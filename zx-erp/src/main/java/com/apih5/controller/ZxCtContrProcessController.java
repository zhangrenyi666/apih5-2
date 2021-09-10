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
import com.apih5.service.ZxCtContrProcessService;

@RestController
public class ZxCtContrProcessController {

	@Autowired(required = true)
	private ZxCtContrProcessService zxCtContrProcessService;

	@ApiOperation(value = "查询分包合同标准工序", notes = "查询分包合同标准工序")
	@ApiImplicitParam(name = "zxCtContrProcess", value = "分包合同标准工序entity", dataType = "ZxCtContrProcess")
	@RequireToken
	@PostMapping("/getZxCtContrProcessList")
	public ResponseEntity getZxCtContrProcessList(@RequestBody(required = false) ZxCtContrProcess zxCtContrProcess) {
		return zxCtContrProcessService.getZxCtContrProcessListByCondition(zxCtContrProcess);
	}

	@ApiOperation(value = "查询详情分包合同标准工序", notes = "查询详情分包合同标准工序")
	@ApiImplicitParam(name = "zxCtContrProcess", value = "分包合同标准工序entity", dataType = "ZxCtContrProcess")
	@RequireToken
	@PostMapping("/getZxCtContrProcessDetails")
	public ResponseEntity getZxCtContrProcessDetails(@RequestBody(required = false) ZxCtContrProcess zxCtContrProcess) {
		return zxCtContrProcessService.getZxCtContrProcessDetails(zxCtContrProcess);
	}

	@ApiOperation(value = "新增分包合同标准工序", notes = "新增分包合同标准工序")
	@ApiImplicitParam(name = "zxCtContrProcess", value = "分包合同标准工序entity", dataType = "ZxCtContrProcess")
	@RequireToken
	@PostMapping("/addZxCtContrProcess")
	public ResponseEntity addZxCtContrProcess(@RequestBody(required = false) ZxCtContrProcess zxCtContrProcess) {
		return zxCtContrProcessService.saveZxCtContrProcess(zxCtContrProcess);
	}

	@ApiOperation(value = "更新分包合同标准工序", notes = "更新分包合同标准工序")
	@ApiImplicitParam(name = "zxCtContrProcess", value = "分包合同标准工序entity", dataType = "ZxCtContrProcess")
	@RequireToken
	@PostMapping("/updateZxCtContrProcess")
	public ResponseEntity updateZxCtContrProcess(@RequestBody(required = false) ZxCtContrProcess zxCtContrProcess) {
		return zxCtContrProcessService.updateZxCtContrProcess(zxCtContrProcess);
	}

	@ApiOperation(value = "删除分包合同标准工序", notes = "删除分包合同标准工序")
	@ApiImplicitParam(name = "zxCtContrProcessList", value = "分包合同标准工序List", dataType = "List<ZxCtContrProcess>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxCtContrProcess")
	public ResponseEntity batchDeleteUpdateZxCtContrProcess(
			@RequestBody(required = false) List<ZxCtContrProcess> zxCtContrProcessList) {
		return zxCtContrProcessService.batchDeleteUpdateZxCtContrProcess(zxCtContrProcessList);
	}

	@ApiOperation(value = "查询分包合同标准工序左边树形", notes = "查询分包合同标准工序左边树形")
	@ApiImplicitParam(name = "zxCtContrProcess", value = "分包合同标准工序entity", dataType = "ZxCtContrProcess")
	@RequireToken
	@PostMapping("/getZxCtContrProcessTree")
	public ResponseEntity getZxCtContrProcessTree(@RequestBody(required = false) ZxCtContrProcess zxCtContrProcess) {
		return zxCtContrProcessService.getZxCtContrProcessTree(zxCtContrProcess);
	}

	@ApiOperation(value = "查询分包合同标准工序右边树形", notes = "查询分包合同标准工序右边树形")
	@ApiImplicitParam(name = "zxCtContrProcess", value = "分包合同标准工序entity", dataType = "ZxCtContrProcess")
	@RequireToken
	@PostMapping("/getZxCtContrProcessItemList")
	public ResponseEntity getZxCtContrProcessItemList(
			@RequestBody(required = false) ZxCtContrProcess zxCtContrProcess) {
		return zxCtContrProcessService.getZxCtContrProcessItemList(zxCtContrProcess);
	}

	// +++++++++++++++++++++++++++++分包合同引用+++++++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "分包合同评审/管理/补充协议中获取标准工序[废弃]", notes = "分包合同评审/管理/补充协议中获取标准工序[废弃]")
	@ApiImplicitParam(name = "zxCtContrProcess", value = "分包合同标准工序entity", dataType = "ZxCtContrProcess")
	@RequireToken
	@PostMapping("/gcsgGetZxCtContrProcessList")
	public ResponseEntity gcsgGetZxCtContrProcessList(
			@RequestBody(required = false) ZxCtContrProcess zxCtContrProcess) {
		return zxCtContrProcessService.gcsgGetZxCtContrProcessList(zxCtContrProcess);
	}
	// +++++++++++++++++++++++++++++分包合同引用+++++++++++++++++++++++++++++++++++++++++++++++++++

}
