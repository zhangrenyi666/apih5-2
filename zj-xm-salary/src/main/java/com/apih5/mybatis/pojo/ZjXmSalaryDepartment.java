package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryDepartment extends BasePojo {
	private String departmentId;

	private String departmentName;

	private String projectType;

	private BigDecimal turnover;

	private BigDecimal contractAmount;

	private BigDecimal outputValue;

	private BigDecimal engineeringCost;

	private BigDecimal measurementAmount;

	private Integer contractPeriod;

	private Date actualStartDate;

	private Date actualEndDate;

	private String regionCode;

	private String regionName;

	private String projectStaffType;

	private String projectPhase;

	private String managerKey;

	private String managerName;

	private String actualStatus;

	private String actualStatusName;

	private String actualType;

	private String actualTypeName;

	private String deptType;

	private String numberUnit;

	private Integer singleValue;

	private Integer leftValue;

	private Integer rightValue;

	private String company;

	private String generalContracting;

	private String projectCompany;

	private String departmentParentId;

	private String departmentPath;

	private String departmentPathName;

	private String departmentFlag;

	private String departmentFlagName;

	private String companyId;

	private String companyName;

	private String projectId;

	private String projectName;

	private String otherId;

	private String remarks;

	private Integer sort;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	public String getDepartmentId() {
		return departmentId == null ? "" : departmentId.trim();
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId == null ? null : departmentId.trim();
	}

	public String getDepartmentName() {
		return departmentName == null ? "" : departmentName.trim();
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName == null ? null : departmentName.trim();
	}

	public String getProjectType() {
		return projectType == null ? "" : projectType.trim();
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType == null ? null : projectType.trim();
	}

	public BigDecimal getTurnover() {
		return turnover;
	}

	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getOutputValue() {
		return outputValue;
	}

	public void setOutputValue(BigDecimal outputValue) {
		this.outputValue = outputValue;
	}

	public BigDecimal getEngineeringCost() {
		return engineeringCost;
	}

	public void setEngineeringCost(BigDecimal engineeringCost) {
		this.engineeringCost = engineeringCost;
	}

	public BigDecimal getMeasurementAmount() {
		return measurementAmount;
	}

	public void setMeasurementAmount(BigDecimal measurementAmount) {
		this.measurementAmount = measurementAmount;
	}

	public Integer getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(Integer contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getRegionCode() {
		return regionCode == null ? "" : regionCode.trim();
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode == null ? null : regionCode.trim();
	}

	public String getRegionName() {
		return regionName == null ? "" : regionName.trim();
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName == null ? null : regionName.trim();
	}

	public String getProjectStaffType() {
		return projectStaffType == null ? "" : projectStaffType.trim();
	}

	public void setProjectStaffType(String projectStaffType) {
		this.projectStaffType = projectStaffType == null ? null : projectStaffType.trim();
	}

	public String getProjectPhase() {
		return projectPhase == null ? "" : projectPhase.trim();
	}

	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase == null ? null : projectPhase.trim();
	}

	public String getManagerKey() {
		return managerKey == null ? "" : managerKey.trim();
	}

	public void setManagerKey(String managerKey) {
		this.managerKey = managerKey == null ? null : managerKey.trim();
	}

	public String getManagerName() {
		return managerName == null ? "" : managerName.trim();
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName == null ? null : managerName.trim();
	}

	public String getActualStatus() {
		return actualStatus == null ? "" : actualStatus.trim();
	}

	public void setActualStatus(String actualStatus) {
		this.actualStatus = actualStatus == null ? null : actualStatus.trim();
	}

	public String getActualStatusName() {
		return actualStatusName == null ? "" : actualStatusName.trim();
	}

	public void setActualStatusName(String actualStatusName) {
		this.actualStatusName = actualStatusName == null ? null : actualStatusName.trim();
	}

	public String getActualType() {
		return actualType == null ? "" : actualType.trim();
	}

	public void setActualType(String actualType) {
		this.actualType = actualType == null ? null : actualType.trim();
	}

	public String getActualTypeName() {
		return actualTypeName == null ? "" : actualTypeName.trim();
	}

	public void setActualTypeName(String actualTypeName) {
		this.actualTypeName = actualTypeName == null ? null : actualTypeName.trim();
	}

	public String getDeptType() {
		return deptType == null ? "" : deptType.trim();
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType == null ? null : deptType.trim();
	}

	public String getNumberUnit() {
		return numberUnit == null ? "" : numberUnit.trim();
	}

	public void setNumberUnit(String numberUnit) {
		this.numberUnit = numberUnit == null ? null : numberUnit.trim();
	}

	public Integer getSingleValue() {
		return singleValue;
	}

	public void setSingleValue(Integer singleValue) {
		this.singleValue = singleValue;
	}

	public Integer getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(Integer leftValue) {
		this.leftValue = leftValue;
	}

	public Integer getRightValue() {
		return rightValue;
	}

	public void setRightValue(Integer rightValue) {
		this.rightValue = rightValue;
	}

	public String getCompany() {
		return company == null ? "" : company.trim();
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getGeneralContracting() {
		return generalContracting == null ? "" : generalContracting.trim();
	}

	public void setGeneralContracting(String generalContracting) {
		this.generalContracting = generalContracting == null ? null : generalContracting.trim();
	}

	public String getProjectCompany() {
		return projectCompany == null ? "" : projectCompany.trim();
	}

	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany == null ? null : projectCompany.trim();
	}

	public String getDepartmentParentId() {
		return departmentParentId == null ? "" : departmentParentId.trim();
	}

	public void setDepartmentParentId(String departmentParentId) {
		this.departmentParentId = departmentParentId == null ? null : departmentParentId.trim();
	}

	public String getDepartmentPath() {
		return departmentPath == null ? "" : departmentPath.trim();
	}

	public void setDepartmentPath(String departmentPath) {
		this.departmentPath = departmentPath == null ? null : departmentPath.trim();
	}

	public String getDepartmentPathName() {
		return departmentPathName == null ? "" : departmentPathName.trim();
	}

	public void setDepartmentPathName(String departmentPathName) {
		this.departmentPathName = departmentPathName == null ? null : departmentPathName.trim();
	}

	public String getDepartmentFlag() {
		return departmentFlag == null ? "" : departmentFlag.trim();
	}

	public void setDepartmentFlag(String departmentFlag) {
		this.departmentFlag = departmentFlag == null ? null : departmentFlag.trim();
	}

	public String getDepartmentFlagName() {
		return departmentFlagName == null ? "" : departmentFlagName.trim();
	}

	public void setDepartmentFlagName(String departmentFlagName) {
		this.departmentFlagName = departmentFlagName == null ? null : departmentFlagName.trim();
	}

	public String getCompanyId() {
		return companyId == null ? "" : companyId.trim();
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId == null ? null : companyId.trim();
	}

	public String getCompanyName() {
		return companyName == null ? "" : companyName.trim();
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.trim();
	}

	public String getProjectId() {
		return projectId == null ? "" : projectId.trim();
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public String getProjectName() {
		return projectName == null ? "" : projectName.trim();
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName == null ? null : projectName.trim();
	}

	public String getOtherId() {
		return otherId == null ? "" : otherId.trim();
	}

	public void setOtherId(String otherId) {
		this.otherId = otherId == null ? null : otherId.trim();
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

}
