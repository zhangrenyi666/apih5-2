package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgSaCoWorkLinkWork extends BasePojo {
    // 主键
    private String saCoWorkLinkWorkId;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 合同ID(contractID)
    private String ctContractId;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 分包清单ID(coWorkID)
    private String ccWorksId;

    // 分包清单编号
    private String coWorkNo;

    // 分包清单名称
    private String coWorkName;

    // 分包清单单位
    private String coUnit;

    // 是否为主项
    private String isMain;

    // 业主清单ID
    private String workID;

    // 业主清单编号
    private String workNo;

    // 业主清单名称
    private String workName;

    // 最后编辑时间
    private Date editTime;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 分包清单父节点id
    private String parentID;

    // 是否叶子节点
    private Integer isLeaf;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getSaCoWorkLinkWorkId() {
        return saCoWorkLinkWorkId == null ? "" : saCoWorkLinkWorkId.trim();
    }

    public void setSaCoWorkLinkWorkId(String saCoWorkLinkWorkId) {
        this.saCoWorkLinkWorkId = saCoWorkLinkWorkId == null ? null : saCoWorkLinkWorkId.trim();
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

    public String getCtContractId() {
        return ctContractId == null ? "" : ctContractId.trim();
    }

    public void setCtContractId(String ctContractId) {
        this.ctContractId = ctContractId == null ? null : ctContractId.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getCcWorksId() {
        return ccWorksId == null ? "" : ccWorksId.trim();
    }

    public void setCcWorksId(String ccWorksId) {
        this.ccWorksId = ccWorksId == null ? null : ccWorksId.trim();
    }

    public String getCoWorkNo() {
        return coWorkNo == null ? "" : coWorkNo.trim();
    }

    public void setCoWorkNo(String coWorkNo) {
        this.coWorkNo = coWorkNo == null ? null : coWorkNo.trim();
    }

    public String getCoWorkName() {
        return coWorkName == null ? "" : coWorkName.trim();
    }

    public void setCoWorkName(String coWorkName) {
        this.coWorkName = coWorkName == null ? null : coWorkName.trim();
    }

    public String getCoUnit() {
        return coUnit == null ? "" : coUnit.trim();
    }

    public void setCoUnit(String coUnit) {
        this.coUnit = coUnit == null ? null : coUnit.trim();
    }

    public String getIsMain() {
        return isMain == null ? "" : isMain.trim();
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain == null ? null : isMain.trim();
    }

    public String getWorkID() {
        return workID == null ? "" : workID.trim();
    }

    public void setWorkID(String workID) {
        this.workID = workID == null ? null : workID.trim();
    }

    public String getWorkNo() {
        return workNo == null ? "" : workNo.trim();
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo == null ? null : workNo.trim();
    }

    public String getWorkName() {
        return workName == null ? "" : workName.trim();
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
