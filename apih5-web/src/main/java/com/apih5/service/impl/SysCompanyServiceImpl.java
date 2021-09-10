package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysCompany;
import com.apih5.framework.api.sysdb.service.SysCompanyService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysCompanyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("sysCompanyService")
public class SysCompanyServiceImpl implements SysCompanyService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private SysCompanyMapper sysCompanyMapper;

	@Override
	public ResponseEntity getSysCompanyListByCondition(SysCompany sysCompany) {
		if (sysCompany == null) {
			sysCompany = new SysCompany();
		}
		// 分页查询
		PageHelper.startPage(sysCompany.getPage(), sysCompany.getLimit());
		// 获取数据
		List<SysCompany> sysCompanyList = sysCompanyMapper.selectBySysCompanyList(sysCompany);
		// 得到分页信息
		PageInfo<SysCompany> p = new PageInfo<>(sysCompanyList);

		return repEntity.okList(sysCompanyList, p.getTotal());
	}

	@Override
	public ResponseEntity saveSysCompany(SysCompany sysCompany) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		sysCompany.setCompanyId(UuidUtil.generate());
		sysCompany.setDelFlag("0");
		sysCompany.setCreateTime(new Date());
		sysCompany.setCreateUser(userKey);
		sysCompany.setModifyTime(new Date());
		sysCompany.setModifyUser(userKey);
		int flag = sysCompanyMapper.insert(sysCompany);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", sysCompany);
		}
	}

	@Override
	public ResponseEntity updateSysCompany(SysCompany sysCompany) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		int flag = 0;
		SysCompany dbsysCompany = sysCompanyMapper.selectByPrimaryKey(sysCompany.getCompanyId());
		if (dbsysCompany != null && StrUtil.isNotEmpty(dbsysCompany.getCompanyId())) {
			// 共通
			dbsysCompany.setModifyTime(new Date());
			dbsysCompany.setModifyUser(userKey);
			flag = sysCompanyMapper.updateByPrimaryKey(dbsysCompany);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", sysCompany);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateSysCompany(List<SysCompany> sysCompanyList) {
		int flag = 0;
		if (sysCompanyList != null && sysCompanyList.size() > 0) {
			flag = sysCompanyMapper.batchDeleteUpdateSysCompany(sysCompanyList);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", sysCompanyList);
		}
	}

	// 获取公司下拉列表
	@Override
	public ResponseEntity getSysCompanySelect(SysCompany sysCompany) {
		List<SysCompany> sysCompanyList = new ArrayList<SysCompany>();
		SysCompany sysCompany1 = new SysCompany();
		sysCompany1.setCompanyId("");
		sysCompany1.setCompanyName("全部");
		sysCompanyList.add(sysCompany1);
		List<SysCompany> sysCompanyList1 = sysCompanyMapper.getSysCompanySelect(sysCompany);
		if (sysCompanyList1 != null && sysCompanyList1.size() > 0) {
			sysCompanyList.addAll(sysCompanyList1);
		}
		return repEntity.okList(sysCompanyList, sysCompanyList.size());
	}

	@Override
	public SysCompany getSysCompanyByUserKey(SysCompany sysCompany) {
		return sysCompanyMapper.getSysCompanyByUserKey(sysCompany);
	}

	@Override
	public SysCompany getSysCompanyByCompanyId(String sysCompanyId) {
		return sysCompanyMapper.selectByPrimaryKey(sysCompanyId);
	}
}
