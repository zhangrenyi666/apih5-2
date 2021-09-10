package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysUserCompany;
import com.apih5.framework.api.sysdb.service.SysUserCompanyService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysUserCompanyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("sysUserCompanyService")
public class SysUserCompanyServiceImpl implements SysUserCompanyService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private SysUserCompanyMapper sysUserCompanyMapper;

	@Override
	public ResponseEntity getSysUserCompanyListByCondition(SysUserCompany sysUserCompany) {
		if (sysUserCompany == null) {
			sysUserCompany = new SysUserCompany();
		}
		// 分页查询
		PageHelper.startPage(sysUserCompany.getPage(), sysUserCompany.getLimit());
		// 获取数据
		List<SysUserCompany> sysUserCompanyList = sysUserCompanyMapper.selectBySysUserCompanyList(sysUserCompany);
		// 得到分页信息
		PageInfo<SysUserCompany> p = new PageInfo<>(sysUserCompanyList);

		return repEntity.okList(sysUserCompanyList, p.getTotal());
	}

	@Override
	public ResponseEntity saveSysUserCompany(SysUserCompany sysUserCompany) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		sysUserCompany.setUserCompanyId(UuidUtil.generate());
		sysUserCompany.setDelFlag("0");
		sysUserCompany.setCreateTime(new Date());
		sysUserCompany.setCreateUser(userKey);
		sysUserCompany.setModifyTime(new Date());
		sysUserCompany.setModifyUser(userKey);
		int flag = sysUserCompanyMapper.insert(sysUserCompany);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", sysUserCompany);
		}
	}

	@Override
	public ResponseEntity updateSysUserCompany(SysUserCompany sysUserCompany) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		int flag = 0;
		SysUserCompany dbsysUserCompany = sysUserCompanyMapper.selectByPrimaryKey(sysUserCompany.getUserCompanyId());
		if (dbsysUserCompany != null && StrUtil.isNotEmpty(dbsysUserCompany.getUserCompanyId())) {
			// 共通
			dbsysUserCompany.setModifyTime(new Date());
			dbsysUserCompany.setModifyUser(userKey);
			flag = sysUserCompanyMapper.updateByPrimaryKey(dbsysUserCompany);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", sysUserCompany);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateSysUserCompany(List<SysUserCompany> sysUserCompanyList) {
		int flag = 0;
		if (sysUserCompanyList != null && sysUserCompanyList.size() > 0) {
			flag = sysUserCompanyMapper.batchDeleteUpdateSysUserCompany(sysUserCompanyList);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", sysUserCompanyList);
		}
	}

	@Override
	public int addSysUserCompanyCommon(SysUserCompany sysUserCompany) {
		sysUserCompany.setUserCompanyId(UuidUtil.generate());
		sysUserCompany.setDelFlag("0");
		sysUserCompany.setCreateTime(new Date());
		sysUserCompany.setCreateUser(sysUserCompany.getUserKey());
		sysUserCompany.setModifyTime(new Date());
		sysUserCompany.setModifyUser(sysUserCompany.getUserKey());
		return sysUserCompanyMapper.insert(sysUserCompany);
	}
}
