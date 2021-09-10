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
import com.apih5.mybatis.dao.ZjXmSalaryHealthConditionMapper;
import com.apih5.mybatis.dao.ZjXmSalaryUserAttachmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryHealthCondition;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.service.ZjXmSalaryHealthConditionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryHealthConditionService")
public class ZjXmSalaryHealthConditionServiceImpl implements ZjXmSalaryHealthConditionService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryHealthConditionMapper zjXmSalaryHealthConditionMapper;
	@Autowired(required = true)
	private ZjXmSalaryUserAttachmentMapper zjXmSalaryUserAttachmentMapper;

	@Override
	public ResponseEntity getZjXmSalaryHealthConditionListByCondition(
			ZjXmSalaryHealthCondition zjXmSalaryHealthCondition) {
		if (zjXmSalaryHealthCondition == null) {
			zjXmSalaryHealthCondition = new ZjXmSalaryHealthCondition();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryHealthCondition.getPage(), zjXmSalaryHealthCondition.getLimit());
		// 获取数据
		List<ZjXmSalaryHealthCondition> dbHealthConditionList = zjXmSalaryHealthConditionMapper
				.selectByZjXmSalaryHealthConditionList(zjXmSalaryHealthCondition);
		if (dbHealthConditionList.size() > 0) {
			for (ZjXmSalaryHealthCondition dbHealth : dbHealthConditionList) {
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbHealth.getHealthId());
				userAttachment.setFileType("8");
				List<ZjXmSalaryUserAttachment> attachmentList = zjXmSalaryUserAttachmentMapper
						.selectByZjXmSalaryUserAttachmentList(userAttachment);
				dbHealth.setHealthAttachmentList(attachmentList);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmSalaryHealthCondition> p = new PageInfo<>(dbHealthConditionList);

		return repEntity.okList(dbHealthConditionList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryHealthConditionDetails(ZjXmSalaryHealthCondition zjXmSalaryHealthCondition) {
		if (zjXmSalaryHealthCondition == null) {
			zjXmSalaryHealthCondition = new ZjXmSalaryHealthCondition();
		}
		// 获取数据
		ZjXmSalaryHealthCondition dbZjXmSalaryHealthCondition = zjXmSalaryHealthConditionMapper
				.selectByPrimaryKey(zjXmSalaryHealthCondition.getHealthId());
		// 数据存在
		if (dbZjXmSalaryHealthCondition != null) {
			return repEntity.ok(dbZjXmSalaryHealthCondition);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryHealthCondition(ZjXmSalaryHealthCondition zjXmSalaryHealthCondition) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryHealthCondition.setHealthId(UuidUtil.generate());
		zjXmSalaryHealthCondition.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryHealthConditionMapper.insert(zjXmSalaryHealthCondition);
		// 插入健康附件
		if (zjXmSalaryHealthCondition.getHealthAttachmentList() != null
				&& zjXmSalaryHealthCondition.getHealthAttachmentList().size() > 0) {
			for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryHealthCondition.getHealthAttachmentList()) {
				insertAttachment.setUid(UuidUtil.generate());
				insertAttachment.setOtherId(zjXmSalaryHealthCondition.getHealthId());
				insertAttachment.setFileType("8");
				insertAttachment.setCreateUserInfo(userKey, realName);
			}
			flag = zjXmSalaryUserAttachmentMapper
					.batchInsertZjXmSalaryUserAttachment(zjXmSalaryHealthCondition.getHealthAttachmentList());
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryHealthCondition);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryHealthCondition(ZjXmSalaryHealthCondition zjXmSalaryHealthCondition) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryHealthCondition dbzjXmSalaryHealthCondition = zjXmSalaryHealthConditionMapper
				.selectByPrimaryKey(zjXmSalaryHealthCondition.getHealthId());
		if (dbzjXmSalaryHealthCondition != null && StrUtil.isNotEmpty(dbzjXmSalaryHealthCondition.getHealthId())) {
			// sysUser扩展表主键
			dbzjXmSalaryHealthCondition.setExtensionId(zjXmSalaryHealthCondition.getExtensionId());
			// 体检类型
			dbzjXmSalaryHealthCondition.setPhysicalType(zjXmSalaryHealthCondition.getPhysicalType());
			// 体检机构
			dbzjXmSalaryHealthCondition.setPhysicalInstitution(zjXmSalaryHealthCondition.getPhysicalInstitution());
			// 健康情况
			dbzjXmSalaryHealthCondition.setHealthCondition(zjXmSalaryHealthCondition.getHealthCondition());
			// 职业病情况
			dbzjXmSalaryHealthCondition.setOccupationalDisease(zjXmSalaryHealthCondition.getOccupationalDisease());
			// 备注
			dbzjXmSalaryHealthCondition.setRemarks(zjXmSalaryHealthCondition.getRemarks());
			// 排序
			dbzjXmSalaryHealthCondition.setSort(zjXmSalaryHealthCondition.getSort());
			// 共通
			dbzjXmSalaryHealthCondition.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryHealthConditionMapper.updateByPrimaryKey(dbzjXmSalaryHealthCondition);
			if (flag != 0) {
				// 删除健康附件
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbzjXmSalaryHealthCondition.getHealthId());
				userAttachment.setFileType("8");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				// 插入健康附件
				if (zjXmSalaryHealthCondition.getHealthAttachmentList() != null
						&& zjXmSalaryHealthCondition.getHealthAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryHealthCondition
							.getHealthAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(zjXmSalaryHealthCondition.getHealthId());
						insertAttachment.setFileType("8");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper
							.batchInsertZjXmSalaryUserAttachment(zjXmSalaryHealthCondition.getHealthAttachmentList());
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryHealthCondition);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryHealthCondition(
			List<ZjXmSalaryHealthCondition> zjXmSalaryHealthConditionList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryHealthConditionList != null && zjXmSalaryHealthConditionList.size() > 0) {
			ZjXmSalaryHealthCondition zjXmSalaryHealthCondition = new ZjXmSalaryHealthCondition();
			zjXmSalaryHealthCondition.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryHealthConditionMapper.batchDeleteUpdateZjXmSalaryHealthCondition(
					zjXmSalaryHealthConditionList, zjXmSalaryHealthCondition);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryHealthConditionList);
		}
	}
}
