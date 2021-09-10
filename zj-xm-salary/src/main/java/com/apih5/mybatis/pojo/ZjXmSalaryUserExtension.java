package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;
import com.apih5.framework.entity.TreeNodeEntity;
import com.google.common.collect.Lists;

public class ZjXmSalaryUserExtension extends BasePojo {
	private String extensionId;

	private String userKey;

	private String realName;

	private String gender;

	private String nation;

	private Date birthday;

	private String nativePlace;

	private String politicCountenance;

	private String idType;

	private String idNumber;

	private String userStatus;

	private String presentAddress;

	private String presentDetailedAddress;

	private String residenceAddress;

	private String residenceDetailedAddress;

	private String legalAddress;

	private String legalDetailedAddress;

	private String postalCode;

	private String phoneNumber;

	private String maritalStatus;

	private Date workFirstDate;

	private Date hiredate;

	private String userType;

	private String position;

	private String projectId;

	private String departmentId;

	private String departmentName;

	private String departmentPath;

	private String departmentPathName;

	private String salaryTemplate;

	private String officeId;

	private String hobby;

	private BigDecimal height;

	private BigDecimal weight;

	private String bloodType;

	private String levelId;

	private String salaryId;

	private String levelSalaryId;

	private String accountingType;

	private String socialInsuranceArea;

	private String providentFundArea;

	private String wageOfProjectId;

	private String externalUnit;

	private String involvedTopic;

	private String opinionField1;

	private String opinionField2;

	private String opinionField3;

	private String opinionField4;

	private String opinionField5;

	private String apih5FlowId;

	private String workId;

	private String apih5FlowStatus;

	private String apih5FlowNodeStatus;

	private String remarks;

	private Integer sort;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;
	// 以下为联查扩展字段
	// 岗级
	// private String positionLevel;
	// 岗薪
	// private BigDecimal positionSalary;
	// 项目名
	private String projectName;
	// 部门名
	// private String departmentName;
	// 科室名
	private String officeName;
	// 工资关系所在项目名
	private String wageProjectName;
	// 近照
	private List<ZjXmSalaryUserAttachment> latestAttachmentList;
	// 身份证附件
	private List<ZjXmSalaryUserAttachment> idAttachmentList;
	// 职称名称
	private String title;
	// 证书名称(证书类别=执业资格)→职业资格
	private String certificateName;
	// 主、兼职
	// private String positionType;
	// 兼职岗位
	private String experiencePosition;
	// 体检类型
	private String physicalType;
	// 健康情况
	private String healthCondition;
	// 职业病情况
	private String occupationalDisease;
	// 合同编号
	private String contractNo;
	// 签订时间
	private Date signingDate;
	// 合同类型
	private String contractType;
	// 劳动合同期限起始时间
	private Date contractStartDate;
	// 劳动合同期限截止时间
	private Date contractEndDate;
	// 健康情况模块
	private ZjXmSalaryUserExtension healthInfo;
	// 合同情况模块
	private ZjXmSalaryUserExtension contractInfo;
	// 薪资模块
	private ZjXmSalaryUserExtension salaryInfo;
	// 项目集合
	private List<TreeNodeEntity> projectTree;
	// 部门集合
	private List<TreeNodeEntity> departmentTree;
	// 科室集合
	private List<TreeNodeEntity> officeTree;
	// 工资关系所在项目集合
	private List<TreeNodeEntity> wageProjectTree;

	public List<TreeNodeEntity> getProjectTree() {
		return projectTree;
	}

	public void setProjectTree(List<TreeNodeEntity> projectTree) {
		this.projectTree = projectTree;
	}

	public List<TreeNodeEntity> getDepartmentTree() {
		return departmentTree;
	}

	public void setDepartmentTree(List<TreeNodeEntity> departmentTree) {
		this.departmentTree = departmentTree;
	}

	public List<TreeNodeEntity> getOfficeTree() {
		return officeTree;
	}

	public void setOfficeTree(List<TreeNodeEntity> officeTree) {
		this.officeTree = officeTree;
	}

	public List<TreeNodeEntity> getWageProjectTree() {
		return wageProjectTree;
	}

	public void setWageProjectTree(List<TreeNodeEntity> wageProjectTree) {
		this.wageProjectTree = wageProjectTree;
	}

	public List<ZjXmSalaryUserAttachment> getLatestAttachmentList() {
		return latestAttachmentList;
	}

