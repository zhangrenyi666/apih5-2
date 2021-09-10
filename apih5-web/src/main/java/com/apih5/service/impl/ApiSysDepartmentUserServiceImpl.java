package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.zjoa.common.CachData;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.api.zjoa.service.ApiSysDepartmentUserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

/**
 * 中交微办公相关数据获取
 * 
 * @return www.apih5.com
 */
@Service("apiSysDepartmentUserService")
public class ApiSysDepartmentUserServiceImpl implements ApiSysDepartmentUserService {
	@Autowired
	private Environment env;  
	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private SysDepartmentMapper sysDepartmentMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Override
	public ResponseEntity getApiSysDepartmentList() {
		List<OADepartment> listDepartment = new ArrayList<OADepartment>();
		SysDepartment sysDepartmentSelect = new SysDepartment();
		List<SysDepartment> list = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
		for (int i = 0; i < list.size(); i++) {
			SysDepartment sysDepartment = list.get(i);
			OADepartment department = new OADepartment();
			department.setId(sysDepartment.getDepartmentId());
			department.setParentid(sysDepartment.getDepartmentParentId());
			department.setName(sysDepartment.getDepartmentName());
			department.setOrgId(sysDepartment.getDepartmentId());
			department.setOrder(i);
			listDepartment.add(department);
		}
		return repEntity.okList(listDepartment, listDepartment.size());
	}

	@Override
	public ResponseEntity getApiSysUserList(OADepartment oaDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		String orgId = oaDepartment.getOrgId();
		
		List<OAMember> listMember = new ArrayList<OAMember>();
		SysUser sysUserSelect = new SysUser();
		sysUserSelect.setDepartmentId(orgId);
		sysUserSelect.setAccountAppType("1");
		List<SysUser> sysUserList = sysUserMapper.selectBySysUserListByDepartment(sysUserSelect);
		if(sysUserList != null && sysUserList.size() > 0){
			for (SysUser sysUser:sysUserList) {
				OAMember member = new OAMember();
				member.setUserid(sysUser.getUserKey());
				member.setName(sysUser.getRealName());
				member.setOrgId(sysUser.getDepartmentId());
				listMember.add(member);
			}
		}
		return repEntity.okList(listMember, listMember.size());
	}

}
