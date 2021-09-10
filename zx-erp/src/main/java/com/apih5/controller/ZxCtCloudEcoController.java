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
import com.apih5.mybatis.pojo.ZxCtCloudEco;
import com.apih5.service.ZxCtCloudEcoService;

@RestController
public class ZxCtCloudEcoController {

	@Autowired(required = true)
	private ZxCtCloudEcoService zxCtCloudEcoService;

	@ApiOperation(value = "查询云电商分包定标数据", notes = "查询云电商分包定标数据")
	@ApiImplicitParam(name = "zxCtCloudEco", value = "云电商分包定标数据entity", dataType = "ZxCtCloudEco")
	@RequireToken
	@PostMapping("/getZxCtCloudEcoList")
	public ResponseEntity getZxCtCloudEcoList(@RequestBody(required = false) ZxCtCloudEco zxCtCloudEco) {
		return zxCtCloudEcoService.getZxCtCloudEcoListByCondition(zxCtCloudEco);
	}

	@ApiOperation(value = "查询详情云电商分包定标数据", notes = "查询详情云电商分包定标数据")
	@ApiImplicitParam(name = "zxCtCloudEco", value = "云电商分包定标数据entity", dataType = "ZxCtCloudEco")
	@RequireToken
	@PostMapping("/getZxCtCloudEcoDetails")
	public ResponseEntity getZxCtCloudEcoDetails(@RequestBody(required = false) ZxCtCloudEco zxCtCloudEco) {
		return zxCtCloudEcoService.getZxCtCloudEcoDetails(zxCtCloudEco);
	}

	@ApiOperation(value = "新增云电商分包定标数据", notes = "新增云电商分包定标数据")
	@ApiImplicitParam(name = "zxCtCloudEco", value = "云电商分包定标数据entity", dataType = "ZxCtCloudEco")
	@RequireToken
	@PostMapping("/addZxCtCloudEco")
	public ResponseEntity addZxCtCloudEco(@RequestBody(required = false) ZxCtCloudEco zxCtCloudEco) {
		return zxCtCloudEcoService.saveZxCtCloudEco(zxCtCloudEco);
	}

	@ApiOperation(value = "更新云电商分包定标数据", notes = "更新云电商分包定标数据")
	@ApiImplicitParam(name = "zxCtCloudEco", value = "云电商分包定标数据entity", dataType = "ZxCtCloudEco")
	@RequireToken
	@PostMapping("/updateZxCtCloudEco")
	public ResponseEntity updateZxCtCloudEco(@RequestBody(required = false) ZxCtCloudEco zxCtCloudEco) {
		return zxCtCloudEcoService.updateZxCtCloudEco(zxCtCloudEco);
	}

	@ApiOperation(value = "删除云电商分包定标数据", notes = "删除云电商分包定标数据")
	@ApiImplicitParam(name = "zxCtCloudEcoList", value = "云电商分包定标数据List", dataType = "List<ZxCtCloudEco>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZxCtCloudEco")
	public ResponseEntity batchDeleteUpdateZxCtCloudEco(
			@RequestBody(required = false) List<ZxCtCloudEco> zxCtCloudEcoList) {
		return zxCtCloudEcoService.batchDeleteUpdateZxCtCloudEco(zxCtCloudEcoList);
	}

	@ApiOperation(value = "工具包批量导入新云电商分包定标数据", notes = "工具包批量导入云电商分包定标数据")
	@ApiImplicitParam(name = "zxCtCloudEco", value = "云电商分包定标数据entity", dataType = "ZxCtCloudEco")
	@RequireToken
	@PostMapping("/batchInputZxCtCloudEco")
	public ResponseEntity batchInputZxCtCloudEco(@RequestBody(required = false) ZxCtCloudEco zxCtCloudEco) {
		return zxCtCloudEcoService.batchInputZxCtCloudEco(zxCtCloudEco);
	}

	@ApiOperation(value = "工程施工合同评审获取云电商分包定标数据", notes = "工程施工合同评审获取云电商分包定标数据")
	@ApiImplicitParam(name = "zxCtCloudEco", value = "云电商分包定标数据entity", dataType = "ZxCtCloudEco")
	@RequireToken
	@PostMapping("/gcsgGetZxCtCloudEcoSelect")
	public ResponseEntity gcsgGetZxCtCloudEcoSelect(@RequestBody(required = false) ZxCtCloudEco zxCtCloudEco) {
		return zxCtCloudEcoService.gcsgGetZxCtCloudEcoSelect(zxCtCloudEco);
	}

	@ApiOperation(value = "测试更新云电商数据", notes = "测试更新云电商数据")
	@RequireToken
	@PostMapping("/synZxCtCloudEco")
	public ResponseEntity synZxCtCloudEco() {
		return zxCtCloudEcoService.synZxCtCloudEco();
	}



}