	public void setLatestAttachmentList(List<ZjXmSalaryUserAttachment> latestAttachmentList) {
		this.latestAttachmentList = latestAttachmentList;
	}

	public ZjXmSalaryUserExtension getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(ZjXmSalaryUserExtension healthInfo) {
		this.healthInfo = healthInfo;
	}

	public ZjXmSalaryUserExtension getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(ZjXmSalaryUserExtension contractInfo) {
		this.contractInfo = contractInfo;
	}

	public ZjXmSalaryUserExtension getSalaryInfo() {
		return salaryInfo;
	}

	public void setSalaryInfo(ZjXmSalaryUserExtension salaryInfo) {
		this.salaryInfo = salaryInfo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

//	public String getPositionType() {
//		return positionType;
//	}
//
//	public void setPositionType(String positionType) {
//		this.positionType = positionType;
//	}

	public String getExperiencePosition() {
		return experiencePosition;
	}

	public void setExperiencePosition(String experiencePosition) {
		this.experiencePosition = experiencePosition;
	}

	public String getPhysicalType() {
		return physicalType;
	}

	public void setPhysicalType(String physicalType) {
		this.physicalType = physicalType;
	}

	public String getHealthCondition() {
		return healthCondition;
	}

	public void setHealthCondition(String healthCondition) {
		this.healthCondition = healthCondition;
	}

	public String getOccupationalDisease() {
		return occupationalDisease;
	}

	public void setOccupationalDisease(String occupationalDisease) {
		this.occupationalDisease = occupationalDisease;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public List<ZjXmSalaryUserAttachment> getIdAttachmentList() {
		return idAttachmentList;
	}

	public void setIdAttachmentList(List<ZjXmSalaryUserAttachment> idAttachmentList) {
		this.idAttachmentList = idAttachmentList;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getWageProjectName() {
		return wageProjectName;
	}

	public void setWageProjectName(String wageProjectName) {
		this.wageProjectName = wageProjectName;
	}

	public String getExtensionId() {
		return extensionId == null ? "" : extensionId.trim();
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId == null ? null : extensionId.trim();
	}

	public String getUserKey() {
		return userKey == null ? "" : userKey.trim();
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey == null ? null : userKey.trim();
	}

	public String getRealName() {
		return realName == null ? "" : realName.trim();
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public String getGender() {
		return gender == null ? "" : gender.trim();
	}

	public void setGender(String gender) {
		this.gender = gender == null ? null : gender.trim();
	}

	public String getNation() {
		return nation == null ? "" : nation.trim();
	}

	public void setNation(String nation) {
		this.nation = nation == null ? null : nation.trim();
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNativePlace() {
		return nativePlace == null ? "" : nativePlace.trim();
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace == null ? null : nativePlace.trim();
	}

	public String getPoliticCountenance() {
		return politicCountenance == null ? "" : politicCountenance.trim();
	}

	public void setPoliticCountenance(String politicCountenance) {
		this.politicCountenance = politicCountenance == null ? null : politicCountenance.trim();
	}

	public String getIdType() {
		return idType == null ? "" : idType.trim();
	}

	public void setIdType(String idType) {
		this.idType = idType == null ? null : idType.trim();
	}

	public String getIdNumber() {
		return idNumber == null ? "" : idNumber.trim();
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber == null ? null : idNumber.trim();
	}

	public String getUserStatus() {
		return userStatus == null ? "" : userStatus.trim();
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus == null ? null : userStatus.trim();
	}

	public String getPresentAddress() {
		return presentAddress == null ? "" : presentAddress.trim();
	}

	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress == null ? null : presentAddress.trim();
	}

	public String getPresentDetailedAddress() {
		return presentDetailedAddress == null ? "" : presentDetailedAddress.trim();
	}

	public void setPresentDetailedAddress(String presentDetailedAddress) {
		this.presentDetailedAddress = presentDetailedAddress == null ? null : presentDetailedAddress.trim();
	}

	public String getResidenceAddress() {
		return residenceAddress == null ? "" : residenceAddress.trim();
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress == null ? null : residenceAddress.trim();
	}

	public String getResidenceDetailedAddress() {
		return residenceDetailedAddress == null ? "" : residenceDetailedAddress.trim();
	}

	public void setResidenceDetailedAddress(String residenceDetailedAddress) {
		this.residenceDetailedAddress = residenceDetailedAddress == null ? null : residenceDetailedAddress.trim();
	}

	public String getLegalAddress() {
		return legalAddress == null ? "" : legalAddress.trim();
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress == null ? null : legalAddress.trim();
	}

	public String getLegalDetailedAddress() {
		return legalDetailedAddress == null ? "" : legalDetailedAddress.trim();
	}

	public void setLegalDetailedAddress(String legalDetailedAddress) {
		this.legalDetailedAddress = legalDetailedAddress == null ? null : legalDetailedAddress.trim();
	}

	public String getPostalCode() {
		return postalCode == null ? "" : postalCode.trim();
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode == null ? null : postalCode.trim();
	}

	public String getPhoneNumber() {
		return phoneNumber == null ? "" : phoneNumber.trim();
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
	}

	public String getMaritalStatus() {
		return maritalStatus == null ? "" : maritalStatus.trim();
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus == null ? null : maritalStatus.trim();
	}

	public Date getWorkFirstDate() {
		return workFirstDate;
	}

	public void setWorkFirstDate(Date workFirstDate) {
		this.workFirstDate = workFirstDate;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getUserType() {
		return userType == null ? "" : userType.trim();
	}

	public void setUserType(String userType) {
		this.userType = userType == null ? null : userType.trim();
	}

	public String getPosition() {
		return position == null ? "" : position.trim();
	}

	public void setPosition(String position) {
		this.position = position == null ? null : position.trim();
	}

	public String getProjectId() {
		return projectId == null ? "" : projectId.trim();
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public String getDepartmentId() {
		return departmentId == null ? "" : departmentId.trim();
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId == null ? null : departmentId.trim();
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentPath() {
		return departmentPath;
	}

	public void setDepartmentPath(String departmentPath) {
		this.departmentPath = departmentPath;
	}

	public String getDepartmentPathName() {
		return departmentPathName;
	}

	public void setDepartmentPathName(String departmentPathName) {
		this.departmentPathName = departmentPathName;
	}

	public String getSalaryTemplate() {
		return salaryTemplate;
	}

	public void setSalaryTemplate(String salaryTemplate) {
		this.salaryTemplate = salaryTemplate;
	}

	public String getOfficeId() {
		return officeId == null ? "" : officeId.trim();
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId == null ? null : officeId.trim();
	}

	public String getHobby() {
		return hobby == null ? "" : hobby.trim();
	}

	public void setHobby(String hobby) {
		this.hobby = hobby == null ? null : hobby.trim();
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getBloodType() {
		return bloodType == null ? "" : bloodType.trim();
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType == null ? null : bloodType.trim();
	}

	public String getLevelId() {
		return levelId == null ? "" : levelId.trim();
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId == null ? null : levelId.trim();
	}

	public String getSalaryId() {
		return salaryId == null ? "" : salaryId.trim();
	}

	public void setSalaryId(String salaryId) {
		this.salaryId = salaryId == null ? null : salaryId.trim();
	}

	public String getLevelSalaryId() {
		return levelSalaryId == null ? "" : levelSalaryId.trim();
	}

	public void setLevelSalaryId(String levelSalaryId) {
		this.levelSalaryId = levelSalaryId == null ? null : levelSalaryId.trim();
	}

	public String getAccountingType() {
		return accountingType == null ? "" : accountingType.trim();
	}

	public void setAccountingType(String accountingType) {
		this.accountingType = accountingType == null ? null : accountingType.trim();
	}

	public String getSocialInsuranceArea() {
		return socialInsuranceArea == null ? "" : socialInsuranceArea.trim();
	}

	public void setSocialInsuranceArea(String socialInsuranceArea) {
		this.socialInsuranceArea = socialInsuranceArea == null ? null : socialInsuranceArea.trim();
	}

	public String getProvidentFundArea() {
		return providentFundArea == null ? "" : providentFundArea.trim();
	}

	public void setProvidentFundArea(String providentFundArea) {
		this.providentFundArea = providentFundArea == null ? null : providentFundArea.trim();
	}

	public String getWageOfProjectId() {
		return wageOfProjectId == null ? "" : wageOfProjectId.trim();
	}

	public void setWageOfProjectId(String wageOfProjectId) {
		this.wageOfProjectId = wageOfProjectId == null ? null : wageOfProjectId.trim();
	}

	public String getExternalUnit() {
		return externalUnit == null ? "" : externalUnit.trim();
	}

	public void setExternalUnit(String externalUnit) {
		this.externalUnit = externalUnit == null ? null : externalUnit.trim();
	}

	public String getInvolvedTopic() {
		return involvedTopic == null ? "" : involvedTopic.trim();
	}

	public void setInvolvedTopic(String involvedTopic) {
		this.involvedTopic = involvedTopic == null ? null : involvedTopic.trim();
	}

	public String getOpinionField1() {
		return opinionField1;
	}

	public void setOpinionField1(String opinionField1) {
		this.opinionField1 = opinionField1;
	}

	public String getOpinionField2() {
		return opinionField2;
	}

	public void setOpinionField2(String opinionField2) {
		this.opinionField2 = opinionField2;
	}

	public String getOpinionField3() {
		return opinionField3;
	}

	public void setOpinionField3(String opinionField3) {
		this.opinionField3 = opinionField3;
	}

	public String getOpinionField4() {
		return opinionField4;
	}

	public void setOpinionField4(String opinionField4) {
		this.opinionField4 = opinionField4;
	}

	public String getOpinionField5() {
		return opinionField5;
	}

	public void setOpinionField5(String opinionField5) {
		this.opinionField5 = opinionField5;
	}

	public String getApih5FlowId() {
		return apih5FlowId;
	}

	public void setApih5FlowId(String apih5FlowId) {
		this.apih5FlowId = apih5FlowId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getApih5FlowStatus() {
		return apih5FlowStatus;
	}

	public void setApih5FlowStatus(String apih5FlowStatus) {
		this.apih5FlowStatus = apih5FlowStatus;
	}

	public String getApih5FlowNodeStatus() {
		return apih5FlowNodeStatus;
	}

	public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
		this.apih5FlowNodeStatus = apih5FlowNodeStatus;
	}

	public String getRemarks() {
		return remarks == null ? "" : remarks.trim();
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDelFlag() {
		return delFlag == null ? "" : delFlag.trim();
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag == null ? null : delFlag.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser == null ? "" : createUser.trim();
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public String getCreateUserName() {
		return createUserName == null ? "" : createUserName.trim();
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName == null ? null : createUserName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser == null ? "" : modifyUser.trim();
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser == null ? null : modifyUser.trim();
	}

	public String getModifyUserName() {
		return modifyUserName == null ? "" : modifyUserName.trim();
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
	}

	// 健康情况字段转对象
	public static void toObjectHealthInfo(ZjXmSalaryUserExtension paramObj) {
		ZjXmSalaryUserExtension healthInfo = new ZjXmSalaryUserExtension();
		healthInfo.setPhysicalType(paramObj.getPhysicalType());
		healthInfo.setHealthCondition(paramObj.getHealthCondition());
		healthInfo.setOccupationalDisease(paramObj.getOccupationalDisease());
		healthInfo.setHeight(paramObj.getHeight());
		healthInfo.setWeight(paramObj.getWeight());
		healthInfo.setBloodType(paramObj.getBloodType());
		paramObj.setHealthInfo(healthInfo);
	}

	// 健康情况对象转字段
	public static void toFieldHealthInfo(ZjXmSalaryUserExtension paramObj) {
		if (paramObj.getHealthInfo() != null) {
			paramObj.setPhysicalType(paramObj.getHealthInfo().getPhysicalType());
			paramObj.setHealthCondition(paramObj.getHealthInfo().getHealthCondition());
			paramObj.setOccupationalDisease(paramObj.getHealthInfo().getOccupationalDisease());
			paramObj.setHeight(paramObj.getHealthInfo().getHeight());
			paramObj.setWeight(paramObj.getHealthInfo().getWeight());
			paramObj.setBloodType(paramObj.getHealthInfo().getBloodType());
		}
	}

	// 合同情况字段转对象
	public static void toObjectContractInfo(ZjXmSalaryUserExtension paramObj) {
		ZjXmSalaryUserExtension contractInfo = new ZjXmSalaryUserExtension();
		contractInfo.setContractNo(paramObj.getContractNo());
		contractInfo.setSigningDate(paramObj.getSigningDate());
		contractInfo.setContractType(paramObj.getContractType());
		contractInfo.setContractStartDate(paramObj.getContractStartDate());
		contractInfo.setContractEndDate(paramObj.getContractEndDate());
		paramObj.setContractInfo(contractInfo);
	}

	// 合同情况对象转字段
	public static void toFieldContractInfo(ZjXmSalaryUserExtension paramObj) {
		if (paramObj.getContractInfo() != null) {
			paramObj.setContractNo(paramObj.getContractInfo().getContractNo());
			paramObj.setSigningDate(paramObj.getContractInfo().getSigningDate());
			paramObj.setContractType(paramObj.getContractInfo().getContractType());
			paramObj.setContractStartDate(paramObj.getContractInfo().getContractStartDate());
			paramObj.setContractEndDate(paramObj.getContractInfo().getContractEndDate());
		}
	}

	// 薪资情况字段转对象
	public static void toObjectSalaryInfo(ZjXmSalaryUserExtension paramObj) {
		ZjXmSalaryUserExtension salaryInfo = new ZjXmSalaryUserExtension();
		// salaryInfo.setPositionLevel(paramObj.getPositionLevel());
		// salaryInfo.setPositionSalary(paramObj.getPositionSalary());
		salaryInfo.setSalaryId(paramObj.getSalaryId());
		if (StrUtil.isNotEmpty(paramObj.getLevelSalaryId())) {
			salaryInfo.setLevelId(paramObj.getLevelSalaryId().substring(paramObj.getLevelSalaryId().indexOf(",") + 1));
		}
		salaryInfo.setLevelSalaryId(paramObj.getLevelSalaryId());
		salaryInfo.setAccountingType(paramObj.getAccountingType());
		salaryInfo.setSocialInsuranceArea(paramObj.getSocialInsuranceArea());
		salaryInfo.setProvidentFundArea(paramObj.getProvidentFundArea());
		salaryInfo.setExternalUnit(paramObj.getExternalUnit());
		salaryInfo.setInvolvedTopic(paramObj.getInvolvedTopic());
		// salaryInfo.setWageProjectName(paramObj.getWageProjectName());
		TreeNodeEntity wageProjectTree = new TreeNodeEntity();
		wageProjectTree.setValue(paramObj.getWageOfProjectId());
		wageProjectTree.setLabel(paramObj.getWageProjectName());
		salaryInfo.setWageProjectTree(Lists.newArrayList(wageProjectTree));
		paramObj.setSalaryInfo(salaryInfo);
	}

	// 薪资情况对象转字段
	public static void toFieldSalaryInfo(ZjXmSalaryUserExtension paramObj) {
		if (paramObj.getSalaryInfo() != null) {
			// paramObj.setPositionLevel(paramObj.getSalaryInfo().getPositionLevel());
			// paramObj.setPositionSalary(paramObj.getSalaryInfo().getPositionSalary());
			paramObj.setSalaryId(paramObj.getSalaryInfo().getSalaryId());
			if (StrUtil.isNotEmpty(paramObj.getSalaryInfo().getLevelSalaryId())) {
				paramObj.setLevelId(paramObj.getSalaryInfo().getLevelSalaryId()
						.substring(paramObj.getSalaryInfo().getLevelSalaryId().indexOf(",") + 1));
			}
			paramObj.setLevelSalaryId(paramObj.getSalaryInfo().getLevelSalaryId());
			paramObj.setAccountingType(paramObj.getSalaryInfo().getAccountingType());
			paramObj.setSocialInsuranceArea(paramObj.getSalaryInfo().getSocialInsuranceArea());
			paramObj.setProvidentFundArea(paramObj.getSalaryInfo().getProvidentFundArea());
			// paramObj.setWageProjectName(paramObj.getSalaryInfo().getWageProjectName());
			// paramObj.setWageOfProjectId(paramObj.getSalaryInfo().getWageOfProjectId());
			// paramObj.setWageProjectTree(paramObj.getSalaryInfo().getWageProjectTree());
			if (paramObj.getSalaryInfo().getWageProjectTree() != null
					&& paramObj.getSalaryInfo().getWageProjectTree().size() > 0) {
				paramObj.setWageOfProjectId(paramObj.getSalaryInfo().getWageProjectTree().get(0).getValue());
			}
			paramObj.setExternalUnit(paramObj.getSalaryInfo().getExternalUnit());
			paramObj.setInvolvedTopic(paramObj.getSalaryInfo().getInvolvedTopic());
		}
	}

}
