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
import com.apih5.mybatis.pojo.ZxCtWorks;
import com.apih5.service.ZxCtWorksService;

@RestController
public class ZxCtWorksController {

	@Autowired(required = true)
	private ZxCtWorksService zxCtWorksService;

	@ApiOperation(value = "查询业主合同管理-业主合同台账-清单树(原表iecc_Works", notes = "查询业主合同管理-业主合同台账-清单树(原表iecc_Works")
	@ApiImplicitParam(name = "zxCtWorks", value = "业主合同管理-业主合同台账-清单树(原表iecc_Worksentity", dataType = "ZxCtWorks")
	@RequireToken
	@PostMapping("/getZxCtWorksList")
	public ResponseEntity getZxCtWorksList(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.getZxCtWorksListByCondition(zxCtWorks);
	}

	@ApiOperation(value = "查询详情业主合同管理-业主合同台账-清单树(原表iecc_Works", notes = "查询详情业主合同管理-业主合同台账-清单树(原表iecc_Works")
	@ApiImplicitParam(name = "zxCtWorks", value = "业主合同管理-业主合同台账-清单树(原表iecc_Worksentity", dataType = "ZxCtWorks")
	@RequireToken
	@PostMapping("/getZxCtWorksDetail")
	public ResponseEntity getZxCtWorksDetail(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.getZxCtWorksDetail(zxCtWorks);
	}

	@ApiOperation(value = "新增业主合同管理-业主合同台账-清单树(原表iecc_Works", notes = "新增业主合同管理-业主合同台账-清单树(原表iecc_Works")
	@ApiImplicitParam(name = "zxCtWorks", value = "业主合同管理-业主合同台账-清单树(原表iecc_Worksentity", dataType = "ZxCtWorks")
	@RequireToken
	@PostMapping("/addZxCtWorks")
	public ResponseEntity addZxCtWorks(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.saveZxCtWorks(zxCtWorks);
	}

	@ApiOperation(value = "更新业主合同管理-业主合同台账-清单树(原表iecc_Works", notes = "更新业主合同管理-业主合同台账-清单树(原表iecc_Works")
	@ApiImplicitParam(name = "zxCtWorks", value = "业主合同管理-业主合同台账-清单树(原表iecc_Worksentity", dataType = "ZxCtWorks")
	@RequireToken
	@PostMapping("/updateZxCtWorks")
	public ResponseEntity updateZxCtWorks(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.updateZxCtWorks(zxCtWorks);
	}

	@ApiOperation(value = "删除业主合同管理-业主合同台账-清单树(原表iecc_Works", notes = "删除业主合同管理-业主合同台账-清单树(原表iecc_Works")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxCtWorks")
	public ResponseEntity batchDeleteUpdateZxCtWorks(@RequestBody(required = false) List<ZxCtWorks> zxCtWorksList) {
		return zxCtWorksService.batchDeleteUpdateZxCtWorks(zxCtWorksList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "获取清单树", notes = "获取清单树")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/getZxCtWorksWorkNameTree")
	public ResponseEntity getZxCtWorksWorkNameTree(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.getZxCtWorksWorkNameTree(zxCtWorks);
	}

	@ApiOperation(value = "获取清单列表", notes = "获取清单列表")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/getZxCtWorksTreeList")
	public ResponseEntity getZxCtWorksTreeList(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.getZxCtWorksTreeList(zxCtWorks);
	}

	@ApiOperation(value = "获取清单计量列表", notes = "获取清单计量列表")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/getZxCtWorksBalanceList")
	public ResponseEntity getZxCtWorksBalanceList(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.getZxCtWorksBalanceList(zxCtWorks);
	}

	@ApiOperation(value = "获取清单计量编辑列表", notes = "获取清单计量列表")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/getZxCtWorksBalanceEditList")
	public ResponseEntity getZxCtWorksBalanceEditList(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.getZxCtWorksBalanceEditList(zxCtWorks);
	}

	@ApiOperation(value = "批量保存清单计量", notes = "获取清单计量列表")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/updateZxCtWorksBalanceList")
	public ResponseEntity updateZxCtWorksBalanceList(@RequestBody(required = false) List<ZxCtWorks> zxCtWorksList) {
		return zxCtWorksService.updateZxCtWorksBalanceList(zxCtWorksList);
	}

	@ApiOperation(value = "批量修改清单信息", notes = "批量修改清单信息")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/updateZxCtWorksList")
	public ResponseEntity updateZxCtWorksList(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.updateZxCtWorksList(zxCtWorks);
	}

	@ApiOperation(value = "批量编辑清单保存", notes = "批量编辑清单保存")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/saveZxCtWorksList")
	public ResponseEntity saveZxCtWorksList(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.saveZxCtWorksList(zxCtWorks);
	}

	@ApiOperation(value = "导入清单信息", notes = "导入清单信息")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/importZxCtWorks")
	public ResponseEntity importZxCtWorks(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.importZxCtWorks(zxCtWorks);
	}

	@ApiOperation(value = "变更-新增清单", notes = "变更-新增清单")
	@ApiImplicitParam(name = "zxCtWorks", value = "业主合同管理-业主合同台账-清单树(原表iecc_Worksentity", dataType = "ZxCtWorks")
	@RequireToken
	@PostMapping("/addZxCtWorksChange")
	public ResponseEntity addZxCtWorksChange(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.addZxCtWorksChange(zxCtWorks);
	}

	@ApiOperation(value = "根据orgID、workBookID删除清单列表", notes = "根据orgID、workBookID删除清单列表")
	@ApiImplicitParam(name = "zxCtWorks", value = "业主合同管理-业主合同台账-清单树", dataType = "ZxCtWorks")
	@RequireToken
	@PostMapping("/delZxCtWorksByOrgIDWorkBookID")
	public ResponseEntity delZxCtWorksByOrgIDWorkBookID(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.delZxCtWorksByOrgIDWorkBookID(zxCtWorks);
	}

	// ++++++++++++++++++++++++++++++分包合同管理tab页-分包清单与业主合同清单的关联start++++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "分包合同管理tab页-分包清单与业主合同清单关联右侧树", notes = "分包合同管理tab页-分包清单与业主合同清单关联右侧树")
	@ApiImplicitParam(name = "zxCtWorksList", value = "业主合同管理-业主合同台账-清单树(原表iecc_WorksList", dataType = "List<ZxCtWorks>")
	@RequireToken
	@PostMapping("/getZxCtWorksRightTree")
	public ResponseEntity getZxCtWorksRightTree(@RequestBody(required = false) ZxCtWorks zxCtWorks) {
		return zxCtWorksService.getZxCtWorksRightTree(zxCtWorks);
	}
	// ++++++++++++++++++++++++++++++分包合同管理tab页-分包清单与业主合同清单的关联end++++++++++++++++++++++++++++++++++++++++++++++++
}
