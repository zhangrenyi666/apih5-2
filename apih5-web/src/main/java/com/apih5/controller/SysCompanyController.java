package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.SysCompany;
import com.apih5.framework.api.sysdb.service.SysCompanyService;
import com.apih5.framework.entity.ResponseEntity;

@RestController
public class SysCompanyController {

	@Autowired(required = true)
	private SysCompanyService sysCompanyService;

	@ApiOperation(value = "查询公司", notes = "查询公司")
	@ApiImplicitParam(name = "sysCompany", value = "公司entity", dataType = "SysCompany")
	@RequireToken
	@PostMapping("/getSysCompanyList")
	public ResponseEntity getSysCompanyList(@RequestBody(required = false) SysCompany sysCompany) {
		return sysCompanyService.getSysCompanyListByCondition(sysCompany);
	}

	@ApiOperation(value = "新增公司", notes = "新增公司")
	@ApiImplicitParam(name = "sysCompany", value = "公司entity", dataType = "SysCompany")
	@RequireToken
	@PostMapping("/addSysCompany")
	public ResponseEntity addSysCompany(@RequestBody(required = false) SysCompany sysCompany) {
		return sysCompanyService.saveSysCompany(sysCompany);
	}

	@ApiOperation(value = "更新公司", notes = "更新公司")
	@ApiImplicitParam(name = "sysCompany", value = "公司entity", dataType = "SysCompany")
	@RequireToken
	@PostMapping("/updateSysCompany")
	public ResponseEntity updateSysCompany(@RequestBody(required = false) SysCompany sysCompany) {
		return sysCompanyService.updateSysCompany(sysCompany);
	}

	@ApiOperation(value = "删除公司", notes = "删除公司")
	@ApiImplicitParam(name = "sysCompanyList", value = "公司List", dataType = "List<SysCompany>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateSysCompany")
	public ResponseEntity batchDeleteUpdateSysCompany(@RequestBody(required = false) List<SysCompany> sysCompanyList) {
		return sysCompanyService.batchDeleteUpdateSysCompany(sysCompanyList);
	}

	/**
	 * pc端问询列表查询条件下拉列表
	 * 
	 * @param zjBaseCompany
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端问询列表查询条件下拉列表", notes = "pc端问询列表查询条件下拉列表")
	@ApiImplicitParam(name = "sysCompany", value = "公司entity", dataType = "SysCompany")
	@RequireToken
	@RequestMapping(value = "/getSysCompanySelect")
	public ResponseEntity getSysCompanySelect(@RequestBody(required = false) SysCompany sysCompany) {
		return sysCompanyService.getSysCompanySelect(sysCompany);

	}

}
