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
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlterWork;
import com.apih5.service.ZxGcsgCcCoAlterWorkService;

@RestController
public class ZxGcsgCcCoAlterWorkController {

	@Autowired(required = true)
	private ZxGcsgCcCoAlterWorkService zxGcsgCcCoAlterWorkService;

	@ApiOperation(value = "查询工程施工类合同补充协议清单明细表", notes = "查询工程施工类合同补充协议清单明细表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWork", value = "工程施工类合同补充协议清单明细表entity", dataType = "ZxGcsgCcCoAlterWork")
	@RequireToken
	@PostMapping("/getZxGcsgCcCoAlterWorkList")
	public ResponseEntity getZxGcsgCcCoAlterWorkList(
			@RequestBody(required = false) ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		return zxGcsgCcCoAlterWorkService.getZxGcsgCcCoAlterWorkListByCondition(zxGcsgCcCoAlterWork);
	}

	@ApiOperation(value = "查询详情工程施工类合同补充协议清单明细表", notes = "查询详情工程施工类合同补充协议清单明细表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWork", value = "工程施工类合同补充协议清单明细表entity", dataType = "ZxGcsgCcCoAlterWork")
	@RequireToken
	@PostMapping("/getZxGcsgCcCoAlterWorkDetail")
	public ResponseEntity getZxGcsgCcCoAlterWorkDetail(
			@RequestBody(required = false) ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		return zxGcsgCcCoAlterWorkService.getZxGcsgCcCoAlterWorkDetail(zxGcsgCcCoAlterWork);
	}

	@ApiOperation(value = "新增工程施工类合同补充协议清单明细表", notes = "新增工程施工类合同补充协议清单明细表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWork", value = "工程施工类合同补充协议清单明细表entity", dataType = "ZxGcsgCcCoAlterWork")
	@RequireToken
	@PostMapping("/addZxGcsgCcCoAlterWork")
	public ResponseEntity addZxGcsgCcCoAlterWork(
			@RequestBody(required = false) ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		return zxGcsgCcCoAlterWorkService.saveZxGcsgCcCoAlterWork(zxGcsgCcCoAlterWork);
	}

	@ApiOperation(value = "更新工程施工类合同补充协议清单明细表", notes = "更新工程施工类合同补充协议清单明细表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWork", value = "工程施工类合同补充协议清单明细表entity", dataType = "ZxGcsgCcCoAlterWork")
	@RequireToken
	@PostMapping("/updateZxGcsgCcCoAlterWork")
	public ResponseEntity updateZxGcsgCcCoAlterWork(
			@RequestBody(required = false) ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		return zxGcsgCcCoAlterWorkService.updateZxGcsgCcCoAlterWork(zxGcsgCcCoAlterWork);
	}

	@ApiOperation(value = "删除工程施工类合同补充协议清单明细表", notes = "删除工程施工类合同补充协议清单明细表")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWorkList", value = "工程施工类合同补充协议清单明细表List", dataType = "List<ZxGcsgCcCoAlterWork>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxGcsgCcCoAlterWork")
	public ResponseEntity batchDeleteUpdateZxGcsgCcCoAlterWork(
			@RequestBody(required = false) List<ZxGcsgCcCoAlterWork> zxGcsgCcCoAlterWorkList) {
		return zxGcsgCcCoAlterWorkService.batchDeleteUpdateZxGcsgCcCoAlterWork(zxGcsgCcCoAlterWorkList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "协议点击清单获取数据[合计金额]", notes = "补充协议点击清单获取数据[合计金额]")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWork", value = "工程施工类合同补充协议清单明细表entity", dataType = "ZxGcsgCcCoAlterWork")
	@RequireToken
	@PostMapping("/getZxGcsgCcCoAlterWorkListAmount")
	public ResponseEntity getZxGcsgCcCoAlterWorkListAmount(
			@RequestBody(required = false) ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		return zxGcsgCcCoAlterWorkService.getZxGcsgCcCoAlterWorkListAmount(zxGcsgCcCoAlterWork);
	}

	@ApiOperation(value = "补充协议选中新增类型的清单挂接工序", notes = "补充协议选中新增类型的清单挂接工序")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWork", value = "工程施工类合同补充协议清单明细表entity", dataType = "ZxGcsgCcCoAlterWork")
	@RequireToken
	@PostMapping("/manualHookZxGcsgCcCoAlterWorkProcess")
	public ResponseEntity manualHookZxGcsgCcCoAlterWorkProcess(
			@RequestBody(required = false) ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		return zxGcsgCcCoAlterWorkService.manualHookZxGcsgCcCoAlterWorkProcess(zxGcsgCcCoAlterWork);
	}

	@ApiOperation(value = "补充协议选中清单挂接计价规则", notes = "补充协议选中清单挂接计价规则")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWork", value = "工程施工类合同补充协议清单明细表entity", dataType = "ZxGcsgCcCoAlterWork")
	@RequireToken
	@PostMapping("/manualHookZxGcsgCcCoAlterWorkRule")
	public ResponseEntity manualHookZxGcsgCcCoAlterWorkRule(
			@RequestBody(required = false) ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		return zxGcsgCcCoAlterWorkService.manualHookZxGcsgCcCoAlterWorkRule(zxGcsgCcCoAlterWork);
	}

	@ApiOperation(value = "补充协议tab页清单工序挂接台账", notes = "合同管理tab页清单工序挂接台账")
	@ApiImplicitParam(name = "zxGcsgCcCoAlterWork", value = "工程施工类合同补充协议清单明细表entity", dataType = "ZxGcsgCcCoAlterWork")
	@RequireToken
	@PostMapping("/getZxGcsgCcCoAlterWorkListProcess")
	public ResponseEntity getZxGcsgCcCoAlterWorkListProcess(
			@RequestBody(required = false) ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		return zxGcsgCcCoAlterWorkService.getZxGcsgCcCoAlterWorkListProcess(zxGcsgCcCoAlterWork);
	}

}
