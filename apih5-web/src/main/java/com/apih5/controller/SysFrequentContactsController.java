package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysFrequentContacts;
import com.apih5.service.SysFrequentContactsService;

@RestController
public class SysFrequentContactsController {

	@Autowired(required = true)
	private SysFrequentContactsService sysFrequentContactsService;

	@ApiOperation(value = "查询常用联系人", notes = "查询常用联系人")
	@ApiImplicitParam(name = "sysFrequentContacts", value = "常用联系人entity", dataType = "SysFrequentContacts")
	@RequireToken
	@PostMapping("/getSysFrequentContactsList")
	public ResponseEntity getSysFrequentContactsList(
			@RequestBody(required = false) SysFrequentContacts sysFrequentContacts) {
		return sysFrequentContactsService.getSysFrequentContactsListByCondition(sysFrequentContacts);
	}

	@ApiOperation(value = "新增常用联系人", notes = "新增常用联系人")
	@ApiImplicitParam(name = "sysFrequentContacts", value = "常用联系人entity", dataType = "SysFrequentContacts")
	@RequireToken
	@PostMapping("/addSysFrequentContacts")
	public ResponseEntity addSysFrequentContacts(
			@RequestBody(required = false) SysFrequentContacts sysFrequentContacts) {
		return sysFrequentContactsService.saveSysFrequentContacts(sysFrequentContacts);
	}

	@ApiOperation(value = "更新常用联系人", notes = "更新常用联系人")
	@ApiImplicitParam(name = "sysFrequentContacts", value = "常用联系人entity", dataType = "SysFrequentContacts")
	@RequireToken
	@PostMapping("/updateSysFrequentContacts")
	public ResponseEntity updateSysFrequentContacts(
			@RequestBody(required = false) SysFrequentContacts sysFrequentContacts) {
		return sysFrequentContactsService.updateSysFrequentContacts(sysFrequentContacts);
	}

	@ApiOperation(value = "删除常用联系人", notes = "删除常用联系人")
	@ApiImplicitParam(name = "sysFrequentContactsList", value = "常用联系人List", dataType = "List<SysFrequentContacts>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateSysFrequentContacts")
	public ResponseEntity batchDeleteUpdateSysFrequentContacts(
			@RequestBody(required = false) List<SysFrequentContacts> sysFrequentContactsList) {
		return sysFrequentContactsService.batchDeleteUpdateSysFrequentContacts(sysFrequentContactsList);
	}

	// +++++++++++++++++++++++++++++++++++++++++++++业务接口++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@ApiOperation(value = "app获取常用联系人列表", notes = "app获取常用联系人列表")
	@ApiImplicitParam(name = "sysFrequentContacts", value = "常用联系人entity", dataType = "SysFrequentContacts")
	@RequireToken
	@PostMapping("/appGetSysFrequentContactsList")
	public ResponseEntity appGetSysFrequentContactsList(
			@RequestBody(required = false) SysFrequentContacts sysFrequentContacts) {
		return sysFrequentContactsService.appGetSysFrequentContactsList(sysFrequentContacts);
	}

	@ApiOperation(value = "app移除常用联系人(批量)", notes = "app移除常用联系人(批量)")
	@ApiImplicitParam(name = "sysFrequentContactsList", value = "常用联系人List", dataType = "List<SysFrequentContacts>")
	@RequireToken
	@PostMapping("/appRemoveSysFrequentContacts")
	public ResponseEntity appRemoveSysFrequentContacts(
			@RequestBody(required = false) List<SysFrequentContacts> sysFrequentContactsList) {
		return sysFrequentContactsService.appRemoveSysFrequentContacts(sysFrequentContactsList);
	}

	@ApiOperation(value = "app添加常用联系人(批量)", notes = "app添加常用联系人(批量)")
	@ApiImplicitParam(name = "sysFrequentContactsList", value = "常用联系人List", dataType = "List<SysFrequentContacts>")
	@RequireToken
	@PostMapping("/appAddSysFrequentContacts")
	public ResponseEntity appAddSysFrequentContacts(
			@RequestBody(required = false) List<SysFrequentContacts> sysFrequentContactsList) {
		return sysFrequentContactsService.appAddSysFrequentContacts(sysFrequentContactsList);
	}

	/**
	 * 每天执行一次,每次统计当天一天内的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "定时统计将流程表的记录添加到常用联系人表", notes = "定时统计将流程表的记录添加到常用联系人表")
	@GetMapping("/jobForAddSysFrequentContacts")
	public ResponseEntity jobForAddSysFrequentContacts() throws Exception {
		return sysFrequentContactsService.jobForAddSysFrequentContacts();
	}
}
