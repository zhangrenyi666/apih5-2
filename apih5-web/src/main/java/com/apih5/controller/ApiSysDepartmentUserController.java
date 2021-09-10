package com.apih5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.service.ApiSysDepartmentUserService;
import com.apih5.framework.api.zjoa.service.ZjOaApiService;
import com.apih5.framework.entity.ResponseEntity;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 * 用户HUI页面的接口功能，涉及到部门、人员
 * 
 * @author www.apih5.com
 *
 */
@RestController
public class ApiSysDepartmentUserController {
	@Autowired
	private ApiSysDepartmentUserService apiSysDepartmentUserService;

	/**
	 * 部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping(path = "/apiSysDepartmentListByNew")
	public ResponseEntity apiSysDepartmentListByNew() {
		return apiSysDepartmentUserService.getApiSysDepartmentList();
	}

	/**
	 * 部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping(path = "/apiSysUserListByNew")
	public ResponseEntity apiSysUserListByNew(@RequestBody(required = false) OADepartment oaDepartment) {
		return apiSysDepartmentUserService.getApiSysUserList(oaDepartment);
	}
}
