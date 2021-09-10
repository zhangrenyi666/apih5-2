package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TreeNodeEntity;
import com.apih5.mybatis.dao.ZjXmSalaryCertificateManagementMapper;
import com.apih5.mybatis.dao.ZjXmSalaryContractManagementMapper;
import com.apih5.mybatis.dao.ZjXmSalaryEducationBackgroundMapper;
import com.apih5.mybatis.dao.ZjXmSalaryFamilyBackgroundMapper;
import com.apih5.mybatis.dao.ZjXmSalaryHealthConditionMapper;
import com.apih5.mybatis.dao.ZjXmSalaryProfessionalTechnologyMapper;
import com.apih5.mybatis.dao.ZjXmSalaryTrainingSituationMapper;
import com.apih5.mybatis.dao.ZjXmSalaryUserAttachmentMapper;
import com.apih5.mybatis.dao.ZjXmSalaryUserExtensionMapper;
import com.apih5.mybatis.dao.ZjXmSalaryWorkExperienceMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryCertificateManagement;
import com.apih5.mybatis.pojo.ZjXmSalaryContractManagement;
import com.apih5.mybatis.pojo.ZjXmSalaryEducationBackground;
import com.apih5.mybatis.pojo.ZjXmSalaryFamilyBackground;
import com.apih5.mybatis.pojo.ZjXmSalaryHealthCondition;
import com.apih5.mybatis.pojo.ZjXmSalaryProfessionalTechnology;
import com.apih5.mybatis.pojo.ZjXmSalaryTrainingSituation;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.mybatis.pojo.ZjXmSalaryUserExtension;
import com.apih5.mybatis.pojo.ZjXmSalaryWorkExperience;
import com.apih5.service.ZjXmSalaryUserExtensionService;
import com.apih5.utils.PinyinUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryUserExtensionService")
public class ZjXmSalaryUserExtensionServiceImpl implements ZjXmSalaryUserExtensionService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryUserExtensionMapper zjXmSalaryUserExtensionMapper;
	@Autowired(required = true)
	private ZjXmSalaryHealthConditionMapper zjXmSalaryHealthConditionMapper;
	@Autowired(required = true)
	private ZjXmSalaryContractManagementMapper zjXmSalaryContractManagementMapper;
	@Autowired(required = true)
	private ZjXmSalaryUserAttachmentMapper zjXmSalaryUserAttachmentMapper;
	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private ZjXmSalaryEducationBackgroundMapper zjXmSalaryEducationBackgroundMapper;
	@Autowired(required = true)
	private ZjXmSalaryWorkExperienceMapper zjXmSalaryWorkExperienceMapper;
	@Autowired(required = true)
	private ZjXmSalaryProfessionalTechnologyMapper zjXmSalaryProfessionalTechnologyMapper;
	@Autowired(required = true)
	private ZjXmSalaryTrainingSituationMapper zjXmSalaryTrainingSituationMapper;
	@Autowired(required = true)
	private ZjXmSalaryFamilyBackgroundMapper zjXmSalaryFamilyBackgroundMapper;
	@Autowired(required = true)
	private ZjXmSalaryCertificateManagementMapper zjXmSalaryCertificateManagementMapper;

	@Override
	public ResponseEntity getZjXmSalaryUserExtensionListByCondition(ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		if (zjXmSalaryUserExtension == null) {
			zjXmSalaryUserExtension = new ZjXmSalaryUserExtension();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryUserExtension.getPage(), zjXmSalaryUserExtension.getLimit());
		// 获取数据
		List<ZjXmSalaryUserExtension> zjXmSalaryUserExtensionList = zjXmSalaryUserExtensionMapper
				.selectByZjXmSalaryUserExtensionList(zjXmSalaryUserExtension);
		// 得到分页信息
		PageInfo<ZjXmSalaryUserExtension> p = new PageInfo<>(zjXmSalaryUserExtensionList);

		return repEntity.okList(zjXmSalaryUserExtensionList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryUserExtensionDetails(ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		if (zjXmSalaryUserExtension == null) {
			zjXmSalaryUserExtension = new ZjXmSalaryUserExtension();
		}
		// 获取数据
		ZjXmSalaryUserExtension dbZjXmSalaryUserExtension = zjXmSalaryUserExtensionMapper
				.selectByPrimaryKey(zjXmSalaryUserExtension.getExtensionId());
		// 数据存在
		if (dbZjXmSalaryUserExtension != null) {
			return repEntity.ok(dbZjXmSalaryUserExtension);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryUserExtension(ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// 将两个模块的对象转换为字段
		ZjXmSalaryUserExtension.toFieldHealthInfo(zjXmSalaryUserExtension);
		// ZjXmSalaryUserExtension.toFieldContractInfo(zjXmSalaryUserExtension);
		ZjXmSalaryUserExtension.toFieldSalaryInfo(zjXmSalaryUserExtension);
		// 处理组织关系的数组
		if (zjXmSalaryUserExtension.getProjectTree() != null && zjXmSalaryUserExtension.getProjectTree().size() > 0) {
			zjXmSalaryUserExtension.setProjectId(zjXmSalaryUserExtension.getProjectTree().get(0).getValue());
		}
		if (zjXmSalaryUserExtension.getDepartmentTree() != null
				&& zjXmSalaryUserExtension.getDepartmentTree().size() > 0) {
			zjXmSalaryUserExtension.setDepartmentId(zjXmSalaryUserExtension.getDepartmentTree().get(0).getValue());
		}
		if (zjXmSalaryUserExtension.getOfficeTree() != null && zjXmSalaryUserExtension.getOfficeTree().size() > 0) {
			zjXmSalaryUserExtension.setOfficeId(zjXmSalaryUserExtension.getOfficeTree().get(0).getValue());
		}
//		if (zjXmSalaryUserExtension.getWageProjectTree() != null
//				&& zjXmSalaryUserExtension.getWageProjectTree().size() > 0) {
//			zjXmSalaryUserExtension.setWageOfProjectId(zjXmSalaryUserExtension.getWageProjectTree().get(0).getValue());
//		}
		zjXmSalaryUserExtension.setExtensionId(UuidUtil.generate());
		zjXmSalaryUserExtension.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryUserExtensionMapper.insert(zjXmSalaryUserExtension);
		// 插入近照附件
		if (zjXmSalaryUserExtension.getLatestAttachmentList() != null
				&& zjXmSalaryUserExtension.getLatestAttachmentList().size() > 0) {
			for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryUserExtension.getLatestAttachmentList()) {
				insertAttachment.setUid(UuidUtil.generate());
				insertAttachment.setOtherId(zjXmSalaryUserExtension.getExtensionId());
				insertAttachment.setFileType("0");
				insertAttachment.setCreateUserInfo(userKey, realName);
			}
			flag = zjXmSalaryUserAttachmentMapper
					.batchInsertZjXmSalaryUserAttachment(zjXmSalaryUserExtension.getLatestAttachmentList());
		}
		// 插入身份证附件
		if (zjXmSalaryUserExtension.getIdAttachmentList() != null
				&& zjXmSalaryUserExtension.getIdAttachmentList().size() > 0) {
			for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryUserExtension.getIdAttachmentList()) {
				insertAttachment.setUid(UuidUtil.generate());
				insertAttachment.setOtherId(zjXmSalaryUserExtension.getExtensionId());
				insertAttachment.setFileType("1");
				insertAttachment.setCreateUserInfo(userKey, realName);
			}
			flag = zjXmSalaryUserAttachmentMapper
					.batchInsertZjXmSalaryUserAttachment(zjXmSalaryUserExtension.getIdAttachmentList());
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryUserExtension);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryUserExtension(ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// 将两个模块的对象转换为字段
		ZjXmSalaryUserExtension.toFieldHealthInfo(zjXmSalaryUserExtension);
		// ZjXmSalaryUserExtension.toFieldContractInfo(zjXmSalaryUserExtension);
		ZjXmSalaryUserExtension.toFieldSalaryInfo(zjXmSalaryUserExtension);
		// 处理组织关系的数组
		if (zjXmSalaryUserExtension.getProjectTree() != null && zjXmSalaryUserExtension.getProjectTree().size() > 0) {
			zjXmSalaryUserExtension.setProjectId(zjXmSalaryUserExtension.getProjectTree().get(0).getValue());
		}
		if (zjXmSalaryUserExtension.getDepartmentTree() != null
				&& zjXmSalaryUserExtension.getDepartmentTree().size() > 0) {
			zjXmSalaryUserExtension.setDepartmentId(zjXmSalaryUserExtension.getDepartmentTree().get(0).getValue());
		}
		if (zjXmSalaryUserExtension.getOfficeTree() != null && zjXmSalaryUserExtension.getOfficeTree().size() > 0) {
			zjXmSalaryUserExtension.setOfficeId(zjXmSalaryUserExtension.getOfficeTree().get(0).getValue());
		}
//		if (zjXmSalaryUserExtension.getWageProjectTree() != null
//				&& zjXmSalaryUserExtension.getWageProjectTree().size() > 0) {
//			zjXmSalaryUserExtension.setWageOfProjectId(zjXmSalaryUserExtension.getWageProjectTree().get(0).getValue());
//		}
		int flag = 0;
		ZjXmSalaryUserExtension dbzjXmSalaryUserExtension = zjXmSalaryUserExtensionMapper
				.selectByPrimaryKey(zjXmSalaryUserExtension.getExtensionId());
		if (dbzjXmSalaryUserExtension != null && StrUtil.isNotEmpty(dbzjXmSalaryUserExtension.getExtensionId())) {
			// sysUser表主键
			// dbzjXmSalaryUserExtension.setUserKey(zjXmSalaryUserExtension.getUserKey());
			// 姓名
			dbzjXmSalaryUserExtension.setRealName(zjXmSalaryUserExtension.getRealName());
			// 性别
			dbzjXmSalaryUserExtension.setGender(zjXmSalaryUserExtension.getGender());
			// 民族
			dbzjXmSalaryUserExtension.setNation(zjXmSalaryUserExtension.getNation());
			// 出生年月日
			dbzjXmSalaryUserExtension.setBirthday(zjXmSalaryUserExtension.getBirthday());
			// 籍贯(省市县code)
			dbzjXmSalaryUserExtension.setNativePlace(zjXmSalaryUserExtension.getNativePlace());
			// 政治面貌
			dbzjXmSalaryUserExtension.setPoliticCountenance(zjXmSalaryUserExtension.getPoliticCountenance());
			// 证件类型
			dbzjXmSalaryUserExtension.setIdType(zjXmSalaryUserExtension.getIdType());
			// 证件号码
			dbzjXmSalaryUserExtension.setIdNumber(zjXmSalaryUserExtension.getIdNumber());
			// 人员状态
			dbzjXmSalaryUserExtension.setUserStatus(zjXmSalaryUserExtension.getUserStatus());
			// 现居住地(省市县code)
			dbzjXmSalaryUserExtension.setPresentAddress(zjXmSalaryUserExtension.getPresentAddress());
			// 现居住地(详细地址)
			dbzjXmSalaryUserExtension.setPresentDetailedAddress(zjXmSalaryUserExtension.getPresentDetailedAddress());
			// 户口所在地(省市县code)
			dbzjXmSalaryUserExtension.setResidenceAddress(zjXmSalaryUserExtension.getResidenceAddress());
			// 户口所在地(详细地址)
			dbzjXmSalaryUserExtension
					.setResidenceDetailedAddress(zjXmSalaryUserExtension.getResidenceDetailedAddress());
			// 法律文书送达地址(省市县code)
			dbzjXmSalaryUserExtension.setLegalAddress(zjXmSalaryUserExtension.getLegalAddress());
			// 法律文书送达地址(详细地址)
			dbzjXmSalaryUserExtension.setLegalDetailedAddress(zjXmSalaryUserExtension.getLegalDetailedAddress());
			// 邮编
			dbzjXmSalaryUserExtension.setPostalCode(zjXmSalaryUserExtension.getPostalCode());
			// 联系电话
			dbzjXmSalaryUserExtension.setPhoneNumber(zjXmSalaryUserExtension.getPhoneNumber());
			// 婚姻状况
			dbzjXmSalaryUserExtension.setMaritalStatus(zjXmSalaryUserExtension.getMaritalStatus());
			// 参加工作时间
			dbzjXmSalaryUserExtension.setWorkFirstDate(zjXmSalaryUserExtension.getWorkFirstDate());
			// 入职时间
			dbzjXmSalaryUserExtension.setHiredate(zjXmSalaryUserExtension.getHiredate());
			// 人员类别
			dbzjXmSalaryUserExtension.setUserType(zjXmSalaryUserExtension.getUserType());
			// 岗位
			dbzjXmSalaryUserExtension.setPosition(zjXmSalaryUserExtension.getPosition());
			// 单位或项目
			dbzjXmSalaryUserExtension.setProjectId(zjXmSalaryUserExtension.getProjectId());
			// 所属部门
			dbzjXmSalaryUserExtension.setDepartmentId(zjXmSalaryUserExtension.getDepartmentId());
			// 所属科室
			dbzjXmSalaryUserExtension.setOfficeId(zjXmSalaryUserExtension.getOfficeId());
			// 爱好及特长
			dbzjXmSalaryUserExtension.setHobby(zjXmSalaryUserExtension.getHobby());
			// 身高
			dbzjXmSalaryUserExtension.setHeight(zjXmSalaryUserExtension.getHeight());
			// 体重
			dbzjXmSalaryUserExtension.setWeight(zjXmSalaryUserExtension.getWeight());
			// 血型
			dbzjXmSalaryUserExtension.setBloodType(zjXmSalaryUserExtension.getBloodType());
			// 岗位等级id
			dbzjXmSalaryUserExtension.setLevelId(zjXmSalaryUserExtension.getLevelId());
			// 档位岗薪id
			dbzjXmSalaryUserExtension.setSalaryId(zjXmSalaryUserExtension.getSalaryId());
			// 岗级及岗薪id
			dbzjXmSalaryUserExtension.setLevelSalaryId(zjXmSalaryUserExtension.getLevelSalaryId());
			// 会计分类
			dbzjXmSalaryUserExtension.setAccountingType(zjXmSalaryUserExtension.getAccountingType());
			// 社保参保地
			dbzjXmSalaryUserExtension.setSocialInsuranceArea(zjXmSalaryUserExtension.getSocialInsuranceArea());
			// 公积金参保地
			dbzjXmSalaryUserExtension.setProvidentFundArea(zjXmSalaryUserExtension.getProvidentFundArea());
			// 工资关系所在项目
			dbzjXmSalaryUserExtension.setWageOfProjectId(zjXmSalaryUserExtension.getWageOfProjectId());
			// 外在单位
			dbzjXmSalaryUserExtension.setExternalUnit(zjXmSalaryUserExtension.getExternalUnit());
			// 参与课题
			dbzjXmSalaryUserExtension.setInvolvedTopic(zjXmSalaryUserExtension.getInvolvedTopic());
			// 共通
			dbzjXmSalaryUserExtension.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryUserExtensionMapper.updateByPrimaryKey(dbzjXmSalaryUserExtension);
			if (flag != 0) {
				// 删除近照附件和身份证附件
				ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
				userAttachment.setOtherId(dbzjXmSalaryUserExtension.getExtensionId());
				userAttachment.setFileType("0");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				userAttachment.setFileType("1");
				zjXmSalaryUserAttachmentMapper.deleteZjXmSalaryUserAttachmentByCondition(userAttachment);
				// 插入近照附件
				if (zjXmSalaryUserExtension.getLatestAttachmentList() != null
						&& zjXmSalaryUserExtension.getLatestAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryUserExtension
							.getLatestAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(zjXmSalaryUserExtension.getExtensionId());
						insertAttachment.setFileType("0");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper
							.batchInsertZjXmSalaryUserAttachment(zjXmSalaryUserExtension.getLatestAttachmentList());
				}
				// 插入身份证附件
				if (zjXmSalaryUserExtension.getIdAttachmentList() != null
						&& zjXmSalaryUserExtension.getIdAttachmentList().size() > 0) {
					for (ZjXmSalaryUserAttachment insertAttachment : zjXmSalaryUserExtension.getIdAttachmentList()) {
						insertAttachment.setUid(UuidUtil.generate());
						insertAttachment.setOtherId(zjXmSalaryUserExtension.getExtensionId());
						insertAttachment.setFileType("1");
						insertAttachment.setCreateUserInfo(userKey, realName);
					}
					flag = zjXmSalaryUserAttachmentMapper
							.batchInsertZjXmSalaryUserAttachment(zjXmSalaryUserExtension.getIdAttachmentList());
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryUserExtension);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryUserExtension(
			List<ZjXmSalaryUserExtension> zjXmSalaryUserExtensionList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryUserExtensionList != null && zjXmSalaryUserExtensionList.size() > 0) {
			ZjXmSalaryUserExtension zjXmSalaryUserExtension = new ZjXmSalaryUserExtension();
			zjXmSalaryUserExtension.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryUserExtensionMapper.batchDeleteUpdateZjXmSalaryUserExtension(zjXmSalaryUserExtensionList,
					zjXmSalaryUserExtension);
			if (flag != 0) {
				// 删除学历情况
				ZjXmSalaryEducationBackground zjXmSalaryEducationBackground = new ZjXmSalaryEducationBackground();
				zjXmSalaryEducationBackground.setModifyUserInfo(userKey, realName);
				zjXmSalaryEducationBackgroundMapper.batchDeleteZjXmSalaryEducationBackgroundByExtensionId(
						zjXmSalaryUserExtensionList, zjXmSalaryEducationBackground);
				// 删除工作履历
				ZjXmSalaryWorkExperience zjXmSalaryWorkExperience = new ZjXmSalaryWorkExperience();
				zjXmSalaryWorkExperience.setModifyUserInfo(userKey, realName);
				zjXmSalaryWorkExperienceMapper.batchDeleteZjXmSalaryWorkExperienceByExtensionId(
						zjXmSalaryUserExtensionList, zjXmSalaryWorkExperience);
				// 删除专业技术
				ZjXmSalaryProfessionalTechnology zjXmSalaryProfessionalTechnology = new ZjXmSalaryProfessionalTechnology();
				zjXmSalaryProfessionalTechnology.setModifyUserInfo(userKey, realName);
				zjXmSalaryProfessionalTechnologyMapper.batchDeleteZjXmSalaryProfessionalTechnologyByExtensionId(
						zjXmSalaryUserExtensionList, zjXmSalaryProfessionalTechnology);
				// 删除合同管理
				ZjXmSalaryContractManagement zjXmSalaryContractManagement = new ZjXmSalaryContractManagement();
				zjXmSalaryContractManagement.setModifyUserInfo(userKey, realName);
				zjXmSalaryContractManagementMapper.batchDeleteZjXmSalaryContractManagementByExtensionId(
						zjXmSalaryUserExtensionList, zjXmSalaryContractManagement);
				// 删除培训情况
				ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation = new ZjXmSalaryTrainingSituation();
				zjXmSalaryTrainingSituation.setModifyUserInfo(userKey, realName);
				zjXmSalaryTrainingSituationMapper.batchDeleteZjXmSalaryTrainingSituationByExtensionId(
						zjXmSalaryUserExtensionList, zjXmSalaryTrainingSituation);
				// 删除家庭状况
				ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground = new ZjXmSalaryFamilyBackground();
				zjXmSalaryFamilyBackground.setModifyUserInfo(userKey, realName);
				zjXmSalaryFamilyBackgroundMapper.batchDeleteZjXmSalaryFamilyBackgroundByExtensionId(
						zjXmSalaryUserExtensionList, zjXmSalaryFamilyBackground);
				// 删除健康情况
				ZjXmSalaryHealthCondition zjXmSalaryHealthCondition = new ZjXmSalaryHealthCondition();
				zjXmSalaryHealthCondition.setModifyUserInfo(userKey, realName);
				zjXmSalaryHealthConditionMapper.batchDeleteZjXmSalaryHealthConditionByExtensionId(
						zjXmSalaryUserExtensionList, zjXmSalaryHealthCondition);
				// 删除证书管理
				ZjXmSalaryCertificateManagement zjXmSalaryCertificateManagement = new ZjXmSalaryCertificateManagement();
				zjXmSalaryCertificateManagement.setModifyUserInfo(userKey, realName);
				zjXmSalaryCertificateManagementMapper.batchDeleteZjXmSalaryCertificateManagementByExtensionId(
						zjXmSalaryUserExtensionList, zjXmSalaryCertificateManagement);
				// 删除政治面貌(待定)
				// 删除sys_user账号
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryUserExtensionList);
		}
	}

	@Override
	public ResponseEntity pcGetZjXmSalaryUserExtensionList(ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		if (zjXmSalaryUserExtension == null) {
			zjXmSalaryUserExtension = new ZjXmSalaryUserExtension();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryUserExtension.getPage(), zjXmSalaryUserExtension.getLimit());
		// 获取数据
		List<ZjXmSalaryUserExtension> zjXmSalaryUserExtensionList = zjXmSalaryUserExtensionMapper
				.getZjXmSalaryUserExtensionListByCondition(zjXmSalaryUserExtension);
		// 得到分页信息
		PageInfo<ZjXmSalaryUserExtension> p = new PageInfo<>(zjXmSalaryUserExtensionList);

		return repEntity.okList(zjXmSalaryUserExtensionList, p.getTotal());
	}

	@Override
	public ResponseEntity pcGetZjXmSalaryUserExtensionDetails(ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		if (zjXmSalaryUserExtension == null) {
			zjXmSalaryUserExtension = new ZjXmSalaryUserExtension();
		}
		// 获取数据
		ZjXmSalaryUserExtension dbUserExtension = zjXmSalaryUserExtensionMapper
				.getZjXmSalaryUserExtensionDetails(zjXmSalaryUserExtension);
		// 数据存在
		if (dbUserExtension != null) {
			// 获取健康情况表字段
			ZjXmSalaryHealthCondition dbHealthCondition = zjXmSalaryHealthConditionMapper
					.getZjXmSalaryHealthConditionByExtensionId(dbUserExtension.getExtensionId());
			if (dbHealthCondition != null) {
				dbUserExtension.setPhysicalType(dbHealthCondition.getPhysicalType());
				dbUserExtension.setHealthCondition(dbHealthCondition.getHealthCondition());
				dbUserExtension.setOccupationalDisease(dbHealthCondition.getOccupationalDisease());
			}
			// 获取合同情况表字段
			ZjXmSalaryContractManagement dbContract = zjXmSalaryContractManagementMapper
					.getZjXmSalaryContractManagementByExtensionId(dbUserExtension.getExtensionId());
			if (dbContract != null) {
				dbUserExtension.setContractNo(dbContract.getContractNo());
				dbUserExtension.setSigningDate(dbContract.getSigningDate());
				dbUserExtension.setContractType(dbContract.getContractType());
				dbUserExtension.setContractStartDate(dbContract.getStartDate());
				dbUserExtension.setContractEndDate(dbContract.getEndDate());
			}
			// 近照附件和身份证附件
			List<ZjXmSalaryUserAttachment> latestAttachmentList = Lists.newArrayList();
			List<ZjXmSalaryUserAttachment> idAttachmentList = Lists.newArrayList();
			ZjXmSalaryUserAttachment userAttachment = new ZjXmSalaryUserAttachment();
			userAttachment.setOtherId(dbUserExtension.getExtensionId());
			List<ZjXmSalaryUserAttachment> attachmentList = zjXmSalaryUserAttachmentMapper
					.selectByZjXmSalaryUserAttachmentList(userAttachment);
			if (attachmentList.size() > 0) {
				for (ZjXmSalaryUserAttachment dbAttachment : attachmentList) {
					if (StrUtil.equals("0", dbAttachment.getFileType())) {
						latestAttachmentList.add(dbAttachment);
					} else if (StrUtil.equals("1", dbAttachment.getFileType())) {
						idAttachmentList.add(dbAttachment);
					}
				}
				dbUserExtension.setLatestAttachmentList(latestAttachmentList);
				dbUserExtension.setIdAttachmentList(idAttachmentList);
			}
			// 处理三个模块
			ZjXmSalaryUserExtension.toObjectHealthInfo(dbUserExtension);
			ZjXmSalaryUserExtension.toObjectContractInfo(dbUserExtension);
			ZjXmSalaryUserExtension.toObjectSalaryInfo(dbUserExtension);

			// 处理组织关系的数组
			TreeNodeEntity projectTree = new TreeNodeEntity();
			projectTree.setValue(dbUserExtension.getProjectId());
			projectTree.setLabel(dbUserExtension.getProjectName());
			dbUserExtension.setProjectTree(Lists.newArrayList(projectTree));
			TreeNodeEntity departmentTree = new TreeNodeEntity();
			departmentTree.setValue(dbUserExtension.getDepartmentId());
			departmentTree.setLabel(dbUserExtension.getDepartmentName());
			dbUserExtension.setDepartmentTree(Lists.newArrayList(departmentTree));
			TreeNodeEntity officeTree = new TreeNodeEntity();
			officeTree.setValue(dbUserExtension.getOfficeId());
			officeTree.setLabel(dbUserExtension.getOfficeName());
			dbUserExtension.setOfficeTree(Lists.newArrayList(officeTree));
			return repEntity.ok(dbUserExtension);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity generateZjXmSalaryUserExtensionAccount(ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		if (zjXmSalaryUserExtension == null) {
			zjXmSalaryUserExtension = new ZjXmSalaryUserExtension();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String accountId = TokenUtils.getAccountId(request);
		int flag = 0;
		// 获取数据
		ZjXmSalaryUserExtension dbUserExtension = zjXmSalaryUserExtensionMapper
				.getZjXmSalaryUserExtensionDetails(zjXmSalaryUserExtension);
		if (dbUserExtension != null) {
			// check根据id_number身份证号码判断sys_user里面是否存在账号
			SysUser check = new SysUser();
			check.setUserId(dbUserExtension.getIdNumber());
			SysUser dbUser = userService.checkUserExists(check);
			// userService.checkUserIdExists(sysUser);
			if (dbUser != null) {
				return repEntity.layerMessage("no", "该用户已存在系统账号。");
			}
			SysUser sysUser = new SysUser();
			sysUser.setUserKey(UuidUtil.generate());
			sysUser.setAccountId(accountId);
			// sysUser.setUserId(PinyinUtils.getPinYinFullChar(dbUserExtension.getRealName()));
			sysUser.setUserId(dbUserExtension.getIdNumber());
			sysUser.setRealName(dbUserExtension.getRealName());
			sysUser.setUserPwd("123456");
			sysUser.setMobile(dbUserExtension.getPhoneNumber());
			// 经理书记
			sysUser.setJobType("");
			// 类型 0:项目副职 1:部门负责人 2:普通员工
			sysUser.setExt2("2");
			sysUser.setUserType("1");
			sysUser.setUserStatus("1");
			// 新增账号
			flag = userService.addUserCommon(sysUser);
			// 修改扩展表的userKey
			dbUserExtension.setUserKey(sysUser.getUserKey());
			zjXmSalaryUserExtensionMapper.updateByPrimaryKey(dbUserExtension);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("账号生成成功。");
		}
	}

	@Override
	public ResponseEntity getZjXmSalaryUserExtensionListByDept(ZjXmSalaryUserExtension zjXmSalaryUserExtension) {
		if (zjXmSalaryUserExtension == null) {
			zjXmSalaryUserExtension = new ZjXmSalaryUserExtension();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryUserExtension.getPage(), zjXmSalaryUserExtension.getLimit());
		// 获取数据
		List<ZjXmSalaryUserExtension> zjXmSalaryUserExtensionList = zjXmSalaryUserExtensionMapper
				.getZjXmSalaryUserExtensionListByDept(zjXmSalaryUserExtension);
		// 得到分页信息
		PageInfo<ZjXmSalaryUserExtension> p = new PageInfo<>(zjXmSalaryUserExtensionList);

		return repEntity.okList(zjXmSalaryUserExtensionList, p.getTotal());
	}
}
