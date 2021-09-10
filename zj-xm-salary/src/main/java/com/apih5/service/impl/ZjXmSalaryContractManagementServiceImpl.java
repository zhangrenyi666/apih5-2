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
import com.apih5.mybatis.dao.ZjXmSalaryContractManagementMapper;
import com.apih5.mybatis.dao.ZjXmSalaryUserAttachmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryContractManagement;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.service.ZjXmSalaryContractManagementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryContractManagementService")
public class ZjXmSalaryContractManagementServiceImpl implements ZjXmSalaryContractManagementService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryContractManagementMapper zjXmSalaryContractManagementMapper;
	@Autowired(required = true)
	private ZjXmSalaryUserAttachmentMapper zjXmSalaryUserAttachmentMapper;

	@Override
	public ResponseEntity getZjXmSalaryContractManagementListByCondition(
			ZjXmSalaryContractManagement zjXmSalaryContractManagement) {
		if (zjXmSalaryContractManagement == null) {
			zjXmSalaryContractManagement = new ZjXmSalaryContractManagement();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryContractManagement.getPage(), zjXmSalaryContractManagement.getLimit());
		// 获取数据
		List<ZjXmSalaryContractManagement> dbContractList = zjXmSalaryContractManagementMapper
				.selectByZjXmSalaryContractManagementList(zjXmSalaryContractManagement);
		if (dbContractList.size() > 0) {
			for (ZjXmSalaryContractManagement dbContractManagement : dbContractList) {
				// 离职证明和合同附件
				List<ZjXmSalaryUserAttachment> quitAttachmentList = Lists.newArrayList();
				List<ZjXmSalaryUserAttachment> contractAttachmentList = Lists.newArrayList();
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbContractManagement.getContractId());
				List<ZjXmSalaryUserAttachment> attachmentList = zjXmSalaryUserAttachmentMapper
						.selectByZjXmSalaryUserAttachmentList(userAttachment);
				if (attachmentList.size() > 0) {
					for (ZjXmSalaryUserAttachment dbAttachment : attachmentList) {
						if (StrUtil.equals("5", dbAttachment.getFileType())) {
							quitAttachmentList.add(dbAttachment);
						} else if (StrUtil.equals("6", dbAttachment.getFileType())) {
							contractAttachmentList.add(dbAttachment);
						}
					}
					dbContractManagement.setQuitAttachmentList(quitAttachmentList);
					dbContractManagement.setContractAttachmentList(contractAttachmentList);
				}
			}
		}
		// 得到分页信息
		PageInfo<ZjXmSalaryContractManagement> p = new PageInfo<>(dbContractList);

		return repEntity.okList(dbContractList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryContractManagementDetails(
			ZjXmSalaryContractManagement zjXmSalaryContractManagement) {
		if (zjXmSalaryContractManagement == null) {
			zjXmSalaryContractManagement = new ZjXmSalaryContractManagement();
		}
		// 获取数据
		ZjXmSalaryContractManagement dbZjXmSalaryContractManagement = zjXmSalaryContractManagementMapper
				.selectByPrimaryKey(zjXmSalaryContractManagement.getContractId());
		// 数据存在
		if (dbZjXmSalaryContractManagement != null) {
			return repEntity.ok(dbZjXmSalaryContractManagement);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryContractManagement(ZjXmSalaryContractManagement zjXmSalaryContractManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryContractManagement.setContractId(UuidUtil.generate());
		zjXmSalaryContractManagement.setCreateUserInfo(userKey, realName);
		if (zjXmSalaryContractManagement.getStartDate() != null && zjXmSalaryContractManagement.getEndDate() != null) {
			zjXmSalaryContractManagement
					.setContractPeriod((int) DateUtil.betweenMonth(zjXmSalaryContractManagement.getStartDate(),
							zjXmSalaryContractManagement.getEndDate(), true));
		}
		int flag = zjXmSalaryContractManagementMapper.insert(zjXmSalaryContractManagement);
		// 插入离职证明附件
		if (zjXmSalaryContractManagement.getQuitAttachmentList() != null
				&& zjXmSalaryContractManagement.getQuitAttachmentList().size() > 0) {
			for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryContractManagement.getQuitAttachmentList()) {
				insertAttachment.setUid(UuidUtil.generate());
				insertAttachment.setOtherId(zjXmSalaryContractManagement.getContractId());
				insertAttachment.setFileType("5");
				insertAttachment.setCreateUserInfo(userKey, realName);
			}
			flag = zjXmSalaryUserAttachmentMapper
					.batchInsertZjXmSalaryUserAttachment(zjXmSalaryContractManagement.getQuitAttachmentList());
		}
		// 插入合同附件
		if (zjXmSalaryContractManagement.getContractAttachmentList() != null
				&& zjXmSalaryContractManagement.getContractAttachmentList().size() > 0) {
			for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryContractManagement.getContractAttachmentList()) {
				insertAttachment.setUid(UuidUtil.generate());
				insertAttachment.setOtherId(zjXmSalaryContractManagement.getContractId());
				insertAttachment.setFileType("6");
				insertAttachment.setCreateUserInfo(userKey, realName);
			}
			flag = zjXmSalaryUserAttachmentMapper
					.batchInsertZjXmSalaryUserAttachment(zjXmSalaryContractManagement.getContractAttachmentList());
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryContractManagement);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryContractManagement(
			ZjXmSalaryContractManagement zjXmSalaryContractManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryContractManagement dbzjXmSalaryContractManagement = zjXmSalaryContractManagementMapper
				.selectByPrimaryKey(zjXmSalaryContractManagement.getContractId());
		if (dbzjXmSalaryContractManagement != null
				&& StrUtil.isNotEmpty(dbzjXmSalaryContractManagement.getContractId())) {
			// sysUser扩展表主键
			dbzjXmSalaryContractManagement.setExtensionId(zjXmSalaryContractManagement.getExtensionId());
			// 合同编号
			dbzjXmSalaryContractManagement.setContractNo(zjXmSalaryContractManagement.getContractNo());
			// 合同签订时间
			dbzjXmSalaryContractManagement.setSigningDate(zjXmSalaryContractManagement.getSigningDate());
			// 合同类型
			dbzjXmSalaryContractManagement.setContractType(zjXmSalaryContractManagement.getContractType());
			// 劳动合同期限起始时间
			dbzjXmSalaryContractManagement.setStartDate(zjXmSalaryContractManagement.getStartDate());
			// 劳动合同期限截止时间
			dbzjXmSalaryContractManagement.setEndDate(zjXmSalaryContractManagement.getEndDate());
			// 合同期限（月）
			if (zjXmSalaryContractManagement.getStartDate() != null
					&& zjXmSalaryContractManagement.getEndDate() != null) {
				dbzjXmSalaryContractManagement
						.setContractPeriod((int) DateUtil.betweenMonth(zjXmSalaryContractManagement.getStartDate(),
								zjXmSalaryContractManagement.getEndDate(), true));
			}
			// 试用期
			dbzjXmSalaryContractManagement.setProbation(zjXmSalaryContractManagement.getProbation());
			// 签订类型
			dbzjXmSalaryContractManagement.setSigningType(zjXmSalaryContractManagement.getSigningType());
			// 备注
			dbzjXmSalaryContractManagement.setRemarks(zjXmSalaryContractManagement.getRemarks());
			// 排序
			dbzjXmSalaryContractManagement.setSort(zjXmSalaryContractManagement.getSort());
			// 共通
			dbzjXmSalaryContractManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryContractManagementMapper.updateByPrimaryKey(dbzjXmSalaryContractManagement);
			if (flag != 0) {
				// 删除离职证明附件和合同附件
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbzjXmSalaryContractManagement.getContractId());
				userAttachment.setFileType("5");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				userAttachment.setFileType("6");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				// 插入离职证明附件
				if (zjXmSalaryContractManagement.getQuitAttachmentList() != null
						&& zjXmSalaryContractManagement.getQuitAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryContractManagement
							.getQuitAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(zjXmSalaryContractManagement.getContractId());
						insertAttachment.setFileType("5");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper
							.batchInsertZjXmSalaryUserAttachment(zjXmSalaryContractManagement.getQuitAttachmentList());
				}
				// 插入合同附件
				if (zjXmSalaryContractManagement.getContractAttachmentList() != null
						&& zjXmSalaryContractManagement.getContractAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryContractManagement
							.getContractAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(zjXmSalaryContractManagement.getContractId());
						insertAttachment.setFileType("6");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper.batchInsertZjXmSalaryUserAttachment(
							zjXmSalaryContractManagement.getContractAttachmentList());
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryContractManagement);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryContractManagement(
			List<ZjXmSalaryContractManagement> zjXmSalaryContractManagementList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryContractManagementList != null && zjXmSalaryContractManagementList.size() > 0) {
			ZjXmSalaryContractManagement zjXmSalaryContractManagement = new ZjXmSalaryContractManagement();
			zjXmSalaryContractManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryContractManagementMapper.batchDeleteUpdateZjXmSalaryContractManagement(
					zjXmSalaryContractManagementList, zjXmSalaryContractManagement);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryContractManagementList);
		}
	}
}
