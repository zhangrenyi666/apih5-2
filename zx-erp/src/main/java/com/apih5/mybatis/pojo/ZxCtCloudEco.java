package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxCtCloudEco extends BasePojo {
	private String id;

	private String serialNumber;

	private String schemeNo;

	private String schemeName;

	private Date doFinishTime;

	private String schemeStatus;

	private String thirdUnit;

	private String packageNo;

	private String packageName;

	private BigDecimal scaledAmount;

	private String winningUnit;

	private String demandUnit;

	private String orgCertificate;

	private String remark;

	private Date editTime;

	private String winRemark;

	private String projectType;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	private List<ZxErpFile> importFileList;// 导入文件集合

	// 是否匹配
	private String isRela;

	public String getIsRela() {
		return isRela;
	}

	public void setIsRela(String isRela) {
		this.isRela = isRela;
	}

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getSerialNumber() {
		return serialNumber == null ? "" : serialNumber.trim();
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber == null ? null : serialNumber.trim();
	}

	public String getSchemeNo() {
		return schemeNo == null ? "" : schemeNo.trim();
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo == null ? null : schemeNo.trim();
	}

	public String getSchemeName() {
		return schemeName == null ? "" : schemeName.trim();
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName == null ? null : schemeName.trim();
	}

	public Date getDoFinishTime() {
		return doFinishTime;
	}

	public void setDoFinishTime(Date doFinishTime) {
		this.doFinishTime = doFinishTime;
	}

	public String getSchemeStatus() {
		return schemeStatus == null ? "" : schemeStatus.trim();
	}

	public void setSchemeStatus(String schemeStatus) {
		this.schemeStatus = schemeStatus == null ? null : schemeStatus.trim();
	}

	public String getThirdUnit() {
		return thirdUnit == null ? "" : thirdUnit.trim();
	}

	public void setThirdUnit(String thirdUnit) {
		this.thirdUnit = thirdUnit == null ? null : thirdUnit.trim();
	}

	public String getPackageNo() {
		return packageNo == null ? "" : packageNo.trim();
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo == null ? null : packageNo.trim();
	}

	public String getPackageName() {
		return packageName == null ? "" : packageName.trim();
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName == null ? null : packageName.trim();
	}

	public BigDecimal getScaledAmount() {
		return scaledAmount;
	}

	public void setScaledAmount(BigDecimal scaledAmount) {
		this.scaledAmount = scaledAmount;
	}

	public String getWinningUnit() {
		return winningUnit == null ? "" : winningUnit.trim();
	}

	public void setWinningUnit(String winningUnit) {
		this.winningUnit = winningUnit == null ? null : winningUnit.trim();
	}

	public String getDemandUnit() {
		return demandUnit == null ? "" : demandUnit.trim();
	}

	public void setDemandUnit(String demandUnit) {
		this.demandUnit = demandUnit == null ? null : demandUnit.trim();
	}

	public String getOrgCertificate() {
		return orgCertificate == null ? "" : orgCertificate.trim();
	}

	public void setOrgCertificate(String orgCertificate) {
		this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
	}

	public String getRemark() {
		return remark == null ? "" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getWinRemark() {
		return winRemark == null ? "" : winRemark.trim();
	}

	public void setWinRemark(String winRemark) {
		this.winRemark = winRemark == null ? null : winRemark.trim();
	}

	public String getProjectType() {
		return projectType == null ? "" : projectType.trim();
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType == null ? null : projectType.trim();
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

	public List<ZxErpFile> getImportFileList() {
		return importFileList;
	}

	public void setImportFileList(List<ZxErpFile> importFileList) {
		this.importFileList = importFileList;
	}

}
