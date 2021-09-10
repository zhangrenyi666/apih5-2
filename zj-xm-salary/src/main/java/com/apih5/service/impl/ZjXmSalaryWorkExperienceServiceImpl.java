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
import com.apih5.mybatis.dao.ZjXmSalaryWorkExperienceMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryWorkExperience;
import com.apih5.service.ZjXmSalaryWorkExperienceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryWorkExperienceService")
public class ZjXmSalaryWorkExperienceServiceImpl implements ZjXmSalaryWorkExperienceService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmSalaryWorkExperienceMapper zjXmSalaryWorkExperienceMapper;

	@Override
	public ResponseEntity getZjXmSalaryWorkExperienceListByCondition(
			ZjXmSalaryWorkExperience zjXmSalaryWorkExperience) {
		if (zjXmSalaryWorkExperience == null) {
			zjXmSalaryWorkExperience = new ZjXmSalaryWorkExperience();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryWorkExperience.getPage(), zjXmSalaryWorkExperience.getLimit());
		// 获取数据
		List<ZjXmSalaryWorkExperience> zjXmSalaryWorkExperienceList = zjXmSalaryWorkExperienceMapper
				.selectByZjXmSalaryWorkExperienceList(zjXmSalaryWorkExperience);
		// 得到分页信息
		PageInfo<ZjXmSalaryWorkExperience> p = new PageInfo<>(zjXmSalaryWorkExperienceList);

		return repEntity.okList(zjXmSalaryWorkExperienceList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryWorkExperienceDetails(ZjXmSalaryWorkExperience zjXmSalaryWorkExperience) {
		if (zjXmSalaryWorkExperience == null) {
			zjXmSalaryWorkExperience = new ZjXmSalaryWorkExperience();
		}
		// 获取数据
		ZjXmSalaryWorkExperience dbZjXmSalaryWorkExperience = zjXmSalaryWorkExperienceMapper
				.selectByPrimaryKey(zjXmSalaryWorkExperience.getExperienceId());
		// 数据存在
		if (dbZjXmSalaryWorkExperience != null) {
			return repEntity.ok(dbZjXmSalaryWorkExperience);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryWorkExperience(ZjXmSalaryWorkExperience zjXmSalaryWorkExperience) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryWorkExperience.setExperienceId(UuidUtil.generate());
		zjXmSalaryWorkExperience.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryWorkExperienceMapper.insert(zjXmSalaryWorkExperience);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryWorkExperience);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryWorkExperience(ZjXmSalaryWorkExperience zjXmSalaryWorkExperience) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryWorkExperience dbzjXmSalaryWorkExperience = zjXmSalaryWorkExperienceMapper
				.selectByPrimaryKey(zjXmSalaryWorkExperience.getExperienceId());
		if (dbzjXmSalaryWorkExperience != null && StrUtil.isNotEmpty(dbzjXmSalaryWorkExperience.getExperienceId())) {
			// sysUser扩展表主键
			dbzjXmSalaryWorkExperience.setExtensionId(zjXmSalaryWorkExperience.getExtensionId());
			// 起始日期
			dbzjXmSalaryWorkExperience.setStartDate(zjXmSalaryWorkExperience.getStartDate());
			// 截止日期
			dbzjXmSalaryWorkExperience.setEndDate(zjXmSalaryWorkExperience.getEndDate());
			// 工作单位
			dbzjXmSalaryWorkExperience.setWorkUnit(zjXmSalaryWorkExperience.getWorkUnit());
			// 所在部门
			dbzjXmSalaryWorkExperience.setDepartment(zjXmSalaryWorkExperience.getDepartment());
			// 岗位
			dbzjXmSalaryWorkExperience.setPosition(zjXmSalaryWorkExperience.getPosition());
			// 主要工作内容
			dbzjXmSalaryWorkExperience.setWorkContent(zjXmSalaryWorkExperience.getWorkContent());
			// 证明人
			dbzjXmSalaryWorkExperience.setCertifier(zjXmSalaryWorkExperience.getCertifier());
			// 联系电话
			dbzjXmSalaryWorkExperience.setPhoneNumber(zjXmSalaryWorkExperience.getPhoneNumber());
			// 主、兼职
			dbzjXmSalaryWorkExperience.setPositionType(zjXmSalaryWorkExperience.getPositionType());
			// 备注
			dbzjXmSalaryWorkExperience.setRemarks(zjXmSalaryWorkExperience.getRemarks());
			// 排序
			dbzjXmSalaryWorkExperience.setSort(zjXmSalaryWorkExperience.getSort());
			// 共通
			dbzjXmSalaryWorkExperience.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryWorkExperienceMapper.updateByPrimaryKey(dbzjXmSalaryWorkExperience);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryWorkExperience);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryWorkExperience(
			List<ZjXmSalaryWorkExperience> zjXmSalaryWorkExperienceList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryWorkExperienceList != null && zjXmSalaryWorkExperienceList.size() > 0) {
			ZjXmSalaryWorkExperience zjXmSalaryWorkExperience = new ZjXmSalaryWorkExperience();
			zjXmSalaryWorkExperience.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryWorkExperienceMapper
					.batchDeleteUpdateZjXmSalaryWorkExperience(zjXmSalaryWorkExperienceList, zjXmSalaryWorkExperience);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryWorkExperienceList);
		}
	}
}
