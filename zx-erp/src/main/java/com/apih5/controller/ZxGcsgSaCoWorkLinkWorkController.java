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
import com.apih5.mybatis.pojo.ZxGcsgSaCoWorkLinkWork;
import com.apih5.service.ZxGcsgSaCoWorkLinkWorkService;

@RestController
public class ZxGcsgSaCoWorkLinkWorkController {

	@Autowired(required = true)
	private ZxGcsgSaCoWorkLinkWorkService zxGcsgSaCoWorkLinkWorkService;

	@ApiOperation(value = "查询分包清单与业主合同清单关联表", notes = "查询分包清单与业主合同清单关联表")
	@ApiImplicitParam(name = "zxGcsgSaCoWorkLinkWork", value = "分包清单与业主合同清单关联表entity", dataType = "ZxGcsgSaCoWorkLinkWork")
	@RequireToken
	@PostMapping("/getZxGcsgSaCoWorkLinkWorkList")
	public ResponseEntity getZxGcsgSaCoWorkLinkWorkList(
			@RequestBody(required = false) ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		return zxGcsgSaCoWorkLinkWorkService.getZxGcsgSaCoWorkLinkWorkListByCondition(zxGcsgSaCoWorkLinkWork);
	}

	@ApiOperation(value = "查询详情分包清单与业主合同清单关联表", notes = "查询详情分包清单与业主合同清单关联表")
	@ApiImplicitParam(name = "zxGcsgSaCoWorkLinkWork", value = "分包清单与业主合同清单关联表entity", dataType = "ZxGcsgSaCoWorkLinkWork")
	@RequireToken
	@PostMapping("/getZxGcsgSaCoWorkLinkWorkDetail")
	public ResponseEntity getZxGcsgSaCoWorkLinkWorkDetail(
			@RequestBody(required = false) ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		return zxGcsgSaCoWorkLinkWorkService.getZxGcsgSaCoWorkLinkWorkDetail(zxGcsgSaCoWorkLinkWork);
	}

	@ApiOperation(value = "新增分包清单与业主合同清单关联表", notes = "新增分包清单与业主合同清单关联表")
	@ApiImplicitParam(name = "zxGcsgSaCoWorkLinkWork", value = "分包清单与业主合同清单关联表entity", dataType = "ZxGcsgSaCoWorkLinkWork")
	@RequireToken
	@PostMapping("/addZxGcsgSaCoWorkLinkWork")
	public ResponseEntity addZxGcsgSaCoWorkLinkWork(
			@RequestBody(required = false) ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		return zxGcsgSaCoWorkLinkWorkService.saveZxGcsgSaCoWorkLinkWork(zxGcsgSaCoWorkLinkWork);
	}

	@ApiOperation(value = "更新分包清单与业主合同清单关联表", notes = "更新分包清单与业主合同清单关联表")
	@ApiImplicitParam(name = "zxGcsgSaCoWorkLinkWork", value = "分包清单与业主合同清单关联表entity", dataType = "ZxGcsgSaCoWorkLinkWork")
	@RequireToken
	@PostMapping("/updateZxGcsgSaCoWorkLinkWork")
	public ResponseEntity updateZxGcsgSaCoWorkLinkWork(
			@RequestBody(required = false) ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		return zxGcsgSaCoWorkLinkWorkService.updateZxGcsgSaCoWorkLinkWork(zxGcsgSaCoWorkLinkWork);
	}

	@ApiOperation(value = "删除分包清单与业主合同清单关联表", notes = "删除分包清单与业主合同清单关联表")
	@ApiImplicitParam(name = "zxGcsgSaCoWorkLinkWorkList", value = "分包清单与业主合同清单关联表List", dataType = "List<ZxGcsgSaCoWorkLinkWork>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxGcsgSaCoWorkLinkWork")
	public ResponseEntity batchDeleteUpdateZxGcsgSaCoWorkLinkWork(
			@RequestBody(required = false) List<ZxGcsgSaCoWorkLinkWork> zxGcsgSaCoWorkLinkWorkList) {
		return zxGcsgSaCoWorkLinkWorkService.batchDeleteUpdateZxGcsgSaCoWorkLinkWork(zxGcsgSaCoWorkLinkWorkList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "分包合同管理tab页-分包清单与业主合同清单关联左侧树", notes = "分包合同管理tab页-分包清单与业主合同清单关联左侧树")
	@ApiImplicitParam(name = "zxGcsgSaCoWorkLinkWork", value = "分包清单与业主合同清单关联表entity", dataType = "ZxGcsgSaCoWorkLinkWork")
	@RequireToken
	@PostMapping("/getZxGcsgSaCoWorkLinkWorkLeftTree")
	public ResponseEntity getZxGcsgSaCoWorkLinkWorkLeftTree(
			@RequestBody(required = false) ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		return zxGcsgSaCoWorkLinkWorkService.getZxGcsgSaCoWorkLinkWorkLeftTree(zxGcsgSaCoWorkLinkWork);
	}

	@ApiOperation(value = "点击右侧业主清单编号新增、删除", notes = "点击右侧业主清单编号新增、删除")
	@ApiImplicitParam(name = "zxGcsgSaCoWorkLinkWork", value = "分包清单与业主合同清单关联表entity", dataType = "ZxGcsgSaCoWorkLinkWork")
	@RequireToken
	@PostMapping("/clickAddOrDeleteZxGcsgSaCoWorkLinkWork")
	public ResponseEntity clickAddOrDeleteZxGcsgSaCoWorkLinkWork(
			@RequestBody(required = false) ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		return zxGcsgSaCoWorkLinkWorkService.clickAddOrDeleteZxGcsgSaCoWorkLinkWork(zxGcsgSaCoWorkLinkWork);
	}

	@ApiOperation(value = "点击左侧分包清单是否主项-验证业主清单是否为叶子节点", notes = "点击左侧分包清单是否主项-验证业主清单是否为叶子节点")
	@ApiImplicitParam(name = "zxGcsgSaCoWorkLinkWork", value = "分包清单与业主合同清单关联表entity", dataType = "ZxGcsgSaCoWorkLinkWork")
	@RequireToken
	@PostMapping("/updateZxGcsgSaCoWorkLinkWorkIsMain")
	public ResponseEntity updateZxGcsgSaCoWorkLinkWorkIsMain(
			@RequestBody(required = false) ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		return zxGcsgSaCoWorkLinkWorkService.updateZxGcsgSaCoWorkLinkWorkIsMain(zxGcsgSaCoWorkLinkWork);
	}

}
