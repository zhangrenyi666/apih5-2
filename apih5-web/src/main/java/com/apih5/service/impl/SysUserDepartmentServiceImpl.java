package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.api.sysdb.service.SysUserDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysUserDepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("sysUserDepartmentService")
public class SysUserDepartmentServiceImpl implements SysUserDepartmentService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private SysUserDepartmentMapper sysUserDepartmentMapper;

	@Override
	public ResponseEntity getSysUserDepartmentListByCondition(SysUserDepartment sysUserDepartment) {
		if (sysUserDepartment == null) {
			sysUserDepartment = new SysUserDepartment();
		}
		// 分页查询
		PageHelper.startPage(sysUserDepartment.getPage(), sysUserDepartment.getLimit());
		// 获取数据
		List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper
				.selectBySysUserDepartmentList(sysUserDepartment);
		// 得到分页信息
		PageInfo<SysUserDepartment> p = new PageInfo<>(sysUserDepartmentList);

		return repEntity.okList(sysUserDepartmentList, p.getTotal());
	}

	@Override
	public ResponseEntity saveSysUserDepartment(SysUserDepartment sysUserDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		sysUserDepartment.setUserDepartmentId(UuidUtil.generate());
		sysUserDepartment.setCreateUserInfo(userKey, realName);
		int flag = sysUserDepartmentMapper.insert(sysUserDepartment);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", sysUserDepartment);
		}
	}

	@Override
	public ResponseEntity updateSysUserDepartment(SysUserDepartment sysUserDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		int flag = 0;
		SysUserDepartment dbsysUserDepartment = sysUserDepartmentMapper
				.selectByPrimaryKey(sysUserDepartment.getUserDepartmentId());
		if (dbsysUserDepartment != null && StrUtil.isNotEmpty(dbsysUserDepartment.getUserDepartmentId())) {
			// 共通
			dbsysUserDepartment.setModifyTime(new Date());
			dbsysUserDepartment.setModifyUser(userKey);
			flag = sysUserDepartmentMapper.updateByPrimaryKey(dbsysUserDepartment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", sysUserDepartment);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateSysUserDepartment(List<SysUserDepartment> sysUserDepartmentList) {
		int flag = 0;
		if (sysUserDepartmentList != null && sysUserDepartmentList.size() > 0) {
			flag = sysUserDepartmentMapper.batchDeleteUpdateSysUserDepartment(sysUserDepartmentList);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", sysUserDepartmentList);
		}
	}

	@Override
	public SysUserDepartment getRelationByUserKeyAndDepId(SysUserDepartment sysUserDepartment) {
		return sysUserDepartmentMapper.getRelationByUserKeyAndDepId(sysUserDepartment);
	}

	@Override
	public int addSysUserDepartmentCommon(SysUserDepartment sysUserDepartment) {
		String userKey = "";
		if(StrUtil.isNotEmpty(sysUserDepartment.getCreateUser())) {
			userKey = sysUserDepartment.getCreateUser();
		}
		String realName = "";
		if(StrUtil.isNotEmpty(sysUserDepartment.getCreateUserName())) {
			realName = sysUserDepartment.getCreateUserName();
		}
		sysUserDepartment.setCreateUserInfo(userKey, realName);
		return sysUserDepartmentMapper.insert(sysUserDepartment);
	}
}
