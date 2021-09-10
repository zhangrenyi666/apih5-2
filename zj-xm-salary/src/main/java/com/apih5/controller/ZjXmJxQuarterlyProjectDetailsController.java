package com.apih5.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyProjectDetails;
import com.apih5.service.ZjXmJxQuarterlyProjectDetailsService;

@RestController
public class ZjXmJxQuarterlyProjectDetailsController {

	@Autowired(required = true)
	private ZjXmJxQuarterlyProjectDetailsService zjXmJxQuarterlyProjectDetailsService;

	@ApiOperation(value = "查询季度考核项目指标库详情", notes = "查询季度考核项目指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyProjectDetailsList")
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsList(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.getZjXmJxQuarterlyProjectDetailsListByCondition(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "查询详情季度考核项目指标库详情", notes = "查询详情季度考核项目指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	@RequireToken
	@PostMapping("/getZjXmJxQuarterlyProjectDetailsDetail")
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsDetail(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.getZjXmJxQuarterlyProjectDetailsDetail(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "新增季度考核项目指标库详情", notes = "新增季度考核项目指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	@RequireToken
	@PostMapping("/addZjXmJxQuarterlyProjectDetails")
	public ResponseEntity addZjXmJxQuarterlyProjectDetails(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService.saveZjXmJxQuarterlyProjectDetails(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "更新季度考核项目指标库详情", notes = "更新季度考核项目指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	@RequireToken
	@PostMapping("/updateZjXmJxQuarterlyProjectDetails")
	public ResponseEntity updateZjXmJxQuarterlyProjectDetails(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService.updateZjXmJxQuarterlyProjectDetails(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "删除季度考核项目指标库详情", notes = "删除季度考核项目指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetailsList", value = "季度考核项目指标库详情List", dataType = "List<ZjXmJxQuarterlyProjectDetails>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxQuarterlyProjectDetails")
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyProjectDetails(
			@RequestBody(required = false) List<ZjXmJxQuarterlyProjectDetails> zjXmJxQuarterlyProjectDetailsList) {
		return zjXmJxQuarterlyProjectDetailsService
				.batchDeleteUpdateZjXmJxQuarterlyProjectDetails(zjXmJxQuarterlyProjectDetailsList);
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
	@ApiOperation(value = "判断是否显示确认按钮", notes = "判断是否显示确认按钮")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	@RequireToken
	@PostMapping("/checkZjXmJxQuarterlyProjectDetailsConfirmStatus")
	public ResponseEntity checkZjXmJxQuarterlyProjectDetailsConfirmStatus(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.checkZjXmJxQuarterlyProjectDetailsConfirmStatus(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "批量确认季度考核项目指标库详情", notes = "批量确认季度考核项目指标库详情")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetailsList", value = "季度考核项目指标库详情List", dataType = "List<ZjXmJxQuarterlyProjectDetails>")
	@RequireToken
	@PostMapping("/batchConfirmZjXmJxQuarterlyProjectDetails")
	public ResponseEntity batchConfirmZjXmJxQuarterlyProjectDetails(
			@RequestBody(required = false) List<ZjXmJxQuarterlyProjectDetails> zjXmJxQuarterlyProjectDetailsList) {
		return zjXmJxQuarterlyProjectDetailsService
				.batchConfirmZjXmJxQuarterlyProjectDetails(zjXmJxQuarterlyProjectDetailsList);
	}

	@ApiOperation(value = "根据考核责任人获取项目列表及各个项目题库", notes = "根据考核责任人获取项目列表及各个项目题库")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	// @RequireToken
	@PostMapping("/getZjXmJxQuarterlyProjectDetailsByPersonLiable")
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsByPersonLiable(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.getZjXmJxQuarterlyProjectDetailsByPersonLiable(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "判断是否显示保存得分按钮", notes = "判断是否显示保存得分按钮")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	@RequireToken
	@PostMapping("/checkZjXmJxQuarterlyProjectDetailsActualScore")
	public ResponseEntity checkZjXmJxQuarterlyProjectDetailsActualScore(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.checkZjXmJxQuarterlyProjectDetailsActualScore(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "考核责任人提交打分[列转行保存]", notes = "考核责任人提交打分[列转行保存]")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	// @RequireToken
	@PostMapping("/submitZjXmJxQuarterlyProjectDetailsByPersonLiable")
	public ResponseEntity submitZjXmJxQuarterlyProjectDetailsByPersonLiable(
			@RequestBody(required = false) List<Map<String, Object>> mapList) {
		return zjXmJxQuarterlyProjectDetailsService.submitZjXmJxQuarterlyProjectDetailsByPersonLiable(mapList);
	}

	@ApiOperation(value = "各个部门的统计数据动态表头", notes = "各个部门的统计数据动态表头")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	// @RequireToken
	@PostMapping("/getZjXmJxQuarterlyProjectDetailsDeptColumn")
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsDeptColumn(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.getZjXmJxQuarterlyProjectDetailsDeptColumn(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "各个季度考核各个类型的权重的动态表头", notes = "各个季度考核各个类型的权重的动态表头")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	// @RequireToken
	@PostMapping("/getZjXmJxQuarterlyProjectDetailsWeightColumn")
	public ResponseEntity getZjXmJxQuarterlyProjectDetailsWeightColumn(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.getZjXmJxQuarterlyProjectDetailsWeightColumn(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "各个部门的统计数据(非收尾/收尾项目)", notes = "各个部门的统计数据(非收尾/收尾项目)")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	// @RequireToken
	@PostMapping("/countZjXmJxQuarterlyProjectDetailsByDept")
	public ResponseEntity countZjXmJxQuarterlyProjectDetailsByDept(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.countZjXmJxQuarterlyProjectDetailsByDept(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "各个季度考核各个类型的权重的统计数据(非收尾/收尾项目)", notes = "各个部门的统计数据(非收尾/收尾项目)")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	// @RequireToken
	@PostMapping("/countZjXmJxQuarterlyProjectDetailsByWeight")
	public ResponseEntity countZjXmJxQuarterlyProjectDetailsByWeight(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails) {
		return zjXmJxQuarterlyProjectDetailsService
				.countZjXmJxQuarterlyProjectDetailsByWeight(zjXmJxQuarterlyProjectDetails);
	}

	@ApiOperation(value = "各个部门的统计数据[客户端流文件]", notes = "各个部门的统计数据[客户端流文件]")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	// @RequireToken
	@PostMapping("/exportZjXmJxQuarterlyProjectDetailsDeptExcel")
	public void exportZjXmJxQuarterlyProjectDetailsDeptExcel(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails,
			HttpServletResponse response) {
		zjXmJxQuarterlyProjectDetailsService.exportZjXmJxQuarterlyProjectDetailsDeptExcel(zjXmJxQuarterlyProjectDetails,
				response);
	}

	@ApiOperation(value = "各个季度考核各个类型的权重统计数据[客户端流文件]", notes = "各个季度考核各个类型的权重统计数据[客户端流文件]")
	@ApiImplicitParam(name = "zjXmJxQuarterlyProjectDetails", value = "季度考核项目指标库详情entity", dataType = "ZjXmJxQuarterlyProjectDetails")
	// @RequireToken
	@PostMapping("/exportZjXmJxQuarterlyProjectDetailsWeightExcel")
	public void exportZjXmJxQuarterlyProjectDetailsWeightExcel(
			@RequestBody(required = false) ZjXmJxQuarterlyProjectDetails zjXmJxQuarterlyProjectDetails,
			HttpServletResponse response) {
		zjXmJxQuarterlyProjectDetailsService
				.exportZjXmJxQuarterlyProjectDetailsWeightExcel(zjXmJxQuarterlyProjectDetails, response);
	}

}
