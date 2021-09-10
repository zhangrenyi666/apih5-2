package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmSalaryUserDepartmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryUserDepartment;
import com.apih5.service.ZjXmSalaryUserDepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryUserDepartmentService")
public class ZjXmSalaryUserDepartmentServiceImpl implements ZjXmSalaryUserDepartmentService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmSalaryUserDepartmentMapper zjXmSalaryUserDepartmentMapper;

	@Override
	public ResponseEntity getZjXmSalaryUserDepartmentListByCondition(
			ZjXmSalaryUserDepartment zjXmSalaryUserDepartment) {
		if (zjXmSalaryUserDepartment == null) {
			zjXmSalaryUserDepartment = new ZjXmSalaryUserDepartment();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryUserDepartment.getPage(), zjXmSalaryUserDepartment.getLimit());
		// 获取数据
		List<ZjXmSalaryUserDepartment> zjXmSalaryUserDepartmentList = zjXmSalaryUserDepartmentMapper
				.selectByZjXmSalaryUserDepartmentList(zjXmSalaryUserDepartment);
		// 得到分页信息
		PageInfo<ZjXmSalaryUserDepartment> p = new PageInfo<>(zjXmSalaryUserDepartmentList);

		return repEntity.okList(zjXmSalaryUserDepartmentList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryUserDepartmentDetails(ZjXmSalaryUserDepartment zjXmSalaryUserDepartment) {
		if (zjXmSalaryUserDepartment == null) {
			zjXmSalaryUserDepartment = new ZjXmSalaryUserDepartment();
		}
		// 获取数据
		ZjXmSalaryUserDepartment dbZjXmSalaryUserDepartment = zjXmSalaryUserDepartmentMapper
				.selectByPrimaryKey(zjXmSalaryUserDepartment.getUserDepartmentId());
		// 数据存在
		if (dbZjXmSalaryUserDepartment != null) {
			return repEntity.ok(dbZjXmSalaryUserDepartment);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryUserDepartment(ZjXmSalaryUserDepartment zjXmSalaryUserDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryUserDepartment.setUserDepartmentId(UuidUtil.generate());
		zjXmSalaryUserDepartment.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryUserDepartmentMapper.insert(zjXmSalaryUserDepartment);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryUserDepartment);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryUserDepartment(ZjXmSalaryUserDepartment zjXmSalaryUserDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryUserDepartment dbzjXmSalaryUserDepartment = zjXmSalaryUserDepartmentMapper
				.selectByPrimaryKey(zjXmSalaryUserDepartment.getUserDepartmentId());
		if (dbzjXmSalaryUserDepartment != null
				&& StrUtil.isNotEmpty(dbzjXmSalaryUserDepartment.getUserDepartmentId())) {
			// 用户扩展表id
			dbzjXmSalaryUserDepartment.setExtensionId(zjXmSalaryUserDepartment.getExtensionId());
			// 用户id(非必存)
			dbzjXmSalaryUserDepartment.setUserKey(zjXmSalaryUserDepartment.getUserKey());
			// 部门ID
			dbzjXmSalaryUserDepartment.setDepartmentId(zjXmSalaryUserDepartment.getDepartmentId());
			// 备注
			dbzjXmSalaryUserDepartment.setRemarks(zjXmSalaryUserDepartment.getRemarks());
			// 排序
			dbzjXmSalaryUserDepartment.setSort(zjXmSalaryUserDepartment.getSort());
			// 共通
			dbzjXmSalaryUserDepartment.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryUserDepartmentMapper.updateByPrimaryKey(dbzjXmSalaryUserDepartment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryUserDepartment);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryUserDepartment(
			List<ZjXmSalaryUserDepartment> zjXmSalaryUserDepartmentList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryUserDepartmentList != null && zjXmSalaryUserDepartmentList.size() > 0) {
			ZjXmSalaryUserDepartment zjXmSalaryUserDepartment = new ZjXmSalaryUserDepartment();
			zjXmSalaryUserDepartment.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryUserDepartmentMapper
					.batchDeleteUpdateZjXmSalaryUserDepartment(zjXmSalaryUserDepartmentList, zjXmSalaryUserDepartment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryUserDepartmentList);
		}
	}
}
