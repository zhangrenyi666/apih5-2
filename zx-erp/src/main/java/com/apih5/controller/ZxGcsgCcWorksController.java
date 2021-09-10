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
import com.apih5.mybatis.pojo.ZxGcsgCcWorks;
import com.apih5.service.ZxGcsgCcWorksService;
import com.apih5.utils.ZxErpUtil;
import cn.hutool.json.JSONObject;

@RestController
public class ZxGcsgCcWorksController {

	@Autowired(required = true)
	private ZxGcsgCcWorksService zxGcsgCcWorksService;

	@ApiOperation(value = "查询工程施工类合同管理清单表", notes = "查询工程施工类合同管理清单表")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCcWorksList")
	public ResponseEntity getZxGcsgCcWorksList(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.getZxGcsgCcWorksListByCondition(zxGcsgCcWorks);
	}

	@ApiOperation(value = "查询详情工程施工类合同管理清单表", notes = "查询详情工程施工类合同管理清单表")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCcWorksDetail")
	public ResponseEntity getZxGcsgCcWorksDetail(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.getZxGcsgCcWorksDetail(zxGcsgCcWorks);
	}

	@ApiOperation(value = "新增工程施工类合同管理清单表", notes = "新增工程施工类合同管理清单表")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/addZxGcsgCcWorks")
	public ResponseEntity addZxGcsgCcWorks(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.saveZxGcsgCcWorks(zxGcsgCcWorks);
	}

	@ApiOperation(value = "更新工程施工类合同管理清单表", notes = "更新工程施工类合同管理清单表")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/updateZxGcsgCcWorks")
	public ResponseEntity updateZxGcsgCcWorks(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.updateZxGcsgCcWorks(zxGcsgCcWorks);
	}

	@ApiOperation(value = "删除工程施工类合同管理清单表", notes = "删除工程施工类合同管理清单表")
	@ApiImplicitParam(name = "zxGcsgCcWorksList", value = "工程施工类合同管理清单表List", dataType = "List<ZxGcsgCcWorks>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxGcsgCcWorks")
	public ResponseEntity batchDeleteUpdateZxGcsgCcWorks(
			@RequestBody(required = false) List<ZxGcsgCcWorks> zxGcsgCcWorksList) {
		return zxGcsgCcWorksService.batchDeleteUpdateZxGcsgCcWorks(zxGcsgCcWorksList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "导入工程施工类合同管理清单信息(旧)", notes = "导入工程施工类合同管理清单信息(旧)")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	// @RequireToken
	@PostMapping("/importZxGcsgCcWorks")
	public ResponseEntity importZxGcsgCcWorks(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.importZxGcsgCcWorks(zxGcsgCcWorks);
	}

	@ApiOperation(value = "查询工程施工类合同管理清单左侧树形结构", notes = "查询工程施工类合同管理清单左侧树形结构")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCcWorksTree")
	public ResponseEntity getZxGcsgCcWorksTree(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.getZxGcsgCcWorksTree(zxGcsgCcWorks);
	}

	@ApiOperation(value = "合同管理点击清单获取数据[合计金额]", notes = "合同管理点击清单获取数据[合计金额]")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCcWorksListAmount")
	public ResponseEntity getZxGcsgCcWorksListAmount(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.getZxGcsgCcWorksListAmount(zxGcsgCcWorks);
	}

	@ApiOperation(value = "合同管理点击自动挂接工序", notes = "合同管理点击自动挂接工序")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/autoHookZxGcsgCcWorksProcess")
	public ResponseEntity autoHookZxGcsgCcWorksProcess(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.autoHookZxGcsgCcWorksProcess(zxGcsgCcWorks);
	}

	@ApiOperation(value = "合同管理选中清单挂接工序", notes = "合同管理选中清单挂接工序")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/manualHookZxGcsgCcWorksProcess")
	public ResponseEntity manualHookZxGcsgCcWorksProcess(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.manualHookZxGcsgCcWorksProcess(zxGcsgCcWorks);
	}

	@ApiOperation(value = "合同管理选中清单挂接计价规则", notes = "合同管理选中清单挂接计价规则")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/manualHookZxGcsgCcWorksRule")
	public ResponseEntity manualHookZxGcsgCcWorksRule(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.manualHookZxGcsgCcWorksRule(zxGcsgCcWorks);
	}

	@ApiOperation(value = "合同管理tab页清单工序挂接台账", notes = "合同管理tab页清单工序挂接台账")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCcWorksListProcess")
	public ResponseEntity getZxGcsgCcWorksListProcess(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.getZxGcsgCcWorksListProcess(zxGcsgCcWorks);
	}

	@ApiOperation(value = "补充协议归属主合同清单编号/清单名称下拉", notes = "补充协议归属主合同清单编号/清单名称下拉")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@RequireToken
	@PostMapping("/getZxGcsgCcWorksSelect")
	public ResponseEntity getZxGcsgCcWorksSelect(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		return zxGcsgCcWorksService.getZxGcsgCcWorksSelect(zxGcsgCcWorks);
	}

	@ApiOperation(value = "施工内容及计价规则表", notes = "施工内容及计价规则表")
	@ApiImplicitParam(name = "zxGcsgCcWorks", value = "工程施工类合同管理清单表entity", dataType = "ZxGcsgCcWorks")
	@PostMapping("/getZxGcsgCcWorksListProcessByTransfer")
	public Object getZxGcsgCcWorksListProcessByTransfer(@RequestBody(required = false) ZxGcsgCcWorks zxGcsgCcWorks) {
		// http://localhost:8080/web/ureport/preview?_u=p&url=http://192.168.1.106:8080/web/&ctContractId=1F0ASCPDJ199080012AC0000156CCFBE
		// String url = "http://localhost:18081/getZxGcsgCcWorksListProcessByUReport";
		// JSONObject jsonObject = new JSONObject();
		// jsonObject.set("ctContractId", zxGcsgCcWorks.getCtContractId());
		// String jsonStr = HttpUtil.sendPostJson(url, jsonObject.toString());
		// return JSONUtil.parseArray(jsonStr);
		String userId = "";// TokenUtils.getUserId(request);
		JSONObject jsonObject = new JSONObject(zxGcsgCcWorks);
		// jsonObject.set("ctContractId", zxGcsgCcWorks.getCtContractId());
		Object resultObject = ZxErpUtil.getApiContent(userId, "getZxGcsgCcWorksListProcessByUReport", jsonObject);
		JSONObject resultJSONObject = new JSONObject(resultObject);
		return resultJSONObject.getObj("data");
	}

}
