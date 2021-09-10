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
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSys;
import com.apih5.service.ZxGcsgCtPriceSysService;
import com.apih5.utils.ZxErpUtil;
import cn.hutool.json.JSONObject;

@RestController
public class ZxGcsgCtPriceSysController {

	@Autowired(required = true)
	private ZxGcsgCtPriceSysService zxGcsgCtPriceSysService;

	@ApiOperation(value = "查询合同单价分析表", notes = "查询合同单价分析表")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/getZxGcsgCtPriceSysList")
	public ResponseEntity getZxGcsgCtPriceSysList(@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.getZxGcsgCtPriceSysListByCondition(zxGcsgCtPriceSys);
	}

	@ApiOperation(value = "查询详情合同单价分析表", notes = "查询详情合同单价分析表")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/getZxGcsgCtPriceSysDetail")
	public ResponseEntity getZxGcsgCtPriceSysDetail(@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.getZxGcsgCtPriceSysDetail(zxGcsgCtPriceSys);
	}

	@ApiOperation(value = "新增合同单价分析表", notes = "新增合同单价分析表")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/addZxGcsgCtPriceSys")
	public ResponseEntity addZxGcsgCtPriceSys(@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.saveZxGcsgCtPriceSys(zxGcsgCtPriceSys);
	}

	@ApiOperation(value = "更新合同单价分析表", notes = "更新合同单价分析表")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/updateZxGcsgCtPriceSys")
	public ResponseEntity updateZxGcsgCtPriceSys(@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.updateZxGcsgCtPriceSys(zxGcsgCtPriceSys);
	}

	@ApiOperation(value = "删除合同单价分析表", notes = "删除合同单价分析表")
	@ApiImplicitParam(name = "zxGcsgCtPriceSysList", value = "合同单价分析表List", dataType = "List<ZxGcsgCtPriceSys>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxGcsgCtPriceSys")
	public ResponseEntity batchDeleteUpdateZxGcsgCtPriceSys(
			@RequestBody(required = false) List<ZxGcsgCtPriceSys> zxGcsgCtPriceSysList) {
		return zxGcsgCtPriceSysService.batchDeleteUpdateZxGcsgCtPriceSys(zxGcsgCtPriceSysList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "合同评审tab页合同单价分析同步挂接数据", notes = "合同评审tab页合同单价分析同步挂接数据")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/psSyncHookDataZxGcsgCtPriceSys")
	public ResponseEntity psSyncHookDataZxGcsgCtPriceSys(
			@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.psSyncHookDataZxGcsgCtPriceSys(zxGcsgCtPriceSys);
	}

	@ApiOperation(value = "合同评审/管理/补充协议tab页合同单价分析查看详情", notes = "合同评审/管理/补充协议tab页合同单价分析查看详情")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/psglbxGetZxGcsgCtPriceSysDetails")
	public ResponseEntity psglbxGetZxGcsgCtPriceSysDetails(
			@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.psglbxGetZxGcsgCtPriceSysDetails(zxGcsgCtPriceSys);
	}

	@ApiOperation(value = "合同评审/管理/补充协议tab页合同单价分析修改后保存", notes = "合同评审tab页合同单价分析查看详情")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/psglbxUpdateZxGcsgCtPriceSysAndItem")
	public ResponseEntity psglbxUpdateZxGcsgCtPriceSysAndItem(
			@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.psglbxUpdateZxGcsgCtPriceSysAndItem(zxGcsgCtPriceSys);
	}

	// ================================================================================================
	@ApiOperation(value = "合同管理tab页合同单价分析同步挂接数据", notes = "合同管理tab页合同单价分析同步挂接数据")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/glSyncHookDataZxGcsgCtPriceSys")
	public ResponseEntity glSyncHookDataZxGcsgCtPriceSys(
			@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.glSyncHookDataZxGcsgCtPriceSys(zxGcsgCtPriceSys);
	}

	// ==================================================================================================
	@ApiOperation(value = "补充协议tab页合同单价分析同步挂接数据", notes = "补充协议tab页合同单价分析同步挂接数据")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@RequireToken
	@PostMapping("/bxSyncHookDataZxGcsgCtPriceSys")
	public ResponseEntity bxSyncHookDataZxGcsgCtPriceSys(
			@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		return zxGcsgCtPriceSysService.bxSyncHookDataZxGcsgCtPriceSys(zxGcsgCtPriceSys);
	}

	@ApiOperation(value = "合同单价分析表", notes = "合同单价分析表")
	@ApiImplicitParam(name = "zxGcsgCtPriceSys", value = "合同单价分析表entity", dataType = "ZxGcsgCtPriceSys")
	@PostMapping("/getZxGcsgCtPriceSysAndItemByTransfer")
	public Object getZxGcsgCtPriceSysAndItemByTransfer(
			@RequestBody(required = false) ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		// http://localhost:8080/web/ureport/preview?_u=p&url=http://192.168.1.106:8080/web/&ctContractId=1ETJILA9G00T080012AC0000C78E557E
		String userId = "";// TokenUtils.getUserId(request);
		JSONObject jsonObject = new JSONObject(zxGcsgCtPriceSys);
		// jsonObject.set("ctContractId", zxGcsgCcWorks.getCtContractId());
		Object resultObject = ZxErpUtil.getApiContent(userId, "getZxGcsgCtPriceSysAndItemByUReport", jsonObject);
		JSONObject resultJSONObject = new JSONObject(resultObject);
		return resultJSONObject.getObj("data");
	}

}
