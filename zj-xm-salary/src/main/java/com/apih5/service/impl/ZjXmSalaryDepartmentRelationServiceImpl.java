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
import com.apih5.mybatis.dao.ZjXmSalaryDepartmentRelationMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartmentRelation;
import com.apih5.service.ZjXmSalaryDepartmentRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryDepartmentRelationService")
public class ZjXmSalaryDepartmentRelationServiceImpl implements ZjXmSalaryDepartmentRelationService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmSalaryDepartmentRelationMapper zjXmSalaryDepartmentRelationMapper;

	@Override
	public ResponseEntity getZjXmSalaryDepartmentRelationListByCondition(
			ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation) {
		if (zjXmSalaryDepartmentRelation == null) {
			zjXmSalaryDepartmentRelation = new ZjXmSalaryDepartmentRelation();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryDepartmentRelation.getPage(), zjXmSalaryDepartmentRelation.getLimit());
		// 获取数据
		List<ZjXmSalaryDepartmentRelation> zjXmSalaryDepartmentRelationList = zjXmSalaryDepartmentRelationMapper
				.selectByZjXmSalaryDepartmentRelationList(zjXmSalaryDepartmentRelation);
		// 得到分页信息
		PageInfo<ZjXmSalaryDepartmentRelation> p = new PageInfo<>(zjXmSalaryDepartmentRelationList);

		return repEntity.okList(zjXmSalaryDepartmentRelationList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryDepartmentRelationDetails(
			ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation) {
		if (zjXmSalaryDepartmentRelation == null) {
			zjXmSalaryDepartmentRelation = new ZjXmSalaryDepartmentRelation();
		}
		// 获取数据
		ZjXmSalaryDepartmentRelation dbZjXmSalaryDepartmentRelation = zjXmSalaryDepartmentRelationMapper
				.selectByPrimaryKey(zjXmSalaryDepartmentRelation.getRelationId());
		// 数据存在
		if (dbZjXmSalaryDepartmentRelation != null) {
			return repEntity.ok(dbZjXmSalaryDepartmentRelation);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryDepartmentRelation(ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryDepartmentRelation.setRelationId(UuidUtil.generate());
		zjXmSalaryDepartmentRelation.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryDepartmentRelationMapper.insert(zjXmSalaryDepartmentRelation);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryDepartmentRelation);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryDepartmentRelation(
			ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryDepartmentRelation dbzjXmSalaryDepartmentRelation = zjXmSalaryDepartmentRelationMapper
				.selectByPrimaryKey(zjXmSalaryDepartmentRelation.getRelationId());
		if (dbzjXmSalaryDepartmentRelation != null
				&& StrUtil.isNotEmpty(dbzjXmSalaryDepartmentRelation.getRelationId())) {
			// 业务项目id
			dbzjXmSalaryDepartmentRelation.setBusDepartmentId(zjXmSalaryDepartmentRelation.getBusDepartmentId());
			// 系统部门ID
			dbzjXmSalaryDepartmentRelation.setSysDepartmentId(zjXmSalaryDepartmentRelation.getSysDepartmentId());
			// 备注
			dbzjXmSalaryDepartmentRelation.setRemarks(zjXmSalaryDepartmentRelation.getRemarks());
			// 排序
			dbzjXmSalaryDepartmentRelation.setSort(zjXmSalaryDepartmentRelation.getSort());
			// 共通
			dbzjXmSalaryDepartmentRelation.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryDepartmentRelationMapper.updateByPrimaryKey(dbzjXmSalaryDepartmentRelation);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryDepartmentRelation);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryDepartmentRelation(
			List<ZjXmSalaryDepartmentRelation> zjXmSalaryDepartmentRelationList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryDepartmentRelationList != null && zjXmSalaryDepartmentRelationList.size() > 0) {
			ZjXmSalaryDepartmentRelation zjXmSalaryDepartmentRelation = new ZjXmSalaryDepartmentRelation();
			zjXmSalaryDepartmentRelation.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryDepartmentRelationMapper.batchDeleteUpdateZjXmSalaryDepartmentRelation(
					zjXmSalaryDepartmentRelationList, zjXmSalaryDepartmentRelation);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryDepartmentRelationList);
		}
	}
}
