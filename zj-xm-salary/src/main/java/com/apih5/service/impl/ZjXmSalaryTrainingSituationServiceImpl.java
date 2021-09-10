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
import com.apih5.mybatis.dao.ZjXmSalaryTrainingSituationMapper;
import com.apih5.mybatis.dao.ZjXmSalaryUserAttachmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryTrainingSituation;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.service.ZjXmSalaryTrainingSituationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryTrainingSituationService")
public class ZjXmSalaryTrainingSituationServiceImpl implements ZjXmSalaryTrainingSituationService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryTrainingSituationMapper zjXmSalaryTrainingSituationMapper;
	@Autowired(required = true)
	private ZjXmSalaryUserAttachmentMapper zjXmSalaryUserAttachmentMapper;

	@Override
	public ResponseEntity getZjXmSalaryTrainingSituationListByCondition(
			ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation) {
		if (zjXmSalaryTrainingSituation == null) {
			zjXmSalaryTrainingSituation = new ZjXmSalaryTrainingSituation();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryTrainingSituation.getPage(), zjXmSalaryTrainingSituation.getLimit());
		// 获取数据
		List<ZjXmSalaryTrainingSituation> dbTrainingList = zjXmSalaryTrainingSituationMapper
				.selectByZjXmSalaryTrainingSituationList(zjXmSalaryTrainingSituation);
		if (dbTrainingList.size() > 0) {
			for (ZjXmSalaryTrainingSituation dbTrainingSituation : dbTrainingList) {
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbTrainingSituation.getTrainingId());
				userAttachment.setFileType("7");
				List<ZjXmSalaryUserAttachment> attachmentList = zjXmSalaryUserAttachmentMapper
						.selectByZjXmSalaryUserAttachmentList(userAttachment);
				dbTrainingSituation.setTrainingAttachmentList(attachmentList);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmSalaryTrainingSituation> p = new PageInfo<>(dbTrainingList);

		return repEntity.okList(dbTrainingList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryTrainingSituationDetails(
			ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation) {
		if (zjXmSalaryTrainingSituation == null) {
			zjXmSalaryTrainingSituation = new ZjXmSalaryTrainingSituation();
		}
		// 获取数据
		ZjXmSalaryTrainingSituation dbZjXmSalaryTrainingSituation = zjXmSalaryTrainingSituationMapper
				.selectByPrimaryKey(zjXmSalaryTrainingSituation.getTrainingId());
		// 数据存在
		if (dbZjXmSalaryTrainingSituation != null) {
			return repEntity.ok(dbZjXmSalaryTrainingSituation);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryTrainingSituation(ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryTrainingSituation.setTrainingId(UuidUtil.generate());
		zjXmSalaryTrainingSituation.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryTrainingSituationMapper.insert(zjXmSalaryTrainingSituation);
		// 插入培训附件
		if (zjXmSalaryTrainingSituation.getTrainingAttachmentList() != null
				&& zjXmSalaryTrainingSituation.getTrainingAttachmentList().size() > 0) {
			for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryTrainingSituation.getTrainingAttachmentList()) {
				insertAttachment.setUid(UuidUtil.generate());
				insertAttachment.setOtherId(zjXmSalaryTrainingSituation.getTrainingId());
				insertAttachment.setFileType("7");
				insertAttachment.setCreateUserInfo(userKey, realName);
			}
			flag = zjXmSalaryUserAttachmentMapper
					.batchInsertZjXmSalaryUserAttachment(zjXmSalaryTrainingSituation.getTrainingAttachmentList());
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryTrainingSituation);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryTrainingSituation(ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryTrainingSituation dbzjXmSalaryTrainingSituation = zjXmSalaryTrainingSituationMapper
				.selectByPrimaryKey(zjXmSalaryTrainingSituation.getTrainingId());
		if (dbzjXmSalaryTrainingSituation != null
				&& StrUtil.isNotEmpty(dbzjXmSalaryTrainingSituation.getTrainingId())) {
			// sysUser扩展表主键
			dbzjXmSalaryTrainingSituation.setExtensionId(zjXmSalaryTrainingSituation.getExtensionId());
			// 培训起始日期
			dbzjXmSalaryTrainingSituation.setStartDate(zjXmSalaryTrainingSituation.getStartDate());
			// 培训截止日期
			dbzjXmSalaryTrainingSituation.setEndDate(zjXmSalaryTrainingSituation.getEndDate());
			// 培训名称
			dbzjXmSalaryTrainingSituation.setTrainingName(zjXmSalaryTrainingSituation.getTrainingName());
			// 培训类型
			dbzjXmSalaryTrainingSituation.setTrainingType(zjXmSalaryTrainingSituation.getTrainingType());
			// 培训学习方式
			dbzjXmSalaryTrainingSituation.setTrainingMode(zjXmSalaryTrainingSituation.getTrainingMode());
			// 培训学时
			dbzjXmSalaryTrainingSituation.setTrainingHours(zjXmSalaryTrainingSituation.getTrainingHours());
			// 培训结果
			dbzjXmSalaryTrainingSituation.setTrainingResult(zjXmSalaryTrainingSituation.getTrainingResult());
			// 共通
			dbzjXmSalaryTrainingSituation.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryTrainingSituationMapper.updateByPrimaryKey(dbzjXmSalaryTrainingSituation);
			if (flag != 0) {
				// 删除培训附件
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbzjXmSalaryTrainingSituation.getTrainingId());
				userAttachment.setFileType("7");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				// 插入培训附件
				if (zjXmSalaryTrainingSituation.getTrainingAttachmentList() != null
						&& zjXmSalaryTrainingSituation.getTrainingAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryTrainingSituation
							.getTrainingAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(dbzjXmSalaryTrainingSituation.getTrainingId());
						insertAttachment.setFileType("7");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper.batchInsertZjXmSalaryUserAttachment(
							zjXmSalaryTrainingSituation.getTrainingAttachmentList());
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryTrainingSituation);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryTrainingSituation(
			List<ZjXmSalaryTrainingSituation> zjXmSalaryTrainingSituationList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryTrainingSituationList != null && zjXmSalaryTrainingSituationList.size() > 0) {
			ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation = new ZjXmSalaryTrainingSituation();
			zjXmSalaryTrainingSituation.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryTrainingSituationMapper.batchDeleteUpdateZjXmSalaryTrainingSituation(
					zjXmSalaryTrainingSituationList, zjXmSalaryTrainingSituation);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryTrainingSituationList);
		}
	}
}
