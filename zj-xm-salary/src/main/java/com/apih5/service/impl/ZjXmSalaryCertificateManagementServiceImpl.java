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
import com.apih5.mybatis.dao.ZjXmSalaryCertificateManagementMapper;
import com.apih5.mybatis.dao.ZjXmSalaryUserAttachmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryCertificateManagement;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.service.ZjXmSalaryCertificateManagementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryCertificateManagementService")
public class ZjXmSalaryCertificateManagementServiceImpl implements ZjXmSalaryCertificateManagementService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryCertificateManagementMapper zjXmSalaryCertificateManagementMapper;
	@Autowired(required = true)
	private ZjXmSalaryUserAttachmentMapper zjXmSalaryUserAttachmentMapper;

	@Override
	public ResponseEntity getZjXmSalaryCertificateManagementListByCondition(
			ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement) {
		if (zjXmSalaryCertificateManagement == null) {
			zjXmSalaryCertificateManagement = new ZjXmSalaryCertificateManagement();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryCertificateManagement.getPage(), zjXmSalaryCertificateManagement.getLimit());
		// 获取数据
		List<ZjXmSalaryCertificateManagement> dbCertificateList = zjXmSalaryCertificateManagementMapper
				.selectByZjXmSalaryCertificateManagementList(zjXmSalaryCertificateManagement);
		if (dbCertificateList.size() > 0) {
			for (ZjXmSalaryCertificateManagement dbCertificateManagement : dbCertificateList) {
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbCertificateManagement.getCertificateId());
				userAttachment.setFileType("9");
				List<ZjXmSalaryUserAttachment> attachmentList = zjXmSalaryUserAttachmentMapper
						.selectByZjXmSalaryUserAttachmentList(userAttachment);
				dbCertificateManagement.setCertificateAttachmentList(attachmentList);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmSalaryCertificateManagement> p = new PageInfo<>(dbCertificateList);

		return repEntity.okList(dbCertificateList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryCertificateManagementDetails(
			ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement) {
		if (zjXmSalaryCertificateManagement == null) {
			zjXmSalaryCertificateManagement = new ZjXmSalaryCertificateManagement();
		}
		// 获取数据
		ZjXmSalaryCertificateManagement dbZjXmSalaryCertificateManagement = zjXmSalaryCertificateManagementMapper
				.selectByPrimaryKey(zjXmSalaryCertificateManagement.getCertificateId());
		// 数据存在
		if (dbZjXmSalaryCertificateManagement != null) {
			return repEntity.ok(dbZjXmSalaryCertificateManagement);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryCertificateManagement(
			ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryCertificateManagement.setCertificateId(UuidUtil.generate());
		zjXmSalaryCertificateManagement.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryCertificateManagementMapper.insert(zjXmSalaryCertificateManagement);
		// 插入证书附件
		if (zjXmSalaryCertificateManagement.getCertificateAttachmentList() != null
				&& zjXmSalaryCertificateManagement.getCertificateAttachmentList().size() > 0) {
			for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryCertificateManagement
					.getCertificateAttachmentList()) {
				insertAttachment.setUid(UuidUtil.generate());
				insertAttachment.setOtherId(zjXmSalaryCertificateManagement.getCertificateId());
				insertAttachment.setFileType("9");
				insertAttachment.setCreateUserInfo(userKey, realName);
			}
			flag = zjXmSalaryUserAttachmentMapper.batchInsertZjXmSalaryUserAttachment(
					zjXmSalaryCertificateManagement.getCertificateAttachmentList());
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryCertificateManagement);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryCertificateManagement(
			ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryCertificateManagement dbzjXmSalaryCertificateManagement = zjXmSalaryCertificateManagementMapper
				.selectByPrimaryKey(zjXmSalaryCertificateManagement.getCertificateId());
		if (dbzjXmSalaryCertificateManagement != null
				&& StrUtil.isNotEmpty(dbzjXmSalaryCertificateManagement.getCertificateId())) {
			// sysUser扩展表主键
			dbzjXmSalaryCertificateManagement.setExtensionId(zjXmSalaryCertificateManagement.getExtensionId());
			// 证书类别
			dbzjXmSalaryCertificateManagement.setCertificateType(zjXmSalaryCertificateManagement.getCertificateType());
			// 证书名称
			dbzjXmSalaryCertificateManagement.setCertificateName(zjXmSalaryCertificateManagement.getCertificateName());
			// 证书专业
			dbzjXmSalaryCertificateManagement
					.setCertificateMajor(zjXmSalaryCertificateManagement.getCertificateMajor());
			// 证书编号
			dbzjXmSalaryCertificateManagement.setCertificateNo(zjXmSalaryCertificateManagement.getCertificateNo());
			// 签发日期
			dbzjXmSalaryCertificateManagement.setIssueDate(zjXmSalaryCertificateManagement.getIssueDate());
			// 证书履约所在项目
			dbzjXmSalaryCertificateManagement.setProjectId(zjXmSalaryCertificateManagement.getProjectId());
			// 一次性奖励标准
			dbzjXmSalaryCertificateManagement.setRewardStandard(zjXmSalaryCertificateManagement.getRewardStandard());
			// 分摊年度
			dbzjXmSalaryCertificateManagement.setApportionYear(zjXmSalaryCertificateManagement.getApportionYear());
			// 已发放年度
			dbzjXmSalaryCertificateManagement.setPaidYear(zjXmSalaryCertificateManagement.getPaidYear());
			// 月度补贴标准
			dbzjXmSalaryCertificateManagement.setSubsidyStandard(zjXmSalaryCertificateManagement.getSubsidyStandard());
			// 发放开始时间
			dbzjXmSalaryCertificateManagement.setStartDate(zjXmSalaryCertificateManagement.getStartDate());
			// 发放截止时间
			dbzjXmSalaryCertificateManagement.setEndDate(zjXmSalaryCertificateManagement.getEndDate());
			// 备注
			dbzjXmSalaryCertificateManagement.setRemarks(zjXmSalaryCertificateManagement.getRemarks());
			// 共通
			dbzjXmSalaryCertificateManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryCertificateManagementMapper.updateByPrimaryKey(dbzjXmSalaryCertificateManagement);
			if (flag != 0) {
				// 删除近照附件和身份证附件
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbzjXmSalaryCertificateManagement.getCertificateId());
				userAttachment.setFileType("9");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				// 插入证书附件
				if (zjXmSalaryCertificateManagement.getCertificateAttachmentList() != null
						&& zjXmSalaryCertificateManagement.getCertificateAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryCertificateManagement
							.getCertificateAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(dbzjXmSalaryCertificateManagement.getCertificateId());
						insertAttachment.setFileType("9");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper.batchInsertZjXmSalaryUserAttachment(
							zjXmSalaryCertificateManagement.getCertificateAttachmentList());
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryCertificateManagement);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryCertificateManagement(
			List<ZjXmSalaryCertificateManagement> zjXmSalaryCertificateManagementList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryCertificateManagementList != null && zjXmSalaryCertificateManagementList.size() > 0) {
			ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement = new ZjXmSalaryCertificateManagement();
			zjXmSalaryCertificateManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryCertificateManagementMapper.batchDeleteUpdateZjXmSalaryCertificateManagement(
					zjXmSalaryCertificateManagementList, zjXmSalaryCertificateManagement);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryCertificateManagementList);
		}
	}
}
