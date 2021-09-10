package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmSalaryEducationBackgroundMapper;
import com.apih5.mybatis.dao.ZjXmSalaryUserAttachmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryEducationBackground;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.service.ZjXmSalaryEducationBackgroundService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryEducationBackgroundService")
public class ZjXmSalaryEducationBackgroundServiceImpl implements ZjXmSalaryEducationBackgroundService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryEducationBackgroundMapper zjXmSalaryEducationBackgroundMapper;
	@Autowired(required = true)
	private ZjXmSalaryUserAttachmentMapper zjXmSalaryUserAttachmentMapper;

	@Override
	public ResponseEntity getZjXmSalaryEducationBackgroundListByCondition(
			ZjXmSalaryEducationBackground zjXmSalaryEducationBackground) {
		if (zjXmSalaryEducationBackground == null) {
			zjXmSalaryEducationBackground = new ZjXmSalaryEducationBackground();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryEducationBackground.getPage(), zjXmSalaryEducationBackground.getLimit());
		// 获取数据
		List<ZjXmSalaryEducationBackground> dbEducationBackgroundList = zjXmSalaryEducationBackgroundMapper
				.selectByZjXmSalaryEducationBackgroundList(zjXmSalaryEducationBackground);
		if (dbEducationBackgroundList.size() > 0) {
			for (ZjXmSalaryEducationBackground dbEducation : dbEducationBackgroundList) {
				// 学历附件和学位附件
				List<ZjXmSalaryUserAttachment> educationAttachmentList = Lists.newArrayList();
				List<ZjXmSalaryUserAttachment> degreeAttachmentList = Lists.newArrayList();
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbEducation.getEducationId());
				List<ZjXmSalaryUserAttachment> attachmentList = zjXmSalaryUserAttachmentMapper
						.selectByZjXmSalaryUserAttachmentList(userAttachment);
				if (attachmentList.size() > 0) {
					for (ZjXmSalaryUserAttachment dbAttachment : attachmentList) {
						if (StrUtil.equals("2", dbAttachment.getFileType())) {
							educationAttachmentList.add(dbAttachment);
						} else if (StrUtil.equals("3", dbAttachment.getFileType())) {
							degreeAttachmentList.add(dbAttachment);
						}
					}
					dbEducation.setEducationAttachmentList(educationAttachmentList);
					dbEducation.setDegreeAttachmentList(degreeAttachmentList);
				}
			}
		}
		// 得到分页信息
		PageInfo<ZjXmSalaryEducationBackground> p = new PageInfo<>(dbEducationBackgroundList);

		return repEntity.okList(dbEducationBackgroundList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryEducationBackgroundDetails(
			ZjXmSalaryEducationBackground zjXmSalaryEducationBackground) {
		if (zjXmSalaryEducationBackground == null) {
			zjXmSalaryEducationBackground = new ZjXmSalaryEducationBackground();
		}
		// 获取数据
		ZjXmSalaryEducationBackground dbZjXmSalaryEducationBackground = zjXmSalaryEducationBackgroundMapper
				.selectByPrimaryKey(zjXmSalaryEducationBackground.getEducationId());
		// 数据存在
		if (dbZjXmSalaryEducationBackground != null) {
			return repEntity.ok(dbZjXmSalaryEducationBackground);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryEducationBackground(
			ZjXmSalaryEducationBackground zjXmSalaryEducationBackground) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryEducationBackground.setEducationId(UuidUtil.generate());
		zjXmSalaryEducationBackground.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryEducationBackgroundMapper.insert(zjXmSalaryEducationBackground);
		if (flag != 0) {
			// 插入学历附件
			if (zjXmSalaryEducationBackground.getEducationAttachmentList() != null
					&& zjXmSalaryEducationBackground.getEducationAttachmentList().size() > 0) {
				for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryEducationBackground
						.getEducationAttachmentList()) {
					insertAttachment.setUid(UuidUtil.generate());
					insertAttachment.setOtherId(zjXmSalaryEducationBackground.getEducationId());
					insertAttachment.setFileType("2");
					insertAttachment.setCreateUserInfo(userKey, realName);
				}
				flag = zjXmSalaryUserAttachmentMapper.batchInsertZjXmSalaryUserAttachment(
						zjXmSalaryEducationBackground.getEducationAttachmentList());
			}
			// 插入学位附件
			if (zjXmSalaryEducationBackground.getDegreeAttachmentList() != null
					&& zjXmSalaryEducationBackground.getDegreeAttachmentList().size() > 0) {
				for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryEducationBackground
						.getDegreeAttachmentList()) {
					insertAttachment.setUid(UuidUtil.generate());
					insertAttachment.setOtherId(zjXmSalaryEducationBackground.getEducationId());
					insertAttachment.setFileType("3");
					insertAttachment.setCreateUserInfo(userKey, realName);
				}
				flag = zjXmSalaryUserAttachmentMapper
						.batchInsertZjXmSalaryUserAttachment(zjXmSalaryEducationBackground.getDegreeAttachmentList());
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryEducationBackground);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryEducationBackground(
			ZjXmSalaryEducationBackground zjXmSalaryEducationBackground) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryEducationBackground dbzjXmSalaryEducationBackground = zjXmSalaryEducationBackgroundMapper
				.selectByPrimaryKey(zjXmSalaryEducationBackground.getEducationId());
		if (dbzjXmSalaryEducationBackground != null
				&& StrUtil.isNotEmpty(dbzjXmSalaryEducationBackground.getEducationId())) {
			// sysUser扩展表主键
			dbzjXmSalaryEducationBackground.setExtensionId(zjXmSalaryEducationBackground.getExtensionId());
			// 入学时间
			dbzjXmSalaryEducationBackground.setEnrollmentDate(zjXmSalaryEducationBackground.getEnrollmentDate());
			// 毕/肄业时间
			dbzjXmSalaryEducationBackground.setGraduateDate(zjXmSalaryEducationBackground.getGraduateDate());
			// 毕/肄业院校
			dbzjXmSalaryEducationBackground.setGraduateSchool(zjXmSalaryEducationBackground.getGraduateSchool());
			// 学历
			dbzjXmSalaryEducationBackground.setEducation(zjXmSalaryEducationBackground.getEducation());
			// 学位
			dbzjXmSalaryEducationBackground.setDegree(zjXmSalaryEducationBackground.getDegree());
			// 专业
			dbzjXmSalaryEducationBackground.setMajor(zjXmSalaryEducationBackground.getMajor());
			// 学位授予时间
			dbzjXmSalaryEducationBackground.setDegreeAwardDate(zjXmSalaryEducationBackground.getDegreeAwardDate());
			// 是否第一学历
			dbzjXmSalaryEducationBackground.setIsFirstEducation(zjXmSalaryEducationBackground.getIsFirstEducation());
			// 是否最高学历
			dbzjXmSalaryEducationBackground
					.setIsHighestEducation(zjXmSalaryEducationBackground.getIsHighestEducation());
			// 备注
			dbzjXmSalaryEducationBackground.setRemarks(zjXmSalaryEducationBackground.getRemarks());
			// 共通
			dbzjXmSalaryEducationBackground.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryEducationBackgroundMapper.updateByPrimaryKey(dbzjXmSalaryEducationBackground);
			if(flag != 0) {
				// 删除学历附件和学位证附件
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbzjXmSalaryEducationBackground.getEducationId());
				userAttachment.setFileType("2");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				userAttachment.setFileType("3");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				// 插入学历附件
				if (zjXmSalaryEducationBackground.getEducationAttachmentList() != null
						&& zjXmSalaryEducationBackground.getEducationAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryEducationBackground
							.getEducationAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(zjXmSalaryEducationBackground.getEducationId());
						insertAttachment.setFileType("2");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper.batchInsertZjXmSalaryUserAttachment(
							zjXmSalaryEducationBackground.getEducationAttachmentList());
				}
				// 插入学位附件
				if (zjXmSalaryEducationBackground.getDegreeAttachmentList() != null
						&& zjXmSalaryEducationBackground.getDegreeAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryEducationBackground
							.getDegreeAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(zjXmSalaryEducationBackground.getEducationId());
						insertAttachment.setFileType("3");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper
							.batchInsertZjXmSalaryUserAttachment(zjXmSalaryEducationBackground.getDegreeAttachmentList());
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryEducationBackground);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryEducationBackground(
			List<ZjXmSalaryEducationBackground> zjXmSalaryEducationBackgroundList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryEducationBackgroundList != null && zjXmSalaryEducationBackgroundList.size() > 0) {
			ZjXmSalaryEducationBackground zjXmSalaryEducationBackground = new ZjXmSalaryEducationBackground();
			zjXmSalaryEducationBackground.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryEducationBackgroundMapper.batchDeleteUpdateZjXmSalaryEducationBackground(
					zjXmSalaryEducationBackgroundList, zjXmSalaryEducationBackground);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryEducationBackgroundList);
		}
	}
}
