package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqEquipDepreciation extends BasePojo {
    private String id;

    private String orgID;

    private String orgName;

    private Date depreDate;

    private Date depreperiodDate;

    private String depreperiod;

    private String auditStatus;

    private String remark;

    private String comID;

    private String comName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxEqEquipDepreciationItem> itemList;

    private BigDecimal depreamout;

    private BigDecimal orginalValue;

    private BigDecimal leftValue;
    
    private String idFlag;
    
    private String orgIDSearch;


    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Date getDepreDate() {
        return depreDate;
    }

    public void setDepreDate(Date depreDate) {
        this.depreDate = depreDate;
    }

    public Date getDepreperiodDate() {
        return depreperiodDate;
    }

    public void setDepreperiodDate(Date depreperiodDate) {
        this.depreperiodDate = depreperiodDate;
    }

    public String getDepreperiod() {
        return depreperiod == null ? "" : depreperiod.trim();
    }

    public void setDepreperiod(String depreperiod) {
        this.depreperiod = depreperiod == null ? null : depreperiod.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
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

    public List<ZxEqEquipDepreciationItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ZxEqEquipDepreciationItem> itemList) {
        this.itemList = itemList;
    }

    public BigDecimal getDepreamout() {
        return depreamout;
    }

    public void setDepreamout(BigDecimal depreamout) {
        this.depreamout = depreamout;
    }

    public BigDecimal getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(BigDecimal orginalValue) {
        this.orginalValue = orginalValue;
    }

    public BigDecimal getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(BigDecimal leftValue) {
        this.leftValue = leftValue;
    }

	public String getIdFlag() {
		return idFlag;
	}

	public void setIdFlag(String idFlag) {
		this.idFlag = idFlag;
	}

	public String getOrgIDSearch() {
		return orgIDSearch;
	}

	public void setOrgIDSearch(String orgIDSearch) {
		this.orgIDSearch = orgIDSearch;
	}
    
}

