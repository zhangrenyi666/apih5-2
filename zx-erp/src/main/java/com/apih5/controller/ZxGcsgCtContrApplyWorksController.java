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
import com.apih5.mybatis.pojo.ZxGcsgCtContrApplyWorks;
import com.apih5.service.ZxGcsgCtContrApplyWorksService;

@RestController
public class ZxGcsgCtContrApplyWorksController {

	@Autowired(required = true)
	private ZxGcsgCtContrApplyWorksService zxGcsgCtContrApplyWorksService;

	@ApiOperation(value = "查询工程施工类合同评审清单表", notes = "查询工程施工类合同评审清单表")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyWorksList")
	public ResponseEntity getZxGcsgCtContrApplyWorksList(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.getZxGcsgCtContrApplyWorksListByCondition(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "查询详情工程施工类合同评审清单表", notes = "查询详情工程施工类合同评审清单表")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyWorksDetail")
	public ResponseEntity getZxGcsgCtContrApplyWorksDetail(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.getZxGcsgCtContrApplyWorksDetail(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "新增工程施工类合同评审清单表", notes = "新增工程施工类合同评审清单表")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/addZxGcsgCtContrApplyWorks")
	public ResponseEntity addZxGcsgCtContrApplyWorks(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.saveZxGcsgCtContrApplyWorks(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "更新工程施工类合同评审清单表", notes = "更新工程施工类合同评审清单表")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/updateZxGcsgCtContrApplyWorks")
	public ResponseEntity updateZxGcsgCtContrApplyWorks(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.updateZxGcsgCtContrApplyWorks(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "删除工程施工类合同评审清单表", notes = "删除工程施工类合同评审清单表")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorksList", value = "工程施工类合同评审清单表List", dataType = "List<ZxGcsgCtContrApplyWorks>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxGcsgCtContrApplyWorks")
	public ResponseEntity batchDeleteUpdateZxGcsgCtContrApplyWorks(
			@RequestBody(required = false) List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList) {
		return zxGcsgCtContrApplyWorksService.batchDeleteUpdateZxGcsgCtContrApplyWorks(zxGcsgCtContrApplyWorksList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "导入工程合同评审清单信息(旧)", notes = "导入工程合同评审清单信息(旧)")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	// @RequireToken
	@PostMapping("/importZxGcsgCtContrApplyWorks")
	public ResponseEntity importZxGcsgCtContrApplyWorks(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.importZxGcsgCtContrApplyWorks(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "查询工程施工类合同评审清单左侧树形结构", notes = "查询工程施工类合同评审清单左侧树形结构")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyWorksTree")
	public ResponseEntity getZxGcsgCtContrApplyWorksTree(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.getZxGcsgCtContrApplyWorksTree(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审点击清单获取数据[合计金额]", notes = "合同评审点击清单获取数据[合计金额]")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyWorksListAmount")
	public ResponseEntity getZxGcsgCtContrApplyWorksListAmount(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.getZxGcsgCtContrApplyWorksListAmount(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审点击编辑下级清单时获取数据", notes = "合同评审点击编辑下级清单时获取数据")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyWorksEditSubList")
	public ResponseEntity getZxGcsgCtContrApplyWorksEditSubList(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.getZxGcsgCtContrApplyWorksEditSubList(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审点击批量编辑清单时获取数据", notes = "合同评审点击批量编辑清单时获取数据")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyWorksEditAllList")
	public ResponseEntity getZxGcsgCtContrApplyWorksEditAllList(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.getZxGcsgCtContrApplyWorksEditAllList(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审编辑下级清单时批量保存数据[增改删]", notes = "合同评审编辑下级清单时批量保存数据")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/batchEditSubListZxGcsgCtContrApplyWorks")
	public ResponseEntity batchEditSubListZxGcsgCtContrApplyWorks(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.batchEditSubListZxGcsgCtContrApplyWorks(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审批量编辑清单时批量保存数据[改删]", notes = "合同评审批量编辑清单时批量保存数据")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/batchEditAllListZxGcsgCtContrApplyWorks")
	public ResponseEntity batchEditAllListZxGcsgCtContrApplyWorks(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.batchEditAllListZxGcsgCtContrApplyWorks(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审点击自动挂接工序", notes = "合同评审点击自动挂接工序")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/autoHookZxGcsgCtContrApplyWorksProcess")
	public ResponseEntity autoHookZxGcsgCtContrApplyWorksProcess(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.autoHookZxGcsgCtContrApplyWorksProcess(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审选中清单挂接工序", notes = "合同评审选中清单挂接工序")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/manualHookZxGcsgCtContrApplyWorksProcess")
	public ResponseEntity manualHookZxGcsgCtContrApplyWorksProcess(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.manualHookZxGcsgCtContrApplyWorksProcess(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审选中清单挂接计价规则", notes = "合同评审选中清单挂接计价规则")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/manualHookZxGcsgCtContrApplyWorksRule")
	public ResponseEntity manualHookZxGcsgCtContrApplyWorksRule(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.manualHookZxGcsgCtContrApplyWorksRule(zxGcsgCtContrApplyWorks);
	}

	@ApiOperation(value = "合同评审tab页清单工序挂接台账", notes = "合同评审tab页清单工序挂接台账")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyWorks", value = "工程施工类合同评审清单表entity", dataType = "ZxGcsgCtContrApplyWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyWorksListProcess")
	public ResponseEntity getZxGcsgCtContrApplyWorksListProcess(
			@RequestBody(required = false) ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		return zxGcsgCtContrApplyWorksService.getZxGcsgCtContrApplyWorksListProcess(zxGcsgCtContrApplyWorks);
	}

}
