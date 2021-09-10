package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxEqIecmOrg extends BasePojo {
    private String iecmOrgID;

    private String orgType;

    private String orgName;

    private String comOrgID;

    private String bsid;

    private String busiID;

    private String parentID;

    private String orgNO;

    private Integer orgStatus;

    private String description;

    private Date editTime;

    private String oldBsid;

    private Integer isLineOut;

    private String contractStatus;

    private String orders;

    private String areaName;

    private String projectType;

    private String orgCode;

    private String comID;

    private String comName;

    private String comOrders;

    private String batchNo;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getIecmOrgID() {
        return iecmOrgID == null ? "" : iecmOrgID.trim();
    }

    public void setIecmOrgID(String iecmOrgID) {
        this.iecmOrgID = iecmOrgID == null ? null : iecmOrgID.trim();
    }

    public String getOrgType() {
        return orgType == null ? "" : orgType.trim();
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getComOrgID() {
        return comOrgID == null ? "" : comOrgID.trim();
    }

    public void setComOrgID(String comOrgID) {
        this.comOrgID = comOrgID == null ? null : comOrgID.trim();
    }

    public String getBsid() {
        return bsid == null ? "" : bsid.trim();
    }

    public void setBsid(String bsid) {
        this.bsid = bsid == null ? null : bsid.trim();
    }

    public String getBusiID() {
        return busiID == null ? "" : busiID.trim();
    }

    public void setBusiID(String busiID) {
        this.busiID = busiID == null ? null : busiID.trim();
    }

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public String getOrgNO() {
        return orgNO == null ? "" : orgNO.trim();
    }

    public void setOrgNO(String orgNO) {
        this.orgNO = orgNO == null ? null : orgNO.trim();
    }

    public Integer getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(Integer orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getDescription() {
        return description == null ? "" : description.trim();
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getOldBsid() {
        return oldBsid == null ? "" : oldBsid.trim();
    }

    public void setOldBsid(String oldBsid) {
        this.oldBsid = oldBsid == null ? null : oldBsid.trim();
    }

    public Integer getIsLineOut() {
        return isLineOut;
    }

    public void setIsLineOut(Integer isLineOut) {
        this.isLineOut = isLineOut;
    }

    public String getContractStatus() {
        return contractStatus == null ? "" : contractStatus.trim();
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus == null ? null : contractStatus.trim();
    }

    public String getOrders() {
        return orders == null ? "" : orders.trim();
    }

    public void setOrders(String orders) {
        this.orders = orders == null ? null : orders.trim();
    }

    public String getAreaName() {
        return areaName == null ? "" : areaName.trim();
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getProjectType() {
        return projectType == null ? "" : projectType.trim();
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getOrgCode() {
        return orgCode == null ? "" : orgCode.trim();
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
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

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getBatchNo() {
        return batchNo == null ? "" : batchNo.trim();
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
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

