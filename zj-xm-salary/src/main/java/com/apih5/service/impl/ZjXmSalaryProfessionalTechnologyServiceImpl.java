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
import com.apih5.mybatis.dao.ZjXmSalaryProfessionalTechnologyMapper;
import com.apih5.mybatis.dao.ZjXmSalaryUserAttachmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryProfessionalTechnology;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.service.ZjXmSalaryProfessionalTechnologyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryProfessionalTechnologyService")
public class ZjXmSalaryProfessionalTechnologyServiceImpl implements ZjXmSalaryProfessionalTechnologyService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryProfessionalTechnologyMapper zjXmSalaryProfessionalTechnologyMapper;
	@Autowired(required = true)
	private ZjXmSalaryUserAttachmentMapper zjXmSalaryUserAttachmentMapper;

	@Override
	public ResponseEntity getZjXmSalaryProfessionalTechnologyListByCondition(
			ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology) {
		if (zjXmSalaryProfessionalTechnology == null) {
			zjXmSalaryProfessionalTechnology = new ZjXmSalaryProfessionalTechnology();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryProfessionalTechnology.getPage(), zjXmSalaryProfessionalTechnology.getLimit());
		// 获取数据
		List<ZjXmSalaryProfessionalTechnology> dbTechnologyList = zjXmSalaryProfessionalTechnologyMapper
				.selectByZjXmSalaryProfessionalTechnologyList(zjXmSalaryProfessionalTechnology);
		if (dbTechnologyList.size() > 0) {
			for (ZjXmSalaryProfessionalTechnology dbTechnology : dbTechnologyList) {
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbTechnology.getTechnologyId());
				userAttachment.setFileType("4");
				List<ZjXmSalaryUserAttachment> attachmentList = zjXmSalaryUserAttachmentMapper
						.selectByZjXmSalaryUserAttachmentList(userAttachment);
				dbTechnology.setTechnologyAttachmentList(attachmentList);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmSalaryProfessionalTechnology> p = new PageInfo<>(dbTechnologyList);

		return repEntity.okList(dbTechnologyList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryProfessionalTechnologyDetails(
			ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology) {
		if (zjXmSalaryProfessionalTechnology == null) {
			zjXmSalaryProfessionalTechnology = new ZjXmSalaryProfessionalTechnology();
		}
		// 获取数据
		ZjXmSalaryProfessionalTechnology dbZjXmSalaryProfessionalTechnology = zjXmSalaryProfessionalTechnologyMapper
				.selectByPrimaryKey(zjXmSalaryProfessionalTechnology.getTechnologyId());
		// 数据存在
		if (dbZjXmSalaryProfessionalTechnology != null) {
			return repEntity.ok(dbZjXmSalaryProfessionalTechnology);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryProfessionalTechnology(
			ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryProfessionalTechnology.setTechnologyId(UuidUtil.generate());
		zjXmSalaryProfessionalTechnology.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryProfessionalTechnologyMapper.insert(zjXmSalaryProfessionalTechnology);
		// 插入专业技术附件
		if (zjXmSalaryProfessionalTechnology.getTechnologyAttachmentList() != null
				&& zjXmSalaryProfessionalTechnology.getTechnologyAttachmentList().size() > 0) {
			for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryProfessionalTechnology
					.getTechnologyAttachmentList()) {
				insertAttachment.setUid(UuidUtil.generate());
				insertAttachment.setOtherId(zjXmSalaryProfessionalTechnology.getTechnologyId());
				insertAttachment.setFileType("4");
				insertAttachment.setCreateUserInfo(userKey, realName);
			}
			flag = zjXmSalaryUserAttachmentMapper.batchInsertZjXmSalaryUserAttachment(
					zjXmSalaryProfessionalTechnology.getTechnologyAttachmentList());
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryProfessionalTechnology);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryProfessionalTechnology(
			ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryProfessionalTechnology dbzjXmSalaryProfessionalTechnology = zjXmSalaryProfessionalTechnologyMapper
				.selectByPrimaryKey(zjXmSalaryProfessionalTechnology.getTechnologyId());
		if (dbzjXmSalaryProfessionalTechnology != null
				&& StrUtil.isNotEmpty(dbzjXmSalaryProfessionalTechnology.getTechnologyId())) {
			// sysUser扩展表主键
			dbzjXmSalaryProfessionalTechnology.setExtensionId(zjXmSalaryProfessionalTechnology.getExtensionId());
			// 职称名称
			dbzjXmSalaryProfessionalTechnology.setTitle(zjXmSalaryProfessionalTechnology.getTitle());
			// 职称专业
			dbzjXmSalaryProfessionalTechnology.setSpecialty(zjXmSalaryProfessionalTechnology.getSpecialty());
			// 职称级别
			dbzjXmSalaryProfessionalTechnology.setLevel(zjXmSalaryProfessionalTechnology.getLevel());
			// 职称取得途径
			dbzjXmSalaryProfessionalTechnology.setAccess(zjXmSalaryProfessionalTechnology.getAccess());
			// 取得资格文号
			dbzjXmSalaryProfessionalTechnology
					.setQualificationNo(zjXmSalaryProfessionalTechnology.getQualificationNo());
			// 取得资格时间
			dbzjXmSalaryProfessionalTechnology
					.setAcquisitionDate(zjXmSalaryProfessionalTechnology.getAcquisitionDate());
			// 证书编号
			dbzjXmSalaryProfessionalTechnology.setCertificateNo(zjXmSalaryProfessionalTechnology.getCertificateNo());
			// 发证单位
			dbzjXmSalaryProfessionalTechnology.setIssueUnit(zjXmSalaryProfessionalTechnology.getIssueUnit());
			// 备注
			// 共通
			dbzjXmSalaryProfessionalTechnology.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryProfessionalTechnologyMapper.updateByPrimaryKey(dbzjXmSalaryProfessionalTechnology);
			if (flag != 0) {
				// 删除专业技术附件
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbzjXmSalaryProfessionalTechnology.getTechnologyId());
				userAttachment.setFileType("4");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				// 插入专业技术附件
				if (zjXmSalaryProfessionalTechnology.getTechnologyAttachmentList() != null
						&& zjXmSalaryProfessionalTechnology.getTechnologyAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryProfessionalTechnology
							.getTechnologyAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(zjXmSalaryProfessionalTechnology.getTechnologyId());
						insertAttachment.setFileType("4");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper.batchInsertZjXmSalaryUserAttachment(
							zjXmSalaryProfessionalTechnology.getTechnologyAttachmentList());
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryProfessionalTechnology);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryProfessionalTechnology(
			List<ZjXmSalaryProfessionalTechnology> zjXmSalaryProfessionalTechnologyList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryProfessionalTechnologyList != null && zjXmSalaryProfessionalTechnologyList.size() > 0) {
			ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology = new ZjXmSalaryProfessionalTechnology();
			zjXmSalaryProfessionalTechnology.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryProfessionalTechnologyMapper.batchDeleteUpdateZjXmSalaryProfessionalTechnology(
					zjXmSalaryProfessionalTechnologyList, zjXmSalaryProfessionalTechnology);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryProfessionalTechnologyList);
		}
	}
}
