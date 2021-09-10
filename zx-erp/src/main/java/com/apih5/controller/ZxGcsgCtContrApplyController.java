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
import com.apih5.mybatis.pojo.ZxGcsgCtContrApply;
import com.apih5.service.ZxGcsgCtContrApplyService;
import com.apih5.utils.ZxErpUtil;
import cn.hutool.json.JSONObject;

@RestController
public class ZxGcsgCtContrApplyController {

	@Autowired(required = true)
	private ZxGcsgCtContrApplyService zxGcsgCtContrApplyService;

	@ApiOperation(value = "查询工程施工类合同评审补充协议表", notes = "查询工程施工类合同评审补充协议表")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyList")
	public ResponseEntity getZxGcsgCtContrApplyList(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.getZxGcsgCtContrApplyListByCondition(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "查询详情工程施工类合同评审补充协议表", notes = "查询详情工程施工类合同评审补充协议表")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/getZxGcsgCtContrApplyDetail")
	public ResponseEntity getZxGcsgCtContrApplyDetail(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.getZxGcsgCtContrApplyDetail(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "新增工程施工类合同评审补充协议表", notes = "新增工程施工类合同评审补充协议表")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/addZxGcsgCtContrApply")
	public ResponseEntity addZxGcsgCtContrApply(@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.saveZxGcsgCtContrApply(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "更新工程施工类合同评审补充协议表", notes = "更新工程施工类合同评审补充协议表")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/updateZxGcsgCtContrApply")
	public ResponseEntity updateZxGcsgCtContrApply(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.updateZxGcsgCtContrApply(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "删除工程施工类合同评审补充协议表", notes = "删除工程施工类合同评审补充协议表")
	@ApiImplicitParam(name = "zxGcsgCtContrApplyList", value = "工程施工类合同评审补充协议表List", dataType = "List<ZxGcsgCtContrApply>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxGcsgCtContrApply")
	public ResponseEntity batchDeleteUpdateZxGcsgCtContrApply(
			@RequestBody(required = false) List<ZxGcsgCtContrApply> zxGcsgCtContrApplyList) {
		return zxGcsgCtContrApplyService.batchDeleteUpdateZxGcsgCtContrApply(zxGcsgCtContrApplyList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@ApiOperation(value = "查询工程施工类合同评审", notes = "查询工程施工类合同评审")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psGetZxGcsgCtContrApplyList")
	public ResponseEntity psGetZxGcsgCtContrApplyList(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psGetZxGcsgCtContrApplyList(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "新增工程施工类合同评审并生成相关数据", notes = "新增工程施工类合同评审并生成相关数据")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psAddZxGcsgCtContrApply")
	public ResponseEntity psAddZxGcsgCtContrApply(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psAddZxGcsgCtContrApply(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "更新工程施工类合同评审并更新清单", notes = "更新工程施工类合同评审并更新清单")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psUpdateZxGcsgCtContrApply")
	public ResponseEntity psUpdateZxGcsgCtContrApply(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psUpdateZxGcsgCtContrApply(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "合同评审中合同编码序号获取", notes = "合同评审中合同编码序号获取")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psGetZxGcsgCtContrApplyContractNo")
	public ResponseEntity psGetZxGcsgCtContrApplyContractNo(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psGetZxGcsgCtContrApplyContractNo(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "工程施工类合同评审导出列表页[客户端流文件]", notes = "工程施工类合同评审导出列表页[客户端流文件]")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psExportZxGcsgCtContrApplyExcel")
	public void psExportZxGcsgCtContrApplyExcel(@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply,
			HttpServletResponse response) {
		zxGcsgCtContrApplyService.psExportZxGcsgCtContrApplyExcel(zxGcsgCtContrApply, response);
	}
	// +++++++++++++++++++++++++++++评审流程start+++++++++++++++++++++++++++++++++++++++++

	@ApiOperation(value = "合同评审发起评审申请check", notes = "合同评审发起评审申请check")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psCheckZxGcsgCtContrApplyBeforeFlow")
	public ResponseEntity psCheckZxGcsgCtContrApplyBeforeFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psCheckZxGcsgCtContrApplyBeforeFlow(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "合同评审发起评审申请check(apollo)", notes = "合同评审发起评审申请check(apollo)")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psCheckZxGcsgCtContrApplyInFlow")
	public ResponseEntity psCheckZxGcsgCtContrApplyInFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psCheckZxGcsgCtContrApplyInFlow(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "创建流程更新合同评审流程状态", notes = "创建流程更新合同评审流程状态")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psUpdateZxGcsgCtContrApplyFirstByFlow")
	public ResponseEntity psUpdateZxGcsgCtContrApplyFirstByFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psUpdateZxGcsgCtContrApplyFirstByFlow(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "继续走流程更新合同评审流程状态", notes = "继续走流程更新合同评审流程状态")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psUpdateZxGcsgCtContrApplySecondByFlow")
	public ResponseEntity psUpdateZxGcsgCtContrApplySecondByFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psUpdateZxGcsgCtContrApplySecondByFlow(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "合同评审获取评审申请数据详情", notes = "合同评审获取评审申请数据详情")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/psGetZxGcsgCtContrApplyDetailsByFlow")
	public ResponseEntity psGetZxGcsgCtContrApplyDetailsByFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.psGetZxGcsgCtContrApplyDetailsByFlow(zxGcsgCtContrApply);
	}
	// +++++++++++++++++++++++++++++评审流程end+++++++++++++++++++++++++++++++++++++++++

	@ApiOperation(value = "查询工程施工类合同补充协议", notes = "查询工程施工类合同补充协议")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxGetZxGcsgCtContrApplyList")
	public ResponseEntity bxGetZxGcsgCtContrApplyList(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxGetZxGcsgCtContrApplyList(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "新增工程施工类合同补充协议", notes = "新增工程施工类合同补充协议")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxAddZxGcsgCtContrApply")
	public ResponseEntity bxAddZxGcsgCtContrApply(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxAddZxGcsgCtContrApply(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "修改工程施工类合同补充协议", notes = "修改工程施工类合同补充协议")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxUpdateZxGcsgCtContrApply")
	public ResponseEntity bxUpdateZxGcsgCtContrApply(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxUpdateZxGcsgCtContrApply(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "补充协议中补充协议编码序号获取", notes = "补充协议中补充协议编码序号获取")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxGetZxGcsgCtContrApplyContractNo")
	public ResponseEntity bxGetZxGcsgCtContrApplyContractNo(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxGetZxGcsgCtContrApplyContractNo(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "工程施工类补充协议导出列表页[客户端流文件]", notes = "工程施工类补充协议导出列表页[客户端流文件]")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxExportZxGcsgCtContrApplyExcel")
	public void bxExportZxGcsgCtContrApplyExcel(@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply,
			HttpServletResponse response) {
		zxGcsgCtContrApplyService.bxExportZxGcsgCtContrApplyExcel(zxGcsgCtContrApply, response);
	}

	// +++++++++++++++++++++++++++++补充协议流程start+++++++++++++++++++++++++++++++++++++++++

	@ApiOperation(value = "补充协议发起评审申请check", notes = "补充协议发起评审申请check")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxCheckZxGcsgCtContrApplyBeforeFlow")
	public ResponseEntity bxCheckZxGcsgCtContrApplyBeforeFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxCheckZxGcsgCtContrApplyBeforeFlow(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "补充协议发起评审申请check(apollo)", notes = "补充协议发起评审申请check(apollo)")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxCheckZxGcsgCtContrApplyInFlow")
	public ResponseEntity bxCheckZxGcsgCtContrApplyInFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxCheckZxGcsgCtContrApplyInFlow(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "创建流程更新补充协议流程状态", notes = "创建流程更新补充协议流程状态")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxUpdateZxGcsgCtContrApplyFirstByFlow")
	public ResponseEntity bxUpdateZxGcsgCtContrApplyFirstByFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxUpdateZxGcsgCtContrApplyFirstByFlow(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "继续走流程更新补充协议流程状态", notes = "继续走流程更新补充协议流程状态")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxUpdateZxGcsgCtContrApplySecondByFlow")
	public ResponseEntity bxUpdateZxGcsgCtContrApplySecondByFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxUpdateZxGcsgCtContrApplySecondByFlow(zxGcsgCtContrApply);
	}

	@ApiOperation(value = "补充协议获取评审申请数据详情", notes = "补充协议获取评审申请数据详情")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@RequireToken
	@PostMapping("/bxGetZxGcsgCtContrApplyDetailsByFlow")
	public ResponseEntity bxGetZxGcsgCtContrApplyDetailsByFlow(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		return zxGcsgCtContrApplyService.bxGetZxGcsgCtContrApplyDetailsByFlow(zxGcsgCtContrApply);
	}
	// +++++++++++++++++++++++++++++补充协议流程end++++++++++++++++++++++++++++++++++++++++++

	@ApiOperation(value = "合同评审台账(工程类)", notes = "合同评审台账(工程类)")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@PostMapping("/psGetZxGcsgCtContrApplyListByTransfer")
	public Object psGetZxGcsgCtContrApplyListByTransfer(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		// String url = "http://localhost:18081/psGetZxGcsgCtContrApplyListByUReport";
		// JSONObject jsonObject = new JSONObject();
		// String jsonStr = HttpUtil.sendPostJson(url, jsonObject.toString());
		// List<ZxGcsgCtContrApply> list = JSONUtil.toList(JSONUtil.parseArray(jsonStr),
		// ZxGcsgCtContrApply.class);
		// return list;
		String userId = "";// TokenUtils.getUserId(request);
		JSONObject jsonObject = new JSONObject(zxGcsgCtContrApply);
		Object resultObject = ZxErpUtil.getApiContent(userId, "psGetZxGcsgCtContrApplyListByUReport", jsonObject);
		JSONObject resultJSONObject = new JSONObject(resultObject);
		return resultJSONObject.getObj("data");
	}

	@ApiOperation(value = "工程合同(招标)统计表", notes = "工程合同(招标)统计表")
	@ApiImplicitParam(name = "zxGcsgCtContrApply", value = "工程施工类合同评审补充协议表entity", dataType = "ZxGcsgCtContrApply")
	@PostMapping("/psGetZxGcsgCtContrApplyBiddingByTransfer")
	public Object psGetZxGcsgCtContrApplyBiddingByTransfer(
			@RequestBody(required = false) ZxGcsgCtContrApply zxGcsgCtContrApply) {
		String userId = "";// TokenUtils.getUserId(request);
		JSONObject jsonObject = new JSONObject(zxGcsgCtContrApply);
		Object resultObject = ZxErpUtil.getApiContent(userId, "psGetZxGcsgCtContrApplyBiddingByUReport", jsonObject);
		JSONObject resultJSONObject = new JSONObject(resultObject);
		return resultJSONObject.getObj("data");
	}

}
